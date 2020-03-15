package br.com.logic.pendotiba.logicbus.controller.relatorios;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.logicbus.dto.relatorios.RelatorioEntradaSaidaPorHorarioDTO;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioEntradaSaidaPorHorarioFilter;
import br.com.logic.pendotiba.logicbus.service.ImpressaoService;
import br.com.logic.pendotiba.logicbus.service.relatorios.RelatorioEntradaSaidaPorHorarioService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/relatorios/entrada-saida-por-horario")
public class RelatorioEntradaSaidaPorHorarioController {
	
	@Autowired
	ImpressaoService impressaoService;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	RelatorioEntradaSaidaPorHorarioService relatorioEntradaSaidaPorHorarioService;
	
	
	
	@GetMapping
	public ModelAndView consultar(RelatorioEntradaSaidaPorHorarioFilter relatorioEntradaSaidaPorHorarioFilter, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("relatorios/entrada-saida-por-horario/Consulta");
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("turnos", turnoRepository.findAll());
		
		mv.addObject("lista", relatorioEntradaSaidaPorHorarioService.listarPorDatas(relatorioEntradaSaidaPorHorarioFilter));
		
		httpServletRequest.getSession().setAttribute("relEntradaSaidaPorHorario", relatorioEntradaSaidaPorHorarioFilter);
		return mv;
	}
	
	@GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_PDF_VALUE)
	public void imprimirPorHorario(HttpServletResponse response, HttpServletRequest httpServletRequest) throws JRException, SQLException, IOException {
		RelatorioEntradaSaidaPorHorarioFilter filtro = (RelatorioEntradaSaidaPorHorarioFilter) httpServletRequest.getSession().getAttribute("relEntradaSaidaPorHorario");
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("filtro", filtro.toString());
		
		List<RelatorioEntradaSaidaPorHorarioDTO> list = relatorioEntradaSaidaPorHorarioService.listarPorDatas(filtro);
		
		impressaoService.imprimir(parametros, "entrada_saida_de_carro_da_garagem_por_horario", response, list);
	}
}
