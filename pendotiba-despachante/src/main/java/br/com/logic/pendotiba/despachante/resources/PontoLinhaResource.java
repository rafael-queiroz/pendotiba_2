package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.repository.PontoLinhaRepository;
import br.com.logic.pendotiba.despachante.dto.PontoLinhaDTO;

@RestController
@RequestMapping("/ponto-linha")
@CrossOrigin(value = "*")
public class PontoLinhaResource {

	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	
	@GetMapping
	ResponseEntity<List<PontoLinhaDTO>> listarTodos() {
		List<PontoLinha> pontoLinhas = pontoLinhaRepository.findAll();
		List<PontoLinhaDTO> listDto = pontoLinhas.stream().map(obj -> new PontoLinhaDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
