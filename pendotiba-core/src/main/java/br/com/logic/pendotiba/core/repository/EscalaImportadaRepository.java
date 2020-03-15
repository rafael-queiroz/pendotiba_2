package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.ArquivoImportado;
import br.com.logic.pendotiba.core.model.EscalaImportada;
import br.com.logic.pendotiba.core.model.Funcionario;

@Repository
public interface EscalaImportadaRepository extends JpaRepository<EscalaImportada, Long> {
	
	List<EscalaImportada> findByDataCompetencia(Date dataCompetencia);

	Optional<EscalaImportada> findByArquivoImportadoAndMotorista(ArquivoImportado arquivoImportado, Funcionario motorista);

	List<EscalaImportada> findByDataCompetenciaOrderBySaida(Date dataCompetencia);

	void deleteByDataCompetencia(Date dataCompetencia);

}
