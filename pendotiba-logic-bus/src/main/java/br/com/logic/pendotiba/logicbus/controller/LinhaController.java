package br.com.logic.pendotiba.logicbus.controller;

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

import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.logicbus.service.LinhaService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/linha")
public class LinhaController {

	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	LinhaService linhaService;
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("linha/ConsultaLinha");
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(Linha linha) {
		ModelAndView mv = new ModelAndView("linha/CadastroLinha");
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Linha linha, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(linha);
		
		try {
			linhaService.salvar(linha);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(linha);
		}
		
		attributes.addFlashAttribute("mensagem", "Linha salva com sucesso!");
		return new ModelAndView("redirect:/linha");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") Linha linha) {
		ModelAndView mv = prepararCadastrar(linha);
		mv.addObject(linha);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Linha linha) {
		try {
			linhaService.excluir(linha);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
