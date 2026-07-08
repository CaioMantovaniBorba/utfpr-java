package br.edu.utfpr;

import java.net.http.HttpClient;
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

        return CompletableFuture.completedFuture(Optional.empty());
    }
}