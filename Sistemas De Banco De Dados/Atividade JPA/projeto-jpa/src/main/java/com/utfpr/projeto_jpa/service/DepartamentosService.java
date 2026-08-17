package com.utfpr.projeto_jpa.service;

import com.utfpr.projeto_jpa.entity.Departamentos;
import com.utfpr.projeto_jpa.entity.Funcionarios;
import com.utfpr.projeto_jpa.repository.DepartamentosRepository;
import com.utfpr.projeto_jpa.repository.FuncionariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartamentosService {
    @Autowired
    private DepartamentosRepository repository;

    @Autowired
    private FuncionariosRepository funcionariosRepository;

    public List<Departamentos> listarTodosDepartamentos() {
        return repository.findAll();
    }

    public Departamentos listarPrimeiroDepartamento() {
        return repository.findFirstByOrderByCodDepartamentoAsc();
    }

    @Transactional
    public Funcionarios salvarDepartamentoComFuncionario(Departamentos departamento, Funcionarios funcionario) {
        Departamentos departamentoSalvo = repository.save(departamento);
        funcionario.setDepartamento(departamentoSalvo);

        return funcionariosRepository.save(funcionario);
    }
}
