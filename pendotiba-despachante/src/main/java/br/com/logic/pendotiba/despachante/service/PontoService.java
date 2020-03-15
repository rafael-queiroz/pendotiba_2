package br.com.logic.pendotiba.despachante.service;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.despachante.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PontoService {
	
	@Autowired
	PontoRepository pontoRepository;
	

	public Ponto buscarPorId(Long id) {
		return pontoRepository.findById(id).orElse(null);
	}
	
	public Ponto buscarPorImei(String imei) {
		Optional<Ponto> obj = pontoRepository.buscarPorImei(imei);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! codigo:  " + imei + ", Tipo: " + Ponto.class.getName()));
	}

}
