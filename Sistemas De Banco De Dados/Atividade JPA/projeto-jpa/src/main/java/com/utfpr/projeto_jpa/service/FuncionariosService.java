package com.utfpr.projeto_jpa.service;

import com.utfpr.projeto_jpa.entity.Funcionarios;
import com.utfpr.projeto_jpa.repository.FuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FuncionariosService {
    @Autowired
    private FuncionariosRepository repository;

    public List<Funcionarios> listarTodosFuncionarios() {
        return repository.findAll();
    }

    public List<Funcionarios> listarPorNomeEQuantidadeDependentes(String nome, Integer qtdDependentes) {
        return repository.findByNomeAndQtdDependentes(nome, qtdDependentes);
    }

    public List<Funcionarios> listarPorDepartamento(Integer codDepartamento) {
        return repository.listarPorDepartamento(codDepartamento);
    }

    public Funcionarios listarFuncionarioComMaiorSalario() {
        return repository.findFirstByOrderBySalarioDesc();
    }

    public List<Funcionarios> listarTresFuncionariosComMaioresSalarios() {
        return repository.findTop3ByOrderBySalarioDesc();
    }

    public List<Funcionarios> listarFuncionariosSemDependentesOrdenadosPorNome() {
        return repository.listarSemDependentesOrdenadoPorNome();
    }

    public List<Funcionarios> listarFuncionariosComSalarioMaiorQue(BigDecimal salario) {
        return repository.findBySalarioGreaterThan(salario);
    }

    public List<Funcionarios> listarFuncionariosComSalarioMaiorQueNativeQuery(BigDecimal salario) {
        return repository.listarPorSalarioMaiorQueNativeQuery(salario);
    }

    public List<Funcionarios> listarPorQuantidadeDependentesNamedQuery(Integer qtdDependentes) {
        return repository.listarPorQtdDependentesNamedQuery(qtdDependentes);
    }

    public List<Funcionarios> listarPorNomeContendoNamedNativeQuery(String nome) {
        return repository.listarPorNomeContendoNamedNativeQuery(nome);
    }
}
