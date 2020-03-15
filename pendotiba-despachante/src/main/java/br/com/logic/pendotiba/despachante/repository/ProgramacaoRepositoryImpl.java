package br.com.logic.pendotiba.despachante.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.util.DataUtil;

@Repository
public class ProgramacaoRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	public List<Programacao> listarPorPontoDataCompetenciaIds(Ponto ponto, Date dataCompetencia, List<Long> ids) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT DISTINCT p FROM Programacao p join fetch p.viagens v  ");
		query.append(" WHERE (v.pontoLinha.pontoOrigem = :pPonto OR v.pontoLinha.pontoDestino = :pPonto) AND p.completa = true ");
		query.append(" AND (p.dataCompetencia = :pDataCompetencia OR (p.dataCompetencia = :pDataOntem AND p.inicioTrabalho >= :pHoraInicioTrabalho) ) ");
		
		if(ids != null && !ids.isEmpty())
			query.append(" AND p.id not in (:pIds) ");
		
		TypedQuery<Programacao> typedQuery = manager.createQuery(query.toString(), Programacao.class)
				.setParameter("pPonto", ponto)
				.setParameter("pDataOntem", DataUtil.getDataAnteriorEmDias(dataCompetencia, 1))
				.setParameter("pHoraInicioTrabalho", DataUtil.getTime("19:00"))
				.setParameter("pDataCompetencia", dataCompetencia);
		
		if(ids != null && !ids.isEmpty())
			typedQuery.setParameter("pIds", ids);
		
		List<Programacao> resultList = typedQuery.getResultList();
		return resultList; 
	}
	
	public Programacao buscarPorDataCompetenciaCarroProgramadoAtiva(Programacao programacao, Carro carro) {
		try {
			StringBuilder query = new StringBuilder();
			
			query.append(" SELECT p FROM Programacao p WHERE p.status.id = :pStatus AND p <> :pProgramacao AND p.carroRealizado = :pCarro ");
			//query.append(" AND p.dataCompetencia = :pDataCompetencia AND (p.carroProgramado = :pCarro OR p.carroRealizado = :pCarro)");
			query.append(" AND (p.dataCompetencia = :pDataCompetencia OR (p.dataCompetencia = :pDataOntem AND p.horaLiberacao >= :pHoraLiberacao) )");
			
			return manager.createQuery(query.toString(), Programacao.class)
					.setParameter("pDataOntem", DataUtil.getDataAnteriorEmDias(programacao.getDataCompetencia(), 1))
					.setParameter("pHoraLiberacao", DataUtil.getTime("19:00"))
					.setParameter("pDataCompetencia", programacao.getDataCompetencia())
					.setParameter("pProgramacao", programacao)
					.setParameter("pCarro", carro)
					.setParameter("pStatus", Status.LIBERADO)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
