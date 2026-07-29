package com.utfpr.projeto_jpa.repository;

import com.utfpr.projeto_jpa.entity.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FuncionariosRepository extends JpaRepository<Funcionarios, Integer> {

    List<Funcionarios> findByNomeAndQtdDependentes(String nome, Integer qtdDependentes);

    @Query("SELECT f FROM Funcionarios f WHERE f.departamento.codDepartamento = :codDepartamento")
    List<Funcionarios> listarPorDepartamento(@Param("codDepartamento") Integer codDepartamento);

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
