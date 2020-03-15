package br.com.logic.pendotiba.logicbus.controller.relatorios;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

import br.com.logic.pendotiba.core.model.MapaDiarioBombaAbastecimento;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioBombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioBombaAbastecimentoFilter;
import br.com.logic.pendotiba.logicbus.repo.MapaDiarioBombaAbastecimentoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.ImpressaoService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/relatorios/abastecimento-por-bomba")
public class RelatorioAbastecimentoPorBombaController {
	
	@Autowired
	ImpressaoService impressaoService;
	
	@Autowired
	MapaDiarioBombaAbastecimentoRepository mapaDiarioBombaAbastecimentoRepository; 
	
	@Autowired
	MapaDiarioBombaAbastecimentoRepositoryImpl mapaDiarioBombaAbastecimentoRepositoryImpl; 
	
	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository; 
	
	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;
	
	
	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(MapaDiarioBombaAbastecimentoFilter mapaDiarioBombaAbastecimentoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		mapaDiarioBombaAbastecimentoFilter = (MapaDiarioBombaAbastecimentoFilter) httpServletRequest.getSession().getAttribute("mapaDiarioBombaAbastecimentoFilter");
		if (mapaDiarioBombaAbastecimentoFilter == null)
			mapaDiarioBombaAbastecimentoFilter = new MapaDiarioBombaAbastecimentoFilter();
		
		ModelAndView mv = consultar(mapaDiarioBombaAbastecimentoFilter, result, pageable, httpServletRequest);
		mv.addObject("mapaDiarioBombaAbastecimentoFilter", mapaDiarioBombaAbastecimentoFilter != null ? mapaDiarioBombaAbastecimentoFilter : new MapaDiarioBombaAbastecimentoFilter());
		return mv;
	}
	
	@GetMapping
	public ModelAndView consultar(MapaDiarioBombaAbastecimentoFilter mapaDiarioBombaAbastecimentoFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("relatorios/abastecimento-por-bomba/Consulta");
		mv.addObject("bombas", bombaAbastecimentoRepository.findByOrderByCodigo());
		
		PageWrapper<MapaDiarioBombaAbastecimento> paginaWrapper = new PageWrapper<>(mapaDiarioBombaAbastecimentoRepositoryImpl.filtrar(mapaDiarioBombaAbastecimentoFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("mapaDiarioBombaAbastecimentoFilter", mapaDiarioBombaAbastecimentoFilter);
		return mv;
	}
	
	@GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_PDF_VALUE)
	public void imprimirResumido(MapaDiarioBombaAbastecimentoFilter mapaDiarioBombaAbastecimentoFilter, HttpServletResponse response, HttpServletRequest request) throws JRException, SQLException, IOException {
		mapaDiarioBombaAbastecimentoFilter = (MapaDiarioBombaAbastecimentoFilter) request.getSession().getAttribute("mapaDiarioBombaAbastecimentoFilter");
		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("filtro", mapaDiarioBombaAbastecimentoFilter.toString());
		parametros.put("tipo", mapaDiarioBombaAbastecimentoFilter.getBombaAbastecimento() != null ? "detalhado" : "resumido");
		
		List<MapaDiarioBombaAbastecimento> list = new ArrayList<>();
		
		mapaDiarioBombaAbastecimentoRepositoryImpl.listarPorFiltro(mapaDiarioBombaAbastecimentoFilter).forEach(obj -> list.add(obj));
		
		if(mapaDiarioBombaAbastecimentoFilter.getBombaAbastecimento() != null)
			for (MapaDiarioBombaAbastecimento m : list)
				if(m.getBombaAbastecimento().isDiesel())
					m.setMapasDiarioCarro(mapaDiarioCarroRepository.findByDataCompetenciaAndBombaAbastecimentoDieselOrderByCarroNumeroDeOrdem(m.getDataCompetencia(), m.getBombaAbastecimento()));
				else
					m.setMapasDiarioCarro(mapaDiarioCarroRepository.findByDataCompetenciaAndBombaAbastecimentoArlaOrderByCarroNumeroDeOrdem(m.getDataCompetencia(), m.getBombaAbastecimento()));

		impressaoService.imprimir(parametros, "relatorio_abastecimento_por_bomba", response, list);
	}
}
