package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Turno;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.despachante.dto.TurnoDTO;

@RestController
@RequestMapping("/turno")
@CrossOrigin(value = "*")
public class TurnoResource {

	@Autowired
	TurnoRepository turnoRepository;
	
	@GetMapping
	ResponseEntity<List<TurnoDTO>> listarTodos() {
		List<Turno> turnos = turnoRepository.findAll();
		List<TurnoDTO> listDto = turnos.stream().map(obj -> new TurnoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
