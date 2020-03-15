package br.com.logic.pendotiba.logicbus.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.repository.PontoLinhaRepository;
import br.com.logic.pendotiba.logicbus.repo.PontoLinhaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.PontoLinhaDTO;
import br.com.logic.pendotiba.logicbus.util.DTOUtil;

@RestController
@RequestMapping("/rest/ponto-linha")
@CrossOrigin(value = "*")
public class PontoLinhaRestController {

	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	
	@Autowired
	PontoLinhaRepositoryImpl pontoLinhaRepositoryImpl;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<PontoLinhaDTO> listarTodos() {
		return pontoLinhaRepositoryImpl.listarPontosLinhaDTO();
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarPontoLinhaPorLinha")
	List<PontoLinhaDTO> listarPontoLinhaPorLinha(@RequestParam(value = "idLinha") Linha linha) {
		return DTOUtil.pontosLinhaParaPontosLinhaDTO(pontoLinhaRepository.findByLinha(linha));
	}
	
}
