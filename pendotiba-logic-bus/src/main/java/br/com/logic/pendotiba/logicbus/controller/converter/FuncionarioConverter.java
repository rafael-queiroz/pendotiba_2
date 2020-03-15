package br.com.logic.pendotiba.logicbus.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;

@Component
public class FuncionarioConverter implements Converter<Long, Funcionario>{

	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	
	
	@Override
	public Funcionario convert(Long id) {
		//if (id != null)
			return funcionarioRepository.findById(id).orElse(null);
		//return null;
	}

}
