package br.com.logic.pendotiba.despachante.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.despachante.dto.RespostaIdDTO;
import br.com.logic.pendotiba.despachante.dto.RespostaViagemCadastroDTO;
import br.com.logic.pendotiba.despachante.dto.ViagemDTO;
import br.com.logic.pendotiba.despachante.dto.ViagemExtraDTO;
import br.com.logic.pendotiba.despachante.dto.ViagemPerdidaDTO;
import br.com.logic.pendotiba.despachante.dto.ViagemProximasChegadasDTO;
import br.com.logic.pendotiba.despachante.service.ViagemService;

@RestController
@RequestMapping("/viagem")
@CrossOrigin(value = "*")
public class ViagemResource {

	@Autowired
	ViagemService viagemService;
	
	
	@GetMapping(value = "/listarPorImei")
	ResponseEntity<List<ViagemDTO>> listarPorPontoDataCompetencia(@RequestParam(value = "imei") String imei, @RequestParam(value = "dataCompetencia") String dataCompetencia) {
		List<ViagemDTO> listDto = viagemService.listarPorImeiDataCompetencia(imei, dataCompetencia);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarExtraPorPonto")
	ResponseEntity<List<ViagemDTO>> listarExtraPorPonto(@RequestParam(value = "idPonto") Ponto pontoDestino, @RequestParam(value = "dataCompetencia") String dataCompetencia) {
		List<ViagemDTO> listDto = viagemService.listarExtraPorPonto(pontoDestino, dataCompetencia);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarProximasChegadasPorPonto")
	ResponseEntity<List<ViagemProximasChegadasDTO>> listarProximasChegadasPorPonto(@RequestParam(value = "dataCompetencia") String dataCompetencia,
																				   @RequestParam(value = "idPonto") Ponto pontoDestino, 
																				   @RequestParam(value = "idsViagens", required=false) List<Long> idsViagens) {
		List<ViagemProximasChegadasDTO> listDto = viagemService.listarProximasChegadasPorPonto(pontoDestino, dataCompetencia, idsViagens);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarViagensPerdidasPorPonto")
	ResponseEntity<List<ViagemPerdidaDTO>> listarPerdidasPorPonto(@RequestParam(value = "idPonto") Ponto pontoDestino,
																		 @RequestParam(value = "dataCompetencia") String dataCompetencia,
																		 @RequestParam(value = "idsViagens", required=false) List<Long> idsViagens) {
		List<ViagemPerdidaDTO> listDto = viagemService.listarPerdidasPorPonto(pontoDestino, dataCompetencia, idsViagens);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarPorPontoProgramacao")
	ResponseEntity<List<ViagemDTO>> listarPorPontoProgramacao(@RequestParam(value = "idPonto") Ponto ponto,  @RequestParam(value = "idsProgramacoes") List<Long> idsProgramacoes) {
		List<ViagemDTO> listDto = viagemService.listarPorPontoProgramacao(ponto, idsProgramacoes);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarPerdidasPorIds")
	ResponseEntity<List<RespostaIdDTO>> listarPerdidasPorIds(@RequestParam(value = "idsViagens") List<Long> idsViagens) {
		List<RespostaIdDTO> listDto = viagemService.listarPerdidasPorIds(idsViagens);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarViagensDescartadasPorPonto")
	ResponseEntity<List<RespostaIdDTO>> listarViagensDescartadasPorPonto(@RequestParam(value = "idPonto") Ponto ponto, @RequestParam(value = "idsViagens", required=false) List<Long> idsViagens) {
		List<RespostaIdDTO> listDto = viagemService.listarViagensDescartadasPorPonto(ponto, idsViagens);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarPorIdsProgramacoes")
	ResponseEntity<List<ViagemDTO>> listarPorIdsProgramacoes(@RequestParam(value = "idsProgramacoes") List<Long> idsProgramacoes) {
		List<ViagemDTO> listDto = viagemService.listarPorIdsProgramacoes(idsProgramacoes);
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/atualizar")
	ResponseEntity<List<RespostaIdDTO>> atualizar(@RequestBody List<ViagemDTO> viagensDTO) {
		List<RespostaIdDTO> listDto = viagensDTO.stream().map(obj -> new RespostaIdDTO(viagemService.atualizar(obj).getId())).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/cadastrar")
	ResponseEntity<List<RespostaViagemCadastroDTO>> cadastrar(@RequestBody List<ViagemDTO> viagensDTO) {
		List<RespostaViagemCadastroDTO> listDto = viagensDTO.stream().map(obj -> new RespostaViagemCadastroDTO(viagemService.cadastrar(obj).getId(), obj.getIdApp())).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/cadastrar/extra")
	ResponseEntity<List<ViagemExtraDTO>> cadastrarExtra(@RequestBody List<ViagemDTO> viagensDTO) {
		List<ViagemExtraDTO> listDto = viagensDTO.stream().map(obj -> new ViagemExtraDTO(viagemService.cadastrarExtra(obj))).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
}
