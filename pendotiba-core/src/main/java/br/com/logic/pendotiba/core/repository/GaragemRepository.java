package br.com.logic.pendotiba.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Garagem;

@Repository
public interface GaragemRepository extends JpaRepository<Garagem, Long>{
	
	Optional<Garagem> findByCodigo(String codigo);

}
