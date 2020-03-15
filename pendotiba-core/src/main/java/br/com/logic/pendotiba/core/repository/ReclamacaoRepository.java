package br.com.logic.pendotiba.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Reclamacao;

@Repository
public interface ReclamacaoRepository extends JpaRepository<Reclamacao, Long> {


}
