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

import br.com.logic.pendotiba.core.model.Reclamacao;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.ReclamacaoRepository;
import br.com.logic.pendotiba.core.repository.TipoReclamacaoRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.ReclamacaoFilter;
import br.com.logic.pendotiba.logicbus.repo.ReclamacaoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.ReclamacaoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;


@RestController
@RequestMapping("/relatorios/reclamacao")
public class ReclamacaoController {
	
	//private static final Logger logger = LoggerFactory.getLogger(Programacao.class);
	
	@Autowired
	ReclamacaoRepository reclamacaoRepository;
	
	@Autowired
	ReclamacaoRepositoryImpl reclamacaoRepositoryImpl;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	TipoReclamacaoRepository tipoReclamacaoRepository;
	
	
	@Autowired
	ReclamacaoService reclamacaoService;
	
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(ReclamacaoFilter reclamacaoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		reclamacaoFilter = (ReclamacaoFilter) httpServletRequest.getSession().getAttribute("reclamacaoFilter");
		if (reclamacaoFilter == null)
			reclamacaoFilter = new ReclamacaoFilter();
		ModelAndView mv = consultar(reclamacaoFilter, result, pageable, httpServletRequest);
		mv.addObject("reclamacaoFilter", reclamacaoFilter != null ? reclamacaoFilter : new ReclamacaoFilter());
		return mv;
	}
	
	@GetMapping
	public ModelAndView consultar(ReclamacaoFilter reclamacaoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("reclamacao/ConsultaReclamacao");
		mv.addObject("tiposReclamacao", tipoReclamacaoRepository.findAll());
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("motoristas", funcionarioRepository.findByFuncaoMotoristaTrueOrderByMatricula());
				
		PageWrapper<Reclamacao> paginaWrapper = new PageWrapper<>(reclamacaoRepositoryImpl.filtrar(reclamacaoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("reclamacaoFilter", reclamacaoFilter);
		return mv;
	}	
	
	
	
	@GetMapping("/visualizar/{id}")
	public ModelAndView visualizar(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("reclamacao/VisualizaReclamacao");
		Reclamacao reclamacao = reclamacaoRepository.findOne(id);
		mv.addObject(reclamacao);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Reclamacao reclamacao) {
		try {
			reclamacaoService.excluir(reclamacao);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
