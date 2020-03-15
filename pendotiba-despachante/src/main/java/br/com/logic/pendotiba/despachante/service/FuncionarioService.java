package br.com.logic.pendotiba.despachante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	

	public Funcionario buscarPorId(Long id) {
		return funcionarioRepository.findById(id).orElse(null);
	}
	
}
