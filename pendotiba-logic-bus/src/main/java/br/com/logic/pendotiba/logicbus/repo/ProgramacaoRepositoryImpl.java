package br.com.logic.pendotiba.logicbus.repo;

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

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.filter.ProgramacaoFilter;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class ProgramacaoRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	@SuppressWarnings("unchecked")
	public Page<Programacao> filtrar(ProgramacaoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Programacao.class);

		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		
		criteria.addOrder(Order.asc("dataCompetencia"));
		criteria.addOrder(Order.asc("inicioJornada")); 
		
		List<Programacao> filtrados = criteria.list();
		
		return new PageImpl<>(filtrados, pageable, total(filtro));
	}

	
	void criarFiltro(ProgramacaoFilter filtro, Criteria criteria) {
		if (filtro != null) {
			//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			criteria.add(Restrictions.eq("completa", filtro.getCompleta()));
			
			//if (filtro.getGaragem() != null && filtro.getGaragem().getId() != null) 
			//	criteria.add(Restrictions.eq("garagem", filtro.getGaragem()));
			
			if (filtro.getLinha() != null && filtro.getLinha().getId() != null) 
				criteria.add(Restrictions.eq("linha", filtro.getLinha()));
			
			if (!StringUtils.isEmpty(filtro.getCodigoEscala())) 
				criteria.add(Restrictions.ilike("codigoEscala", filtro.getCodigoEscala(), MatchMode.ANYWHERE));
			
			if (filtro.getDataInicial() != null)	
				criteria.add(Restrictions.ge("dataCompetencia", filtro.getDataInicial()));
			
			if (filtro.getDataFinal() != null)
				criteria.add(Restrictions.le("dataCompetencia", filtro.getDataFinal()));
			
			if (filtro.getMotorista() != null && filtro.getMotorista().getId() != null) 
				criteria.add(Restrictions.eq("motorista", filtro.getMotorista()));
			
			if (filtro.getStatus() != null) 
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			
			if (filtro.getTurno() != null) 
				criteria.add(Restrictions.eq("turno", filtro.getTurno()));
			
			if (filtro.getTiposCarro() != null && !filtro.getTiposCarro().isEmpty()) {
				criteria.createAlias("carroProgramado", "carroProgramado");
				criteria.add(Restrictions.in("carroProgramado.tipoCarro", filtro.getTiposCarro()));
			}
			
			/*
			if (filtro.getCarro() != null && filtro.getCarro().getId() != null) { 
				criteria.createAlias("roletas", "r", JoinType.LEFT_OUTER_JOIN);
				criteria.add(Restrictions.eq("r.carro", filtro.getCarro()));
			}
			*/
			// 22.02.2019
			if (filtro.getCarro() != null && filtro.getCarro().getId() != null) { 
				criteria.add(Restrictions.eq("carroRealizado", filtro.getCarro()));
			}
			
			if (filtro.getPonto() != null) {
				StringBuilder sql = new StringBuilder(); 
				sql.append("EXISTS (SELECT 1 FROM viagem v ");
					sql.append("LEFT JOIN ponto_linha pl ON v.id_ponto_linha = pl.id ");
					sql.append("WHERE this_.id = v.id_programacao ");
					sql.append("AND (pl.id_ponto_origem = ").append(filtro.getPonto().getId()).append("OR pl.id_ponto_destino = ").append(filtro.getPonto().getId()).append("))");
				criteria.add(Restrictions.sqlRestriction(sql.toString()));
			}
		}
	}
	
	
	Long total(ProgramacaoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Programacao.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		Long uniqueResult = (Long) criteria.uniqueResult();
		return uniqueResult;
	}


	/*
	
	public Programacao carregarProgramacao(Long id) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Programacao.class);
		criteria.createAlias("viagens", "v", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", id));
		return (Programacao) criteria.uniqueResult();
	}
	*/
	
	
	
	public List<Programacao> listarPorPonto(Ponto ponto, Date dataCompetencia, List<Long> ids) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT DISTINCT p FROM Programacao p join fetch p.viagens v ");
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


	
	public List<Programacao> listarPorStatusIds(Long idStatus, List<Long> ids) {
		return manager.createQuery(" SELECT p FROM Programacao p WHERE p.id IN (:pIds) AND p.status.id = :pStatus ", Programacao.class)
				.setParameter("pIds", ids)
				.setParameter("pStatus", idStatus)
				.getResultList();
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


	
	public Programacao buscarPorDataCompetenciaMotoristaAtiva(Programacao programacao) {
		try {
			StringBuilder query = new StringBuilder();
			
			query.append(" SELECT p FROM Programacao p WHERE p.status.id = :pStatus AND p <> :pProgramacao AND p.motorista = :pMotorista");
			query.append(" AND (p.dataCompetencia = :pDataCompetencia OR (p.dataCompetencia = :pDataOntem AND p.horaLiberacao >= :pHoraLiberacao) )");
			
			return manager.createQuery(query.toString(), Programacao.class)
					.setParameter("pDataOntem", DataUtil.getDataAnteriorEmDias(programacao.getDataCompetencia(), 1))
					.setParameter("pHoraLiberacao", DataUtil.getTime("19:00"))
					.setParameter("pDataCompetencia", programacao.getDataCompetencia())
					.setParameter("pProgramacao", programacao)
					.setParameter("pMotorista", programacao.getMotorista())
					.setParameter("pStatus", Status.LIBERADO)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	public List<Programacao> listarProgramacoes(ProgramacaoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Programacao.class);

		criarFiltro(filtro, criteria);
		
		criteria.addOrder(Order.asc("dataCompetencia"));
		criteria.addOrder(Order.asc("inicioJornada")); 
		
		return criteria.list();
	}

}
