package br.com.logic.pendotiba.despachante.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.TipoViagemPerdida;
import br.com.logic.pendotiba.core.repository.TipoViagemPerdidaRepository;
import br.com.logic.pendotiba.despachante.exception.ObjectNotFoundException;

@Service
public class TipoViagemPerdidaService {
	
	@Autowired
	TipoViagemPerdidaRepository tipoViagemPerdidaRepository;
	

	public TipoViagemPerdida buscarPorId(Long id) {
		Optional<TipoViagemPerdida> obj = tipoViagemPerdidaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! codigo: " + id + ", Tipo: " + TipoViagemPerdida.class.getName()));
	}
	
}
