package br.com.logic.pendotiba.logicbus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.repository.FuncaoRepository;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.FuncionarioFilter;
import br.com.logic.pendotiba.logicbus.repo.FuncionarioRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.FuncionarioService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {

	@Autowired
	FuncionarioService funcionarioService;

	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	FuncionarioRepositoryImpl funcionarioRepositoryImpl;
	
	@Autowired
	FuncaoRepository funcaoRepository;
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(FuncionarioFilter funcionarioFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		funcionarioFilter = (FuncionarioFilter) httpServletRequest.getSession().getAttribute("funcionarioFilter");
		ModelAndView mv = consultar(funcionarioFilter, result, pageable, httpServletRequest);
		mv.addObject("funcionarioFilter", funcionarioFilter != null ? funcionarioFilter : new FuncionarioFilter());
		return mv;
	}
	
	@GetMapping
	public ModelAndView consultar(FuncionarioFilter funcionarioFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("funcionario/ConsultaFuncionario");
		mv.addObject("funcoes", funcaoRepository.findByOrderByDescricao());
		
		PageWrapper<Funcionario> paginaWrapper = new PageWrapper<>(funcionarioRepositoryImpl.filtrar(funcionarioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("funcionarioFilter", funcionarioFilter);
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("funcionario/CadastroFuncionario");
		mv.addObject("funcoes", funcaoRepository.findByOrderByDescricao());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(funcionario);
		
		try {
			funcionarioService.salvar(funcionario);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(funcionario);
		}
		
		attributes.addFlashAttribute("mensagem", "Funcionário salvo com sucesso!");
		return new ModelAndView("redirect:/funcionario/inicial");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") Funcionario funcionario) {
		ModelAndView mv = prepararCadastrar(funcionario);
		mv.addObject(funcionario);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Funcionario funcionario) {
		try {
			funcionarioService.excluir(funcionario);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/cobrador/rapida")
	public @ResponseBody List<Funcionario> pesquisarCobrador(String nome) {
		validarTamanhoNome(nome);
		return funcionarioService.findByFuncaoCobradorTrueAndNomeStartingWithIgnoreCase(nome);
	}
	
	
	@GetMapping("/motorista/rapida")
	public @ResponseBody List<Funcionario> pesquisarMotorista(String nome) {
		validarTamanhoNome(nome);
		return funcionarioService.findByFuncaoMotoristaTrueAndNomeStartingWithIgnoreCase(nome);
	}
	
	@GetMapping("/rapida")
	public @ResponseBody List<Funcionario> pesquisar(String nome) {
		validarTamanhoNome(nome);
		return funcionarioService.findByNomeStartingWithIgnoreCase(nome);
	}
	
	void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) 
			throw new IllegalArgumentException();
	}

	
}
