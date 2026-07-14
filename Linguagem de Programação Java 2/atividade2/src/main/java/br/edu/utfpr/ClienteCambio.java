package br.edu.utfpr;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ClienteCambio {

    private static final String BASE_URL = "https://api.frankfurter.dev/v1/latest?base=USD&symbols=";

    private final HttpClient client;

    public ClienteCambio(HttpClient client) {
        this.client = client;
    }

    public CompletableFuture<Optional<Cotacao>> consultar(String moeda) {

        // TODO implementar aqui a chamada para a API
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + moeda))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .handle((response, ex) -> {
                    if (ex != null) {
                        System.err.println("Erro ao consultar a moeda " + moeda + ": " + ex.getMessage());
                        return Optional.<Cotacao>empty();
                    }

                    if (response.statusCode() < 200 || response.statusCode() >= 300) {
                        System.err.println("Erro ao consultar a moeda " + moeda + ": status HTTP " + response.statusCode());
                        return Optional.<Cotacao>empty();
                    }

                    final Optional<Double> taxa = JsonParser.extrairTaxa(response.body(), moeda);
                    if (taxa.isEmpty()) {
                        System.err.println("Erro ao consultar a moeda " + moeda + ": resposta invalida");
                        return Optional.<Cotacao>empty();
                    }

                    return Optional.of(new Cotacao(moeda, taxa.get(), LocalDateTime.now()));
                });
    }
}
