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

import br.com.logic.pendotiba.core.model.MotivoPuloViagem;
import br.com.logic.pendotiba.core.repository.MotivoPuloViagemRepository;
import br.com.logic.pendotiba.logicbus.service.MotivoPuloViagemService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/motivo-pulo-viagem")
public class MotivoPuloViagemController {

	@Autowired
	MotivoPuloViagemRepository motivoPuloViagemRepository;
	
	@Autowired
	MotivoPuloViagemService motivoPuloViagemService;
	
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("motivo-pulo-viagem/ConsultaMotivoPuloViagem");
		mv.addObject("motivosPuloViagem", motivoPuloViagemRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(MotivoPuloViagem motivoPuloViagem) {
		ModelAndView mv = new ModelAndView("motivo-pulo-viagem/CadastroMotivoPuloViagem");
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid MotivoPuloViagem motivoPuloViagem, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(motivoPuloViagem);
		
		try {
			motivoPuloViagemService.salvar(motivoPuloViagem);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(motivoPuloViagem);
		}
		
		attributes.addFlashAttribute("mensagem", "Motivo salvo com sucesso!");
		return new ModelAndView("redirect:/motivo-pulo-viagem/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") MotivoPuloViagem motivoPuloViagem) {
		ModelAndView mv = prepararCadastrar(motivoPuloViagem);
		mv.addObject(motivoPuloViagem);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") MotivoPuloViagem motivoPuloViagem) {
		try {
			motivoPuloViagemService.excluir(motivoPuloViagem);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
