package br.com.logic.pendotiba.logicbus.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.Perfil;
import br.com.logic.pendotiba.logicbus.resources.dto.PerfilDTO;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class PerfilRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	

	@SuppressWarnings("unchecked")
	public Page<Perfil> filtrar(Perfil filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Perfil.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		
		List<Perfil> filtrados = criteria.list();
		filtrados.forEach(u -> Hibernate.initialize(u.getPermissoes()));
		
		return new PageImpl<>(filtrados, pageable, total(filtro));
	}

	
	void criarFiltro(Perfil filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome()))
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
	}
	
	
	Long total(Perfil filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Perfil.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	/*
	
	public Perfil carregarPerfil(Long id) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Perfil.class);
		criteria.createAlias("permissoes", "p", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Perfil) criteria.uniqueResult();
	}
	*/
	
	
	
	public List<PerfilDTO> listarPerfisDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.PerfilDTO(p) FROM Perfil p", PerfilDTO.class).getResultList();
	}
	
}
