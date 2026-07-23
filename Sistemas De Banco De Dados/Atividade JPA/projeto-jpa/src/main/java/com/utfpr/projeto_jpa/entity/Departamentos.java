package com.utfpr.projeto_jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Departamentos")
@Data
public class Departamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_departamento")
    private Integer codDepartamento;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
}
