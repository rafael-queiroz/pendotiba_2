package br.com.logic.pendotiba.logicbus.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.logicbus.resources.dto.PontoDTO;

@Repository
public class PontoRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;

	
	
	
	public List<PontoDTO> listarPontosDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.PontoDTO(p) FROM Ponto p", PontoDTO.class).getResultList();
	}
	
	
	
	public Ponto buscarPontoPorDispositivoMovel(String imeiDispositivoMovel) {
		return manager.createQuery(" SELECT p FROM Ponto p join fetch p.dispositivos d WHERE d.imei = :pImei ", Ponto.class)
				.setParameter("pImei", imeiDispositivoMovel)
				.getSingleResult();
	}


	
	public List<Ponto> listarPorLinha(Linha linha) {
		return manager.createQuery(" SELECT p FROM Ponto p LEFT JOIN PontoLinha pl ON pl.linha = :pLinha AND (p = pl.pontoOrigem OR p = pl.pontoDestino) ", Ponto.class)
				.setParameter("pLinha", linha)
				.getResultList();
	}
	
}
