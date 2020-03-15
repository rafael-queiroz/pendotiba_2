package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.TipoReclamacao;
import br.com.logic.pendotiba.core.repository.TipoReclamacaoRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class TipoReclamacaoService {
	
	@Autowired
	TipoReclamacaoRepository tipoReclamacaoRepository;
	
	
	public void salvar(TipoReclamacao tipoReclamacao){
		Optional<TipoReclamacao> tipoReclamacaoPersistido = tipoReclamacaoRepository.findByDescricao(tipoReclamacao.getDescricao());
		if (tipoReclamacaoPersistido.isPresent() && !tipoReclamacaoPersistido.get().getId().equals(tipoReclamacao.getId()))
			throw new NegocioException("Já existe um tipo de reclamação com os dados informados");
		
		tipoReclamacaoRepository.save(tipoReclamacao);
	}
	

	public void excluir(TipoReclamacao tipoReclamacao){
		try {
			tipoReclamacaoRepository.delete(tipoReclamacao);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe reclamação cadastrado para o tipo " + tipoReclamacao);
		}
	}

}
