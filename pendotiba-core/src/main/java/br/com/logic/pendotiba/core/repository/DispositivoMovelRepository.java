package br.com.logic.pendotiba.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.DispositivoMovel;

@Repository
public interface DispositivoMovelRepository extends JpaRepository<DispositivoMovel, Long> {

	Optional<DispositivoMovel> findByImei(String imei);
	
}
