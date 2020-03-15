package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;
import br.com.logic.pendotiba.despachante.dto.FuncionarioDTO;

@RestController
@RequestMapping("/funcionario")
@CrossOrigin(value = "*")
public class FuncionarioResource {

	@Autowired
	FuncionarioRepository funcionarioRepository;
	

	
	@GetMapping(value = "/listarMotoristas")
	ResponseEntity<List<FuncionarioDTO>> listarMotoristas() {
		List<Funcionario> funcionarios = funcionarioRepository.findByFuncaoMotoristaTrueAndAtivoTrue();
		List<FuncionarioDTO> listDto = funcionarios.stream().map(obj -> new FuncionarioDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarCobradores")
	ResponseEntity<List<FuncionarioDTO>> listarCobradores() {
		List<Funcionario> funcionarios = funcionarioRepository.findByFuncaoCobradorTrueAndAtivoTrue();
		List<FuncionarioDTO> listDto = funcionarios.stream().map(obj -> new FuncionarioDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	/*
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarPorMatriculaOuNome")
	List<Funcionario> listarPorMatriculaOuNome(@RequestParam(value = "matriculaOuNome") String matriculaOuNome) {
		return  funcionarioRepository.listarPorMatriculaOuNome(matriculaOuNome);
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarMotoristasDisponiveisPorDataCompetencia")
	List<Funcionario> listarMotoristasDisponiveisPorDataCompetencia(@RequestParam(value = "dataCompetencia") Date dataCompetencia) {
		return funcionarioRepository.listarMotoristasDisponiveisPorDataCompetencia(dataCompetencia);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarParceirosDisponiveisPorDataCompetencia")
	List<Funcionario> listarParceirosDisponiveisPorDataCompetencia(@RequestParam(value = "dataCompetencia") Date dataCompetencia) {
		return funcionarioRepository.listarParceirosDisponiveisPorDataCompetencia(dataCompetencia);
	}
	*/
}
