package br.com.logic.pendotiba.logicbus.validation;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.com.logic.pendotiba.core.model.MapaDiarioCarro;

@Service
public class MapaDiarioCarroValidation {
	
	public void validarMapa(MapaDiarioCarro mapaDiarioCarro, BindingResult result) {
		if(StringUtils.isEmpty(mapaDiarioCarro.getVolumeDieselStr()))
			result.addError(new FieldError("", "volumeDieselStr", "Volume de diesel é obrigatória"));
			
		if(mapaDiarioCarro.getBombaAbastecimentoDiesel() == null)
			result.addError(new FieldError("", "bombaAbastecimentoDiesel", "Bomba de abastecimento diesel é obrigatório"));
	}

}
