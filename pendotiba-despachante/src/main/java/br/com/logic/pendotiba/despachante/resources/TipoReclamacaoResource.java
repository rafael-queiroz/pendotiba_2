package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.TipoReclamacao;
import br.com.logic.pendotiba.core.repository.TipoReclamacaoRepository;
import br.com.logic.pendotiba.despachante.dto.TipoReclamacaoDTO;

@RestController
@RequestMapping("/tipo-reclamacao")
@CrossOrigin(value = "*")
public class TipoReclamacaoResource {

	@Autowired
	TipoReclamacaoRepository tipoReclamacaoRepository;
	
	@GetMapping
	ResponseEntity<List<TipoReclamacaoDTO>> listarTodos() {
		List<TipoReclamacao> tipoReclamacaos = tipoReclamacaoRepository.findAll();
		List<TipoReclamacaoDTO> listDto = tipoReclamacaos.stream().map(obj -> new TipoReclamacaoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
