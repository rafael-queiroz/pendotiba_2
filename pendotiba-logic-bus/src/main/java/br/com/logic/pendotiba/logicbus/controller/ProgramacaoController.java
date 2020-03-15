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
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;
import br.com.logic.pendotiba.core.repository.GaragemRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.PontoLinhaRepository;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.StatusRepository;
import br.com.logic.pendotiba.core.repository.TipoCarroRepository;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.ProgramacaoFilter;
import br.com.logic.pendotiba.logicbus.repo.ProgramacaoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.CarroService;
import br.com.logic.pendotiba.logicbus.service.FuncionarioService;
import br.com.logic.pendotiba.logicbus.service.ImpressaoService;
import br.com.logic.pendotiba.logicbus.service.MapaDiarioCarroService;
import br.com.logic.pendotiba.logicbus.service.ProgramacaoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelLiberarCarroException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;
import br.com.logic.pendotiba.logicbus.session.TabelaViagemSession;
import br.com.logic.pendotiba.logicbus.validation.ProgramacaoValidation;
import net.sf.jasperreports.engine.JRException;


@RestController
@RequestMapping("/programacao")
public class ProgramacaoController {
	
	//private static final Logger logger = LoggerFactory.getLogger(Programacao.class);
	
	@Autowired
	ProgramacaoValidation programacaoValidation;
	
	
	@Autowired
	ImpressaoService impressaoService;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	ProgramacaoRepositoryImpl programacaoRepositoryImpl;
	
	@Autowired
	ProgramacaoService programacaoService;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	FuncionarioService funcionarioService;
	
	@Autowired
	PontoRepository pontoRepository;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	StatusRepository statusRepository;
	
	@Autowired
	GaragemRepository garagemRepository;
	
	@Autowired
	TipoCarroRepository tipoCarroRepository;
	
	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	
	@Autowired
	TabelaViagemSession tabelaViagemSession;
	
	@Autowired
	MapaDiarioCarroService mapaDiarioCarroService;
	
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(ProgramacaoFilter programacaoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		programacaoFilter = (ProgramacaoFilter) httpServletRequest.getSession().getAttribute("programacaoFilter");
		if (programacaoFilter == null)
			programacaoFilter = new ProgramacaoFilter();
		ModelAndView mv = consultar(programacaoFilter, result, pageable, httpServletRequest);
		mv.addObject("programacaoFilter", programacaoFilter != null ? programacaoFilter : new ProgramacaoFilter());
		return mv;
	}
	
