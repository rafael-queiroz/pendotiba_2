package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.TipoObservacao;
import br.com.logic.pendotiba.core.repository.TipoObservacaoRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class TipoObservacaoService {
	
	@Autowired
	TipoObservacaoRepository tipoObservacaoRepository;
	

	
	public void salvar(TipoObservacao tipoObservacao){
		Optional<TipoObservacao> tipoObservacaoPersistido = tipoObservacaoRepository.findByDescricao(tipoObservacao.getDescricao());
		if (tipoObservacaoPersistido.isPresent() && !tipoObservacaoPersistido.get().getId().equals(tipoObservacao.getId()))
			throw new NegocioException("Já existe um tipo de observação com os dados informados");
		
		tipoObservacaoRepository.save(tipoObservacao);
	}
	

	public void excluir(TipoObservacao tipoObservacao){
		try {
			tipoObservacaoRepository.delete(tipoObservacao);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe observação cadastrada para o tipo " + tipoObservacao);
		}
	}

}
