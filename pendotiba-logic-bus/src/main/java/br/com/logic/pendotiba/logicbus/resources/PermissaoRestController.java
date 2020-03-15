package br.com.logic.pendotiba.logicbus.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Permissao;
import br.com.logic.pendotiba.core.repository.PermissaoRepository;

@RestController
@RequestMapping("/rest/permissao")
@CrossOrigin(value = "*")
public class PermissaoRestController {

	@Autowired
	PermissaoRepository permissaoRepository;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<Permissao> listarTodas() {
		return permissaoRepository.findAll();
	}
}
