package br.com.logic.pendotiba.logicbus.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
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

import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.logicbus.filter.UsuarioFilter;
import br.com.logic.pendotiba.logicbus.resources.dto.UsuarioDTO;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class UsuarioRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	
	
	public List<String> permissoes(Usuario usuario) {
		return manager.createQuery(
				"SELECT DISTINCT p.role FROM Usuario u INNER JOIN u.perfis pe INNER JOIN pe.permissoes p WHERE u = :pUsuario", String.class)
				.setParameter("pUsuario", usuario)
				.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criarFiltro(filtro, criteria);
		
		List<Usuario> filtrados = criteria.list();
		//filtrados.forEach(u -> Hibernate.initialize(u.getPerfis()));
		
		return new PageImpl<>(filtrados, pageable, total(filtro));
	}

	
	void criarFiltro(UsuarioFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.createAlias("funcionario", "funcionario");
				criteria.add(Restrictions.ilike("funcionario.nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}
			
			if(filtro.getPerfil() != null && filtro.getPerfil().getId() != null)
				criteria.add(Restrictions.eq("perfil", filtro.getPerfil()));
			
			//criteria.createAlias("perfis", "p", JoinType.LEFT_OUTER_JOIN);
			/*
			if (filtro.getPerfis() != null && !filtro.getPerfis().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for (Long idPerfil : filtro.getPerfis().stream().mapToLong(Perfil::getId).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioPerfil.class);
					dc.add(Restrictions.eq("id.perfil.id", idPerfil));
					dc.setProjection(Projections.property("id.usuario"));
					
					subqueries.add(Subqueries.propertyIn("id", dc));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.or(subqueries.toArray(criterions)));
			}
			*/
		}
	}
	
	
	Long total(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	/*
	
	public Usuario carregarUsuario(Long id) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.createAlias("perfis", "p", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Usuario) criteria.uniqueResult();
	}
	*/


	
	public List<UsuarioDTO> listasUsuariosDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.UsuarioDTO(u) FROM Usuario u", UsuarioDTO.class).getResultList();
	}
	
}
