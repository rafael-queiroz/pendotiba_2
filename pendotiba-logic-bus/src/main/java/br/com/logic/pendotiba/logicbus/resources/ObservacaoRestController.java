package br.com.logic.pendotiba.logicbus.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.logicbus.resources.dto.ObservacaoDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.RespostaIdDTO;
import br.com.logic.pendotiba.logicbus.service.ObservacaoService;
import br.com.logic.pendotiba.logicbus.util.DTOUtil;

@RestController
@RequestMapping("/rest/observacao")
@CrossOrigin(value = "*")
public class ObservacaoRestController {

	@Autowired
	ObservacaoService observacaoService;
	

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaIdDTO> cadastrarObservacao(@RequestBody List<ObservacaoDTO> observacoesDTO, BindingResult result) {
		return DTOUtil.observacoesParaRespostasIdDTO(observacaoService.salvarObservacoesDTO(observacoesDTO));
	}
	
}