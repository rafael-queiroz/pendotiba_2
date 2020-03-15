package br.com.logic.pendotiba.logicbus.repo;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ViagemImportadaRepositoryImpl{
	
	@PersistenceContext
	EntityManager manager;
	

	@Transactional(readOnly = true)
	public void excluirViagemImportadaPorDataCompetencia(Date dataCompetencia) {
		manager.createQuery("DELETE FROM ViagemImportada WHERE dataCompetencia = :pDataCompetencia").setParameter("pDataCompetencia", dataCompetencia).executeUpdate();
	}
	
}
