package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.model.PontoLinha;

@Repository
public interface PontoLinhaRepository extends JpaRepository<PontoLinha, Long> {
	
	Optional<PontoLinha> findByLinhaAndSentido(Linha linha, String sentido);
	
	//Optional<PontoLinha> findByPontoOrigemIdAndPontoDestinoId(Long pontoOrigem, Long pontoDestino);
	
	Optional<PontoLinha> findByPontoOrigemIdAndPontoDestinoIdAndLinhaId(Long pontoOrigem, Long pontoDestino, Long linha);
	
	Optional<PontoLinha> findByPontoOrigemAndPontoDestinoAndLinhaAndSentido(Ponto pontoOrigem, Ponto pontoDestino, Linha linha, String sentido);
	
	boolean existsByLinhaAndPontoOrigemAndPontoDestinoAndSentido(Linha linha, Ponto pontoOrigem, Ponto pontoDestino, String sentido);

	List<PontoLinha> findByLinha(Linha linha);

}