	@GetMapping
	public ModelAndView consultar(ProgramacaoFilter programacaoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("programacao/ConsultaProgramacao");
		//mv.addObject("garagens", garagemRepository.findAll());
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("pontos", pontoRepository.findByOrderByCodigo());
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("motoristas", funcionarioRepository.findByFuncaoMotoristaTrueOrderByMatricula());
		mv.addObject("status", statusRepository.findAll());
		mv.addObject("turnos", turnoRepository.findAll());
		mv.addObject("tiposCarro", tipoCarroRepository.findAllByOrderByDescricaoAsc());
				
		PageWrapper<Programacao> paginaWrapper = new PageWrapper<>(programacaoRepositoryImpl.filtrar(programacaoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("programacaoFilter", programacaoFilter);
		return mv;
	}	
	
	
	@RequestMapping("/novo")
	public ModelAndView prepararCadastrar(Programacao programacao) {
		ModelAndView mv = new ModelAndView("programacao/CadastroProgramacao");
		mv.addObject("linhas", linhaRepository.findAll());
		mv.addObject("pontos", pontoRepository.findAll());
		mv.addObject("turnos", turnoRepository.findAll());
		mv.addObject("status", statusRepository.findAll());
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("motoristas", funcionarioRepository.findByFuncaoMotoristaTrueOrderByMatricula());
		mv.addObject("parceiros", funcionarioRepository.findByFuncaoCobradorTrueOrderByMatricula());
		mv.addObject("viagens", programacao.getViagens());
		if(programacao.getStatus() == null)
			programacao.setStatus(statusRepository.findOne(Status.ATIVO));
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Programacao programacao, BindingResult result, Model model, RedirectAttributes attributes) {
		programacaoValidation.validarProgramacao(programacao, result);
		if (result.hasErrors()) {
			return prepararCadastrar(programacao);
		}
		
		try {
			programacao = programacaoService.salvar(programacao);
			if(programacao.encerrado())
				mapaDiarioCarroService.atualizarMapaPelaProgramacao(programacao);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(programacao);
		}
		
		attributes.addFlashAttribute("mensagem", "Programação salva com sucesso");
		return new ModelAndView("redirect:/programacao/" + programacao.getId());
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararEditar(@PathVariable Long id) {
		Programacao programacao = programacaoRepository.carregarProgramacao(id);
		if(programacao != null) 
			if(!programacao.podeAlterar())
				return visualizar(id);
			
		ModelAndView mv = prepararCadastrar(programacao);
		mv.addObject(programacao);
		return mv;
		
	}
	
	
	@GetMapping("/visualizar/{id}")
	public ModelAndView visualizar(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("programacao/VisualizaProgramacao");
		Programacao programacao = programacaoRepository.carregarProgramacao(id);
		mv.addObject(programacao);
		return mv;
	}
	
	
	@GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_PDF_VALUE)
	public void imprimir(ProgramacaoFilter filtro, HttpServletResponse response, HttpServletRequest httpServletRequest) throws JRException, SQLException, IOException {
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicial", filtro.getDataInicial());
		parametros.put("dataFinal", filtro.getDataFinal());
		
		filtro = (ProgramacaoFilter) httpServletRequest.getSession().getAttribute("programacaoFilter");
		
		List<Programacao> programacoes = programacaoRepositoryImpl.listarProgramacoes(filtro);

		impressaoService.imprimir(parametros, "programacoes", response, programacoes);
	}
	
	
	@PostMapping("/liberar/{id}")
	public @ResponseBody ResponseEntity<?> liberar(@PathVariable("id") Programacao programacao) {
		try {
			programacao.setStatus(statusRepository.findOne(Status.LIBERADO));
			programacao.carroAtual().setRoletaInicial1(programacao.getCarroRealizado().getRoleta1());
			programacaoValidation.validarLiberarProgramacaoDireto(programacao);
			programacaoService.salvar(programacao);
		} catch (ImpossivelLiberarCarroException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	/*
	@PostMapping("/viagem")
	public ModelAndView adicionarViagem(String uuid, String horaSaidaProgramada, Long linhaProgramada, Long pontoOrigem, Long pontoDestino) {
		validarViagem(horaSaidaProgramada, linhaProgramada, pontoOrigem, pontoDestino);
		
		Viagem viagem = new Viagem();
		viagem.setHoraSaidaProgramada(DataUtil.getTime(horaSaidaProgramada));
		viagem.setLinhaProgramada(linhaRepository.findOne(linhaProgramada));
		viagem.setPontoLinha(pontoLinhaRepository.findByPontoOrigemIdAndPontoDestinoIdAndLinhaId(pontoOrigem, pontoDestino, linhaProgramada).get());
		
		tabelaViagemSession.adicionarViagem(uuid, viagem);
		return mvTabelaViagemProgramacao(uuid);
	}
	
	void validarViagem(String horaSaidaProgramada, Long linhaProgramada, Long pontoOrigem, Long pontoDestino) {
		if (StringUtils.isEmpty(horaSaidaProgramada) || linhaProgramada == null || pontoOrigem == null || pontoDestino == null ) {
			throw new IllegalArgumentException();
		}
	}
	
	ModelAndView mvTabelaViagemProgramacao(String uuid) {
		ModelAndView mv = new ModelAndView("programacao/TabelaViagemProgramacao");
		mv.addObject("viagens", tabelaViagemSession.getViagens(uuid));
		return mv;
	}
	 */

}
