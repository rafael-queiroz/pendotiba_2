package br.com.logic.pendotiba.logicbus.service;

import java.text.Normalizer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Permissao;
import br.com.logic.pendotiba.core.repository.PerfilRepository;
import br.com.logic.pendotiba.core.repository.PermissaoRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class PermissaoService {
	
	@Autowired
	PermissaoRepository permissaoRepository;

	@Autowired
	PerfilRepository perfilRepository;

	
	
	public void salvar(Permissao permissao){
		Optional<Permissao> permissaoPersistido = permissaoRepository.findByNome(permissao.getNome());
		if (permissaoPersistido.isPresent() && !permissaoPersistido.get().getId().equals(permissao.getId()))
			throw new NegocioException("Já existe um perfil com os dados informados");
		
		permissao.setRole(gerarRolePorNome(permissao.getNome()));
		permissaoRepository.save(permissao);
	}
	
	
	String gerarRolePorNome(String nome) {
		String role = Normalizer.normalize(nome.trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		role = role.replace(" ", "_");
		role = role.toUpperCase();
		return "ROLE_".concat(role);
	}


	public void excluir(Permissao permissao) {
		try {
			permissaoRepository.delete(permissao);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir permissao. Existe perfil configurado para esta permissão.");
		}
	}
	
}
