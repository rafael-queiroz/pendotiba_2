package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.TipoViagemPerdida;

@Repository
public interface TipoViagemPerdidaRepository extends JpaRepository<TipoViagemPerdida, Long>{

	Optional<TipoViagemPerdida> findByDescricao(String descricao);
	
	Optional<TipoViagemPerdida> findById(Long id);

	List<TipoViagemPerdida> findByOrderByDescricao();
	
}
