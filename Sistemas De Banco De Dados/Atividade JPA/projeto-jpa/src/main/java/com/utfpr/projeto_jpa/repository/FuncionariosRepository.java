package com.utfpr.projeto_jpa.repository;

import com.utfpr.projeto_jpa.entity.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FuncionariosRepository extends JpaRepository<Funcionarios, Integer> {

    @Procedure(procedureName = "aumentar_salario_funcionarios")
    void aumentarSalarioFuncionarios(@Param("percentual") Integer percentual);

    List<Funcionarios> findByNomeAndQtdDependentes(String nome, Integer qtdDependentes);

    @Query("SELECT f FROM Funcionarios f WHERE f.departamento.codDepartamento = :codDepartamento")
    List<Funcionarios> listarPorDepartamento(@Param("codDepartamento") Integer codDepartamento);

    @Query("SELECT f FROM Funcionarios f WHERE f.departamento.codDepartamento = :codDepartamento AND f.qtdDependentes = 0")
    List<Funcionarios> listarPorDepartamentoSemDependentes(@Param("codDepartamento") Integer codDepartamento);

    @Modifying
    @Query(value = "UPDATE Funcionarios SET cod_departamento = :codDepartamentoDestino WHERE cod_departamento = :codDepartamentoOrigem", nativeQuery = true)
    int trocarDepartamentoDosFuncionarios(
            @Param("codDepartamentoOrigem") Integer codDepartamentoOrigem,
            @Param("codDepartamentoDestino") Integer codDepartamentoDestino
    );

    @Modifying
    @Query("DELETE FROM Funcionarios f WHERE f.departamento.codDepartamento = :codDepartamento")
    int excluirPorDepartamento(@Param("codDepartamento") Integer codDepartamento);

    Funcionarios findFirstByOrderBySalarioDesc();

    List<Funcionarios> findTop3ByOrderBySalarioDesc();

    @Query("SELECT f FROM Funcionarios f WHERE f.qtdDependentes = 0 ORDER BY f.nome ASC")
    List<Funcionarios> listarSemDependentesOrdenadoPorNome();

    @Query("SELECT f FROM Funcionarios f WHERE f.salario > :salario")
    List<Funcionarios> findBySalarioGreaterThan(@Param("salario") BigDecimal salario);

    @Query(value = "SELECT * FROM Funcionarios WHERE salario > :salario", nativeQuery = true)
    List<Funcionarios> listarPorSalarioMaiorQueNativeQuery(@Param("salario") BigDecimal salario);

    @Query(name = "Funcionarios.listarPorQtdDependentes")
    List<Funcionarios> listarPorQtdDependentesNamedQuery(@Param("qtdDependentes") Integer qtdDependentes);

    @Query(name = "Funcionarios.listarPorNomeContendo", nativeQuery = true)
    List<Funcionarios> listarPorNomeContendoNamedNativeQuery(@Param("nome") String nome);
}
