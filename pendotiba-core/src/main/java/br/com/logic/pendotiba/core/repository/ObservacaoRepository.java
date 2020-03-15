package br.com.logic.pendotiba.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Observacao;

@Repository
public interface ObservacaoRepository extends JpaRepository<Observacao, Long>  {


}
