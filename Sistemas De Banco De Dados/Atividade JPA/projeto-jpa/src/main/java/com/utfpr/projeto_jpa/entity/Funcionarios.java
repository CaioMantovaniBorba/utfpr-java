package com.utfpr.projeto_jpa.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Funcionarios")
@NamedQuery(
        name = "Funcionarios.listarPorQtdDependentes",
        query = "SELECT f FROM Funcionarios f WHERE f.qtdDependentes = :qtdDependentes"
)
@NamedNativeQuery(
        name = "Funcionarios.listarPorNomeContendo",
        query = "SELECT * FROM Funcionarios WHERE nome LIKE CONCAT('%', :nome, '%')",
        resultClass = Funcionarios.class
)
@Data
public class Funcionarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_funcionario")
    private Integer codFuncionario;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "qtd_dependentes", nullable = false)
    private Integer qtdDependentes = 0;

    @Column(name = "salario", nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_departamento", nullable = false)
    private Departamentos departamento;
}
