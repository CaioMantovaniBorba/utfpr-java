package br.edu.utfpr;

import br.edu.utfpr.dominio.CotacaoFrete;
import br.edu.utfpr.dominio.Pedido;
import br.edu.utfpr.dominio.Relatorio;
import br.edu.utfpr.dominio.ResultadoPedido;

import java.nio.file.Path;

public final class ProcessadorDePedidos {

    public ResultadoPedido processarPedido(Pedido pedido) {
        // TODO ver requisitos
    }

    private CotacaoFrete cotarFrete(String produto) throws InterruptedException {
        // TODO ver requisitos
    }

    public Relatorio processarArquivo(Path arquivoEntrada) {
        // TODO ver requisitos
    }
}
