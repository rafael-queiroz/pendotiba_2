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

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioCarroFilter;
import br.com.logic.pendotiba.logicbus.service.ImpressaoService;
import br.com.logic.pendotiba.logicbus.service.relatorios.RelatorioAbastecimentoPorCarroService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/relatorios/abastecimento-por-carro")
public class RelatorioAbastecimentoPorCarroController {
	
	@Autowired
	ImpressaoService impressaoService;
	
	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository; 
	
	@Autowired
	RelatorioAbastecimentoPorCarroService relatorioAbastecimentoPorCarroService;
	
	@Autowired
	CarroRepository carroRepository;
	
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(MapaDiarioCarroFilter mapaDiarioCarroFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		mapaDiarioCarroFilter = (MapaDiarioCarroFilter) httpServletRequest.getSession().getAttribute("mapaDiarioCarroFilter");
		if (mapaDiarioCarroFilter == null)
			mapaDiarioCarroFilter = new MapaDiarioCarroFilter();
		ModelAndView mv = consultar(mapaDiarioCarroFilter, result, pageable, httpServletRequest);
		mv.addObject("mapaDiarioCarroFilter", mapaDiarioCarroFilter != null ? mapaDiarioCarroFilter : new MapaDiarioCarroFilter());
		return mv;
	}
	
	@GetMapping
	public ModelAndView consultar(MapaDiarioCarroFilter mapaDiarioCarroFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("relatorios/abastecimento-por-carro/Consulta");
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		
		PageWrapper<MapaDiarioCarro> paginaWrapper = new PageWrapper<>(relatorioAbastecimentoPorCarroService.filtrarPaginando(mapaDiarioCarroFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("mapaDiarioCarroFilter", mapaDiarioCarroFilter);
		return mv;
	}
	
	@GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_PDF_VALUE)
	public void imprimir(MapaDiarioCarroFilter filtro, HttpServletResponse response, HttpServletRequest httpServletRequest) throws JRException, SQLException, IOException {
		filtro = (MapaDiarioCarroFilter) httpServletRequest.getSession().getAttribute("mapaDiarioCarroFilter");

		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("filtro", filtro.toString());
		
		List<MapaDiarioCarro> mapas = relatorioAbastecimentoPorCarroService.filtrar(filtro);
		
		List<MapaDiarioCarro> carrosAbastecidos = relatorioAbastecimentoPorCarroService.filtrarMapasComCarrosAbastecidos(mapas);
		
		
		List<Carro> carrosNaoAbastecidos = relatorioAbastecimentoPorCarroService.filtrarCarrosNaoAbastecidos(filtro);
		List<Carro> carrosComTanqueCheio = relatorioAbastecimentoPorCarroService.filtrarCarrosComTanqueCheio(filtro);
		parametros.put("carrosNaoAbastecidos", numerosDeOrdemStr(carrosNaoAbastecidos));
		parametros.put("carrosComTanqueCheio", numerosDeOrdemStr(carrosComTanqueCheio));
		parametros.put("qtdCarrosNaoAbastecidos", carrosNaoAbastecidos.size());
		parametros.put("qtdCarrosComTanqueCheio", carrosComTanqueCheio.size());
		
		//carrosAbastecidos.forEach(c -> System.out.println(c.getCarro()));
		if(filtro.getAgrupamento().equals(0L))
			impressaoService.imprimir(parametros, "relatorio_abastecimento_por_carro_agrupado_por_linha", response, carrosAbastecidos);
		else if(filtro.getAgrupamento().equals(1L))
			impressaoService.imprimir(parametros, "relatorio_abastecimento_por_carro_agrupado_por_tipo_de_chassi", response, carrosAbastecidos);
		else
			impressaoService.imprimir(parametros, "relatorio_abastecimento_por_carro_agrupado_por_carro", response, carrosAbastecidos);
		
	}
	
	
	String numerosDeOrdemStr(List<Carro> carros) {
		StringBuilder numerosDeOrdemStr = new StringBuilder();
		carros.forEach(c -> {
			if(numerosDeOrdemStr.toString().isEmpty()) 
				numerosDeOrdemStr.append(c.getNumeroDeOrdem());
			else
				numerosDeOrdemStr.append(", ").append(c.getNumeroDeOrdem());
		});
		return numerosDeOrdemStr.toString();
	}
}
