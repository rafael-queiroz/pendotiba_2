package br.com.logic.pendotiba.logicbus.repo;

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

import br.com.logic.pendotiba.core.model.ArquivoImportado;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class ArquivoImportadoRepositoryImpl  {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	
	@SuppressWarnings("unchecked")
	public Page<ArquivoImportado> filtrar(ArquivoImportado filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ArquivoImportado.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		criteria.addOrder(Order.desc("dataImportacao"));
		criteria.addOrder(Order.desc("dataCompetencia"));
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	
	void criarFiltro(ArquivoImportado filtro, Criteria criteria) {
		if (filtro != null) {
			criteria.add(Restrictions.eq("tipoArquivo", filtro.getTipoArquivo()));
			
			if (filtro.getDataCompetencia() != null)
				criteria.add(Restrictions.eq("dataCompetencia", filtro.getDataCompetencia()));
		}
	}
	
	
	Long total(ArquivoImportado filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ArquivoImportado.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}


	
	public ArquivoImportado carregarArquivoImportado(Long id) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ArquivoImportado.class);
		//criteria.createAlias("perfis", "p", JoinType.LEFT_OUTER_JOIN);
		//criteria.add(Restrictions.eq("id", id));
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (ArquivoImportado) criteria.uniqueResult();
	}
	
}
