package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.logic.pendotiba.core.model.Perfil;


@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>  {

	Optional<Perfil> findByNome(String nome);

	@Transactional(readOnly=true)
	@Query(value="SELECT p FROM Perfil p LEFT JOIN p.permissoes per WHERE p.id = :idPerfil")
	Perfil carregarPerfil(@Param("idPerfil") Long idPerfil);

	List<Perfil> findByOrderByNome();

}
