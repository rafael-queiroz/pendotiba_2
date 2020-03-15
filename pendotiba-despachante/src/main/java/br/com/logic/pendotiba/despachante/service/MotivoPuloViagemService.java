package br.com.logic.pendotiba.despachante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.MotivoPuloViagem;
import br.com.logic.pendotiba.core.repository.MotivoPuloViagemRepository;

@Service
public class MotivoPuloViagemService {
	
	@Autowired
	MotivoPuloViagemRepository motivoPuloViagemRepository;
	

	public MotivoPuloViagem buscarPorId(Long id) {
		return motivoPuloViagemRepository.findOne(id);
	}
	
}
