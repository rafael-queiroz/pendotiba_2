package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class PontoService {
	
	@Autowired
	PontoRepository pontoRepository;
	

	public void salvar(Ponto ponto){
		Optional<Ponto> pontoPersistido = pontoRepository.findByCodigoAndDescricao(ponto.getCodigo(), ponto.getDescricao());
		if (pontoPersistido.isPresent() && !pontoPersistido.get().getId().equals(ponto.getId()))
			throw new RuntimeException("Já existe um ponto com os dados informados");
		
		pontoRepository.save(ponto);
	}
	
	
	public void excluir(Ponto ponto){
		try {
			pontoRepository.delete(ponto);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe registros cadastrados para o ponto " + ponto);
		}
	}


	public Ponto buscarPorCodigo(String codigo) {
		Optional<Ponto> obj = pontoRepository.findByCodigo(codigo);
		return obj.isPresent() ? obj.get() : null;
	}
}
