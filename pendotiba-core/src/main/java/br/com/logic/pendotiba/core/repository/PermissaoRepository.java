package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.ModuloAcesso;
import br.com.logic.pendotiba.core.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

	Optional<Permissao> findByNome(String nome);

	List<Permissao> findByModuloAcessoNome(String string);

	List<Permissao> findByModuloAcessoOrderByModuloAcessoNome(ModuloAcesso moduloAcesso);
	
}
