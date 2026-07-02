package br.edu.utfpr.dominio;

public record ContaPessoaFisica(
        @br.edu.utfpr.anotacoes.NaoNulo
        @br.edu.utfpr.anotacoes.Tamanho(min = 4, max = 4)
        String agencia,
        @br.edu.utfpr.anotacoes.NaoNulo
        String numero,
        @br.edu.utfpr.anotacoes.NaoNulo
        String titular,
        @br.edu.utfpr.anotacoes.NaoNulo
        @br.edu.utfpr.anotacoes.Tamanho(min = 11, max = 11)
        String cpf,
        @br.edu.utfpr.anotacoes.NaoNulo
        String email,
        @br.edu.utfpr.anotacoes.NaoNulo(mensagem = "a data de nascimento e obrigatoria")
        java.time.LocalDate dataNascimento
) implements ContaBancaria {
    // TODO revisar e implementar corretamente conforme requisitos
}
