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

import br.com.logic.pendotiba.core.model.TipoObservacao;
import br.com.logic.pendotiba.core.repository.TipoObservacaoRepository;
import br.com.logic.pendotiba.logicbus.service.TipoObservacaoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/tipo-observacao")
public class TipoObservacaoController {

	@Autowired
	TipoObservacaoRepository tipoObservacaoRepository;
	
	@Autowired
	TipoObservacaoService tipoObservacaoService;
	
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("tipo-observacao/ConsultaTipoObservacao");
		mv.addObject("tipos", tipoObservacaoRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(TipoObservacao tipoObservacao) {
		ModelAndView mv = new ModelAndView("tipo-observacao/CadastroTipoObservacao");
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid TipoObservacao tipoObservacao, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(tipoObservacao);
		
		try {
			tipoObservacaoService.salvar(tipoObservacao);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(tipoObservacao);
		}
		
		attributes.addFlashAttribute("mensagem", "Tipo de observacao salvo com sucesso!");
		return new ModelAndView("redirect:/tipo-observacao/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") TipoObservacao tipoObservacao) {
		ModelAndView mv = prepararCadastrar(tipoObservacao);
		mv.addObject(tipoObservacao);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") TipoObservacao tipoObservacao) {
		try {
			tipoObservacaoService.excluir(tipoObservacao);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
