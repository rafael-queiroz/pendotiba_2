package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.Perfil;

@Component
public class PerfilConverter implements Converter<String, Perfil>{

	@Override
	public Perfil convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			Perfil perfil = new Perfil();
			perfil.setId(Long.valueOf(id));
			return perfil;
		}
		
		return null;
	}

}
