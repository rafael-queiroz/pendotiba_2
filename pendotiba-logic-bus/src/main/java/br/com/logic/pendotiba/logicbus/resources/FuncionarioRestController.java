package br.com.logic.pendotiba.logicbus.resources;

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

import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.logicbus.repo.FuncionarioRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.FuncionarioDTO;

@RestController
@RequestMapping("/rest/funcionario")
@CrossOrigin(value = "*")
public class FuncionarioRestController {

	@Autowired
	FuncionarioRepositoryImpl funcionarioRepositoryImpl;
	

	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarMotoristas")
	@ResponseBody List<FuncionarioDTO> listarMotoristas() {
		return funcionarioRepositoryImpl.listarMotoristasDTO();
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarCobradores")
	@ResponseBody List<FuncionarioDTO> listarCobradores() {
		return funcionarioRepositoryImpl.listarCobradoresDTO();
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarPorMatriculaOuNome")
	List<Funcionario> listarPorMatriculaOuNome(@RequestParam(value = "matriculaOuNome") String matriculaOuNome) {
		return  funcionarioRepositoryImpl.listarPorMatriculaOuNome(matriculaOuNome);
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarMotoristasDisponiveisPorDataCompetencia")
	List<Funcionario> listarMotoristasDisponiveisPorDataCompetencia(@RequestParam(value = "dataCompetencia") Date dataCompetencia) {
		return funcionarioRepositoryImpl.listarMotoristasDisponiveisPorDataCompetencia(dataCompetencia);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarParceirosDisponiveisPorDataCompetencia")
	List<Funcionario> listarParceirosDisponiveisPorDataCompetencia(@RequestParam(value = "dataCompetencia") Date dataCompetencia) {
		return funcionarioRepositoryImpl.listarParceirosDisponiveisPorDataCompetencia(dataCompetencia);
	}
	
}
