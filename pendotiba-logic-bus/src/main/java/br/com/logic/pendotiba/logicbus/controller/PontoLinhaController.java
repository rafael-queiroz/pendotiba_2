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

import br.com.logic.pendotiba.core.enums.IdaVoltaEnum;
import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.PontoLinhaRepository;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.logicbus.filter.PontoLinhaFilter;
import br.com.logic.pendotiba.logicbus.repo.PontoLinhaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.PontoLinhaService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/ponto-linha")
public class PontoLinhaController {

	@Autowired
	PontoLinhaService pontoLinhaService;
	
	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	
	@Autowired
	PontoLinhaRepositoryImpl pontoLinhaRepositoryImpl;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	PontoRepository pontoRepository;
	
	
	
	@GetMapping
	public ModelAndView consultar(PontoLinhaFilter pontoLinhaFilter) {
		ModelAndView mv = new ModelAndView("ponto-linha/ConsultaPontoLinha");
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("pontos", pontoRepository.findByOrderByCodigo());
		mv.addObject("sentidos", IdaVoltaEnum.values());
		
		mv.addObject("pontosLinha", pontoLinhaRepositoryImpl.filtrar(pontoLinhaFilter));
		return mv;
	}
	

	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(PontoLinha pontoLinha) {
		ModelAndView mv = new ModelAndView("ponto-linha/CadastroPontoLinha");
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("pontos", pontoRepository.findByOrderByCodigo());
		mv.addObject("sentidos", IdaVoltaEnum.values());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid PontoLinha pontoLinha, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(pontoLinha);
		
		try {
			pontoLinhaService.salvar(pontoLinha);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(pontoLinha);
		}
		
		attributes.addFlashAttribute("mensagem", "Relação ponto linha salva com sucesso!");
		return new ModelAndView("redirect:/ponto-linha");
	}
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") PontoLinha pontoLinha) {
		ModelAndView mv = prepararCadastrar(pontoLinha);
		mv.addObject(pontoLinha);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") PontoLinha pontoLinha) {
		try {
			pontoLinhaService.excluir(pontoLinha);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
