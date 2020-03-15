package br.com.logic.pendotiba.logicbus.repo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioCarroFilter;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class MapaDiarioCarroRepositoryImpl  {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	@SuppressWarnings("unchecked")
	public Page<MapaDiarioCarro> filtrar(MapaDiarioCarroFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MapaDiarioCarro.class);

		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		
		criteria.addOrder(Order.desc("dataCompetencia"));
		criteria.addOrder(Order.asc("carro.numeroDeOrdem")); 
		criteria.addOrder(Order.desc("dataHoraCadastroAbastecimentoDiesel"));
		
		List<MapaDiarioCarro> filtrados = criteria.list();
		
		return new PageImpl<>(filtrados, pageable, total(filtro));
	}

	
	void criarFiltro(MapaDiarioCarroFilter filtro, Criteria criteria) {
		criteria.createAlias("carro", "carro");
		if (filtro != null) {
			if (filtro.getDataInicial() != null)	
				criteria.add(Restrictions.ge("dataCompetencia", filtro.getDataInicial()));
			
			if (filtro.getDataFinal() != null)
				criteria.add(Restrictions.le("dataCompetencia", filtro.getDataFinal()));
			
			if (filtro.getCarro() != null && filtro.getCarro().getId() != null) 
				criteria.add(Restrictions.eq("carro", filtro.getCarro()));
			
			if (filtro.getBombaAbastecimento() != null && filtro.getBombaAbastecimento().getId() != null) {
				criteria.add(Restrictions.or(Restrictions.eq("bombaAbastecimentoArla", filtro.getBombaAbastecimento()), 
											 Restrictions.eq("bombaAbastecimentoDiesel", filtro.getBombaAbastecimento())
											 ));
			}
		}
	}
	
	
	Long total(MapaDiarioCarroFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MapaDiarioCarro.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		Long uniqueResult = (Long) criteria.uniqueResult();
		return uniqueResult;
	}


	@SuppressWarnings("unchecked")
	public List<MapaDiarioCarro> listarPorFiltro(MapaDiarioCarroFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MapaDiarioCarro.class);

		criarFiltro(filtro, criteria);
		
		criteria.addOrder(Order.asc("linha"));
		criteria.addOrder(Order.asc("carro.numeroDeOrdem")); 
		criteria.addOrder(Order.asc("dataCompetencia"));
		criteria.addOrder(Order.asc("dataHoraCadastroAbastecimentoDiesel"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<MapaDiarioCarro> listarParaExportacaoDieselTransoft(MapaDiarioCarroFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MapaDiarioCarro.class);

		criarFiltro(filtro, criteria);
		criteria.add(Restrictions.eq("carro.exportaTransoft", true));
		criteria.add(Restrictions.isNotNull("bombaAbastecimentoDiesel"));
		
		criteria.addOrder(Order.asc("linha"));
		criteria.addOrder(Order.asc("carro.numeroDeOrdem")); 
		criteria.addOrder(Order.asc("dataCompetencia"));
		criteria.addOrder(Order.asc("dataHoraCadastroAbastecimentoDiesel"));
		
		List<MapaDiarioCarro> list = criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<MapaDiarioCarro> listarParaExportacaoArlaTransoft(MapaDiarioCarroFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(MapaDiarioCarro.class);

		criarFiltro(filtro, criteria);
		criteria.add(Restrictions.eq("carro.exportaTransoft", true));
		criteria.add(Restrictions.isNotNull("bombaAbastecimentoArla"));
	//	criteria.add(Restrictions.not("0.0"));
		
		criteria.addOrder(Order.asc("linha"));
		criteria.addOrder(Order.asc("carro.numeroDeOrdem")); 
		criteria.addOrder(Order.asc("dataCompetencia"));
		criteria.addOrder(Order.asc("dataHoraCadastroAbastecimentoDiesel"));
		
		List<MapaDiarioCarro> list = criteria.list();
		return list;
	}	
	
	public MapaDiarioCarro buscarMapaAnteriorPorDataPorCarro(Date dataCompetencia, Carro carro) {
		try {
			StringBuilder query = new StringBuilder();
			query.append(" SELECT mapa FROM MapaDiarioCarro mapa WHERE mapa.dataCompetencia <= :pDataCompetencia AND mapa.carro = :pCarro ORDER BY mapa.dataCompetencia DESC ");
			return manager.createQuery(query.toString(), MapaDiarioCarro.class)
					.setParameter("pDataCompetencia", dataCompetencia)
					.setParameter("pCarro", carro)
					.setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MapaDiarioCarro buscarProximoMapaPorDataPorCarro(Date dataCompetencia, Carro carro) {
		try {
			StringBuilder query = new StringBuilder();
			query.append(" SELECT mapa FROM MapaDiarioCarro mapa WHERE mapa.dataCompetencia > :pDataCompetencia AND mapa.carro = :pCarro ORDER BY mapa.dataCompetencia ");
			return manager.createQuery(query.toString(), MapaDiarioCarro.class)
					.setParameter("pDataCompetencia", dataCompetencia)
					.setParameter("pCarro", carro)
					.setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
