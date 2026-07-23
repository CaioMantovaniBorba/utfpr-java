package com.utfpr.projeto_jpa.service;

import com.utfpr.projeto_jpa.entity.Funcionarios;
import com.utfpr.projeto_jpa.repository.FuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionariosService {
    @Autowired
    private FuncionariosRepository repository;

    public List<Funcionarios> listarTodosFuncionarios() {
        return repository.findAll();
    }
}
