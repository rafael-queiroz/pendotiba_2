package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Ponto;

@Repository
public interface PontoRepository extends JpaRepository<Ponto, Long> {
	
	Optional<Ponto> findByCodigo(String codigo);
	
	Optional<Ponto> findByCodigoAndDescricao(String codigo, String descricao);

	List<Ponto> findByOrderByCodigo();
	
	@Query(value = " SELECT p FROM Ponto p join fetch p.dispositivos d WHERE d.imei = :imei ")
	Optional<Ponto> buscarPorImei(@Param("imei") String imei);

}
