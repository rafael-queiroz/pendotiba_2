package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.logic.pendotiba.core.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
	Optional<Usuario> findByEmailIgnoreCaseAndAtivoTrue(String email);
	
	Optional<Usuario> findByFuncionarioMatriculaAndAtivoTrue(String matricula);
	
	List<Usuario> findByIdIn(Long[] ids);

	List<Usuario> findByFuncionarioFuncaoDespachanteTrueOrderByFuncionarioMatricula();

	Usuario findByFuncionarioMatricula(String matricula);

	@Transactional(readOnly=true)
	@Query(value = "SELECT DISTINCT p.role FROM Usuario u LEFT JOIN u.perfil.permissoes p WHERE u.id = :idUsuario")
	List<String> permissoes(@Param("idUsuario") Long idUsuario);
	
	Usuario findByFuncionarioMatriculaAndSenhaAndAtivoTrue(String matricula, String senha);

}
