package br.com.logic.pendotiba.logicbus.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.EntradaSaidaDeCarroDaGaragemRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.EntradaSaidaDeCarroDaGaragemFilter;
import br.com.logic.pendotiba.logicbus.repo.EntradaSaidaDeCarroDaGaragemRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.EntradaSaidaDeCarroDaGaragemService;
import br.com.logic.pendotiba.logicbus.service.ImpressaoService;
import br.com.logic.pendotiba.logicbus.service.MapaDiarioCarroService;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/entrada-saida-de-carro-da-garagem")
public class EntradaSaidaDeCarroDaGaragemController {
	
	@Autowired
	ImpressaoService impressaoService;

	@Autowired
	EntradaSaidaDeCarroDaGaragemRepository entradaSaidaDeCarroDaGaragemRepository;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemRepositoryImpl entradaSaidaDeCarroDaGaragemRepositoryImpl;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemService entradaSaidaDeCarroDaGaragemService;

	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	MapaDiarioCarroService mapaDiarioCarroService;
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(EntradaSaidaDeCarroDaGaragemFilter entradaSaidaDeCarroDaGaragemFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		entradaSaidaDeCarroDaGaragemFilter = (EntradaSaidaDeCarroDaGaragemFilter) httpServletRequest.getSession().getAttribute("entradaSaidaDeCarroDaGaragemFilter");
		if (entradaSaidaDeCarroDaGaragemFilter == null)
			entradaSaidaDeCarroDaGaragemFilter = new EntradaSaidaDeCarroDaGaragemFilter();
		ModelAndView mv = consultar(entradaSaidaDeCarroDaGaragemFilter, result, pageable, httpServletRequest);
		mv.addObject("entradaSaidaDeCarroDaGaragemFilter", entradaSaidaDeCarroDaGaragemFilter != null ? entradaSaidaDeCarroDaGaragemFilter : new EntradaSaidaDeCarroDaGaragemFilter());
		return mv;
	}
	
	@GetMapping
	public ModelAndView consultar(EntradaSaidaDeCarroDaGaragemFilter entradaSaidaDeCarroDaGaragemFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("entrada-saida-carro-garagem/ConsultaEntradaSaidaDeCarroDaGaragem");
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("turnos", turnoRepository.findAll());
		
		PageWrapper<EntradaSaidaDeCarroDaGaragem> paginaWrapper = new PageWrapper<>(entradaSaidaDeCarroDaGaragemRepositoryImpl.filtrar(entradaSaidaDeCarroDaGaragemFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("entradaSaidaDeCarroDaGaragemFilter", entradaSaidaDeCarroDaGaragemFilter);
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(EntradaSaidaDeCarroDaGaragem entradaSaidaDeCarroDaGaragem) {
		ModelAndView mv = new ModelAndView("entrada-saida-carro-garagem/CadastroEntradaSaidaDeCarroDaGaragem");
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("turnos", turnoRepository.findAll());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid EntradaSaidaDeCarroDaGaragem entradaSaidaDeCarroDaGaragem, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(entradaSaidaDeCarroDaGaragem);
		
		try {
			entradaSaidaDeCarroDaGaragem = entradaSaidaDeCarroDaGaragemService.salvar(entradaSaidaDeCarroDaGaragem);
			mapaDiarioCarroService.atualizarMapaPelaEntrada(entradaSaidaDeCarroDaGaragem);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(entradaSaidaDeCarroDaGaragem);
		}
		
		attributes.addFlashAttribute("mensagem", "Entrada e saída salvo com sucesso!");
		return new ModelAndView("redirect:/entrada-saida-de-carro-da-garagem/inicial" );
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id")EntradaSaidaDeCarroDaGaragem entradaSaidaDeCarroDaGaragem) {
		ModelAndView mv = prepararCadastrar(entradaSaidaDeCarroDaGaragem);
		mv.addObject(entradaSaidaDeCarroDaGaragem);
		return mv;
	}
	
	
	@GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_PDF_VALUE)
	public void imprimir(EntradaSaidaDeCarroDaGaragemFilter filtro, HttpServletResponse response, HttpServletRequest httpServletRequest) throws JRException, SQLException, IOException {
		filtro = (EntradaSaidaDeCarroDaGaragemFilter) httpServletRequest.getSession().getAttribute("entradaSaidaDeCarroDaGaragemFilter");
		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("filtro", filtro.toString());
		
		List<EntradaSaidaDeCarroDaGaragem> list = entradaSaidaDeCarroDaGaragemRepositoryImpl.filtrar(filtro);
		
		impressaoService.imprimir(parametros, "entrada_saida_de_carro_da_garagem", response, list);
	}
	
}
