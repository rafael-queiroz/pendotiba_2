package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;

@Repository
public interface MapaDiarioCarroRepository extends JpaRepository<MapaDiarioCarro, Long> {

	@Transactional
	void deleteByDataCompetencia(Date dataCompetencia);

	List<MapaDiarioCarro> findByDataCompetenciaAndCarro(Date dataCompetencia, Carro carro);
	
	@Query(value="SELECT mapa FROM MapaDiarioCarro mapa WHERE mapa.dataCompetencia = :dataCompetencia AND roleta is null AND odometro is null AND volumeDiesel is null ")
	List<MapaDiarioCarro> listarMapasSemDados(@Param("dataCompetencia") Date dataCompetencia);

	List<MapaDiarioCarro> findByDataCompetenciaOrderByCarroNumeroDeOrdem(Date dataCompetencia); // app abastecimento

	@Query(value="SELECT mapa FROM MapaDiarioCarro mapa WHERE mapa.dataCompetencia = :dataCompetencia AND roleta is null order by mapa.carro.numeroDeOrdem ")
	List<MapaDiarioCarro> listarRoletasIncompletas(@Param("dataCompetencia") Date dataCompetencia); // app abastecimento
	
	@Query(value="SELECT mapa FROM MapaDiarioCarro mapa WHERE mapa.dataCompetencia = :dataCompetencia AND (volumeDiesel is null OR odometro is null AND volumeDiesel <> 0) order by mapa.carro.numeroDeOrdem ")
	List<MapaDiarioCarro> listarAbastecimentoOdometoIncompletos(@Param("dataCompetencia") Date dataCompetencia); // app - abastecimento
	
	List<MapaDiarioCarro> findByDataCompetenciaAndBombaAbastecimentoDieselOrderByDataHoraCadastroAbastecimentoDiesel(Date dataCompetenciaAbastecimentoDiesel, BombaAbastecimento bombaAbastecimentoDiesel);
	
	List<MapaDiarioCarro> findByDataCompetenciaAndBombaAbastecimentoArlaOrderByDataHoraCadastroAbastecimentoArla(Date dataCompetenciaAbastecimentoArla, BombaAbastecimento bombaAbastecimentoArla);
	
	
	// 2019-06-10
	List<MapaDiarioCarro> findByDataCompetenciaAndBombaAbastecimentoDieselOrderByCarroNumeroDeOrdem(Date dataCompetencia, BombaAbastecimento bombaAbastecimentoDiesel);
	
	List<MapaDiarioCarro> findByDataCompetenciaAndBombaAbastecimentoArlaOrderByCarroNumeroDeOrdem(Date dataCompetencia, BombaAbastecimento bombaAbastecimentoArla);

	List<MapaDiarioCarro> findByDataCompetenciaBetween(Date dataInicial, Date dataFinal);

	@Query(value="SELECT mapa FROM MapaDiarioCarro mapa WHERE mapa.dataCompetencia = :dataCompetencia AND ((volumeDiesel is null AND odometro is not null) or (volumeDiesel > 0 AND odometro is null)) order by mapa.carro.numeroDeOrdem ")
	List<MapaDiarioCarro> listarAbastecimentosOuOdometroNulos(@Param("dataCompetencia") Date dataCompetencia);

	Optional<MapaDiarioCarro> findByDataCompetencia(Date dataPosteriorEmDias);
}
