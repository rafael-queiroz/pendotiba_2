package br.com.logic.pendotiba.logicbus.resources;

import java.util.ArrayList;
import java.util.List;

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

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.repository.ViagemRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.repo.PontoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.repo.ViagemRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.RespostaIdDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.RespostaViagemCadastroDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemExtraDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemPerdidaDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemProximasChegadasDTO;
import br.com.logic.pendotiba.logicbus.service.ViagemService;
import br.com.logic.pendotiba.logicbus.util.DTOUtil;

@RestController
@RequestMapping("/rest/viagem")
@CrossOrigin(value = "*")
public class ViagemRestController {

	@Autowired
	ViagemService viagemService;
	
	@Autowired
	ViagemRepository viagemRepository;
	
	@Autowired
	ViagemRepositoryImpl viagemRepositoryImpl;
	
	@Autowired
	PontoRepositoryImpl pontoRepositoryImpl;
	
	
	@GetMapping(value = "/listarPorImei", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ViagemDTO> listarPorImei(@RequestParam(value = "imei") String imei, @RequestParam(value = "dataCompetencia") String dataCompetencia) {
		Ponto ponto = pontoRepositoryImpl.buscarPontoPorDispositivoMovel(imei);
		return DTOUtil.viagensParaViagensDTO(viagemRepositoryImpl.listarPorPonto(ponto, DataUtil.getDateDDMMYYYY(dataCompetencia)));
	}
	
	@GetMapping(value = "/listarExtraPorPonto", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ViagemDTO> listarPorImei(@RequestParam(value = "idPonto") Ponto pontoDestino, @RequestParam(value = "dataCompetencia") String dataCompetencia) {
		return DTOUtil.viagensParaViagensDTO(viagemRepositoryImpl.listarExtraPorPonto(pontoDestino, DataUtil.getDateDDMMYYYY(dataCompetencia)));
	}
	
	@GetMapping(value = "/listarProximasChegadasPorPonto", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ViagemProximasChegadasDTO> listarProximasChegadasPorPonto(@RequestParam(value = "dataCompetencia") String dataCompetencia,
			@RequestParam(value = "idPonto") Ponto pontoDestino, 
			@RequestParam(value = "idsViagens", required=false) List<Long> idsViagens) {
		return DTOUtil.viagensParaViagemProximasChegadasDTO(viagemRepositoryImpl.listarProximasChegadasPorPonto(DataUtil.getDateDDMMYYYY(dataCompetencia), pontoDestino, idsViagens));
	}
	
	@GetMapping(value = "/listarViagensPerdidasPorPonto", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ViagemPerdidaDTO> listarViagensPerdidasPorPonto(@RequestParam(value = "idPonto") Ponto ponto,
			@RequestParam(value = "dataCompetencia") String dataCompetencia,
			@RequestParam(value = "idsViagens", required=false) List<Long> idsViagens) {
		return DTOUtil.viagensParaViagensPerdidasDTO(viagemRepositoryImpl.listarViagensPerdidasPorPonto(ponto, DataUtil.getDateDDMMYYYY(dataCompetencia), idsViagens));
	}
	
	@PostMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaIdDTO> atualizarViagensDTO(@RequestBody List<ViagemDTO> viagensDTO, BindingResult result) {
		return DTOUtil.viagensParaRespostasIdDTO(viagemService.salvarViagensDTO(viagensDTO));
	}
	
	@PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<RespostaViagemCadastroDTO> cadastrarViagensDTO(@RequestBody List<ViagemDTO> viagensDTO, BindingResult result) {
		//List<RespostaIdDTO> resp = DTOUtil.viagensParaRespostasIdDTO(viagemService.cadastrarViagensProgramadasDTO(viagensDTO));
		
		List<RespostaViagemCadastroDTO> retorno = new ArrayList<>();
		for (ViagemDTO dto : viagensDTO) 
			retorno.add(new RespostaViagemCadastroDTO(viagemService.cadastrarViagemProgramadaPeloApp(dto).getId(), dto.getIdApp()));
		
		return retorno;
	}
	
	@PostMapping(value = "/cadastrar/extra", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<ViagemExtraDTO> cadastrarViagensExtraDTO(@RequestBody List<ViagemDTO> viagensDTO, BindingResult result) {
		return DTOUtil.viagensParaViagensExtrasDTO(viagemService.cadastrarViagensExtrasDTO(viagensDTO));
	}
	
	
	
	
	
	
	
	
	
	
	@GetMapping(value = "/listarPorPontoProgramacao", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ViagemDTO> listarPorProgramacao(@RequestParam(value = "idPonto") Ponto ponto, @RequestParam(value = "idsProgramacoes") List<Long> idsProgramacoes) {
		return DTOUtil.viagensParaViagensDTO(viagemRepositoryImpl.listarPorPontoProgramacoes(ponto, idsProgramacoes));
	}
	
	
	@GetMapping(value = "/listarPerdidasPorIds", produces = MediaType.APPLICATION_JSON_VALUE)
	List<RespostaIdDTO> listarPerdidasPorIds(@RequestParam(value = "idsViagens") List<Long> idsViagens) {
		return DTOUtil.viagensParaRespostasIdDTO(viagemRepository.findByIdInAndTipoViagemPerdidaNotNull(idsViagens));
	}
	
	
	@GetMapping(value = "/listarViagensDescartadasPorPonto", produces = MediaType.APPLICATION_JSON_VALUE)
	List<RespostaIdDTO> listarViagensDescartadasPorPonto(@RequestParam(value = "idPonto") Ponto ponto, 
																     @RequestParam(value = "idsViagens", required=false) List<Long> idsViagens) {
		return DTOUtil.viagensParaRespostasIdDTO(viagemRepositoryImpl.listarViagensDescartadasPorPonto(ponto, idsViagens));
	}
	
	
	@GetMapping(value = "/listarPorIdsProgramacoes", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ViagemDTO> listarPorIdsProgramacoes(@RequestParam(value = "idsProgramacoes") List<Long> idsProgramacoes) {
		return DTOUtil.viagensParaViagensDTO(viagemRepository.findByProgramacaoIdIn(idsProgramacoes));
	}
	
}