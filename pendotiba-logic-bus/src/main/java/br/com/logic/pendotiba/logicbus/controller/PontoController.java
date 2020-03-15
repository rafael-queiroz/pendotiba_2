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

import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.core.repository.TerminalRepository;
import br.com.logic.pendotiba.logicbus.service.PontoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/ponto")
public class PontoController {

	@Autowired
	PontoRepository pontoRepository;
	
	@Autowired
	PontoService pontoService;
	
	@Autowired
	TerminalRepository terminalRepository;
	
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("ponto/ConsultaPonto");
		mv.addObject("pontos", pontoRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(Ponto ponto) {
		ModelAndView mv = new ModelAndView("ponto/CadastroPonto");
		mv.addObject("terminais", terminalRepository.findAll());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Ponto ponto, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(ponto);
		
		try {
			pontoService.salvar(ponto);
		} catch (RuntimeException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(ponto);
		}
		
		attributes.addFlashAttribute("mensagem", "Ponto salvo com sucesso!");
		return new ModelAndView("redirect:/ponto/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") Ponto ponto) {
		ModelAndView mv = prepararCadastrar(ponto);
		mv.addObject(ponto);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Ponto ponto) {
		try {
			pontoService.excluir(ponto);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
