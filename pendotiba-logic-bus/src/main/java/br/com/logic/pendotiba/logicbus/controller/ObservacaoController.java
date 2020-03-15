package br.com.logic.pendotiba.logicbus.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.logic.pendotiba.core.model.Observacao;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.ObservacaoRepository;
import br.com.logic.pendotiba.core.repository.TipoObservacaoRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.ObservacaoFilter;
import br.com.logic.pendotiba.logicbus.repo.ObservacaoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.ObservacaoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;


@RestController
@RequestMapping("/relatorios/observacao")
public class ObservacaoController {
	
	//private static final Logger logger = LoggerFactory.getLogger(Programacao.class);
	
	@Autowired
	ObservacaoRepository observacaoRepository;
	
	@Autowired
	ObservacaoRepositoryImpl observacaoRepositoryImpl;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	TipoObservacaoRepository tipoObservacaoRepository;
	
	
	@Autowired
	ObservacaoService observacaoService;
	
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(ObservacaoFilter observacaoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		observacaoFilter = (ObservacaoFilter) httpServletRequest.getSession().getAttribute("observacaoFilter");
		if (observacaoFilter == null)
			observacaoFilter = new ObservacaoFilter();
		ModelAndView mv = consultar(observacaoFilter, result, pageable, httpServletRequest);
		mv.addObject("observacaoFilter", observacaoFilter != null ? observacaoFilter : new ObservacaoFilter());
		return mv;
	}
	
	@GetMapping
	public ModelAndView consultar(ObservacaoFilter observacaoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("observacao/ConsultaObservacao");
		mv.addObject("tiposObservacao", tipoObservacaoRepository.findAll());
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("motoristas", funcionarioRepository.findByFuncaoMotoristaTrueOrderByMatricula());
				
		PageWrapper<Observacao> paginaWrapper = new PageWrapper<>(observacaoRepositoryImpl.filtrar(observacaoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("observacaoFilter", observacaoFilter);
		return mv;
	}	
	
	
	
	@GetMapping("/visualizar/{id}")
	public ModelAndView visualizar(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("observacao/VisualizaObservacao");
		Observacao observacao = observacaoRepository.findOne(id);
		mv.addObject(observacao);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Observacao observacao) {
		try {
			observacaoService.excluir(observacao);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
