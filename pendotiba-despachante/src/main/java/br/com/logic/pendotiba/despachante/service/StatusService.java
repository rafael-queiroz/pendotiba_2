package br.com.logic.pendotiba.despachante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.repository.StatusRepository;

@Service
public class StatusService {
	
	@Autowired
	StatusRepository statusRepository;
	

	public Status buscarPorId(Long id) {
		return statusRepository.findById(id).orElse(null);
	}
	
}
