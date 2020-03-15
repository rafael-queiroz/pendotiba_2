package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.ErroProgramacao;

@Repository
public interface ErroProgramacaoRepository extends JpaRepository<ErroProgramacao, Long> {
	
	@Transactional
	void deleteByDataCompetencia(Date dataCompetencia);

	List<ErroProgramacao> findByDataCompetenciaAndDescricao(Date dataCompetencia, String erroSemViagens);

}
