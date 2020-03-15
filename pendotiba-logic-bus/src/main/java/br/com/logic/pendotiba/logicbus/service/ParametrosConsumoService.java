package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.ParametrosConsumo;
import br.com.logic.pendotiba.core.repository.ParametrosConsumoRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class ParametrosConsumoService {
	
	@Autowired
	ParametrosConsumoRepository parametrosConsumoRepository;
	

	
	public void salvar(ParametrosConsumo parametrosConsumo){
		parametrosConsumo.atualizarValores();
		
		Optional<ParametrosConsumo> parametrosConsumoPersistido = parametrosConsumoRepository.findByLinhaAndTipoChassi(parametrosConsumo.getLinha(), parametrosConsumo.getTipoChassi());
		if (parametrosConsumoPersistido.isPresent() && !parametrosConsumoPersistido.get().getId().equals(parametrosConsumo.getId()))
			throw new NegocioException("Já existe pâmentros de consumo com os dados informados");
		
		parametrosConsumoRepository.save(parametrosConsumo);
	}
	

	public void excluir(ParametrosConsumo parametrosConsumo){
		try {
			parametrosConsumoRepository.delete(parametrosConsumo);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Erro ao tentar exluir " + parametrosConsumo);
		}
	}

}
