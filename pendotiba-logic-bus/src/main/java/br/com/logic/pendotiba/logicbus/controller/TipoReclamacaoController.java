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

import br.com.logic.pendotiba.core.model.TipoReclamacao;
import br.com.logic.pendotiba.core.repository.TipoReclamacaoRepository;
import br.com.logic.pendotiba.logicbus.service.TipoReclamacaoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/tipo-reclamacao")
public class TipoReclamacaoController {

	@Autowired
	TipoReclamacaoRepository tipoReclamacaoRepository;
	
	@Autowired
	TipoReclamacaoService tipoReclamacaoService;
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("tipo-reclamacao/ConsultaTipoReclamacao");
		mv.addObject("tipos", tipoReclamacaoRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararSalvar(TipoReclamacao tipoReclamacao) {
		ModelAndView mv = new ModelAndView("tipo-reclamacao/CadastroTipoReclamacao");
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid TipoReclamacao tipoReclamacao, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararSalvar(tipoReclamacao);
		
		try {
			tipoReclamacaoService.salvar(tipoReclamacao);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararSalvar(tipoReclamacao);
		}
		
		attributes.addFlashAttribute("mensagem", "Tipo de reclamacao salvo com sucesso!");
		return new ModelAndView("redirect:/tipo-reclamacao/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") TipoReclamacao tipoReclamacao) {
		ModelAndView mv = prepararSalvar(tipoReclamacao);
		mv.addObject(tipoReclamacao);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") TipoReclamacao tipoReclamacao) {
		try {
			tipoReclamacaoService.excluir(tipoReclamacao);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
