package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.logic.pendotiba.core.model.CarroProgramacao;
import br.com.logic.pendotiba.core.repository.CarroProgramacaoRepository;

@Component
public class CarroProgramacaoConverter implements Converter<Long,  CarroProgramacao>{

	@Autowired
	CarroProgramacaoRepository  carroProgramacaoRepository;
	
	@Override
	public  CarroProgramacao convert(Long id) {
		//if (id != null)
			return carroProgramacaoRepository.findById(id).orElse(null);
		//return null;
	}

}
 