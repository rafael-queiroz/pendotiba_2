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

import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;
import br.com.logic.pendotiba.logicbus.filter.EntradaSaidaDeCarroDaGaragemFilter;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioEntradaSaidaPorHorarioFilter;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class EntradaSaidaDeCarroDaGaragemRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	@SuppressWarnings("unchecked")
	public Page<EntradaSaidaDeCarroDaGaragem> filtrar(EntradaSaidaDeCarroDaGaragemFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(EntradaSaidaDeCarroDaGaragem.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		criteria.createAlias("carro", "carro");
		criteria.addOrder(Order.asc("carro.numeroDeOrdem"));
		criteria.addOrder(Order.asc("dataCompetencia"));
		criarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	
	void criarFiltro(EntradaSaidaDeCarroDaGaragemFilter filtro, Criteria criteria) {
		if (filtro != null){
			
			if (filtro.getDataInicial() != null)	
				criteria.add(Restrictions.ge("dataCompetencia", filtro.getDataInicial()));
			
			if (filtro.getDataFinal() != null)
				criteria.add(Restrictions.le("dataCompetencia", filtro.getDataFinal()));
			
			if(filtro.getLinha() != null)
				criteria.add(Restrictions.eq("linha", filtro.getLinha()));
			
			if(filtro.getCarro() != null)
				criteria.add(Restrictions.eq("carro", filtro.getCarro()));
			
			if(filtro.getTurno() != null)
				criteria.add(Restrictions.eq("turno", filtro.getTurno()));
		}
	}
	
	
	Long total(EntradaSaidaDeCarroDaGaragemFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(EntradaSaidaDeCarroDaGaragem.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	
	public List<EntradaSaidaDeCarroDaGaragem> filtrar(EntradaSaidaDeCarroDaGaragemFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(EntradaSaidaDeCarroDaGaragem.class);
		
		criteria.createAlias("linha", "linha");
		criteria.addOrder(Order.asc("linha.codigo"));
		criteria.createAlias("turno", "turno");
		criteria.addOrder(Order.asc("turno.agrupamento"));
		criteria.createAlias("carro", "carro");
		criteria.addOrder(Order.asc("carro.numeroDeOrdem"));
		criteria.addOrder(Order.asc("dataCompetencia"));
		criarFiltro(filtro, criteria);
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	
	public List<EntradaSaidaDeCarroDaGaragem> filtrarParaRelatorioPorHorario(RelatorioEntradaSaidaPorHorarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(EntradaSaidaDeCarroDaGaragem.class);
		
		if(filtro.getLinha() != null)
			criteria.add(Restrictions.eq("linha", filtro.getLinha()));
		
		if(filtro.getTurno() != null)
			criteria.add(Restrictions.eq("turno", filtro.getTurno()));
		
		if(!filtro.getDatas().isEmpty())
			criteria.add(Restrictions.in("dataCompetencia", filtro.getDatas()));
		
		return criteria.list();
	}

}
