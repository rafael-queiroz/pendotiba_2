package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.TipoCarro;

@Component
public class TipoVeiculoConverter implements Converter<String, TipoCarro>{

	@Override
	public TipoCarro convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			TipoCarro tipoVeiculo = new TipoCarro();
			tipoVeiculo.setId(Long.valueOf(id));
			return tipoVeiculo;
		}
		
		return null;
	}

}
