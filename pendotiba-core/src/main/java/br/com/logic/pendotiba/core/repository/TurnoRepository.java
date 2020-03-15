package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{

	Optional<Turno> findById(Long turno);

	List<Turno> findByAgrupamento(String agrupamento);
	
}
