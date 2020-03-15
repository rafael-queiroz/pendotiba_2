package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Perfil;
import br.com.logic.pendotiba.core.repository.PerfilRepository;
import br.com.logic.pendotiba.despachante.dto.PerfilDTO;

@RestController
@RequestMapping("/perfil")
@CrossOrigin(value = "*")
public class PerfilResource {

	@Autowired
	PerfilRepository perfilRepository;
	
	@GetMapping
	ResponseEntity<List<PerfilDTO>> listarTodos() {
		List<Perfil> perfils = perfilRepository.findAll();
		List<PerfilDTO> listDto = perfils.stream().map(obj -> new PerfilDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
