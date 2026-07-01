package br.edu.utfpr.anotacoes;

// TODO requisito 7
public @interface NaoNulo {
    String mensagem() default "nao pode ser nulo";
}
