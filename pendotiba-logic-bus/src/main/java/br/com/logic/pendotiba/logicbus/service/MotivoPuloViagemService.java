package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.MotivoPuloViagem;
import br.com.logic.pendotiba.core.repository.MotivoPuloViagemRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class MotivoPuloViagemService {
	
	@Autowired
	MotivoPuloViagemRepository motivoPuloViagemRepository;
	

	
	public void salvar(MotivoPuloViagem motivoPuloViagem){
		Optional<MotivoPuloViagem> motivoPuloViagemPersistido = motivoPuloViagemRepository.findByDescricao(motivoPuloViagem.getDescricao());
		if (motivoPuloViagemPersistido.isPresent() && !motivoPuloViagemPersistido.get().getId().equals(motivoPuloViagem.getId()))
			throw new NegocioException("Já existe um motivo com os dados informados");
		
		motivoPuloViagemRepository.save(motivoPuloViagem);
	}
	

	public void excluir(MotivoPuloViagem motivoPuloViagem){
		try {
			motivoPuloViagemRepository.delete(motivoPuloViagem);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe troca cadastrada para o motivo " + motivoPuloViagem);
		}
	}

}
