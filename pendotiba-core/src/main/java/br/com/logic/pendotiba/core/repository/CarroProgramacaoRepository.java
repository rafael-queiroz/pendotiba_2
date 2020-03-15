package br.com.logic.pendotiba.core.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.CarroProgramacao;

@Repository
public interface CarroProgramacaoRepository extends JpaRepository<CarroProgramacao, Long> {

	@Transactional
	void deleteByDataCompetencia(Date dataCompetencia);
	
}
