package br.com.logic.pendotiba.logicbus.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.repo.PontoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.repo.ProgramacaoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.ProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.RespostaIdDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.RespostaProgramacaoCadastroDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.TrocaCarroProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.TrocaMotoristaProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.service.CarroProgramacaoService;
import br.com.logic.pendotiba.logicbus.service.CarroService;
import br.com.logic.pendotiba.logicbus.service.ProgramacaoService;
import br.com.logic.pendotiba.logicbus.util.DTOUtil;

@RestController
@RequestMapping("/rest/programacao")
@CrossOrigin(value = "*")
public class ProgramacaoRestController {

	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	ProgramacaoService programacaoService;

	@Autowired
	PontoRepository pontoRepository;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	CarroProgramacaoService carroProgramacaoService;
	
	
	
	////////////
	@Autowired
	PontoRepositoryImpl pontoRepositoryImpl;
	
	@Autowired
	ProgramacaoRepositoryImpl programacaoRepositoryImpl;
	
	
	

	@GetMapping(value = "/listarPorImei", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ProgramacaoDTO> listarPorImei(@RequestParam(value = "imei") String imei, 
									   @RequestParam(value = "dataCompetencia") String dataCompetencia,
									   @RequestParam(value = "ids", required=false) List<Long> ids) {
		List<ProgramacaoDTO> retu = DTOUtil.programacoesParaProgramacoesDTO(programacaoRepositoryImpl.listarPorPonto(pontoRepositoryImpl.buscarPontoPorDispositivoMovel(imei),
																							DataUtil.getDateDDMMYYYY(dataCompetencia),
																							ids));
		return retu;
	}
	
	
	/*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/listarAtivosPorImei")
	List<ProgramacaoDTO> listarAtivoPorImei(@RequestParam(value = "imei") String imei, @RequestParam(value = "dataCompetencia") String dataCompetencia) {
		return DTOUtil.programacaoParaProgramacaoDTO(programacaoRepository.listarPorPonto(pontoRepository.buscarPontoPorDispositivoMovel(imei), 
																						  true, 
																						  DataUtil.getDate(dataCompetencia)));
	}*/
	
	
	@GetMapping(value = "/listarAtivosPorIds", params = "ids", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ProgramacaoDTO> listarAtivosPorIds(@RequestParam List<Long> ids) {
		return DTOUtil.programacoesParaProgramacoesDTO(programacaoRepositoryImpl.listarPorStatusIds(Status.LIBERADO, ids));
	}
	
	
	@PostMapping(value = "/encerrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaIdDTO> encerrarProgramacaoDTO(@RequestBody List<ProgramacaoDTO> programacoesDTO, BindingResult result) {
		return programacoesDTO.stream().map(obj -> new RespostaIdDTO(programacaoService.encerrar(obj).getId())).collect(Collectors.toList());  
	}
	
	
	@GetMapping(value = "/listarEncerradasPorIds", params = "ids", produces = MediaType.APPLICATION_JSON_VALUE)
	List<RespostaIdDTO> listarFinalizadasPorIds(@RequestParam List<Long> ids) {
		List<RespostaIdDTO> te = DTOUtil.programacoesParaRespostasIdDTO(programacaoRepositoryImpl.listarPorStatusIds(Status.ENCERRADO, ids));
		return te;
	}
	
	@GetMapping(value = "/listarInativasPorIds", params = "ids", produces = MediaType.APPLICATION_JSON_VALUE)
	List<RespostaIdDTO> listarInativasPorIds(@RequestParam List<Long> ids) {
		return DTOUtil.programacoesParaRespostasIdDTO(programacaoRepositoryImpl.listarPorStatusIds(Status.CORTADO, ids));
	}
	
	@PostMapping(value = "/trocarCarro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaIdDTO> trocarCarro(@RequestBody List<TrocaCarroProgramacaoDTO> trocasDTO, BindingResult result) {
		carroProgramacaoService.salvarTrocasCarroProgramacaoDTO(trocasDTO);
		List<Programacao> programacoes = programacaoService.realizarTrocasCarroProgramacaoDTO(trocasDTO);
		return DTOUtil.programacoesParaRespostasIdDTO(programacoes);
	}
	
	@PostMapping(value = "/trocarMotorista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaIdDTO> trocarMotorista(@RequestBody List<TrocaMotoristaProgramacaoDTO> trocasDTO, BindingResult result) {
		List<Programacao> programacoes = programacaoService.realizarTrocasMotoristasProgramacaoDTO(trocasDTO);
		return DTOUtil.programacoesParaRespostasIdDTO(programacoes);
	}
	
	
	@PostMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaIdDTO> atualizarProgramacoesDTO(@RequestBody List<ProgramacaoDTO> programacoesDTO, BindingResult result) {
		return DTOUtil.programacoesParaRespostasIdDTO(programacaoService.salvarProgramacoesDTO(programacoesDTO));
	}
	
	@PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaProgramacaoCadastroDTO> cadastrarProgramacoesDTO(@RequestBody List<ProgramacaoDTO> programacoesDTO, BindingResult result) {
		List<RespostaProgramacaoCadastroDTO> retorno = new ArrayList<>();
		for (ProgramacaoDTO dto : programacoesDTO) 
			retorno.add(new RespostaProgramacaoCadastroDTO(programacaoService.salvarProgramacaoDTO(dto).getId(), dto.getIdApp()));
		
		return retorno;
	}
		
	@GetMapping(value = "/listarPorIds", params = "ids", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ProgramacaoDTO> listarPorIds(@RequestParam List<Long> ids) {
		List<ProgramacaoDTO> retu = DTOUtil.programacoesParaProgramacoesDTO(programacaoRepository.findByIdIn(ids));
		return retu;
	}
	
	@PostMapping(value = "/inativar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaIdDTO> inativarProgramacoesDTO(@RequestBody List<ProgramacaoDTO> programacoesDTO, BindingResult result) {
		return DTOUtil.programacoesParaRespostasIdDTO(programacaoService.inativarProgramacoesDTO(programacoesDTO));
	}
	
}