package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.MotivoPuloViagem;
import br.com.logic.pendotiba.core.repository.MotivoPuloViagemRepository;
import br.com.logic.pendotiba.despachante.dto.MotivoPuloViagemDTO;

@RestController
@RequestMapping("/motivo-pulo-viagem")
@CrossOrigin(value = "*")
public class MotivoPuloViagemResource {

	@Autowired
	MotivoPuloViagemRepository motivoPuloViagemRepository;
	
	@GetMapping
	ResponseEntity<List<MotivoPuloViagemDTO>> listarTodos() {
		List<MotivoPuloViagem> motivoPuloViagems = motivoPuloViagemRepository.findAll();
		List<MotivoPuloViagemDTO> listDto = motivoPuloViagems.stream().map(obj -> new MotivoPuloViagemDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
