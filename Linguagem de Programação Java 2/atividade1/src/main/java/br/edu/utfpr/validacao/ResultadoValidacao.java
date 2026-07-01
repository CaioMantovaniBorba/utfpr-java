package br.edu.utfpr.validacao;

import java.util.List;

public interface ResultadoValidacao {

    class Valido() implements ResultadoValidacao {
        // TODO revisar e implementar corretamente conforme requisitos
    }

    class Invalido() implements ResultadoValidacao {
        // TODO revisar e implementar corretamente conforme requisitos
    }

    static ResultadoValidacao de(List<Violacao> violacoes) {
        return violacoes.isEmpty() ? new Valido() : new Invalido(violacoes);
    }
}
