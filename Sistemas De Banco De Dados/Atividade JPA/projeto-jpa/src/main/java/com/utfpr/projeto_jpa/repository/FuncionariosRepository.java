package com.utfpr.projeto_jpa.repository;

import com.utfpr.projeto_jpa.entity.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionariosRepository extends JpaRepository<Funcionarios, Long> {
}
