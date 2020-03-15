package br.com.logic.pendotiba.logicbus.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.logicbus.repo.CarroRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.CarroDTO;

@RestController
@RequestMapping("/rest/carro")
@CrossOrigin(value = "*")
public class CarroRestController {

	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	CarroRepositoryImpl carroRepositoryImpl;
	

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<CarroDTO> listarTodos() {
		List<CarroDTO> carrosDTO = new ArrayList<>();
		carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem().forEach(c -> carrosDTO.add(new CarroDTO(c)));
		return carrosDTO;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarCarrosDisponiveisPorDataCompetencia")
	List<Carro> listarCarrosDisponiveisPorDataCompetencia(@RequestParam(value = "dataCompetencia") Date dataCompetencia) {
		return carroRepositoryImpl.listarCarrosDisponiveisPorDataCompetencia(dataCompetencia);
	}
	
	/*
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarCarrosGlobusDisponiveisPorDataCompetencia")
	List<CarroGlobus> listarCarrosDisponiveisPorDataCompetencia() {
		return carroGlobusRepository.findAll();
	}
	*/
	
}
