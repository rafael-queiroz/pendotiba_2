package br.com.logic.pendotiba.logicbus.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.logicbus.security.UsuarioSistema;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelIncluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public Usuario getUsuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		return usuarioSistema.getUsuario();
	}
	
	
	public void salvar(Usuario usuario){
		if(!usuario.getEmail().isEmpty()) {
			Optional<Usuario> usuarioEmailExistente = usuarioRepository.findByEmail(usuario.getEmail());
			if (usuarioEmailExistente.isPresent() && !usuarioEmailExistente.get().equals(usuario))
				throw new ImpossivelIncluirEntidadeException("E-mail já cadastrado");
		}
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new NegocioException("Senha é obrigatória para novo usuário");
		}
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		} else if (StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioRepository.findByFuncionarioMatricula(usuario.getFuncionario().getMatricula()).getSenha());
		}
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if (!usuario.isNovo() && usuario.getAtivo() == null) {
			Optional<Usuario> usuarioMatriculaExistente = usuarioRepository.findByFuncionarioMatriculaAndAtivoTrue(usuario.getFuncionario().getMatricula());
			usuario.setAtivo(usuarioMatriculaExistente.get().getAtivo());
		}
		
		usuarioRepository.save(usuario);
	}
	
	
	@Transactional
	public void alterarStatus(Long[] ids, StatusUsuario statusUsuario) {
		statusUsuario.executar(ids, usuarioRepository);
	}
	
}
