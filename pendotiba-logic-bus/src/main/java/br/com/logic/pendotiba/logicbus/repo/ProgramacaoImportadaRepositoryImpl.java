package br.com.logic.pendotiba.logicbus.repo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.ProgramacaoImportada;

@Repository
public class ProgramacaoImportadaRepositoryImpl {
	
	@PersistenceContext
	EntityManager manager;
	
	
	@Transactional(readOnly = true)
	public void excluirProgramacaoImportadaPorDataCompetencia(Date dataCompetencia) {
		manager.createQuery("DELETE FROM ProgramacaoImportada WHERE dataCompetencia = :pDataCompetencia ").setParameter("pDataCompetencia", dataCompetencia).executeUpdate();
	}


	
	public List<ProgramacaoImportada> listarPorLinhaOrdemProgramacaoDiaDaSemanaOrdenadoPorOrdemViagem(Linha linha, String ordemProgramacao, String versao) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT p FROM ProgramacaoImportada p");
		query.append(" WHERE p.linha = :pLinha AND substring(p.versao, 3, 1) = :pVersao AND p.ordemProgramacao = :pOrdemProgramacao order By p.ordemViagem ");
		
		return manager.createQuery(query.toString(), ProgramacaoImportada.class)
				.setParameter("pLinha", linha)
				.setParameter("pVersao", versao)
				.setParameter("pOrdemProgramacao", ordemProgramacao)
				.getResultList();
	}


	
	public ProgramacaoImportada buscarPorLinhaHoraSaidaOrdemViagemDiaSemana(Linha linha, Date saida, long l, String versao) {
		try {
			StringBuilder query = new StringBuilder();
			query.append(" SELECT p FROM ProgramacaoImportada p ");
			query.append(" WHERE p.linha = :pLinha AND substring(p.versao, 3, 1) = :pVersao AND p.saida = :pSaida AND p.ordemViagem = 1 ");
			
			return manager.createQuery(query.toString(), ProgramacaoImportada.class)
					.setParameter("pLinha", linha)
					.setParameter("pVersao", versao)
					.setParameter("pSaida", saida)
					.setMaxResults(1)
					.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public ProgramacaoImportada buscarPorLinhaHoraSaidaHoraChegadaOrdemViagemDiaSemana(Linha linha, Date inicioJornada, Date saida, long l, String versao) {
		try {
			//System.out.println(inicioJornada + " - " + saida);
			StringBuilder query = new StringBuilder();
			query.append(" SELECT p FROM ProgramacaoImportada p ");
			query.append(" WHERE p.linha = :pLinha ");
			query.append(" AND substring(p.versao, 3, 1) = :pVersao ");
			query.append(" AND p.ordemViagem = 1 ");
			query.append(" AND p.saida = :pInicioJornada ");
			query.append(" AND p.chegada = :pSaida ");
			query.append(" ORDER BY p.id desc");
			
			return manager.createQuery(query.toString(), ProgramacaoImportada.class)
					.setParameter("pVersao", versao)
					.setParameter("pSaida", saida)
					.setParameter("pInicioJornada", inicioJornada)
					.setParameter("pLinha", linha)
					.setMaxResults(1)
					.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	
	public List<ProgramacaoImportada> buscarPorDiaSemanaLinha(String versao, Linha linha) {
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT p FROM ProgramacaoImportada p ");
		query.append(" WHERE substring(p.versao, 3, 1) = :pVersao AND p.linha = :pLinha ");
		
		return manager.createQuery(query.toString(), ProgramacaoImportada.class)
				.setParameter("pVersao", versao)
				.setParameter("pLinha", linha)
				.getResultList();
	}
	
}
