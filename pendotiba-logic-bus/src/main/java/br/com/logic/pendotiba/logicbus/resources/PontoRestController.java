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

import br.com.logic.pendotiba.logicbus.repo.PontoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.PontoDTO;

@RestController
@RequestMapping("/rest/ponto")
@CrossOrigin(value = "*")
public class PontoRestController {

	@Autowired
	PontoRepositoryImpl pontoRepositoryImpl;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<PontoDTO> listarTodos() {
		return pontoRepositoryImpl.listarPontosDTO();
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/buscarPorImei")
	PontoDTO buscarPorImei(@RequestParam(value = "imei") String imei) {
		return new PontoDTO(pontoRepositoryImpl.buscarPontoPorDispositivoMovel(imei));
	}
	
}
