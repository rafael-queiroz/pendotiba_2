package br.com.logic.pendotiba.logicbus.controller;

import java.math.BigInteger;

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

import br.com.logic.pendotiba.core.enums.LocalDeAtuacaoEnum;
import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.EntradaSaidaDeCarroDaGaragemRepository;
import br.com.logic.pendotiba.core.repository.TipoCarroRepository;
import br.com.logic.pendotiba.core.repository.TipoChassiRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.CarroFilter;
import br.com.logic.pendotiba.logicbus.repo.CarroRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.CarroProgramacaoService;
import br.com.logic.pendotiba.logicbus.service.CarroService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/carro")
public class CarroController {

	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	CarroRepositoryImpl carroRepositoryImpl;
	
	@Autowired
	TipoCarroRepository tipoCarroRepository;
	
	@Autowired
	TipoChassiRepository tipoChassiRepository;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemRepository movimentacaoCarroRepository;
	
	@Autowired
	CarroProgramacaoService carroProgramacaoService;
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(CarroFilter carroFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		carroFilter = (CarroFilter) httpServletRequest.getSession().getAttribute("carroFilter");
		if (carroFilter == null)
			carroFilter = new CarroFilter();
		
		ModelAndView mv = consultar(carroFilter, result, pageable, httpServletRequest);
		mv.addObject("carroFilter", carroFilter != null ? carroFilter : new CarroFilter());
		return mv;
	}
	
	
	@GetMapping
	public ModelAndView consultar(CarroFilter carroFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("carro/ConsultaCarro");
		mv.addObject("tiposCarro", tipoCarroRepository.findAll());
		mv.addObject("tiposChassi", tipoChassiRepository.findAll());
		
		PageWrapper<Carro> paginaWrapper = new PageWrapper<>(carroRepositoryImpl.filtrar(carroFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("carroFilter", carroFilter);
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(Carro carro) {
		ModelAndView mv = new ModelAndView("carro/CadastroCarro");
		mv.addObject("tiposCarro", tipoCarroRepository.findAll());
		mv.addObject("tiposChassi", tipoChassiRepository.findAll());
		mv.addObject("locaisDeAtuacao", LocalDeAtuacaoEnum.values());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Carro carro, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if (result.hasErrors()) 
			return prepararCadastrar(carro);
		
		try {
			carro = carroService.salvar(carro);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(carro);
		}
		
		attributes.addFlashAttribute("mensagem", "Carro salvo com sucesso!");
		return new ModelAndView("redirect:/carro/inicial" );
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") Carro carro) {
		ModelAndView mv = prepararCadastrar(carro);
		mv.addObject(carro);
		return mv;
	}
	
	
	@GetMapping("/visualizar/{id}")
	public ModelAndView prepararVisualizar(@PathVariable("id") Carro carro, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("carro/VisualizaCarro");
		//CarroFilter carroFilter = (CarroFilter) httpServletRequest.getSession().getAttribute("carroFilter");
		mv.addObject(carro);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Carro carro) {
		try {
			carroService.excluir(carro);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	
	/*
	@GetMapping("/roletas")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("carro/ListaCarro");
		mv.addObject("carros", carroRepository.findByOrderByNumeroDeOrdem());
		return mv;
	}
	
	@PostMapping("/roletas-atualizar")
	ModelAndView atualizarRoletas(Long idCarro, BigInteger roletaFinal1, BigInteger roletaFinal2, BigInteger roletaFinal3,
				@AuthenticationPrincipal UsuarioSistema usuario, RedirectAttributes attributes) {
		validarCarro(roletaFinal1, roletaFinal2, roletaFinal3);
		Carro carro = carroRepository.findById(idCarro).get();
		carroService.atualizarRoletas(carro, roletaFinal1);
		
		attributes.addFlashAttribute("mensagem", "Roletas atualizado com sucesso!");
		return new ModelAndView("redirect:/carro/roletas");
		
	}
	*/


	void validarCarro(BigInteger roletaFinal1, BigInteger roletaFinal2, BigInteger roletaFinal3) {
		if (roletaFinal1 == null || roletaFinal2 == null || roletaFinal3 == null) 
			throw new IllegalArgumentException();
	}
	
}
