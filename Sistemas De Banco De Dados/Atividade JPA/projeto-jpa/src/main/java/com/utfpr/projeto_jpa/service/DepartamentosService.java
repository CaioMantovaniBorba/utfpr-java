package com.utfpr.projeto_jpa.service;

import com.utfpr.projeto_jpa.entity.Departamentos;
import com.utfpr.projeto_jpa.repository.DepartamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentosService {
    @Autowired
    private DepartamentosRepository repository;

    public List<Departamentos> listarTodosDepartamentos() {
        return repository.findAll();
    }

    public Departamentos listarPrimeiroDepartamento() {
        return repository.findFirstByOrderByCodDepartamentoAsc();
    }
}
