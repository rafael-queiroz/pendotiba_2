package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.despachante.dto.PontoDTO;
import br.com.logic.pendotiba.despachante.service.PontoService;

@RestController
@RequestMapping("/ponto")
@CrossOrigin(value = "*")
public class PontoResource {

	@Autowired
	PontoRepository pontoRepository;
	
	@Autowired
	PontoService pontoService;
	
	
	@GetMapping
	ResponseEntity<List<PontoDTO>> listarTodos() {
		List<Ponto> pontos = pontoRepository.findAll();
		List<PontoDTO> listDto = pontos.stream().map(obj -> new PontoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/buscarPorImei")
	ResponseEntity<PontoDTO> buscarPorImei(@RequestParam(value = "imei") String imei) {
		Ponto ponto = pontoService.buscarPorImei(imei);
		return ResponseEntity.ok().body(new PontoDTO(ponto));
	}
	
}
