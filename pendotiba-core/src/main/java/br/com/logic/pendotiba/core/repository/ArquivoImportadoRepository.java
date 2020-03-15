package br.com.logic.pendotiba.core.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.enums.TipoArquivoEnum;
import br.com.logic.pendotiba.core.model.ArquivoImportado;

@Repository
public interface ArquivoImportadoRepository extends JpaRepository<ArquivoImportado, Long> {

	ArquivoImportado findByDataCompetenciaAndTipoArquivo(Date dataCompetencia, TipoArquivoEnum tipoArquivo);

}
