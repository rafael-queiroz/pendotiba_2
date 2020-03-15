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

import br.com.logic.pendotiba.core.model.TipoChassi;
import br.com.logic.pendotiba.core.repository.TipoChassiRepository;
import br.com.logic.pendotiba.logicbus.service.TipoChassiService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/tipo-chassi")
public class TipoChassiController {

	@Autowired
	TipoChassiRepository tipoChassiRepository;
	
	@Autowired
	TipoChassiService tipoChassiService;
	
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("tipo-chassi/ConsultaTipoChassi");
		mv.addObject("tipos", tipoChassiRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(TipoChassi tipoChassi) {
		ModelAndView mv = new ModelAndView("tipo-chassi/CadastroTipoChassi");
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid TipoChassi tipoChassi, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(tipoChassi);
		
		try {
			tipoChassiService.salvar(tipoChassi);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(tipoChassi);
		}
		
		attributes.addFlashAttribute("mensagem", "Tipo de chassi salvo com sucesso!");
		return new ModelAndView("redirect:/tipo-chassi/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") TipoChassi tipoChassi) {
		ModelAndView mv = prepararCadastrar(tipoChassi);
		mv.addObject(tipoChassi);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") TipoChassi tipoChassi) {
		try {
			tipoChassiService.excluir(tipoChassi);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
