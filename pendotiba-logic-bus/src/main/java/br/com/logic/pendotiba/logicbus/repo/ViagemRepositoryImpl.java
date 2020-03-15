package br.com.logic.pendotiba.logicbus.repo;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.filter.ViagemFilter;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class ViagemRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	@SuppressWarnings("unchecked")
	public Page<Viagem> filtrar(ViagemFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Viagem.class);

		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		
		criteria.addOrder(Order.asc("horaSaidaProgramada"));
		
		List<Viagem> filtrados = criteria.list();
		
		return new PageImpl<>(filtrados, pageable, total(filtro));
	}

	
	void criarFiltro(ViagemFilter filtro, Criteria criteria) {
		if (filtro != null) {
			//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			criteria.createAlias("programacao", "programacao");
			
			if (!StringUtils.isEmpty(filtro.getCodigoEscala())) 
				criteria.add(Restrictions.ilike("programacao.codigoEscala", filtro.getCodigoEscala(), MatchMode.ANYWHERE));
			
			if (filtro.getDataInicial() != null)	
				criteria.add(Restrictions.ge("programacao.dataCompetencia", filtro.getDataInicial()));
			
			if (filtro.getDataFinal() != null)
				criteria.add(Restrictions.le("programacao.dataCompetencia", filtro.getDataFinal()));
			
			if (filtro.getCarro() != null && filtro.getCarro().getId() != null) 
				criteria.add(Restrictions.eq("programacao.carroProgramado", filtro.getCarro()));
			
			if (filtro.getTurno() != null && filtro.getTurno().getId() != null) 
				criteria.add(Restrictions.eq("programacao.turno", filtro.getTurno()));
		}
	}
	
	
	Long total(ViagemFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Viagem.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		Long uniqueResult = (Long) criteria.uniqueResult();
		return uniqueResult;
	}
	
	
	
	public List<Viagem> listarPorPonto(Ponto ponto, Date data) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT v FROM Viagem v ");
		//query.append(" WHERE programacao.completa = true AND dataCompetencia between :pDataOntem AND :pData ");
		query.append(" WHERE programacao.completa = true AND dataCompetencia = :pData ");
		query.append(" AND (pontoLinha.pontoOrigem = :pPonto OR pontoLinha.pontoDestino = :pPonto) ");
		
		return manager.createQuery(query.toString(), Viagem.class)
				.setParameter("pPonto", ponto)
				//.setParameter("pDataOntem", DataUtil.getDataAnteriorEmDias(data, 1))
				.setParameter("pData", data)
				.getResultList();
	}


	
	public List<Viagem> listarProximasChegadasPorPonto(Date dataCompetencia, Ponto pontoDestino, List<Long> idsViagens) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT v FROM Viagem v WHERE v.tipoViagemPerdida is null and horaChegada is null AND horaSaidaRealizada is not null ");
		//query.append(" AND v.dataCompetencia = :pData AND pontoLinha.pontoDestino = :pPontoDestino ");
		query.append(" AND v.programacao.status.id = :pStatusLiberado AND pontoLinha.pontoDestino = :pPontoDestino "); 
		query.append(" AND (v.dataCompetencia = :pDataCompetencia OR (v.dataCompetencia = :pDataOntem AND v.programacao.inicioTrabalho >= :pHoraInicioTrabalho) ) ");
		
		//query.append(" AND v.dataCompetencia between :pDataOntem AND :pData AND pontoLinha.pontoDestino = :pPontoDestino "); // 22.01.2019 V
		//query.append(" AND v.programacao.inicioTrabalho >= :pHoraInicioTrabalho "); 
		
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


	
	public BigInteger buscarOrdemProximaVigem(Viagem viagem) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT MAX(ordemViagem)+1 FROM Viagem ");
		query.append(" WHERE programacao = :pProgramacao AND pontoLinha.sentido = :pSentido ");
		
		BigInteger resultado = manager.createQuery(query.toString(), BigInteger.class)
				.setParameter("pProgramacao", viagem.getProgramacao())
				.setParameter("pSentido", viagem.getPontoLinha().getSentido())
				.getSingleResult();
		
		return resultado != null ? resultado : BigInteger.ONE;
	}


	
	public List<Viagem> listarExtraPorPonto(Ponto pontoDestino, Date data) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT v FROM Viagem v ");
		query.append(" WHERE pontoLinha.pontoDestino = :pPontoDestino AND v.horaSaidaProgramada is null ");
		query.append(" AND programacao.completa = true AND dataCompetencia = :pData AND programacao.status.id <> :pIdStatus ");
		
		return manager.createQuery(query.toString(), Viagem.class)
				.setParameter("pPontoDestino", pontoDestino)
				.setParameter("pData", data)
				.setParameter("pIdStatus", Status.ENCERRADO)
				.getResultList();
	}


	
	public Viagem buscarUltimaViagemPorProgramacao(Programacao programacao) {
		//return manager.createQuery(" SELECT v FROM Viagem v WHERE v.programacao = :pProgramacao AND v.horaChegada is not null ORDER BY v.horaChegada DESC ", Viagem.class)
		try {
			return manager.createQuery(" SELECT v FROM Viagem v WHERE v.programacao = :pProgramacao AND v.horaChegada is not null ORDER BY v.ordemViagem DESC, v.id  DESC ", Viagem.class)
					.setParameter("pProgramacao", programacao)
					.setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	
	public List<Viagem> listarViagensPerdidasPorPonto(Ponto ponto, Date data, List<Long> idsViagens) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT DISTINCT v FROM Viagem v WHERE v.tipoViagemPerdida is not null AND dataCompetencia = :pData ");
		query.append(" AND (v.pontoLinha.pontoOrigem = :pPonto OR v.pontoLinha.pontoDestino = :pPonto)");
		if(idsViagens != null)
			query.append(" AND v.id not in (:pIdsViagens) ");
		
		TypedQuery<Viagem> typedQuery = manager.createQuery(query.toString(), Viagem.class).setParameter("pPonto", ponto).setParameter("pData", data);
		
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

	/*
	
	public Viagem carregarViagem(Long id) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Viagem.class);
		criteria.createAlias("erros", "e", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", id));
		return (Viagem) criteria.uniqueResult();
	}
	*/


	
	public List<Viagem> listarPorPontoProgramacoes(Ponto ponto, List<Long> idsProgramacoes) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT DISTINCT v FROM Viagem v WHERE ");
		query.append(" (v.pontoLinha.pontoOrigem = :pPonto OR v.pontoLinha.pontoDestino = :pPonto) ");
		query.append(" AND v.programacao.id in (:pIdsProgramacoes) ");
		
		return manager.createQuery(query.toString(), Viagem.class)
				.setParameter("pPonto", ponto)
				.setParameter("pIdsProgramacoes", idsProgramacoes)
				.getResultList();
	}


	
	public List<Viagem> listarViagensDescartadasPorPonto(Ponto ponto, List<Long> idsViagens) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT DISTINCT v FROM Viagem v WHERE v.id in (:pIdsViagens) ");
		query.append(" AND (v.pontoLinha.pontoOrigem <> :pPonto AND v.pontoLinha.pontoDestino <> :pPonto)");
		
		return manager.createQuery(query.toString(), Viagem.class).setParameter("pPonto", ponto).setParameter("pIdsViagens", idsViagens).getResultList();
	}
}
