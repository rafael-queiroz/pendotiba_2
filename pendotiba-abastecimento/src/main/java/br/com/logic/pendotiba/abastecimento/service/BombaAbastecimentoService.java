package br.com.logic.pendotiba.abastecimento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;

@Service
public class BombaAbastecimentoService {
	
	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;
	

	public BombaAbastecimento buscarPorId(Long id) {
		return bombaAbastecimentoRepository.findById(id).orElse(null);
	}
}
