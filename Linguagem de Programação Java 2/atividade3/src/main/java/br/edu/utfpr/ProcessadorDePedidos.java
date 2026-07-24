package br.edu.utfpr;

import br.edu.utfpr.dominio.CotacaoFrete;
import br.edu.utfpr.dominio.Estoque;
import br.edu.utfpr.dominio.Pedido;
import br.edu.utfpr.dominio.PedidoAprovado;
import br.edu.utfpr.dominio.PedidoRejeitado;
import br.edu.utfpr.dominio.Preco;
import br.edu.utfpr.dominio.Relatorio;
import br.edu.utfpr.dominio.ResultadoPedido;
import br.edu.utfpr.utilidades.LeitorDePedidos;
import br.edu.utfpr.utilidades.ServicosExternos;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;

public final class ProcessadorDePedidos {

    public ResultadoPedido processarPedido(Pedido pedido) {
        try (final var scope = StructuredTaskScope.open(Joiner.awaitAllSuccessfulOrThrow())) {
            final var estoque = scope.fork(() -> ServicosExternos.consultarEstoque(pedido.produto(), pedido.identificador()));
            final var preco = scope.fork(() -> ServicosExternos.consultarPreco(pedido.produto(), pedido.identificador()));

            scope.join();

            final Estoque estoqueEncontrado = estoque.get();
            if (estoqueEncontrado.quantidadeDisponivel() < pedido.quantidade()) {
                return new PedidoRejeitado(
                        pedido.identificador(),
                        "estoque insuficiente para o pedido " + pedido.identificador()
                );
            }

            final Preco precoEncontrado = preco.get();
            final CotacaoFrete frete = cotarFrete(pedido.produto());
            final BigDecimal valorProdutos = precoEncontrado.valorUnitario().multiply(BigDecimal.valueOf(pedido.quantidade()));
            final BigDecimal valorTotal = valorProdutos.add(frete.valor());

            return new PedidoAprovado(pedido.identificador(), valorTotal, frete);
        } catch (final StructuredTaskScope.FailedException excecao) {
            return new PedidoRejeitado(
                    pedido.identificador(),
                    "erro ao processar pedido " + pedido.identificador() + ": " + excecao.getCause().getMessage()
            );
        } catch (final InterruptedException excecao) {
            Thread.currentThread().interrupt();
            return new PedidoRejeitado(
                    pedido.identificador(),
                    "processamento interrompido para o pedido " + pedido.identificador()
            );
        }
    }

    private CotacaoFrete cotarFrete(String produto) throws InterruptedException {
        try (final var scope = StructuredTaskScope.open(Joiner.<CotacaoFrete>anySuccessfulResultOrThrow())) {
            scope.fork(() -> ServicosExternos.cotarFreteTransportadoraUm(produto));
            scope.fork(() -> ServicosExternos.cotarFreteTransportadoraDois(produto));

            return scope.join();
        }
    }

    public Relatorio processarArquivo(Path arquivoEntrada) {
        final List<Pedido> pedidos = LeitorDePedidos.ler(arquivoEntrada);

        try (final var scope = StructuredTaskScope.open(Joiner.<ResultadoPedido>allSuccessfulOrThrow())) {
            for (final Pedido pedido : pedidos) {
                scope.fork(() -> processarPedido(pedido));
            }

            final List<ResultadoPedido> resultados = scope.join()
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();
            final List<PedidoAprovado> aprovados = new ArrayList<>();
            final List<PedidoRejeitado> rejeitados = new ArrayList<>();

            for (final ResultadoPedido resultado : resultados) {
                if (resultado instanceof PedidoAprovado aprovado) {
                    aprovados.add(aprovado);
                } else if (resultado instanceof PedidoRejeitado rejeitado) {
                    rejeitados.add(rejeitado);
                }
            }

            return new Relatorio(aprovados, rejeitados);
        } catch (final InterruptedException excecao) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("processamento do arquivo interrompido", excecao);
        }
    }
}
