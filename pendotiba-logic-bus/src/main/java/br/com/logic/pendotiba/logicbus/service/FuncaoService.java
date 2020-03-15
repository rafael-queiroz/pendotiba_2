package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Funcao;
import br.com.logic.pendotiba.core.repository.FuncaoRepository;

@Service
public class FuncaoService {
	
	@Autowired
	FuncaoRepository funcaoRepository;
	
	
	
	public Funcao buscarOuGeralFuncaoPelaDescricao(String descricao) {
		Optional<Funcao> funcaoOptional = funcaoRepository.findByDescricao(descricao); 
		if(funcaoOptional.isPresent()) 
			return funcaoOptional.get();
		else {
			Funcao funcao = new Funcao();
			funcao.setDescricao(descricao);
			funcao.identificarTipoFuncao();
			return funcaoRepository.save(funcao);
		}
	}

}
