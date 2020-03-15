package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Garagem;
import br.com.logic.pendotiba.core.repository.GaragemRepository;
import br.com.logic.pendotiba.despachante.dto.GaragemDTO;

@RestController
@RequestMapping("/garagem")
@CrossOrigin(value = "*")
public class GaragemResource {

	@Autowired
	GaragemRepository garagemRepository;
	
	@GetMapping
	ResponseEntity<List<GaragemDTO>> listarTodos() {
		List<Garagem> garagems = garagemRepository.findAll();
		List<GaragemDTO> listDto = garagems.stream().map(obj -> new GaragemDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
}
