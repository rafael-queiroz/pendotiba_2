package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.Programacao;

@Repository
public interface ProgramacaoRepository extends JpaRepository<Programacao, Long> {

	List<Programacao> findByDataCompetencia(Date dataCompetencia);

	List<Programacao> findByCompletaTrue();

	@Transactional
	void deleteByDataCompetencia(Date dataCompetencia);

	List<Programacao> findByIdIn(List<Long> ids);
	
	List<Programacao> findByStatusIdAndIdIn(Long liberado, List<Long> ids);

	Programacao findByDataCompetenciaAndCarroRealizadoAndTurnoId(Date dataCompetencia, Carro carroRealizado, Long tarde);
	
	@Transactional
	@Query(value="SELECT p FROM Programacao p LEFT JOIN p.viagens v WHERE p.id = :idProgramacao")
	Programacao carregarProgramacao(@Param("idProgramacao") Long idProgramacao);
	
}
