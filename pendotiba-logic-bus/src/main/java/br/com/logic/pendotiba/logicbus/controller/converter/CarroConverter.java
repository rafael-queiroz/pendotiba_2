package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.repository.CarroRepository;

@Component
public class CarroConverter implements Converter<Long, Carro>{

	@Autowired
	CarroRepository carroRepository;
	
	@Override
	public Carro convert(Long id) {
		if (id != null) 
			return carroRepository.findOne(id);
		return null;
	}

}
 