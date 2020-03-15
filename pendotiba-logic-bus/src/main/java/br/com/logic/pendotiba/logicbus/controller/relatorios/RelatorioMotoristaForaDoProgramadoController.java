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

import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.dto.relatorios.RelatorioMotoristaForaDoProgramadoDTO;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioMotoristaForaDoProgramadoFilter;
import br.com.logic.pendotiba.logicbus.repo.relatorios.RelatorioMotoristaForaDoProgramadoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.ImpressaoService;
import br.com.logic.pendotiba.logicbus.service.relatorios.RelatorioMotoristaForaDoProgramadoService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/relatorios/motorista-fora-do-programado")
public class RelatorioMotoristaForaDoProgramadoController {
	
	@Autowired
	ImpressaoService impressaoService;
	
	@Autowired
	RelatorioMotoristaForaDoProgramadoService relatorioMotoristaForaDoProgramadoService; 
	
	@Autowired
	RelatorioMotoristaForaDoProgramadoRepositoryImpl relatorioMotoristaForaDoProgramadoRepository;
	
	
	@GetMapping
	public ModelAndView consultar(RelatorioMotoristaForaDoProgramadoFilter filter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("relatorios/motorista-fora-do-programado/Consulta");
		PageWrapper<RelatorioMotoristaForaDoProgramadoDTO> paginaWrapper = new PageWrapper<>(relatorioMotoristaForaDoProgramadoService.filtrar(filter, pageable), httpServletRequest);
		mv.addObject("filter", filter);
		mv.addObject("pagina", paginaWrapper);
		httpServletRequest.getSession().setAttribute("relatorioMotoristaForaDoProgramadoFilter", filter);
		return mv;
	}
	
	
	@GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_PDF_VALUE)
	public void imprimir(RelatorioMotoristaForaDoProgramadoFilter relatorioMotoristaForaDoProgramadoFilter, HttpServletResponse response, HttpServletRequest httpServletRequest) throws JRException, SQLException, IOException {
		relatorioMotoristaForaDoProgramadoFilter = (RelatorioMotoristaForaDoProgramadoFilter) httpServletRequest.getSession().getAttribute("relatorioMotoristaForaDoProgramadoFilter");
		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("filtro", relatorioMotoristaForaDoProgramadoFilter.toString());
		
		List<RelatorioMotoristaForaDoProgramadoDTO> lista = relatorioMotoristaForaDoProgramadoRepository.listar(relatorioMotoristaForaDoProgramadoFilter);

		impressaoService.imprimir(parametros, "relatorio_motoristas_fora_do_programado", response, lista);
	}
	
}
