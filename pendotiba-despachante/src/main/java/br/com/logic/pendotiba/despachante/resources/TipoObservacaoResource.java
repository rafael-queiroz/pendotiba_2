package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.TipoObservacao;
import br.com.logic.pendotiba.core.repository.TipoObservacaoRepository;
import br.com.logic.pendotiba.despachante.dto.TipoObservacaoDTO;

@RestController
@RequestMapping("/tipo-observacao")
@CrossOrigin(value = "*")
public class TipoObservacaoResource {

	@Autowired
	TipoObservacaoRepository tipoObservacaoRepository;
	
	@GetMapping
	ResponseEntity<List<TipoObservacaoDTO>> listarTodos() {
		List<TipoObservacao> tipoObservacaos = tipoObservacaoRepository.findAll();
		List<TipoObservacaoDTO> listDto = tipoObservacaos.stream().map(obj -> new TipoObservacaoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
