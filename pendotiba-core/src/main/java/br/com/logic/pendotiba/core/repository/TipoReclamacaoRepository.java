package br.com.logic.pendotiba.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.TipoReclamacao;

@Repository
public interface TipoReclamacaoRepository extends JpaRepository<TipoReclamacao, Long>{
	
	Optional<TipoReclamacao> findByDescricao(String descricao);

}
