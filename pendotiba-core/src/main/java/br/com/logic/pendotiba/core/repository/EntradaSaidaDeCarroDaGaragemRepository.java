package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;
import br.com.logic.pendotiba.core.model.Turno;

@Repository
public interface EntradaSaidaDeCarroDaGaragemRepository extends JpaRepository<EntradaSaidaDeCarroDaGaragem, Long> {

	Optional<EntradaSaidaDeCarroDaGaragem> findByCarroAndTurnoAndDataCompetencia(Carro carro, Turno turno, Date dataCompetencia);

	List<EntradaSaidaDeCarroDaGaragem> findByDataCompetenciaAndCarroAndTurnoIn(Date date, Carro carro, List<Turno> turnos);

	List<EntradaSaidaDeCarroDaGaragem> findByDataCompetenciaAndCarro(Date dataCompetencia, Carro carro);

	List<EntradaSaidaDeCarroDaGaragem> findByDataCompetencia(Date date);

}
