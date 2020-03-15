package br.com.logic.pendotiba.logicbus.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.repository.ViagemRepository;
import br.com.logic.pendotiba.logicbus.service.UsuarioService;

@Service
public class ViagemValidation {
	
	@Autowired
	ViagemRepository viagemRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	
	public void validarViagem(Viagem viagem, BindingResult result) {
		// validar obrigatoriedades
		
		if(viagem.isNova()) {
			if(viagem.getHoraSaidaProgramada() == null)
				result.addError(new ObjectError("", "Hora saída programada é obrigatória"));
			
			if(viagem.getLinhaProgramada() == null)
				result.addError(new ObjectError("", "Linha programada é obrigatória"));
		} 
				
		if(viagem.getPontoLinha() == null)
			result.addError(new ObjectError("", "Relação ponto-linha é obrigatória"));
		
		if(viagem.getRoletaFinal1() == null)
			result.addError(new ObjectError("", "Roleta é obrigatória"));
	}
	
}
