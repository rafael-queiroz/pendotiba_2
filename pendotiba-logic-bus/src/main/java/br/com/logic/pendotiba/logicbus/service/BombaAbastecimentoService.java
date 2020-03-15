package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class BombaAbastecimentoService {
	
	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;
	

	
	public void salvar(BombaAbastecimento bombaAbastecimento){
		Optional<BombaAbastecimento> bombaAbastecimentoPersistido = bombaAbastecimentoRepository.findByDescricao(bombaAbastecimento.getDescricao());
		if (bombaAbastecimentoPersistido.isPresent() && !bombaAbastecimentoPersistido.get().getId().equals(bombaAbastecimento.getId()))
			throw new NegocioException("Já existe um motivo com os dados informados");
		
		bombaAbastecimentoRepository.save(bombaAbastecimento);
	}
	

	public void excluir(BombaAbastecimento bombaAbastecimento){
		try {
			bombaAbastecimentoRepository.delete(bombaAbastecimento);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe mapeamento para a bomba de abastecimento " + bombaAbastecimento);
		}
	}

}
