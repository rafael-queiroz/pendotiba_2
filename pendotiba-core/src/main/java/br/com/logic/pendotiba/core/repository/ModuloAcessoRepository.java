package br.com.logic.pendotiba.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.ModuloAcesso;

@Repository
public interface ModuloAcessoRepository extends JpaRepository<ModuloAcesso, Long>{
	
	@Query("from ModuloAcesso ORDER BY nome")
	List<ModuloAcesso> findAllOrderByNome();
	
}
