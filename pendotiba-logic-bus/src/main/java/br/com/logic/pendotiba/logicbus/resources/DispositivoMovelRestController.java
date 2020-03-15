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

import br.com.logic.pendotiba.core.repository.DispositivoMovelRepository;
import br.com.logic.pendotiba.logicbus.resources.dto.DispositivoMovelDTO;

@RestController
@RequestMapping("/rest/aparelho")
@CrossOrigin(value = "*")
public class DispositivoMovelRestController {

	@Autowired
	DispositivoMovelRepository dispositivoMovelRepository;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<DispositivoMovelDTO> listarTodos() {
		List<DispositivoMovelDTO> dispositivosMoveis = new ArrayList<>();
		dispositivoMovelRepository.findAll().forEach(d -> dispositivosMoveis.add(new DispositivoMovelDTO(d)));
		return dispositivosMoveis;
	}
}
