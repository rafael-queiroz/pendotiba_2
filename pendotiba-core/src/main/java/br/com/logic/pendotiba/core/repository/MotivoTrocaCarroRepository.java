package br.com.logic.pendotiba.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.MotivoTrocaCarro;

@Repository
public interface MotivoTrocaCarroRepository extends JpaRepository<MotivoTrocaCarro, Long> {

	Optional<MotivoTrocaCarro> findByDescricao(String descricao);

}
