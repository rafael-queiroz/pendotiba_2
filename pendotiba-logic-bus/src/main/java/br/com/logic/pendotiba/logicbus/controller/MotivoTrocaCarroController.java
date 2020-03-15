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

import br.com.logic.pendotiba.core.model.MotivoTrocaCarro;
import br.com.logic.pendotiba.core.repository.MotivoTrocaCarroRepository;
import br.com.logic.pendotiba.logicbus.service.MotivoTrocaCarroService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/motivo-troca-carro")
public class MotivoTrocaCarroController {

	@Autowired
	MotivoTrocaCarroRepository motivoTrocaCarroRepository;
	
	@Autowired
	MotivoTrocaCarroService motivoTrocaCarroService;
	
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("motivo-troca-carro/ConsultaMotivoTrocaCarro");
		mv.addObject("motivosTrocaCarro", motivoTrocaCarroRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(MotivoTrocaCarro motivoTrocaCarro) {
		ModelAndView mv = new ModelAndView("motivo-troca-carro/CadastroMotivoTrocaCarro");
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid MotivoTrocaCarro motivoTrocaCarro, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(motivoTrocaCarro);
		
		try {
			motivoTrocaCarroService.salvar(motivoTrocaCarro);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(motivoTrocaCarro);
		}
		
		attributes.addFlashAttribute("mensagem", "Motivo salvo com sucesso!");
		return new ModelAndView("redirect:/motivo-troca-carro/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") MotivoTrocaCarro motivoTrocaCarro) {
		ModelAndView mv = prepararCadastrar(motivoTrocaCarro);
		mv.addObject(motivoTrocaCarro);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") MotivoTrocaCarro motivoTrocaCarro) {
		try {
			motivoTrocaCarroService.excluir(motivoTrocaCarro);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
