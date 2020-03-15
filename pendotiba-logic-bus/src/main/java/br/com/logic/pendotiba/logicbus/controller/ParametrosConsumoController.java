package br.com.logic.pendotiba.logicbus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import br.com.logic.pendotiba.core.model.ParametrosConsumo;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.ParametrosConsumoRepository;
import br.com.logic.pendotiba.core.repository.TipoChassiRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.ParametrosConsumoFilter;
import br.com.logic.pendotiba.logicbus.repo.ParametrosConsumoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.ParametrosConsumoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/parametros-consumo")
public class ParametrosConsumoController {

	@Autowired
	ParametrosConsumoRepository parametrosConsumoRepository;
	
	@Autowired
	ParametrosConsumoRepositoryImpl parametrosConsumoRepositoryImpl;
	
	@Autowired
	ParametrosConsumoService parametrosConsumoService;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	TipoChassiRepository tipoChassiRepository;
	
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(ParametrosConsumoFilter parametrosConsumoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		parametrosConsumoFilter = (ParametrosConsumoFilter) httpServletRequest.getSession().getAttribute("parametrosConsumoFilter");
		ModelAndView mv = consultar(parametrosConsumoFilter, result, pageable, httpServletRequest);
		mv.addObject("parametrosConsumoFilter", parametrosConsumoFilter != null ? parametrosConsumoFilter : new ParametrosConsumoFilter());
		return mv;
	}
	
	
	@GetMapping
	public ModelAndView consultar(ParametrosConsumoFilter parametrosConsumoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("parametros-consumo/ConsultaParametrosConsumo");
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("tiposChassi", tipoChassiRepository.findAll());
		
		PageWrapper<ParametrosConsumo> paginaWrapper = new PageWrapper<>(parametrosConsumoRepositoryImpl.filtrar(parametrosConsumoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("parametrosConsumoFilter", parametrosConsumoFilter);
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(ParametrosConsumo parametrosConsumo) {
		ModelAndView mv = new ModelAndView("parametros-consumo/CadastroParametrosConsumo");
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("tiposChassi", tipoChassiRepository.findAll());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid ParametrosConsumo parametrosConsumo, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(parametrosConsumo);
		
		try {
			parametrosConsumoService.salvar(parametrosConsumo);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(parametrosConsumo);
		}
		
		attributes.addFlashAttribute("mensagem", "Parâmetros de consumo salvo com sucesso!");
		return new ModelAndView("redirect:/parametros-consumo/novo");
	}
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") ParametrosConsumo parametrosConsumo) {
		ModelAndView mv = prepararCadastrar(parametrosConsumo);
		mv.addObject(parametrosConsumo);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") ParametrosConsumo parametrosConsumo) {
		try {
			parametrosConsumoService.excluir(parametrosConsumo);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
