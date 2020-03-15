package br.com.logic.pendotiba.despachante.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.despachante.exception.ObjectNotFoundException;

@Service
public class CarroService {
	
	@Autowired
	CarroRepository carroRepository;
	

	public Carro buscarPorId(Long id) {
		Optional<Carro> obj = carroRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! codigo: " + id + ", Tipo: " + Carro.class.getName()));
	}
	
	public void atualizarRoletas(Carro carro, BigInteger roleta) {
		if(roleta != null) {
			Carro carroPersistido = buscarPorId(carro.getId());
			carroPersistido.setRoleta1(roleta);
			carroRepository.save(carroPersistido);
		}
	}
	
}
