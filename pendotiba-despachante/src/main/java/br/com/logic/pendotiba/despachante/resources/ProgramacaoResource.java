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

import br.com.logic.pendotiba.despachante.dto.ProgramacaoDTO;
import br.com.logic.pendotiba.despachante.dto.RespostaIdDTO;
import br.com.logic.pendotiba.despachante.dto.RespostaProgramacaoCadastroDTO;
import br.com.logic.pendotiba.despachante.dto.TrocaCarroProgramacaoDTO;
import br.com.logic.pendotiba.despachante.dto.TrocaMotoristaProgramacaoDTO;
import br.com.logic.pendotiba.despachante.service.ProgramacaoService;

@RestController
@RequestMapping("/programacao")
@CrossOrigin(value = "*")
public class ProgramacaoResource {

	@Autowired
	ProgramacaoService programacaoService;
	
	
	@GetMapping(value = "/listarPorImei")
	ResponseEntity<List<ProgramacaoDTO>> listarPorPontoDataCompetencia(@RequestParam(value = "imei") String imei, 
												 @RequestParam(value = "dataCompetencia") String dataCompetencia,
												 @RequestParam(value = "ids", required=false) List<Long> ids) {
		List<ProgramacaoDTO> listDto = programacaoService.listarPorImeiDataCompetenciaIds(imei, dataCompetencia, ids);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarPorIds")
	ResponseEntity<List<ProgramacaoDTO>> listarPorIds(@RequestParam(value = "ids") List<Long> ids) {
		List<ProgramacaoDTO> listDto = programacaoService.listarPorIds(ids);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarAtivosPorIds")
	ResponseEntity<List<ProgramacaoDTO>> listarAtivosPorIds(@RequestParam(value = "ids") List<Long> ids) {
		List<ProgramacaoDTO> listDto = programacaoService.listarAtivosPorIds(ids);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarInativasPorIds")
	ResponseEntity<List<RespostaIdDTO>> listarInativasPorIds(@RequestParam(value = "ids") List<Long> ids) {
		List<RespostaIdDTO> listDto = programacaoService.listarInativasPorIds(ids);
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/listarEncerradasPorIds")
	ResponseEntity<List<RespostaIdDTO>> listarEncerradasPorIds(@RequestParam(value = "ids") List<Long> ids) {
		List<RespostaIdDTO> listDto = programacaoService.listarEncerradasPorIds(ids);
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/encerrar")
	ResponseEntity<List<RespostaIdDTO>> encerrar(@RequestBody List<ProgramacaoDTO> programacoesDTO) {
		List<RespostaIdDTO> listDto = programacoesDTO.stream().map(dto -> new RespostaIdDTO(programacaoService.encerrar(dto).getId())).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/atualizar")
	ResponseEntity<List<RespostaIdDTO>> atualizar(@RequestBody List<ProgramacaoDTO> programacoesDTO) {
		List<RespostaIdDTO> listDto = programacoesDTO.stream().map(dto -> new RespostaIdDTO(programacaoService.salvar(dto).getId())).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/cadastrar")
	ResponseEntity<List<RespostaProgramacaoCadastroDTO>> cadastrar(@RequestBody List<ProgramacaoDTO> programacoesDTO) {
		List<RespostaProgramacaoCadastroDTO> listDto = programacoesDTO.stream().map(dto -> new RespostaProgramacaoCadastroDTO(programacaoService.salvar(dto).getId(), dto.getIdApp())).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/trocarCarro")
	ResponseEntity<List<RespostaIdDTO>> trocarCarro(@RequestBody List<TrocaCarroProgramacaoDTO> trocas) {
		List<RespostaIdDTO> listDto = trocas.stream().map(dto -> new RespostaIdDTO(programacaoService.trocarCarro(dto).getId())).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/trocarMotorista")
	ResponseEntity<List<RespostaIdDTO>> trocarMotorista(@RequestBody List<TrocaMotoristaProgramacaoDTO> trocas) {
		List<RespostaIdDTO> listDto = trocas.stream().map(dto -> new RespostaIdDTO(programacaoService.trocarMotorista(dto).getId())).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping(value = "/inativar")
	ResponseEntity<List<RespostaIdDTO>> inativar(@RequestBody List<ProgramacaoDTO> programacoesDTO) {
		List<RespostaIdDTO> listDto = programacoesDTO.stream().map(dto -> new RespostaIdDTO(programacaoService.inativar(dto).getId())).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
}
