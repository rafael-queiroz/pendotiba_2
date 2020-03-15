package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.enums.TipoBombaEnum;
import br.com.logic.pendotiba.core.model.BombaAbastecimento;

@Repository
public interface BombaAbastecimentoRepository extends JpaRepository<BombaAbastecimento, Long> {

	Optional<BombaAbastecimento> findByDescricao(String descricao);

	List<BombaAbastecimento> findByOrderByCodigo();

	List<BombaAbastecimento> findByTipoBombaOrderByCodigo(TipoBombaEnum diesel);

}
