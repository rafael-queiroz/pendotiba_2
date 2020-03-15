package br.com.logic.pendotiba.logicbus.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
		//Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailIgnoreCaseAndAtivoTrue(matricula);
		Optional<Usuario> usuarioOptional = usuarioRepository.findByFuncionarioMatriculaAndAtivoTrue(matricula);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		//usuario = usuarioRepository.carregarUsuario(usuario.getId());
		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}
	
	
	Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		List<String> permissoes = (List<String>) usuarioRepository.permissoes(usuario.getId());
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		return authorities;
	}

}
