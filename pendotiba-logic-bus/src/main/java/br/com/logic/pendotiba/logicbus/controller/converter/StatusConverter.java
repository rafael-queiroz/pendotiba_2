package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.repository.StatusRepository;

@Component
public class StatusConverter implements Converter<String, Status>{

	@Autowired
	StatusRepository statusRepository;
	
	@Override
	public Status convert(String id) {
		if (!StringUtils.isEmpty(id)) 
			return statusRepository.findOne(Long.valueOf(id));
		
		return null;
	}

}
 