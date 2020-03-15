package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.ViagemImportada;

@Repository
public interface ViagemImportadaRepository extends JpaRepository<ViagemImportada, Long> {

	List<ViagemImportada> findByDataCompetencia(Date dataCompetencia);

	void deleteByDataCompetencia(Date dataCompetencia);

}
