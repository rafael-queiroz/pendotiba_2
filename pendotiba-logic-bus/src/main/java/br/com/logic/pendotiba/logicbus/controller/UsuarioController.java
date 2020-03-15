package br.com.logic.pendotiba.logicbus.controller;

import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;
import br.com.logic.pendotiba.core.repository.PerfilRepository;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.UsuarioFilter;
import br.com.logic.pendotiba.logicbus.repo.UsuarioRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.StatusUsuario;
import br.com.logic.pendotiba.logicbus.service.UsuarioService;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	
	@Autowired
	PerfilRepository perfilRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioRepositoryImpl usuarioRepositoryImpl;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	
	
	@GetMapping
	public ModelAndView consultar(UsuarioFilter usuarioFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/usuario/ConsultaUsuario");
		mv.addObject("perfis", perfilRepository.findByOrderByNome());
		
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarioRepositoryImpl.filtrar(usuarioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	

	@RequestMapping("/novo")
	public ModelAndView prepararCadastrar(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("perfis", perfilRepository.findByOrderByNome());
		mv.addObject("funcionarios", funcionarioRepository.findByFuncaoMotoristaFalse());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return prepararCadastrar(usuario);
		}
		
		try {
			usuarioService.salvar(usuario);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
		return new ModelAndView("redirect:/usuario/novo");
	}
	
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("ids[]") Long[] ids, @RequestParam("status") StatusUsuario statusUsuario) {
		usuarioService.alterarStatus(ids, statusUsuario);
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararEditar(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		ModelAndView mv = prepararCadastrar(usuario);
		mv.addObject(usuario);
		return mv;
	}
	
}
