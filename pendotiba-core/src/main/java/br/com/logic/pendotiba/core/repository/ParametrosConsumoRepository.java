package br.com.logic.pendotiba.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.ParametrosConsumo;
import br.com.logic.pendotiba.core.model.TipoChassi;

@Repository
public interface ParametrosConsumoRepository extends JpaRepository<ParametrosConsumo, Long>  {

	Optional<ParametrosConsumo> findByLinhaAndTipoChassi(Linha linha, TipoChassi tipoChassi);
	
}
