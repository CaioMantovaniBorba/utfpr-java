package br.edu.utfpr;

import br.edu.utfpr.model.FormaPagamento;
import br.edu.utfpr.model.RankingVendedor;
import br.edu.utfpr.model.ResumoVendas;
import br.edu.utfpr.model.Venda;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;

public class LeitorDeVendas {

    private final List<Venda> vendas;

    public LeitorDeVendas(String salesFile) {

        final var stream = getClass().getClassLoader().getResourceAsStream(salesFile);

        if (stream == null) {
            throw new IllegalStateException("Arquivo não encontrado");
        }

        final var builder = new CsvToBeanBuilder<VendaCsv>(new InputStreamReader(stream, StandardCharsets.UTF_8));

        vendas = builder
                .withType(VendaCsv.class)
                .withSeparator(';')
                .build()
                .parse()
                .stream()
                .map(VendaCsv::toVenda)
                .toList();
    }

    public List<Venda> vendasConcluidasAcimaDoValorNaRegiao(BigDecimal valorMinimo, String regiao) {
        // TODO apenas operações funcionais com streams
        return List.of();
    }

    public Optional<Venda> vendaDeMaiorValorNaRegiao(String regiao) {
        // TODO apenas operações funcionais com streams
        return Optional.empty();
    }

    public List<Venda> topNVendasPorValor(int n) {
        // TODO utilizar PriorityQueue
        return List.of();
    }

    public List<RankingVendedor> top3VendedoresPorFaturamento() {
        // TODO apenas operações funcionais com streams
        return List.of();
    }

    public Map<String, BigDecimal> valorTotalDeVendasConcluidasPorRegiao() {
        // TODO apenas operações funcionais com streams
        return Map.of();
    }

    public ResumoVendas resumoDeVendasConcluidas() {
        // TODO apenas operações funcionais com streams, usar collector customizado, use a classe AcumuladorDeResumo
        return new ResumoVendas(BigDecimal.ZERO, BigDecimal.ZERO, 0);
    }

    public List<BigDecimal> faturamentoAcumuladoPorVendedorEMes(String vendedor, YearMonth mes) {
        // TODO apenas operações funcionais com streams, use Gatherers
        return List.of();
    }

    public FormaPagamento formaPagamentoComMaisCancelamentos() {
        // TODO apenas operações funcionais com streams, usar groupingBy + counting
        return null;
    }

    public long diasEntrePrimeiraEUltimaVendaCancelada() {
        // TODO apenas operações funcionais com streams, usar Collectors.teeing com minBy e maxBy
        return 0;
    }

    public Map<Integer, Map<FormaPagamento, Long>> quantidadeDeVendasConcluidasPorFormaDePagamentoAgrupadasPorAno() {
        // TODO apenas operações funcionais com streams, usar groupingBy aninhado
        return Map.of();
    }

    private static final class AcumuladorDeResumo {

        private BigDecimal total = BigDecimal.ZERO;
        private long quantidade = 0;

        private void somar(BigDecimal valor) {
            total = total.add(valor);
            quantidade++;
        }

        private AcumuladorDeResumo combinar(AcumuladorDeResumo outro) {
            total = total.add(outro.total);
            quantidade += outro.quantidade;
            return this;
        }

        private ResumoVendas finalizar() {
            final var media = quantidade == 0
                    ? BigDecimal.ZERO
                    : total.divide(BigDecimal.valueOf(quantidade), 2, RoundingMode.HALF_UP);
            return new ResumoVendas(total, media, quantidade);
        }
    }
}
