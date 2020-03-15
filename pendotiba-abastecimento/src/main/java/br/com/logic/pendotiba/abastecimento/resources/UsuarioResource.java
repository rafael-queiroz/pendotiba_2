package br.com.logic.pendotiba.abastecimento.resources;

import br.com.logic.pendotiba.abastecimento.dto.LoginUsuarioDTO;
import br.com.logic.pendotiba.abastecimento.dto.UsuarioDTO;
import br.com.logic.pendotiba.abastecimento.security.MD5PasswordEncoder;
import br.com.logic.pendotiba.abastecimento.service.MapaDiarioBombaAbastecimentoService;
import br.com.logic.pendotiba.abastecimento.service.UsuarioService;
import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(value = "*")
public class UsuarioResource {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	MD5PasswordEncoder passwordEncoder;
	
	@Autowired
	MapaDiarioBombaAbastecimentoService mapaDiarioBombaAbastecimentoService;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<UsuarioDTO> listarTodos() {
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		List<Usuario> usuarios = usuarioRepository.findAll();
		usuarios.forEach(ui -> usuariosDTO.add(new UsuarioDTO(usuarioRepository.findById(ui.getId()).get())));
		return usuariosDTO;
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/login")
	@ResponseBody LoginUsuarioDTO logarUsuario(@RequestParam(value = "uuidDispositivoMovel") String uuidDispositivoMovel, 
											   @RequestParam(value = "matricula") String matricula,
											   @RequestParam(value = "senha") String senha) {
		
		Usuario usuario = usuarioRepository.findByFuncionarioMatriculaAndSenhaAndAtivoTrue(matricula, passwordEncoder.encode(senha));
		LoginUsuarioDTO l;
		if(usuario != null && usuario.isAppAbastecimentoOdometroRoleta())
			l = new LoginUsuarioDTO(usuarioService.buscarPorId(usuario.getId()), "success", mapaDiarioBombaAbastecimentoService.podeAbastecer());
		else
			l = new LoginUsuarioDTO("error", "Matrícula e/ou senha não conferem");
		
		return l;
	}

}
