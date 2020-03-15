package br.com.logic.pendotiba.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.MotivoPuloViagem;

@Repository
public interface MotivoPuloViagemRepository extends JpaRepository<MotivoPuloViagem, Long> {

	Optional<MotivoPuloViagem> findByDescricao(String descricao);

}
