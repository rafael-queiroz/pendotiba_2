package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.logic.pendotiba.core.model.Turno;
import br.com.logic.pendotiba.core.repository.TurnoRepository;


@Component
public class TurnoConverter implements Converter<Long, Turno>{

	@Autowired
	TurnoRepository turnoRepository;

	@Override
	public Turno convert(Long id) {
		if (id != null)
			return turnoRepository.findOne(id);
		
		return null;
	}

}
