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

        return ResultadoValidacao.de(violacoes);
    }

    public List<Violacao> validarCampos(Object objeto) {

        final List<Violacao> violacoes = new ArrayList<>();

        // TODO ver requisito 2, implementar aqui

        return violacoes;
    }

    private void verificarNaoNulo(Field campo, Object valor, List<Violacao> acc) {
        // TODO implementar regra de negocio de nao nulo
    }

    private void verificarTamanho(Field campo, Object valor, List<Violacao> acc) {
        // TODO implementar regra de negocio de tamanho
    }

    private void verificarPositivo(Field campo, Object valor, List<Violacao> acc) {
        // TODO implementar regra de negocio de positivo
    }

    private List<Violacao> regrasDeNegocio(ContaBancaria conta) {
        // TODO implementar as regras de negocio por tipo
        return List.of();
    }

    private List<Violacao> validarMaioridade(ContaPessoaFisica pf) {
        // TODO implementar regra de negocio de maioridade
        return List.of();
    }

    private List<Violacao> validarCapitalSocial(BigDecimal capital) {
        // TODO implementar regra de negocio de capital social
        return List.of();
    }
}
