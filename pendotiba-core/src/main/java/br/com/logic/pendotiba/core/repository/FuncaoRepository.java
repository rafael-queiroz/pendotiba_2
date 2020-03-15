package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Funcao;

@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Long> {

	Optional<Funcao> findByDescricao(String descricaoFuncao);
	
	Funcao findByCodigo(String codigo);
	
	List<Funcao> findByOrderByDescricao();
	
}
