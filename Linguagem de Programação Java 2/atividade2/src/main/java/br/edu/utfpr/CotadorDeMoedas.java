package br.edu.utfpr;

import java.nio.file.Path;
import java.util.List;

public class CotadorDeMoedas {

    public Path verificarEntrada() {
        // TODO
        return Path.of("");
    }

    public Path verificarSaida() {
        // TODO
        return Path.of("");
    }

    public List<String> lerArquivoDeMoedas(Path entrada) {
        // TODO
        return List.of();
    }

    public void cotarERegistrar(Path saida, List<String> moedas, ClienteCambio cliente) {

        // TODO

        gravarEmCSV(saida, List.of());
    }

    private void gravarEmCSV(Path saida, List<Cotacao> cotacoes) {

        // TODO
    }
}
