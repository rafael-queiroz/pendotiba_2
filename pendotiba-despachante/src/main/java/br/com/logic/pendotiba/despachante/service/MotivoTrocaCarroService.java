package br.com.logic.pendotiba.despachante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.MotivoTrocaCarro;
import br.com.logic.pendotiba.core.repository.MotivoTrocaCarroRepository;

@Service
public class MotivoTrocaCarroService {
	
	@Autowired
	MotivoTrocaCarroRepository motivoTrocaCarroRepository;
	

	public MotivoTrocaCarro buscarPorId(Long id) {
		return motivoTrocaCarroRepository.findById(id).orElse(null);
	}
	
}
