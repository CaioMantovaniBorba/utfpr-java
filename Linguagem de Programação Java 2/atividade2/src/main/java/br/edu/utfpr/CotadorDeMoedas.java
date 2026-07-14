package br.edu.utfpr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class CotadorDeMoedas {

    public Path verificarEntrada() {
        // TODO
        return Path.of("entrada", "moedas.txt");
    }

    public Path verificarSaida() {
        // TODO
        final Path saida = Path.of("saida", "cotacoes.csv");

        try {
            Files.createDirectories(saida.getParent());
        } catch (IOException e) {
            System.err.println("Erro ao criar o diretorio de saida: " + saida.getParent());
            System.exit(1);
        }

        return saida;
    }

    public List<String> lerArquivoDeMoedas(Path entrada) {
        // TODO
        if (!Files.exists(entrada)) {
            return List.of();
        }

        try {
            return Files.readAllLines(entrada)
                    .stream()
                    .map(linha -> linha.trim().toUpperCase(Locale.ROOT))
                    .filter(moeda -> moeda.matches("[A-Z]{3}"))
                    .toList();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de moedas: " + entrada);
            System.exit(1);
            return List.of();
        }
    }

    public void cotarERegistrar(Path saida, List<String> moedas, ClienteCambio cliente) {

        // TODO
        if (moedas.isEmpty()) {
            return;
        }

        final List<CompletableFuture<Optional<Cotacao>>> consultas = moedas.stream()
                .map(moeda -> cliente.consultar(moeda)
                        .exceptionally(ex -> {
                            System.err.println("Erro ao consultar a moeda " + moeda + ": " + ex.getMessage());
                            return Optional.empty();
                        }))
                .toList();

        try {
            CompletableFuture.allOf(consultas.toArray(CompletableFuture[]::new)).join();

            final List<Cotacao> cotacoes = consultas.stream()
                    .map(CompletableFuture::join)
                    .flatMap(Optional::stream)
                    .toList();

            gravarEmCSV(saida, cotacoes);
        } catch (CompletionException e) {
            System.err.println("Erro ao cotar moedas: " + e.getMessage());
            System.exit(1);
        }
    }

    private void gravarEmCSV(Path saida, List<Cotacao> cotacoes) {

        // TODO
        final List<String> linhas = new ArrayList<>();
        linhas.add("moeda,valor,coletadoEm");
        linhas.addAll(cotacoes.stream().map(Cotacao::paraCsv).toList());

        try {
            final Path diretorio = saida.getParent();
            if (diretorio != null) {
                Files.createDirectories(diretorio);
            }

            Files.writeString(saida, String.join("\n", linhas) + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo CSV: " + saida);
            System.exit(1);
        }
    }
}
