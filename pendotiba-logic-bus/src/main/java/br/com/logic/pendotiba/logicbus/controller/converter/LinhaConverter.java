package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.repository.LinhaRepository;

@Component
public class LinhaConverter implements Converter<Long, Linha>{
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Override
	public Linha convert(Long id) {
		if (id != null)
			return linhaRepository.findOne(id);
		
		return null;
	}

}
