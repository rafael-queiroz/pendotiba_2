package br.com.logic.pendotiba.logicbus.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.logicbus.repo.LinhaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.LinhaDTO;

@RestController
@RequestMapping("/rest/linha")
@CrossOrigin(value = "*")
public class LinhaRestController {

	@Autowired
	LinhaRepositoryImpl linhaRepositoryImpl;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<LinhaDTO> listarTodas() {
		return linhaRepositoryImpl.listarLinhasDTO();
	}
}
