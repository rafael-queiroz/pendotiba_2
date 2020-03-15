package br.com.logic.pendotiba.despachante.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.util.DataUtil;

@Repository
public class ViagemRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	public Viagem buscarUltimaViagemPorProgramacao(Programacao programacao) {
		try {
			return manager.createQuery(" SELECT v FROM Viagem v WHERE v.programacao = :pProgramacao AND v.horaChegada is not null ORDER BY v.ordemViagem DESC, v.id  DESC ", Viagem.class)
					.setParameter("pProgramacao", programacao)
					.setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Viagem> listarPorPontoDataCompetencia(Ponto ponto, Date dataCompetencia) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT v FROM Viagem v ");
		query.append(" WHERE programacao.completa = true ");
		//query.append(" AND dataCompetencia = :pDataCompetencia ");
		query.append(" AND (v.dataCompetencia = :pDataCompetencia OR (v.dataCompetencia = :pDataOntem AND v.programacao.inicioTrabalho >= :pHoraInicioTrabalho) ) ");
		query.append(" AND (pontoLinha.pontoOrigem = :pPonto OR pontoLinha.pontoDestino = :pPonto) ");
		
		return manager.createQuery(query.toString(), Viagem.class)
				.setParameter("pPonto", ponto)
				.setParameter("pDataCompetencia", dataCompetencia)
				.setParameter("pDataOntem", DataUtil.getDataAnteriorEmDias(dataCompetencia, 1))
				.setParameter("pHoraInicioTrabalho", DataUtil.getTime("19:00"))
				.getResultList();
	}
	
	public List<Viagem> listarProximasChegadasPorPonto(Date dataCompetencia, Ponto pontoDestino, List<Long> idsViagens) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT v FROM Viagem v WHERE v.tipoViagemPerdida is null and horaChegada is null AND horaSaidaRealizada is not null ");
		query.append(" AND v.programacao.status.id = :pStatusLiberado AND pontoLinha.pontoDestino = :pPontoDestino "); 
		query.append(" AND (v.dataCompetencia = :pDataCompetencia OR (v.dataCompetencia = :pDataOntem AND v.programacao.inicioTrabalho >= :pHoraInicioTrabalho) ) ");
		
		
		if(idsViagens != null)
			query.append(" AND id not in (:pIdsViagens) ");
		
		TypedQuery<Viagem> typedQuery = manager.createQuery(query.toString(), Viagem.class)
				.setParameter("pPontoDestino", pontoDestino)
				.setParameter("pDataCompetencia", dataCompetencia)
				.setParameter("pStatusLiberado", Status.LIBERADO)
				.setParameter("pDataOntem", DataUtil.getDataAnteriorEmDias(dataCompetencia, 1)) // 22.01.2019 V
				.setParameter("pHoraInicioTrabalho", DataUtil.getTime("19:00"));
		
		if(idsViagens != null)
			typedQuery.setParameter("pIdsViagens", idsViagens);
			
		return typedQuery.getResultList();
	}
	
	public List<Viagem> listarPerdidasPorPonto(Date dataCompetencia, Ponto ponto, List<Long> idsViagens) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT DISTINCT v FROM Viagem v WHERE v.tipoViagemPerdida is not null AND dataCompetencia = :pDataCompetencia ");
		query.append(" AND (v.pontoLinha.pontoOrigem = :pPonto OR v.pontoLinha.pontoDestino = :pPonto)");
		if(idsViagens != null)
			query.append(" AND v.id not in (:pIdsViagens) ");
		
		TypedQuery<Viagem> typedQuery = manager.createQuery(query.toString(), Viagem.class).setParameter("pPonto", ponto).setParameter("pDataCompetencia", dataCompetencia);
		
		if(idsViagens != null)
			typedQuery.setParameter("pIdsViagens", idsViagens);
			
		return typedQuery.getResultList();
	}
	
	public Viagem buscarProximaVigem(Viagem viagem) {
		try {
			StringBuilder query = new StringBuilder();
			
			query.append(" SELECT v FROM Viagem v ");
			query.append(" WHERE v.programacao = :pProgramacao AND v.horaSaidaProgramada > :pHoraSaidaProgramada ORDER BY v.horaSaidaProgramada");

			return manager.createQuery(query.toString(), Viagem.class)
					.setParameter("pProgramacao", viagem.getProgramacao())
					.setParameter("pHoraSaidaProgramada", viagem.getHoraSaidaProgramada())
					.setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public BigInteger buscarOrdemProximaViagem(Viagem viagem) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT MAX(ordemViagem)+1 FROM Viagem ");
		query.append(" WHERE programacao = :pProgramacao AND pontoLinha.sentido = :pSentido ");
		
		BigInteger resultado = manager.createQuery(query.toString(), BigInteger.class)
				.setParameter("pProgramacao", viagem.getProgramacao())
				.setParameter("pSentido", viagem.getPontoLinha().getSentido())
				.getSingleResult();
		
		return resultado != null ? resultado : BigInteger.ONE;
	}

}
