package br.com.logic.pendotiba.logicbus.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.TipoReclamacao;
import br.com.logic.pendotiba.core.repository.TipoReclamacaoRepository;

@RestController
@RequestMapping("/rest/tipo-reclamacao")
@CrossOrigin(value = "*")
public class TipoReclamacaoRestController {

	@Autowired
	TipoReclamacaoRepository tipoReclamacaoRepository;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<TipoReclamacao> listarTodas() {
		return tipoReclamacaoRepository.findAll();
	}
}
