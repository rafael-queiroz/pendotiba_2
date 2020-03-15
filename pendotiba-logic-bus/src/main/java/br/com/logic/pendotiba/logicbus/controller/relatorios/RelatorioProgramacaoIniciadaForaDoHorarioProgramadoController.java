package br.com.logic.pendotiba.logicbus.controller.relatorios;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.dto.relatorios.RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioProgramacaoIniciadaForaDoHorarioProgramadoFilter;
import br.com.logic.pendotiba.logicbus.repo.relatorios.RelatorioProgramacaoIniciadaForaDoHorarioProgramadoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.ImpressaoService;
import br.com.logic.pendotiba.logicbus.service.relatorios.RelatorioProgamacaoIniciadaForaDoHorarioProgramadoService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/relatorios/programacao-iniciada-fora-do-horario-programado")
public class RelatorioProgramacaoIniciadaForaDoHorarioProgramadoController {
	
	@Autowired
	ImpressaoService impressaoService;
	
	@Autowired
	RelatorioProgramacaoIniciadaForaDoHorarioProgramadoRepositoryImpl repository;
	
	@Autowired
	RelatorioProgamacaoIniciadaForaDoHorarioProgramadoService service;
	
	
	@Autowired
	PontoRepository pontoRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	
	@GetMapping
	public ModelAndView consultar(RelatorioProgramacaoIniciadaForaDoHorarioProgramadoFilter filter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("relatorios/programacao-iniciada-fora-do-horario-programado/Consulta");
		mv.addObject("pontos", pontoRepository.findByOrderByCodigo());
		mv.addObject("turnos", turnoRepository.findAll());
		mv.addObject("filter", filter);
				
		PageWrapper<RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO> paginaWrapper = new PageWrapper<>(service.filtrar(filter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("programacaoIniciadaForaDoHorarioProgramadoFilter", filter);
		return mv;
	}
	
	
	@GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_PDF_VALUE)
	public void imprimir(RelatorioProgramacaoIniciadaForaDoHorarioProgramadoFilter filter, HttpServletResponse response, HttpServletRequest httpServletRequest) throws JRException, SQLException, IOException {
		filter = (RelatorioProgramacaoIniciadaForaDoHorarioProgramadoFilter) httpServletRequest.getSession().getAttribute("programacaoIniciadaForaDoHorarioProgramadoFilter");
		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("filtro", filter.toString());
		
		List<RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO> lista = repository.listar(filter);

		impressaoService.imprimir(parametros, "relatorio_programacao_iniciada_fora_do_horario_programado", response, lista);
	}
	
}
