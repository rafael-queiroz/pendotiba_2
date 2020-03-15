package br.com.logic.pendotiba.logicbus.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Reclamacao;
import br.com.logic.pendotiba.logicbus.filter.ReclamacaoFilter;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class ReclamacaoRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	
	@SuppressWarnings("unchecked")
	public Page<Reclamacao> filtrar(ReclamacaoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Reclamacao.class);

		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		
		criteria.addOrder(Order.asc("hora"));
		
		List<Reclamacao> filtrados = criteria.list();
		
		return new PageImpl<>(filtrados, pageable, total(filtro));
	}

	
	void criarFiltro(ReclamacaoFilter filtro, Criteria criteria) {
		if (filtro != null) {
			//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			/*
			if (filtro.getDataInicial() != null)	
				criteria.add(Restrictions.ge("hora", filtro.getDataInicial()));
			
			if (filtro.getDataFinal() != null)
				criteria.add(Restrictions.le("hora", filtro.getDataFinal()));
			*/
			
			if (filtro.getMotorista() != null) {
				criteria.createAlias("programacao", "programacao");
				criteria.add(Restrictions.eq("programacao.motorista", filtro.getMotorista()));
			}
			
			if (filtro.getLinha() != null) 
				criteria.add(Restrictions.eq("linha", filtro.getLinha()));
			
			if (filtro.getTipoReclamacao() != null) 
				criteria.add(Restrictions.eq("tipoReclamacao", filtro.getTipoReclamacao()));
			
			if (filtro.getCarro() != null && filtro.getCarro().getId() != null) { 
				criteria.add(Restrictions.eq("carro", filtro.getCarro()));
			}
			
		}
	}
	
	
	Long total(ReclamacaoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Reclamacao.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		Long uniqueResult = (Long) criteria.uniqueResult();
		return uniqueResult;
	}

}
