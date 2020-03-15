package br.com.logic.pendotiba.logicbus.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.logicbus.repo.PerfilRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.PerfilDTO;

@RestController
@RequestMapping("/rest/perfil")
@CrossOrigin(value = "*")
public class PerfilRestController {

	@Autowired
	PerfilRepositoryImpl perfilRepositoryImpl;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<PerfilDTO> listarTodos() {
		return perfilRepositoryImpl.listarPerfisDTO();
	}
}
