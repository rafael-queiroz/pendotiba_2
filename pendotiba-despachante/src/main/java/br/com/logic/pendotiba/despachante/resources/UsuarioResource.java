package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.despachante.dto.UsuarioDTO;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(value = "*")
public class UsuarioResource {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping
	ResponseEntity<List<UsuarioDTO>> listarTodos() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioDTO> listDto = usuarios.stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
