package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.repository.PontoLinhaRepository;
import br.com.logic.pendotiba.core.util.Constantes;
import br.com.logic.pendotiba.core.util.NumberUtil;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class PontoLinhaService {
	
	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	

	public void salvar(PontoLinha pontoLinha){
		Optional<PontoLinha> pontoLinhaPersistido = pontoLinhaRepository.findByPontoOrigemAndPontoDestinoAndLinhaAndSentido(pontoLinha.getPontoOrigem(), pontoLinha.getPontoDestino(), pontoLinha.getLinha(), pontoLinha.getSentido());
		if (pontoLinhaPersistido.isPresent() && !pontoLinhaPersistido.get().getId().equals(pontoLinha.getId()))
			throw new NegocioException("Já existe uma relação ponto-linha com os dados informados");
		
		atualizarValores(pontoLinha);
		pontoLinhaRepository.save(pontoLinha);
	}
	
	
	public void excluir(PontoLinha pontoLinha){
		try {
			pontoLinhaRepository.delete(pontoLinha);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe registros cadastrados para esse ponto linha " + pontoLinha);
		}
	}
	
	
	
	void atualizarValores(PontoLinha pontoLinha) {
		// DISTANCIA OCIOSA
		if(!StringUtils.isEmpty(pontoLinha.getDistanciaOciosaStr()))
			pontoLinha.setDistanciaOciosa(NumberUtil.novoBigDecimal(pontoLinha.getDistanciaOciosaStr(), Constantes.ODOMETRO_CASAS_DECIMAIS));
		
		// DISTANCIA PRODUTIVA
		if(!StringUtils.isEmpty(pontoLinha.getDistanciaProdutivaStr()))
			pontoLinha.setDistanciaProdutiva(NumberUtil.novoBigDecimal(pontoLinha.getDistanciaProdutivaStr(), Constantes.ODOMETRO_CASAS_DECIMAIS));
	}
	
}
