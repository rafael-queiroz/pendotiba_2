package br.com.logic.pendotiba.logicbus.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.ParametrosConsumo;
import br.com.logic.pendotiba.logicbus.filter.ParametrosConsumoFilter;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class ParametrosConsumoRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	@SuppressWarnings("unchecked")
	public Page<ParametrosConsumo> filtrar(ParametrosConsumoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ParametrosConsumo.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	void criarFiltro(ParametrosConsumoFilter filtro, Criteria criteria) {
		if (filtro != null){

			if(filtro.getLinha() != null)
				criteria.add(Restrictions.eq("linha", filtro.getLinha()));
			
			if(filtro.getTipoChassi() != null)
				criteria.add(Restrictions.eq("tipoChassi", filtro.getTipoChassi()));
		}
	}
	
	Long total(ParametrosConsumoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ParametrosConsumo.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

}
