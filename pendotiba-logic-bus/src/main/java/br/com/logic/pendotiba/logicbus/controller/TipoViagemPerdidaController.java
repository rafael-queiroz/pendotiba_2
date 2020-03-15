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

import br.com.logic.pendotiba.core.model.TipoViagemPerdida;
import br.com.logic.pendotiba.core.repository.TipoViagemPerdidaRepository;
import br.com.logic.pendotiba.logicbus.service.TipoViagemPerdidaService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/tipo-viagem-perdida")
public class TipoViagemPerdidaController {

	@Autowired
	TipoViagemPerdidaRepository tipoViagemPerdidaRepository;
	
	@Autowired
	TipoViagemPerdidaService tipoViagemPerdidaService;
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("tipo-viagem-perdida/ConsultaTipoViagemPerdida");
		mv.addObject("tipos", tipoViagemPerdidaRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararSalvar(TipoViagemPerdida tipoViagemPerdida) {
		ModelAndView mv = new ModelAndView("tipo-viagem-perdida/CadastroTipoViagemPerdida");
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid TipoViagemPerdida tipoViagemPerdida, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararSalvar(tipoViagemPerdida);
		
		try {
			tipoViagemPerdidaService.salvar(tipoViagemPerdida);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararSalvar(tipoViagemPerdida);
		}
		
		attributes.addFlashAttribute("mensagem", "Tipo de reclamacao salvo com sucesso!");
		return new ModelAndView("redirect:/tipo-viagem-perdida/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") TipoViagemPerdida tipoViagemPerdida) {
		ModelAndView mv = prepararSalvar(tipoViagemPerdida);
		mv.addObject(tipoViagemPerdida);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") TipoViagemPerdida tipoViagemPerdida) {
		try {
			tipoViagemPerdidaService.excluir(tipoViagemPerdida);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
