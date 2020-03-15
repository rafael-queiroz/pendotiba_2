package br.com.logic.pendotiba.abastecimento.resources;

import br.com.logic.pendotiba.abastecimento.dto.MapaDiarioCarroDTO;
import br.com.logic.pendotiba.abastecimento.dto.RespostaMensagemDTO;
import br.com.logic.pendotiba.abastecimento.service.CarroService;
import br.com.logic.pendotiba.abastecimento.service.MapaDiarioCarroService;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mapa-diario-carro")
@CrossOrigin(value = "*")
public class MapaDiarioCarroResource {

	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	@Autowired
	MapaDiarioCarroService mapaDiarioCarroService;
	
	@Autowired
	CarroService carroService;
	

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<MapaDiarioCarroDTO> listarPorDataCompetencia() {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.findByDataCompetenciaOrderByCarroNumeroDeOrdem(DataUtil.getDataAppAbastecimentoOdometroRoleta());
		List<MapaDiarioCarroDTO> mapasDTO = new ArrayList<>();
		mapas.forEach(mapa -> mapasDTO.add(new MapaDiarioCarroDTO(mapa)));
		return mapasDTO;
	}

	@GetMapping(value = "/roleta/incompleto" ,produces = MediaType.APPLICATION_JSON_VALUE)
	List<MapaDiarioCarroDTO> listarRoletasIncompletas() {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.listarRoletasIncompletas(DataUtil.getDataAppAbastecimentoOdometroRoleta());
		List<MapaDiarioCarroDTO> mapasDTO = new ArrayList<>();
		mapas.forEach(mapa -> mapasDTO.add(new MapaDiarioCarroDTO(mapa)));
		return mapasDTO;
	}
	
	@GetMapping(value = "/abastecimento-hodometro/incompleto" ,produces = MediaType.APPLICATION_JSON_VALUE)
	List<MapaDiarioCarroDTO> listarAbastecimentoOdometroIncompletos() {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.listarAbastecimentoOdometoIncompletos(DataUtil.getDataAppAbastecimentoOdometroRoleta());
		List<MapaDiarioCarroDTO> mapasDTO = new ArrayList<>();
		mapas.forEach(mapa -> mapasDTO.add(new MapaDiarioCarroDTO(mapa)));
		return mapasDTO;
	}
	
	@GetMapping(value = "/abastecimento-ou-hodometro-nulo" ,produces = MediaType.APPLICATION_JSON_VALUE)
	List<MapaDiarioCarroDTO> listarAbastecimentosOuOdometroNulos() {
		List<MapaDiarioCarroDTO> listDto = new ArrayList<>();
		List<MapaDiarioCarro> list = mapaDiarioCarroRepository.listarAbastecimentosOuOdometroNulos(DataUtil.getDataAppAbastecimentoOdometroRoleta());
		list.forEach(obj -> listDto.add(new MapaDiarioCarroDTO(obj)));
		return listDto;
	}
	
	
	@GetMapping(value = "/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
	MapaDiarioCarroDTO buscarPorId(@PathVariable("id") Long id) {
		Optional<MapaDiarioCarro> obj = mapaDiarioCarroRepository.findById(id);
		if(obj.isPresent())
			return new MapaDiarioCarroDTO(obj.get());
		return null;
	}
		
	
	@Transactional
	@PostMapping(value = "/roleta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RespostaMensagemDTO salvarRoleta(@RequestBody MapaDiarioCarroDTO dto, BindingResult result) {
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
	@PostMapping(value = "/abastecimento-hodometro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RespostaMensagemDTO salvarAbastecimentoOdometro(@RequestBody MapaDiarioCarroDTO dto, BindingResult result) {
		try {
			if(dto.getId() == null) {
				MapaDiarioCarro obj = new MapaDiarioCarro(DataUtil.getDataAppAbastecimentoOdometroRoleta(), carroService.buscarPorId(dto.getIdCarro()));
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
	@ResponseBody RespostaMensagemDTO salvarTanqueCheio(@RequestBody MapaDiarioCarroDTO dto, BindingResult result) {
		try {
			mapaDiarioCarroService.salvarTanqueCheio(dto);
			return new RespostaMensagemDTO("success", "Abastecimento/Odômetro salvo(s) com sucesso");
		} catch (Exception e) {
			return new RespostaMensagemDTO("error", "Erro ao salvar Abastecimento/Odômetro");
		}
	}
}
