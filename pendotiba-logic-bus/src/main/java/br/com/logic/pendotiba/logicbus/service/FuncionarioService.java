package br.com.logic.pendotiba.logicbus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;
import br.com.logic.pendotiba.logicbus.repo.FuncionarioRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class FuncionarioService {
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	FuncionarioRepositoryImpl funcionarioRepositoryImpl;
	
	
	
	public void salvar(Funcionario funcionario){
		Optional<Funcionario> funcionarioPersistido = funcionarioRepository.findByMatricula(funcionario.getMatricula());
		if (funcionarioPersistido.isPresent() && !funcionarioPersistido.get().getId().equals(funcionario.getId()))
			throw new NegocioException("Já existe um funcionário com os dados informados");
		
		funcionarioRepository.save(funcionario);
	}
	
	
	public void excluir(Funcionario funcionario) {
		try {
			funcionarioRepository.delete(funcionario);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir funcionario. Já foi usado em algum movimento.");
		}
	}


	public List<Funcionario> listarMotoristasDisponiveisParaProgramacao(Programacao programacao) {
		List<Funcionario> funcionariosDisponiveis = funcionarioRepositoryImpl.listarMotoristasDisponiveisPorDataCompetencia(programacao.getDataCompetencia());
		if(programacao.getMotorista() != null)
			funcionariosDisponiveis.add(programacao.getMotorista());
		return funcionariosDisponiveis;
	}
	
	
	public Funcionario buscarFuncionarioPorMatricula(String matricula) {
		Optional<Funcionario> obj = funcionarioRepository.findByMatricula(matricula);
		return obj.isPresent() ? obj.get() : null;
	}
	
	
	public List<Funcionario> findByNomeStartingWithIgnoreCase(String nome) {
		return (List<Funcionario>) funcionarioRepository.findByNomeStartingWithIgnoreCase(nome);
	}


	public List<Funcionario> findByFuncaoCobradorTrueAndNomeStartingWithIgnoreCase(String nome) {
		return (List<Funcionario>) funcionarioRepository.findByFuncaoCobradorTrueAndNomeStartingWithIgnoreCase(nome);
	}
	
	
	public List<Funcionario> findByFuncaoMotoristaTrueAndNomeStartingWithIgnoreCase(String nome) {
		return (List<Funcionario>) funcionarioRepository.findByFuncaoMotoristaTrueAndNomeStartingWithIgnoreCase(nome);
	}

}
