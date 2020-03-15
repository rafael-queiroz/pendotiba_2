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

import br.com.logic.pendotiba.core.model.MapaDiarioBombaAbastecimento;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioBombaAbastecimentoFilter;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class MapaDiarioBombaAbastecimentoRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	@SuppressWarnings("unchecked")
	public Page<MapaDiarioBombaAbastecimento> filtrar(MapaDiarioBombaAbastecimentoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MapaDiarioBombaAbastecimento.class);

		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		
		criteria.createAlias("bombaAbastecimento", "bombaAbastecimento");
		criteria.addOrder(Order.desc("dataCompetencia"));
		criteria.addOrder(Order.asc("bombaAbastecimento.codigo"));
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	
	void criarFiltro(MapaDiarioBombaAbastecimentoFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (filtro.getDataInicial() != null)	
				criteria.add(Restrictions.ge("dataCompetencia", filtro.getDataInicial()));
			
			if (filtro.getDataFinal() != null)
				criteria.add(Restrictions.le("dataCompetencia", filtro.getDataFinal()));
			
			if (filtro.getBombaAbastecimento() != null && filtro.getBombaAbastecimento().getId() != null) 
				criteria.add(Restrictions.eq("bombaAbastecimento", filtro.getBombaAbastecimento()));
		}
	}
	
	
	Long total(MapaDiarioBombaAbastecimentoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MapaDiarioBombaAbastecimento.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		Long uniqueResult = (Long) criteria.uniqueResult();
		return uniqueResult;
	}


	@SuppressWarnings("unchecked")
	public List<MapaDiarioBombaAbastecimento> listarPorFiltro(MapaDiarioBombaAbastecimentoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MapaDiarioBombaAbastecimento.class);

		criarFiltro(filtro, criteria);
		
		criteria.createAlias("bombaAbastecimento", "bombaAbastecimento");
		criteria.addOrder(Order.asc("bombaAbastecimento.codigo"));
		criteria.addOrder(Order.asc("dataCompetencia"));
		
		return criteria.list();
	}
}
