package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.MotivoTrocaCarro;
import br.com.logic.pendotiba.core.repository.MotivoTrocaCarroRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class MotivoTrocaCarroService {
	
	@Autowired
	MotivoTrocaCarroRepository motivoTrocaCarroRepository;
	

	
	public void salvar(MotivoTrocaCarro motivoTrocaCarro){
		Optional<MotivoTrocaCarro> motivoTrocaCarroPersistido = motivoTrocaCarroRepository.findByDescricao(motivoTrocaCarro.getDescricao());
		if (motivoTrocaCarroPersistido.isPresent() && !motivoTrocaCarroPersistido.get().getId().equals(motivoTrocaCarro.getId()))
			throw new NegocioException("Já existe um motivo com os dados informados");
		
		motivoTrocaCarroRepository.save(motivoTrocaCarro);
	}
	

	public void excluir(MotivoTrocaCarro motivoTrocaCarro){
		try {
			motivoTrocaCarroRepository.delete(motivoTrocaCarro);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe troca cadastrada para o motivo " + motivoTrocaCarro);
		}
	}

}
