package br.com.logic.pendotiba.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.TipoChassi;

@Repository
public interface TipoChassiRepository extends JpaRepository<TipoChassi, Long>{
	
	Optional<TipoChassi> findByNome(String descricao);

}
