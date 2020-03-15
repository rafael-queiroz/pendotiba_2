package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Perfil;
import br.com.logic.pendotiba.core.repository.PerfilRepository;
import br.com.logic.pendotiba.core.repository.PermissaoRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class PerfilService {
	
	@Autowired
	PerfilRepository perfilRepository;
	
	@Autowired
	PermissaoRepository permissaoRepository;
	
	
	public void salvar(Perfil perfil){
		Optional<Perfil> perfilPersistido = perfilRepository.findByNome(perfil.getNome());
		if (perfilPersistido.isPresent() && !perfilPersistido.get().getId().equals(perfil.getId()))
			throw new NegocioException("Já existe um perfil com os dados informados");
		
		perfilRepository.save(perfil);
	}
	
	
	public void excluir(Perfil perfil) {
		try {
			perfilRepository.delete(perfil);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir perfil. Existe usuário configurado para este perfil.");
		}
	}
	
}
