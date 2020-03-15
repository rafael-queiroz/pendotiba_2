package br.com.logic.pendotiba.despachante.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.despachante.exception.ObjectNotFoundException;

@Service
public class PontoService {
	
	@Autowired
	PontoRepository pontoRepository;
	

	public Ponto buscarPorId(Long id) {
		return pontoRepository.findOne(id);
	}
	
	public Ponto buscarPorImei(String imei) {
		Optional<Ponto> obj = pontoRepository.buscarPorImei(imei);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! codigo:  " + imei + ", Tipo: " + Ponto.class.getName()));
	}

}
