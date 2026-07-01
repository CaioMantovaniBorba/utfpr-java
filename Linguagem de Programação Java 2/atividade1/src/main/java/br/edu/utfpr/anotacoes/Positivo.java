package br.edu.utfpr.anotacoes;

// TODO requisito 7
public @interface Positivo {
    String mensagem() default "deve ser maior que zero";
}
