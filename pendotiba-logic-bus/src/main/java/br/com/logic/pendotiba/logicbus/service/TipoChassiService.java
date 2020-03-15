package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.TipoChassi;
import br.com.logic.pendotiba.core.repository.TipoChassiRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class TipoChassiService {
	
	@Autowired
	TipoChassiRepository tipoChassiRepository;
	

	
	public void salvar(TipoChassi tipoChassi){
		Optional<TipoChassi> tipoChassiPersistido = tipoChassiRepository.findByNome(tipoChassi.getNome());
		if (tipoChassiPersistido.isPresent() && !tipoChassiPersistido.get().getId().equals(tipoChassi.getId()))
			throw new NegocioException("Já existe um tipo de chassi com os dados informados");
		
		tipoChassiRepository.save(tipoChassi);
	}
	

	public void excluir(TipoChassi tipoChassi){
		try {
			tipoChassiRepository.delete(tipoChassi);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe carro cadastrado para o tipo " + tipoChassi);
		}
	}

}
