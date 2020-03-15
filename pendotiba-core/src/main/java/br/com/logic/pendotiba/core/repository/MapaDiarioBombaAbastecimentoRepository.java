package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.enums.TipoBombaEnum;
import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.model.MapaDiarioBombaAbastecimento;

@Repository
public interface MapaDiarioBombaAbastecimentoRepository extends JpaRepository<MapaDiarioBombaAbastecimento, Long> {

	List<MapaDiarioBombaAbastecimento> findByDataCompetenciaAndBombaAbastecimentoTipoBombaOrderByBombaAbastecimentoDescricao(Date dataAppAbastecimentoOdometroRoleta, TipoBombaEnum tipo);

	Optional<MapaDiarioBombaAbastecimento> findByDataCompetenciaAndBombaAbastecimento(Date dataCompetencia, BombaAbastecimento bombaAbastecimento);
	
	List<MapaDiarioBombaAbastecimento> findByDataCompetencia(Date dataCompentecia);

}
