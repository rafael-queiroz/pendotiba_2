package br.com.logic.pendotiba.core.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.model.Viagem;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

	List<Viagem> findByDataCompetencia(Date dataCompetencia);

	List<Viagem> findByProgramacaoCompletaTrue();

	List<Viagem> findByProgramacao(Programacao programacao);

	@Transactional
	void deleteByDataCompetencia(Date dataCompetencia);

	List<Viagem> findByIdInAndTipoViagemPerdidaNotNull(List<Long> idsViagens);

	List<Viagem> findByProgramacaoIdIn(List<Long> idsProgramacoes);
	
	//Viagem findByProgramacaoOrderByHoraChegadaDesc(Programacao programacao);

	//List<Viagem> findByPontoLinhaPontoDestinoAndHoraSaidaRealizadaIsNotNullAndHoraChegadaIsNull(Ponto pontoDestino);
	
	@Transactional
	@Query(value="SELECT v FROM Viagem v LEFT JOIN v.erros e WHERE v.id = :idViagem")
	Viagem carregarViagem(@Param("idViagem") Long idViagem);
	
	
	
	
	// app despachante - inicio
	List<Viagem> findByPontoLinhaPontoDestinoAndDataCompetenciaAndHoraSaidaProgramadaIsNullAndProgramacaoCompletaTrueAndProgramacaoStatusNot(Ponto pontoDestino, Date data, Status status);
	
	@Query(value = "SELECT DISTINCT v FROM Viagem v WHERE v.programacao.id in (:idsProgramacoes) AND (v.pontoLinha.pontoOrigem = :ponto OR v.pontoLinha.pontoDestino = :ponto)")
	List<Viagem> listarPorPontoProgramacao(@Param("ponto") Ponto ponto, @Param("idsProgramacoes") List<Long> pIdsProgramacoes);
	
	@Query(value = "SELECT DISTINCT v FROM Viagem v WHERE v.id in (:idsViagens) AND v.pontoLinha.pontoOrigem <> :ponto AND v.pontoLinha.pontoDestino <> :ponto")
	List<Viagem> listarViagensDescartadasPorPonto(@Param("ponto") Ponto ponto, @Param("idsViagens") List<Long> idsViagens);
	//app despachante - fim
}
