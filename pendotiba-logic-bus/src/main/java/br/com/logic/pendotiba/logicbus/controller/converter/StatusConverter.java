package br.com.logic.pendotiba.logicbus.controller.converter;

import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatusConverter implements Converter<String, Status>{

	@Autowired
	StatusRepository statusRepository;
	
	@Override
	public Status convert(String id) {
		//if (!StringUtils.isEmpty(id))
			return statusRepository.findById(Long.valueOf(id)).orElse(null);
		
		//return null;
	}

}
 