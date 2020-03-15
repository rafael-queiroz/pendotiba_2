package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.repository.PontoRepository;

@Component
public class PontoConverter implements Converter<String, Ponto>{
	
	@Autowired
	PontoRepository pontoRepository;
	

	@Override
	public Ponto convert(String id) {
		if (!StringUtils.isEmpty(id))
			return pontoRepository.findOne(Long.valueOf(id));
		
		return null;
	}

}
