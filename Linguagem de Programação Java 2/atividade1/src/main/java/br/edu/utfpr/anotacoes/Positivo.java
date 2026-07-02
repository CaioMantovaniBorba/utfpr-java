package br.edu.utfpr.anotacoes;

// TODO requisito 7
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.RECORD_COMPONENT})
public @interface Positivo {
    String mensagem() default "deve ser maior que zero";
}
