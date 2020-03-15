package br.com.logic.pendotiba.logicbus.repo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.model.Turno;
import br.com.logic.pendotiba.logicbus.filter.FuncionarioFilter;
import br.com.logic.pendotiba.logicbus.resources.dto.FuncionarioDTO;
import br.com.logic.pendotiba.logicbus.util.PaginacaoUtil;

@Repository
public class FuncionarioRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	
	@SuppressWarnings("unchecked")
	public Page<Funcionario> filtrar(FuncionarioFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Funcionario.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		criarFiltro(filtro, criteria);
		
		criteria.addOrder(Order.asc("nome"));
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	
	void criarFiltro(FuncionarioFilter filtro, Criteria criteria) {
		if (filtro != null){
			
			criteria.add(Restrictions.eq("ativo", filtro.getAtivo()));
			
			if (!StringUtils.isEmpty(filtro.getMatricula())) 
				criteria.add(Restrictions.eq("matricula", filtro.getMatricula()));
			
			if (!StringUtils.isEmpty(filtro.getNome())) 
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			
			if (!StringUtils.isEmpty(filtro.getCartao())) 
				criteria.add(Restrictions.eq("cartao", filtro.getCartao()));
			
			if(filtro.getFuncao() != null)
				criteria.add(Restrictions.eq("funcao", filtro.getFuncao()));
		}
	}
	
	
	Long total(FuncionarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Funcionario.class);
		criarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	
	
	public List<FuncionarioDTO> listarFuncionariosDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.FuncionarioDTO(f) FROM Funcionario f", FuncionarioDTO.class).getResultList();
	}
	
	
	
	public List<FuncionarioDTO> listarMotoristasDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.FuncionarioDTO(f) FROM Funcionario f WHERE f.funcao.motorista is true AND f.ativo is true ", FuncionarioDTO.class).getResultList();
	}
	
	
	public List<FuncionarioDTO> listarCobradoresDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.FuncionarioDTO(f) FROM Funcionario f WHERE f.funcao.cobrador is true AND f.ativo is true ", FuncionarioDTO.class).getResultList();
	}
	
	
	
	public List<FuncionarioDTO> listarDespachantesDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.FuncionarioDTO(f) FROM Funcionario f WHERE f.funcao.despachante is true ", FuncionarioDTO.class).getResultList();
	}
	
	
	
	public List<Funcionario> listarPorMatriculaOuNome(String matriculaOuNome) {
		return manager.createQuery(" SELECT f FROM Funcionario f WHERE matricula like :pMatricula OR nome like :pNome ORDER BY f.matricula", Funcionario.class)
				.setParameter("pMatricula", "%".concat(StringUtils.upperCase(matriculaOuNome)).concat("%"))
				.setParameter("pNome", "%".concat(StringUtils.upperCase(matriculaOuNome)).concat("%"))
				.getResultList();
	}
	
	
	
	public List<Funcionario> listarMotoristasDisponiveisPorDataCompetencia(Date dataCompetencia) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT f FROM Funcionario f WHERE f.funcao.motorista is true AND f NOT IN ( ");
			query.append(" SELECT DISTINCT p.motorista FROM Programacao p WHERE p.dataCompetencia = :pDataCompetencia ");
		query.append(" ) ORDER BY matricula ");
		
		return manager.createQuery(query.toString(), Funcionario.class).setParameter("pDataCompetencia", dataCompetencia).getResultList();
	}
	
	
	public List<Funcionario> listarParceirosDisponiveisPorDataCompetencia(Date dataCompetencia) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT f FROM Funcionario f WHERE f.funcao.cobrador is true AND f NOT IN ( ");
			query.append(" SELECT DISTINCT p.parceiro FROM Programacao p WHERE p.dataCompetencia = :pDataCompetencia ");
		query.append(" ) ORDER BY matricula ");
		
		return manager.createQuery(query.toString(), Funcionario.class).setParameter("pDataCompetencia", dataCompetencia).getResultList();
	}

	//nao utilizado
	public List<Funcionario> listarMotoristasDisponiveisPorDataCompetencia(Date dataCompetencia, Turno turno) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT f FROM Funcionario f WHERE f.funcao.motorista is true AND f NOT IN ( ");
			query.append(" SELECT DISTINCT p.motorista FROM Programacao p WHERE p.dataCompetencia = :pDataCompetencia ");
		query.append(" )  AND f NOT IN ( ");
			query.append(" SELECT DISTINCT p.motorista FROM Programacao p WHERE p.turno = :pTurno");
		query.append(" ) ORDER BY matricula ");
		
		return manager.createQuery(query.toString(), Funcionario.class)
				.setParameter("pDataCompetencia", dataCompetencia)
				.setParameter("pTurno", turno)
				.getResultList();
	}
}
