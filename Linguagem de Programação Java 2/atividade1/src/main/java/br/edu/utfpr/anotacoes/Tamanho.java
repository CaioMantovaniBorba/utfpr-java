package br.edu.utfpr.anotacoes;

// TODO requisito 7
public @interface Tamanho {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String mensagem() default "tamanho fora do intervalo permitido";
}
