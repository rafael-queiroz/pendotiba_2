package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Linha;

@Repository
public interface LinhaRepository extends JpaRepository<Linha, Long> {
	
	Optional<Linha> findByCodigo(String codigo);

	List<Linha> findByOrderByCodigo();

}
