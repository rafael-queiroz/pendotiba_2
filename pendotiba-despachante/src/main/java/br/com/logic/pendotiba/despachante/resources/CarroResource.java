package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.despachante.dto.CarroDTO;

@RestController
@RequestMapping("/carro")
@CrossOrigin(value = "*")
public class CarroResource {

	@Autowired
	CarroRepository carroRepository;
	
	@GetMapping
	ResponseEntity<List<CarroDTO>> listarTodos() {
		List<Carro> carros = carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem();
		List<CarroDTO> listDto = carros.stream().map(obj -> new CarroDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	
	/*
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarCarrosDisponiveisPorDataCompetencia")
	List<Carro> listarCarrosDisponiveisPorDataCompetencia(@RequestParam(value = "dataCompetencia") Date dataCompetencia) {
		return carroRepository.listarCarrosDisponiveisPorDataCompetencia(dataCompetencia);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarCarrosGlobusDisponiveisPorDataCompetencia")
	List<CarroGlobus> listarCarrosDisponiveisPorDataCompetencia() {
		return carroGlobusRepository.findAll();
	}
	*/
	
}
