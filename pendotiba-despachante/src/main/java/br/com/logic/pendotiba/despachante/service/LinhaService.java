package br.com.logic.pendotiba.despachante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.repository.LinhaRepository;

@Service
public class LinhaService {
	
	@Autowired
	LinhaRepository linhaRepository;
	

	public Linha buscarPorId(Long id) {
		return linhaRepository.findOne(id);
	}
	
}
