package br.com.logic.pendotiba.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.TipoCarro;

@Repository
public interface TipoCarroRepository extends JpaRepository<TipoCarro, Long> {

	List<TipoCarro> findAllByOrderByDescricaoAsc();

}
