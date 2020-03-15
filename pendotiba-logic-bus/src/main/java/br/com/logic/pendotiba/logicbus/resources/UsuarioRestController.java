package br.com.logic.pendotiba.logicbus.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.logicbus.resources.dto.LoginUsuarioDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.UsuarioDTO;
import br.com.logic.pendotiba.logicbus.security.MD5PasswordEncoder;
import br.com.logic.pendotiba.logicbus.service.MapaDiarioBombaAbastecimentoService;

@RestController
@RequestMapping("/rest/usuario")
@CrossOrigin(value = "*")
public class UsuarioRestController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	MD5PasswordEncoder passwordEncoder;
	
	@Autowired
	MapaDiarioBombaAbastecimentoService mapaDiarioBombaAbastecimentoService;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody List<UsuarioDTO> listarTodos() {
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		usuarios.forEach(ui -> usuariosDTO.add(new UsuarioDTO(usuarioRepository.findOne(ui.getId()))));
		return usuariosDTO;
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/login")
	@ResponseBody LoginUsuarioDTO logarUsuario(@RequestParam(value = "uuidDispositivoMovel") String uuidDispositivoMovel, 
											   @RequestParam(value = "matricula") String matricula,
											   @RequestParam(value = "senha") String senha) {
		
		Usuario usuario = usuarioRepository.findByFuncionarioMatriculaAndSenhaAndAtivoTrue(matricula, passwordEncoder.encode(senha));
	
		if(usuario != null && usuario.isAppAbastecimentoOdometroRoleta())
			return new LoginUsuarioDTO(usuarioRepository.findOne(usuario.getId()), "success", mapaDiarioBombaAbastecimentoService.podeAbastecer());
		else
			return new LoginUsuarioDTO("error", "Matrícula e/ou senha não conferem");
	}
	
}
