package br.com.logic.pendotiba.logicbus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.enums.TipoBombaEnum;
import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;
import br.com.logic.pendotiba.logicbus.service.BombaAbastecimentoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/bomba-abastecimento")
public class BombaAbastecimentoController {

	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;
	
	@Autowired
	BombaAbastecimentoService bombaAbastecimentoService;
	
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("bomba-abastecimento/ConsultaBombaAbastecimento");
		mv.addObject("bombasAbastecimento", bombaAbastecimentoRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(BombaAbastecimento bombaAbastecimento) {
		ModelAndView mv = new ModelAndView("bomba-abastecimento/CadastroBombaAbastecimento");
		mv.addObject("tiposBomba", TipoBombaEnum.values());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid BombaAbastecimento bombaAbastecimento, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(bombaAbastecimento);
		
		try {
			bombaAbastecimentoService.salvar(bombaAbastecimento);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(bombaAbastecimento);
		}
		
		attributes.addFlashAttribute("mensagem", "Bomba de abastecimento salvo com sucesso!");
		return new ModelAndView("redirect:/bomba-abastecimento/novo");
	}
	
	
	//@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") BombaAbastecimento bombaAbastecimento) {
		ModelAndView mv = prepararCadastrar(bombaAbastecimento);
		mv.addObject(bombaAbastecimento);
		return mv;
	}
	
	
	//@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") BombaAbastecimento bombaAbastecimento) {
		try {
			bombaAbastecimentoService.excluir(bombaAbastecimento);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
