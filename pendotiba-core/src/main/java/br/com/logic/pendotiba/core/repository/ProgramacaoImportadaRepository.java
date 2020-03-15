package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.ProgramacaoImportada;

@Repository
public interface ProgramacaoImportadaRepository extends JpaRepository<ProgramacaoImportada, Long> {
	
	List<ProgramacaoImportada> findByDataCompetencia(Date dataCompetencia);

	List<ProgramacaoImportada> findAllByOrderBySaida();

	//List<ProgramacaoImportada> findByOrdemProgramacaoOrderBySaida(String ordemProgramacao);
	List<ProgramacaoImportada> findByOrdemProgramacaoOrderByOrdemViagem(String ordemProgramacao);

	ProgramacaoImportada findBySaidaAndOrdemViagem(Date saida, Long ordemViagem);

	ProgramacaoImportada findByOrdemProgramacaoAndOrdemViagemAndVersao(String ordemProgramacao, Long ordemViagem, String versao);

}
