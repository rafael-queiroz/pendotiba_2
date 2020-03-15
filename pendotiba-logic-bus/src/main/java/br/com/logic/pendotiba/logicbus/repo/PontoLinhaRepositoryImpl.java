package br.com.logic.pendotiba.logicbus.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.logicbus.filter.PontoLinhaFilter;
import br.com.logic.pendotiba.logicbus.resources.dto.PontoLinhaDTO;

@Repository
public class PontoLinhaRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;

	
	
	
	public List<PontoLinhaDTO> listarPontosLinhaDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.PontoLinhaDTO(p) FROM PontoLinha p", PontoLinhaDTO.class).getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<PontoLinha> filtrar(PontoLinhaFilter pontoLinhaFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(PontoLinha.class);
		
		if (pontoLinhaFilter.getLinha() != null) 
			criteria.add(Restrictions.eq("linha", pontoLinhaFilter.getLinha()));
			
		if (pontoLinhaFilter.getPontoOrigem() != null) 
			criteria.add(Restrictions.eq("pontoOrigem", pontoLinhaFilter.getPontoOrigem()));
			
		if (pontoLinhaFilter.getPontoDestino() != null) 
			criteria.add(Restrictions.eq("pontoDestino", pontoLinhaFilter.getPontoDestino()));
			
		if (!StringUtils.isEmpty(pontoLinhaFilter.getSentido())) 
			criteria.add(Restrictions.ilike("sentido", pontoLinhaFilter.getSentido()));
		
		return (List<PontoLinha>) criteria.list();
	}


	
	public PontoLinha buscarPontoLinhaInverso(PontoLinha pontoLinha) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT pl FROM PontoLinha pl WHERE pl.pontoOrigem = :pPontoDestino AND pl.pontoDestino = :pPontoOrigem AND pl.linha = :pLinha ");
		
		return manager.createQuery(query.toString(), PontoLinha.class)
				.setParameter("pPontoDestino", pontoLinha.getPontoDestino())
				.setParameter("pPontoOrigem", pontoLinha.getPontoOrigem())
				.setParameter("pLinha", pontoLinha.getLinha())
				.getSingleResult();
	}
	
}
