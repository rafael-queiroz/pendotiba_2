package br.com.logic.pendotiba.logicbus.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.logicbus.resources.dto.LinhaDTO;

@Repository
public class LinhaRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;


	public List<LinhaDTO> listarLinhasDTO() {
		return manager.createQuery(" SELECT new br.com.logic.pendotiba.logicbus.resources.dto.LinhaDTO(l) FROM Linha l ", LinhaDTO.class).getResultList();
	}
	
}
