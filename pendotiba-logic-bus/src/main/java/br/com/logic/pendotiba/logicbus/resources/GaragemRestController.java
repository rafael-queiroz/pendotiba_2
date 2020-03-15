package br.com.logic.pendotiba.logicbus.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.repository.GaragemRepository;
import br.com.logic.pendotiba.logicbus.resources.dto.GaragemDTO;

@RestController
@RequestMapping("/rest/garagem")
@CrossOrigin(value = "*")
public class GaragemRestController {

	@Autowired
	GaragemRepository garagemRepository;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<GaragemDTO> listarTodos() {
		List<GaragemDTO> garagensDTO = new ArrayList<>();
		garagemRepository.findAll().forEach(g -> garagensDTO.add(new GaragemDTO(g)));
		return garagensDTO;
	}
}
