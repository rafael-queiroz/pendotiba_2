package br.com.logic.pendotiba.logicbus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

import br.com.logic.pendotiba.core.model.MapaDiarioBombaAbastecimento;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioBombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioBombaAbastecimentoFilter;
import br.com.logic.pendotiba.logicbus.service.MapaDiarioBombaAbastecimentoService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/mapa-diario-bomba-abastecimento")
public class MapaDiarioBombaAbastecimentoController {

	@Autowired
	MapaDiarioBombaAbastecimentoRepository mapaDiarioBombaAbastecimentoRepository;
	
	@Autowired
	MapaDiarioBombaAbastecimentoService mapaDiarioBombaAbastecimentoService;
	
	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;
	
	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	
	
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
		ModelAndView mv = new ModelAndView("mapa-diario-bomba-abastecimento/ConsultaMapaDiarioBombaAbastecimento");
		mv.addObject("bombas", bombaAbastecimentoRepository.findByOrderByCodigo());
		PageWrapper<MapaDiarioBombaAbastecimento> paginaWrapper = new PageWrapper<>(mapaDiarioBombaAbastecimentoService.filtrar(mapaDiarioBombaAbastecimentoFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		
		httpServletRequest.getSession().setAttribute("mapaDiarioBombaAbastecimentoFilter", mapaDiarioBombaAbastecimentoFilter);
		return mv;
	}
	
	
	//@GetMapping("/novo")
	public ModelAndView prepararCadastrar(MapaDiarioBombaAbastecimento mapaDiarioBombaAbastecimento) {
		ModelAndView mv = new ModelAndView("mapa-diario-bomba-abastecimento/CadastroMapaDiarioBombaAbastecimento");
		mv.addObject("bombas", bombaAbastecimentoRepository.findByOrderByCodigo());
		return mv;
	}
	
	
	@Transactional
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid MapaDiarioBombaAbastecimento mapaDiarioBombaAbastecimento, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(mapaDiarioBombaAbastecimento);
		
		try {
			mapaDiarioBombaAbastecimento = mapaDiarioBombaAbastecimentoService.salvar(mapaDiarioBombaAbastecimento);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(mapaDiarioBombaAbastecimento);
		}
		
		attributes.addFlashAttribute("mensagem", "Mapa diário salvo com sucesso!");
		return new ModelAndView("redirect:/mapa-diario-bomba-abastecimento/inicial" );
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") MapaDiarioBombaAbastecimento mapaDiarioBombaAbastecimento) {
		ModelAndView mv = prepararCadastrar(mapaDiarioBombaAbastecimento);
		mv.addObject(mapaDiarioBombaAbastecimento);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") MapaDiarioBombaAbastecimento mapaDiarioBombaAbastecimento) {
		try {
			mapaDiarioBombaAbastecimentoService.excluir(mapaDiarioBombaAbastecimento);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	
	/*
	@GetMapping(value = "/imprimir/{tipo}", produces = MediaType.APPLICATION_PDF_VALUE)
	public void imprimirResumido(@PathVariable("tipo") String tipo, MapaDiarioBombaAbastecimentoFilter mapaDiarioBombaAbastecimentoFilter, HttpServletResponse response, HttpServletRequest request) throws JRException, SQLException, IOException {
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("filtro", mapaDiarioBombaAbastecimentoFilter.toString());
		
		mapaDiarioBombaAbastecimentoFilter = (MapaDiarioBombaAbastecimentoFilter) request.getSession().getAttribute("mapaDiarioBombaAbastecimentoFilter");
		
		List<MapaDiarioBombaAbastecimento> list = new ArrayList<>();
		
		mapaDiarioBombaAbastecimentoRepository.listarPorFiltro(mapaDiarioBombaAbastecimentoFilter).forEach(obj -> list.add(obj));
		
		if(tipo.equals("detalhado"))
			for (MapaDiarioBombaAbastecimento m : list)
				if(m.getBombaAbastecimento().isDiesel())
					m.setMapasDiarioCarro(mapaDiarioCarroRepository.findByDataCompetenciaAndBombaAbastecimentoDiesel(m.getDataCompetencia(), m.getBombaAbastecimento()));
				else
					m.setMapasDiarioCarro(mapaDiarioCarroRepository.findByDataCompetenciaAndBombaAbastecimentoArla(m.getDataCompetencia(), m.getBombaAbastecimento()));

		impressaoService.imprimir(parametros, "mapa_diario_bomba_abastecimento", response, list);
	}
	*/
	
}
