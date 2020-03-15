package br.com.logic.pendotiba.logicbus.repo;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EscalaImportadaRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	
	@Transactional(readOnly = true)
	public void excluirEscalaImportadaPorDataCompetencia(Date dataCompetencia) {
		manager.createQuery("DELETE FROM EscalaImportada WHERE dataCompetencia = :pDataCompetencia ").setParameter("pDataCompetencia", dataCompetencia).executeUpdate();
	}

}
