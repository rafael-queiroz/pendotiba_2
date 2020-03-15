package br.com.logic.pendotiba.logicbus.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.enums.TipoBombaEnum;
import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;

@RestController
@RequestMapping("/rest/bomba-abastecimento")
@CrossOrigin(value = "*")
public class BombaAbastecimentoRestController {

	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;
	
	
	@GetMapping(value = "/diesel", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<BombaAbastecimento> listarBombaDeDiesel() {
		return bombaAbastecimentoRepository.findByTipoBombaOrderByCodigo(TipoBombaEnum.DIESEL);
	}
	
	@GetMapping(value = "/arla", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<BombaAbastecimento> listarBombaDeArla() {
		return bombaAbastecimentoRepository.findByTipoBombaOrderByCodigo(TipoBombaEnum.ARLA);
	}
}
