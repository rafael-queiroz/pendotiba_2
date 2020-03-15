package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Terminal;
import br.com.logic.pendotiba.core.repository.TerminalRepository;
import br.com.logic.pendotiba.despachante.dto.TerminalDTO;

@RestController
@RequestMapping("/terminal")
@CrossOrigin(value = "*")
public class TerminalResource {

	@Autowired
	TerminalRepository terminalRepository;
	
	@GetMapping
	ResponseEntity<List<TerminalDTO>> listarTodos() {
		List<Terminal> terminals = terminalRepository.findAll();
		List<TerminalDTO> listDto = terminals.stream().map(obj -> new TerminalDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
