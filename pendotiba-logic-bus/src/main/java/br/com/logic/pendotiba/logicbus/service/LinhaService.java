package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class LinhaService {
	
	@Autowired
	LinhaRepository linhaRepository;
	

	public void salvar(Linha linha){
		Optional<Linha> linhaPersistido = linhaRepository.findByCodigo(linha.getCodigo());
		if (linhaPersistido.isPresent() && !linhaPersistido.get().getId().equals(linha.getId()))
			throw new NegocioException("Já existe uma linha com o código informado");
		
		linhaRepository.save(linha);
	}
	
	
	public void excluir(Linha linha){
		try {
			linhaRepository.delete(linha);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe registros cadastrados para a linha " + linha);
		}
	}


	public Linha buscarPorCodigo(String codigo) {
		Optional<Linha> obj = linhaRepository.findByCodigo(codigo.replaceFirst("0*", ""));
		return obj.isPresent() ? obj.get() : null;
	}
}
