package br.com.logic.pendotiba.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	Optional<Funcionario> findByMatricula(String matricula);
	
	List<Funcionario> findByFuncaoMotoristaFalse();
	
	List<Funcionario> findByFuncaoMotoristaTrueOrderByMatricula();

	List<Funcionario> findByFuncaoCobradorTrueOrderByMatricula();

	List<Funcionario> findByNomeStartingWithIgnoreCase(String nome);

	List<Funcionario> findByFuncaoCobradorTrueAndNomeStartingWithIgnoreCase(String nome);
	
	List<Funcionario> findByFuncaoMotoristaTrueAndNomeStartingWithIgnoreCase(String nome);
	
	List<Funcionario> findByFuncaoMotoristaTrueAndAtivoTrue();

	List<Funcionario> findByFuncaoCobradorTrueAndAtivoTrue();

}
