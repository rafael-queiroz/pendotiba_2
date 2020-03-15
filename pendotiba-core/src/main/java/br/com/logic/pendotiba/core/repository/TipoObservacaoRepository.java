package br.com.logic.pendotiba.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.TipoObservacao;

@Repository
public interface TipoObservacaoRepository extends JpaRepository<TipoObservacao, Long>{
	
	Optional<TipoObservacao> findByDescricao(String descricao);

}
