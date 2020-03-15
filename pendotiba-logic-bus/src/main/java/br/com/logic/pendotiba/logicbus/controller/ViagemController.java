package br.com.logic.pendotiba.logicbus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.core.repository.StatusRepository;
import br.com.logic.pendotiba.core.repository.TipoViagemPerdidaRepository;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.core.repository.ViagemRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.ViagemFilter;
import br.com.logic.pendotiba.logicbus.repo.ViagemRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.ProgramacaoService;
import br.com.logic.pendotiba.logicbus.service.ViagemService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;
import br.com.logic.pendotiba.logicbus.validation.ViagemValidation;


@RestController
@RequestMapping("/viagem")
public class ViagemController {
	
	@Autowired
	ViagemValidation viagemValidation;
	
	@Autowired
	ViagemService viagemService;
	
	@Autowired
	ViagemRepository viagemRepository;
	
	@Autowired
	ViagemRepositoryImpl viagemRepositoryImpl;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	PontoRepository pontoRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TipoViagemPerdidaRepository tipoViagemPerdidaRepository;
	
	@Autowired
	ProgramacaoService programacaoService;
	
	@Autowired
	StatusRepository statusRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(ViagemFilter viagemFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		viagemFilter = (ViagemFilter) httpServletRequest.getSession().getAttribute("viagemFilter");
		if (viagemFilter == null)
			viagemFilter = new ViagemFilter();
		ModelAndView mv = consultar(viagemFilter, result, pageable, httpServletRequest);
		mv.addObject("viagemFilter", viagemFilter != null ? viagemFilter : new ViagemFilter());
		return mv;
	}
	
	
	@GetMapping
	public ModelAndView consultar(ViagemFilter viagemFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("viagem/ConsultaViagem");
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("turnos", turnoRepository.findAll());
				
		PageWrapper<Viagem> paginaWrapper = new PageWrapper<>(viagemRepositoryImpl.filtrar(viagemFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("viagemFilter", viagemFilter);
		return mv;
	}	
	
	
	
	
	

	@RequestMapping("/nova/programacao/{idProgramacao}")
	public ModelAndView prepararSalvar(Viagem viagem, @PathVariable("idProgramacao") Programacao programacao) {
		ModelAndView mv = new ModelAndView("viagem/CadastroViagem");
		viagem.setProgramacao(programacao);
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("pontos", pontoRepository.findByOrderByCodigo());
		mv.addObject("usuarios", usuarioRepository.findByFuncionarioFuncaoDespachanteTrueOrderByFuncionarioMatricula());
		mv.addObject("tiposViagemPerdida", tipoViagemPerdidaRepository.findByOrderByDescricao());
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		viagem.tratarRoletas();
		mv.addObject(viagem);
		return mv;
	}
	
	
	@PostMapping({"/nova/programacao/{idProgramacao}", "{\\+d}"})
	public ModelAndView salvar(@Valid Viagem viagem, @PathVariable("idProgramacao") Programacao programacao, BindingResult result, RedirectAttributes attributes, Model model) {
		viagem.setProgramacao(programacao);
		viagem.setCarroRealizado(programacao.carroAtual().getCarro());
		viagemValidation.validarViagem(viagem, result);
		
		if (result.hasErrors()) {	
			return prepararSalvar(viagem, programacao);
		}
		
		try {
			viagemService.salvar(viagem);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararSalvar(viagem, programacao);
		}	
		
		attributes.addFlashAttribute("mensagem", "Viagem salva com sucesso");
		return new ModelAndView("redirect:/programacao/" + programacao.getId());
	}
	
	
	/*
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Viagem viagem, BindingResult result, RedirectAttributes attributes, Model model) {
		if (result.hasErrors()) {	
			return prepararSalvar(viagem);
		}
		
		try {
			viagem.setCarroRealizado(viagem.getProgramacao().getCarroRealizado());
			viagemService.salvar(viagem);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararSalvar(viagem);
		}
		
		attributes.addFlashAttribute("mensagem", "Viagem salva com sucesso");
		return new ModelAndView("redirect:/programacao/" + viagem.getProgramacao().getId());
	}
	*/
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararEditar(@PathVariable Long id) {
		Viagem viagem = viagemRepository.carregarViagem(id);
		ModelAndView mv = prepararSalvar(viagem, viagem.getProgramacao());
		mv.addObject(viagem);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Viagem viagem) {
		try {
			viagemService.excluir(viagem);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
