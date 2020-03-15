package br.com.logic.pendotiba.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.DispositivoMovel;
import br.com.logic.pendotiba.core.model.HistoricoDispositivoMovel;

@Repository
public interface HistoricoDispositivoMovelRepository extends JpaRepository<HistoricoDispositivoMovel, Long> {

	List<HistoricoDispositivoMovel> findByDispositivoMovel(DispositivoMovel dispositivoMovel);
	
}
