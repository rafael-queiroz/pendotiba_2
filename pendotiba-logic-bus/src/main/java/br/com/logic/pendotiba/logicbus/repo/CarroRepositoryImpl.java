package br.com.logic.pendotiba.logicbus.repo;

import java.util.Date;
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
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.filter.CarroFilter;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class CarroRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	public Page<Carro> filtrar(CarroFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("numeroDeOrdem"));
		criarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	
	void criarFiltro(CarroFilter filtro, Criteria criteria) {
		if (filtro != null){
			
			criteria.add(Restrictions.eq("ativo", filtro.getAtivo()));
			
			if (!StringUtils.isEmpty(filtro.getNumeroDeOrdem())) 
				criteria.add(Restrictions.eq("numeroDeOrdem", filtro.getNumeroDeOrdem()));
			
			if(filtro.getTipoCarro() != null)
				criteria.add(Restrictions.eq("tipoCarro", filtro.getTipoCarro()));
			
			if(filtro.getTipoChassi() != null)
				criteria.add(Restrictions.eq("tipoChassi", filtro.getTipoChassi()));
			
			if (filtro.getDataCompetencia() != null) {
				StringBuilder sql = new StringBuilder(); 
				sql.append("EXISTS (SELECT 1 FROM movimentacao_carro mc ");
						sql.append(" LEFT JOIN carro c ON mc.id_carro = c.id ");
						sql.append(" WHERE this_.id = c.id AND CONVERT(varchar(12),mc.data_competencia, 112) = '").append(DataUtil.getDataStringYYYYMMDD(filtro.getDataCompetencia())).append("' )");
				criteria.add(Restrictions.sqlRestriction(sql.toString()));
			}
		}
	}
	
	
	Long total(CarroFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}


	
	public List<Carro> listarCarrosDisponiveisPorDataCompetencia(Date dataCompetencia) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT c FROM Carro c WHERE c NOT IN ( ");
			query.append(" SELECT DISTINCT p.carroProgramado FROM Programacao p WHERE p.dataCompetencia = :pDataCompetencia ");
		query.append(" ) ORDER BY numeroDeOrdem  ");
		
		return manager.createQuery(query.toString(), Carro.class).setParameter("pDataCompetencia", dataCompetencia).getResultList();
	}

}
