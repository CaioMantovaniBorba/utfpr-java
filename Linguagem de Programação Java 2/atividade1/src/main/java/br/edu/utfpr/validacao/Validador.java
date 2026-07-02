package br.edu.utfpr.validacao;

import br.edu.utfpr.dominio.ContaBancaria;
import br.edu.utfpr.dominio.ContaPessoaFisica;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Validador {

    private static final int IDADE_MINIMA = 18;
    private static final BigDecimal CAPITAL_SOCIAL_MINIMO = new BigDecimal("10000");

    public ResultadoValidacao validar(ContaBancaria conta) {

        final List<Violacao> violacoes = new ArrayList<>();

        // TODO ver requisito 1, implementar aqui
        if (conta == null) {
            violacoes.add(new Violacao("(objeto)", "O objeto a validar e nulo"));
            return ResultadoValidacao.de(violacoes);
        }

        violacoes.addAll(validarCampos(conta));
        violacoes.addAll(regrasDeNegocio(conta));

        return ResultadoValidacao.de(violacoes);
    }

    public List<Violacao> validarCampos(Object objeto) {

        final List<Violacao> violacoes = new ArrayList<>();

        // TODO ver requisito 2, implementar aqui
        final Field[] campos = objeto.getClass().getDeclaredFields();
        for (final Field campo : campos) {
            try {
                campo.setAccessible(true);
                final Object valor = campo.get(objeto);

                verificarNaoNulo(campo, valor, violacoes);
                verificarTamanho(campo, valor, violacoes);
                verificarPositivo(campo, valor, violacoes);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("nao foi possivel acessar o campo " + campo.getName(), e);
            }
        }

        return violacoes;
    }

    private void verificarNaoNulo(Field campo, Object valor, List<Violacao> acc) {
        // TODO implementar regra de negocio de nao nulo
        final br.edu.utfpr.anotacoes.NaoNulo naoNulo =
                campo.getAnnotation(br.edu.utfpr.anotacoes.NaoNulo.class);

        if (naoNulo != null && valor == null) {
            acc.add(new Violacao(campo.getName(), naoNulo.mensagem()));
        }
    }

    private void verificarTamanho(Field campo, Object valor, List<Violacao> acc) {
        // TODO implementar regra de negocio de tamanho
        final br.edu.utfpr.anotacoes.Tamanho tamanho =
                campo.getAnnotation(br.edu.utfpr.anotacoes.Tamanho.class);

        if (tamanho != null && valor instanceof String texto
                && (texto.length() < tamanho.min() || texto.length() > tamanho.max())) {
            acc.add(new Violacao(campo.getName(), tamanho.mensagem()));
        }
    }

    private void verificarPositivo(Field campo, Object valor, List<Violacao> acc) {
        // TODO implementar regra de negocio de positivo
        final br.edu.utfpr.anotacoes.Positivo positivo =
                campo.getAnnotation(br.edu.utfpr.anotacoes.Positivo.class);

        final boolean invalido = switch (valor) {
            case null -> false;
            case Integer numero -> numero <= 0;
            case Double numero -> numero <= 0;
            case Long numero -> numero <= 0;
            case BigDecimal numero -> numero.compareTo(BigDecimal.ZERO) <= 0;
            default -> false;
        };

        if (positivo != null && invalido) {
            acc.add(new Violacao(campo.getName(), positivo.mensagem()));
        }
    }

    private List<Violacao> regrasDeNegocio(ContaBancaria conta) {
        // TODO implementar as regras de negocio por tipo
        return switch (conta) {
            case ContaPessoaFisica pf -> validarMaioridade(pf);
            case br.edu.utfpr.dominio.ContaPessoaJuridica(_, _, _, _, BigDecimal capital) ->
                    validarCapitalSocial(capital);
        };
    }

    private List<Violacao> validarMaioridade(ContaPessoaFisica pf) {
        // TODO implementar regra de negocio de maioridade
        final java.time.LocalDate dataNascimento = pf.dataNascimento();
        if (dataNascimento == null) {
            return List.of();
        }

        final int idade = java.time.Period.between(dataNascimento, java.time.LocalDate.now()).getYears();
        if (idade < IDADE_MINIMA) {
            return List.of(new Violacao(Violacao.DATA_NASCIMENTO_CAMPO, Violacao.DATA_NASCIMENTO_MENSAGEM));
        }

        return List.of();
    }

    private List<Violacao> validarCapitalSocial(BigDecimal capital) {
        // TODO implementar regra de negocio de capital social
        if (capital == null) {
            return List.of();
        }

        if (capital.compareTo(CAPITAL_SOCIAL_MINIMO) < 0) {
            return List.of(new Violacao(Violacao.CAPITAL_SOCIAL_CAMPO, Violacao.CAPITAL_SOCIAL_MENSAGEM));
        }

        return List.of();
    }
}
