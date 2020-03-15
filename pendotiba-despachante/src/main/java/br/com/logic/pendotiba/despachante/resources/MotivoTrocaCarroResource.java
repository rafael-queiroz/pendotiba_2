package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.MotivoTrocaCarro;
import br.com.logic.pendotiba.core.repository.MotivoTrocaCarroRepository;
import br.com.logic.pendotiba.despachante.dto.MotivoTrocaCarroDTO;

@RestController
@RequestMapping("/motivo-troca-carro")
@CrossOrigin(value = "*")
public class MotivoTrocaCarroResource {

	@Autowired
	MotivoTrocaCarroRepository motivoTrocaCarroRepository;
	
	@GetMapping
	ResponseEntity<List<MotivoTrocaCarroDTO>> listarTodos() {
		List<MotivoTrocaCarro> motivoTrocaCarros = motivoTrocaCarroRepository.findAll();
		List<MotivoTrocaCarroDTO> listDto = motivoTrocaCarros.stream().map(obj -> new MotivoTrocaCarroDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
