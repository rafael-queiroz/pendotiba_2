package br.com.logic.pendotiba.despachante.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.repository.PontoLinhaRepository;
import br.com.logic.pendotiba.despachante.exception.ObjectNotFoundException;

@Service
public class PontoLinhaService {
	
	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	

	public PontoLinha buscarPorPontoOrigemPontoDestinoLinha(Long idPontoOrigem, Long idPontoDestino, Long idLinha) {
		Optional<PontoLinha> obj = pontoLinhaRepository.findByPontoOrigemIdAndPontoDestinoIdAndLinhaId(idPontoOrigem, idPontoDestino, idLinha);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ponto origem: " + idPontoOrigem + ", ponto destino: " + idPontoDestino + " e linha:" + idLinha + ", Tipo: " + PontoLinha.class.getName()));
	}


	public PontoLinha buscarPontoLinhaInverso(PontoLinha pontoLinha) {
		Optional<PontoLinha> obj = pontoLinhaRepository.findByPontoOrigemIdAndPontoDestinoIdAndLinhaId(pontoLinha.getPontoDestino().getId(), pontoLinha.getPontoOrigem().getId(), pontoLinha.getLinha().getId());
		return obj.orElseThrow(() -> new ObjectNotFoundException("Ponto Linha inverso não encontrado! ponto origem: " + pontoLinha.getPontoOrigem().getId() + 
																									  ", ponto destino: " + pontoLinha.getPontoDestino().getId() + 
																									  " e linha:" + pontoLinha.getLinha().getId() + ", Tipo: " + PontoLinha.class.getName()));
	}
	
}
