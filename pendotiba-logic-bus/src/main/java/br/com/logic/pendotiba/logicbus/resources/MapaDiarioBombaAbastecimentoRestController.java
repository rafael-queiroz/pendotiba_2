package br.com.logic.pendotiba.logicbus.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.enums.TipoBombaEnum;
import br.com.logic.pendotiba.core.model.MapaDiarioBombaAbastecimento;
import br.com.logic.pendotiba.core.repository.MapaDiarioBombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.resources.dto.MapaDiarioBombaAbastecimentoDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.RespostaMensagemDTO;
import br.com.logic.pendotiba.logicbus.service.MapaDiarioBombaAbastecimentoService;
import br.com.logic.pendotiba.logicbus.vo.StatusDescricaoVO;

@RestController
@RequestMapping("/rest/mapa-diario-bomba-abastecimento")
@CrossOrigin(value = "*")
public class MapaDiarioBombaAbastecimentoRestController {

	@Autowired
	MapaDiarioBombaAbastecimentoService mapaDiarioBombaAbastecimentoService;
	
	@Autowired
	MapaDiarioBombaAbastecimentoRepository mapaDiarioBombaAbastecimentoRepository;
	
	
	@Transactional
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RespostaMensagemDTO salvar(@RequestBody MapaDiarioBombaAbastecimentoDTO dto, BindingResult result) {
		try {
			 mapaDiarioBombaAbastecimentoService.salvarMapaApp(dto);
			return new RespostaMensagemDTO("success", "Mapa salvo com sucesso");
		} catch (Exception e) {
			return new RespostaMensagemDTO("error", "Erro ao salvar mapa");
		}
	}
	
	
	@GetMapping(value = "/diesel", produces = MediaType.APPLICATION_JSON_VALUE)
	List<MapaDiarioBombaAbastecimentoDTO> listarPorDataCompetenciaDiesel() {
		List<MapaDiarioBombaAbastecimentoDTO> listDto = new ArrayList<>();
		List<MapaDiarioBombaAbastecimento> list = mapaDiarioBombaAbastecimentoRepository.findByDataCompetenciaAndBombaAbastecimentoTipoBombaOrderByBombaAbastecimentoDescricao(DataUtil.getDataAppMapaBombaAbastecimento(), TipoBombaEnum.DIESEL);
		list.forEach(obj -> listDto.add(new MapaDiarioBombaAbastecimentoDTO(obj)));
		return listDto;
	}
	
	@GetMapping(value = "/arla", produces = MediaType.APPLICATION_JSON_VALUE)
	List<MapaDiarioBombaAbastecimentoDTO> listarPorDataCompetenciaArla() {
		List<MapaDiarioBombaAbastecimentoDTO> listDto = new ArrayList<>();
		List<MapaDiarioBombaAbastecimento> list = mapaDiarioBombaAbastecimentoRepository.findByDataCompetenciaAndBombaAbastecimentoTipoBombaOrderByBombaAbastecimentoDescricao(DataUtil.getDataAppMapaBombaAbastecimento(), TipoBombaEnum.ARLA);
		list.forEach(obj -> listDto.add(new MapaDiarioBombaAbastecimentoDTO(obj)));
		return listDto;
	}
	
	@GetMapping(value = "/pode-abastecer", produces = MediaType.APPLICATION_JSON_VALUE)
	StatusDescricaoVO podeAbastecer() {
		return mapaDiarioBombaAbastecimentoService.podeAbastecer();
	}
	
}
