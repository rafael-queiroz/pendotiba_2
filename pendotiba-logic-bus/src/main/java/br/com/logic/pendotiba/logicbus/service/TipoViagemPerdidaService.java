package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.TipoViagemPerdida;
import br.com.logic.pendotiba.core.repository.TipoViagemPerdidaRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class TipoViagemPerdidaService {
	
	@Autowired
	TipoViagemPerdidaRepository tipoViagemPerdidaRepository;
	
	
	public void salvar(TipoViagemPerdida tipoViagemPerdida){
		Optional<TipoViagemPerdida> tipoViagemPerdidaPersistido = tipoViagemPerdidaRepository.findByDescricao(tipoViagemPerdida.getDescricao());
		if (tipoViagemPerdidaPersistido.isPresent() && !tipoViagemPerdidaPersistido.get().getId().equals(tipoViagemPerdida.getId()))
			throw new NegocioException("Já existe um tipo de viagem perdida com os dados informados");
		
		tipoViagemPerdidaRepository.save(tipoViagemPerdida);
	}
	

	public void excluir(TipoViagemPerdida tipoViagemPerdida){
		try {
			tipoViagemPerdidaRepository.delete(tipoViagemPerdida);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe viagem cadastrado para o tipo " + tipoViagemPerdida);
		}
	}

}
