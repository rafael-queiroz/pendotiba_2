package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.despachante.dto.LinhaDTO;

@RestController
@RequestMapping("/linha")
@CrossOrigin(value = "*")
public class LinhaResource {

	@Autowired
	LinhaRepository LinhaRepository;
	
	@GetMapping
	ResponseEntity<List<LinhaDTO>> listarTodos() {
		List<Linha> Linhas = LinhaRepository.findAll();
		List<LinhaDTO> listDto = Linhas.stream().map(obj -> new LinhaDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
