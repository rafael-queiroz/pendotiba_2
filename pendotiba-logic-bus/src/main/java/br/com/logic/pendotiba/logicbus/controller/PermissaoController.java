package br.com.logic.pendotiba.logicbus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.model.Permissao;
import br.com.logic.pendotiba.core.repository.ModuloAcessoRepository;
import br.com.logic.pendotiba.core.repository.PerfilRepository;
import br.com.logic.pendotiba.core.repository.PermissaoRepository;
import br.com.logic.pendotiba.logicbus.service.PermissaoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/permissao")
public class PermissaoController {
	
	@Autowired
	PermissaoService permissaoService;
	
	@Autowired
	PermissaoRepository permissaoRepository;

	@Autowired
	PerfilRepository perfilRepository;
	
	@Autowired
	ModuloAcessoRepository moduloAcessoRepository;
	
	
	
	@GetMapping
	public ModelAndView consultar(Permissao permissao, BindingResult result, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/permissao/ConsultaPermissao");
		mv.addObject("modulosAcesso", moduloAcessoRepository.findAllOrderByNome());
		mv.addObject("permissoes", permissaoRepository.findByModuloAcessoOrderByModuloAcessoNome(permissao.getModuloAcesso()));
		return mv;
	}
	

	@RequestMapping("/novo")
	public ModelAndView prepararCadastrar(Permissao permissao) {
		ModelAndView mv = new ModelAndView("permissao/CadastroPermissao");
		mv.addObject("modulosAcesso", moduloAcessoRepository.findAllOrderByNome());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Permissao permissao, BindingResult result, Model model,  RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return prepararCadastrar(permissao);
		}
		
		try {
			permissaoService.salvar(permissao);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(permissao);
		}
		
		attributes.addFlashAttribute("mensagem", "Permissao salvo com sucesso");
		return new ModelAndView("redirect:/permissao/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararEditar(@PathVariable("id") Permissao permissao) {
		ModelAndView mv = prepararCadastrar(permissao);
		mv.addObject(permissao);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Permissao permissao) {
		try {
			permissaoService.excluir(permissao);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
