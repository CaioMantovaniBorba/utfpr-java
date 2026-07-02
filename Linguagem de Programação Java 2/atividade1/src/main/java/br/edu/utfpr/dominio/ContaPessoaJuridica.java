package br.edu.utfpr.dominio;

public record ContaPessoaJuridica(
        @br.edu.utfpr.anotacoes.NaoNulo
        @br.edu.utfpr.anotacoes.Tamanho(min = 4, max = 4)
        String agencia,
        @br.edu.utfpr.anotacoes.NaoNulo
        String numero,
        @br.edu.utfpr.anotacoes.NaoNulo
        String razaoSocial,
        @br.edu.utfpr.anotacoes.NaoNulo
        @br.edu.utfpr.anotacoes.Tamanho(min = 14, max = 14)
        String cnpj,
        @br.edu.utfpr.anotacoes.NaoNulo(mensagem = "o capital social e obrigatorio")
        @br.edu.utfpr.anotacoes.Positivo
        java.math.BigDecimal capitalSocial
) implements ContaBancaria {
    // TODO revisar e implementar corretamente conforme requisitos
}
