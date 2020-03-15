package br.com.logic.pendotiba.logicbus.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.resources.dto.AbastecimentoOdometroRoletaDTO;
import br.com.logic.pendotiba.logicbus.service.CarroService;
import br.com.logic.pendotiba.logicbus.service.MapaDiarioCarroService;

@RestController
@RequestMapping("/rest/abastecimento-odometro-roleta")
@CrossOrigin(value = "*")
public class MapaDiarioCarroRestController {

	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	@Autowired
	MapaDiarioCarroService mapaDiarioCarroService;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	CarroRepository carroRepository;
	
	
	

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<AbastecimentoOdometroRoletaDTO> listarPorDataCompetencia() {
		List<AbastecimentoOdometroRoletaDTO> listDto = new ArrayList<>();
		List<MapaDiarioCarro> list = mapaDiarioCarroRepository.findByDataCompetenciaOrderByCarroNumeroDeOrdem(DataUtil.getDataAppAbastecimentoOdometroRoleta());
		list.forEach(obj -> listDto.add(new AbastecimentoOdometroRoletaDTO(obj)));
		return listDto;
	}
	
	@GetMapping(value = "/roleta/incompleto" ,produces = MediaType.APPLICATION_JSON_VALUE)
	List<AbastecimentoOdometroRoletaDTO> listarRoletasIncompletas() {
		List<AbastecimentoOdometroRoletaDTO> listDto = new ArrayList<>();
		List<MapaDiarioCarro> list = mapaDiarioCarroRepository.listarRoletasIncompletas(DataUtil.getDataAppAbastecimentoOdometroRoleta());
		list.forEach(obj -> listDto.add(new AbastecimentoOdometroRoletaDTO(obj)));
		return listDto;
	}
	
	@GetMapping(value = "/abastecimento-odometro/incompleto" ,produces = MediaType.APPLICATION_JSON_VALUE)
	List<AbastecimentoOdometroRoletaDTO> listarAbastecimentoOdometroIncompletos() {
		List<AbastecimentoOdometroRoletaDTO> listDto = new ArrayList<>();
		List<MapaDiarioCarro> list = mapaDiarioCarroRepository.listarAbastecimentoOdometoIncompletos(DataUtil.getDataAppAbastecimentoOdometroRoleta());
		list.forEach(obj -> listDto.add(new AbastecimentoOdometroRoletaDTO(obj)));
		return listDto;
	}
	
	@GetMapping(value = "/abastecimento-ou-odometro-nulo" ,produces = MediaType.APPLICATION_JSON_VALUE)
	List<AbastecimentoOdometroRoletaDTO> listarAbastecimentosOuOdometroNulos() {
		List<AbastecimentoOdometroRoletaDTO> listDto = new ArrayList<>();
		List<MapaDiarioCarro> list = mapaDiarioCarroRepository.listarAbastecimentosOuOdometroNulos(DataUtil.getDataAppAbastecimentoOdometroRoleta());
		list.forEach(obj -> listDto.add(new AbastecimentoOdometroRoletaDTO(obj)));
		return listDto;
	}
	
	@GetMapping(value = "/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
	AbastecimentoOdometroRoletaDTO buscarPorId(@PathVariable("id") Long id) {
		MapaDiarioCarro obj = mapaDiarioCarroRepository.findOne(id);
		if(obj != null)
			return new AbastecimentoOdometroRoletaDTO(obj);
		return null;
	}
	
	/*
	@Transactional
	@PostMapping(value = "/salvarRoleta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RespostaMensagemDTO salvarRoleta(@RequestBody AbastecimentoOdometroRoletaDTO dto, BindingResult result) {
		try {
			if(dto.getRoleta() != null) {
				MapaDiarioCarro obj = mapaDiarioCarroService.salvarRoleta(dto);
				carroService.atualizarRoletas(obj.getCarro(), obj.getRoleta());
			}
			return new RespostaMensagemDTO("success", "Roleta salvo com sucesso");
		} catch (Exception e) {
			return new RespostaMensagemDTO("error", "Erro ao salvar roleta");
		}
	}
	
	@Transactional
	@PostMapping(value = "/salvarAbastecimentoOdometro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RespostaMensagemDTO salvarAbastecimentoOdometro(@RequestBody AbastecimentoOdometroRoletaDTO dto, BindingResult result) {
		try {
			if(dto.getId() == null) {
				MapaDiarioCarro obj = new MapaDiarioCarro(DataUtil.getDataAppAbastecimentoOdometroRoleta(), carroRepository.findOne(dto.getIdCarro()));
				MapaDiarioCarro objSalvo = mapaDiarioCarroService.salvar(obj);
				dto.setId(objSalvo.getId());
			}
			
			if (dto.getVolumeDiesel() != null || dto.getOdometro() != null || dto.getVolumeArla() != null) {
				if(dto.getVolumeDiesel() != null) {
					MapaDiarioCarro obj = mapaDiarioCarroService.salvarAbastecimento(dto);
					carroService.atualizarUltimoAbastecimentoDiesel(obj.getCarro(), obj.getVolumeDiesel());
				}
				if(dto.getOdometro() != null) {
					MapaDiarioCarro obj = mapaDiarioCarroService.salvarOdometro(dto);
					carroService.atualizarOdometro(obj.getCarro(), obj.getOdometro());
				}
				if(dto.getVolumeArla() != null) {
					MapaDiarioCarro obj = mapaDiarioCarroService.salvarAbastecimentoArla(dto);
					carroService.atualizarUltimoAbastecimentoArla(obj.getCarro(), obj.getVolumeArla());
				}
			}
			return new RespostaMensagemDTO("success", "Abastecimento/Odômetro salvo(s) com sucesso");
		} catch (Exception e) {
			return new RespostaMensagemDTO("error", "Erro ao salvar Abastecimento/Odômetro");
		}
	}
	
	
	@Transactional
	@PostMapping(value = "/tanque-cheio", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RespostaMensagemDTO salvarTanqueCheio(@RequestBody AbastecimentoOdometroRoletaDTO dto, BindingResult result) {
		try {
			dto.setVolumeDiesel(BigDecimal.ZERO);
			dto.setVolumeArla(BigDecimal.ZERO);
			mapaDiarioCarroService.salvarTanqueCheio(dto);
			
			return new RespostaMensagemDTO("success", "Abastecimento/Odômetro salvo(s) com sucesso");
		} catch (Exception e) {
			return new RespostaMensagemDTO("error", "Erro ao salvar Abastecimento/Odômetro");
		}
	}
	*/
}
