package br.com.logic.pendotiba.despachante.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Turno;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.despachante.exception.ObjectNotFoundException;

@Service
public class TurnoService {
	
	@Autowired
	TurnoRepository turnoRepository;
	

	public Turno buscarPorId(Long id) {
		Optional<Turno> obj = turnoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! codigo: " + id + ", Tipo: " + Turno.class.getName()));
	}
	
}
