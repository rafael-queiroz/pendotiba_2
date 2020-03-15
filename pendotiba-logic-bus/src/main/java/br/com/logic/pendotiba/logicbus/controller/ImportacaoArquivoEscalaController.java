package br.com.logic.pendotiba.logicbus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.enums.TipoArquivoEnum;
import br.com.logic.pendotiba.core.model.ArquivoImportado;
import br.com.logic.pendotiba.core.repository.ArquivoImportadoRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.ViagemRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.dto.ArquivoImportadoDTO;
import br.com.logic.pendotiba.logicbus.repo.ArquivoImportadoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.ArquivoEscalaImportadoService;
import br.com.logic.pendotiba.logicbus.service.ImportacaoEscalaService;
import br.com.logic.pendotiba.logicbus.service.ProgramacaoService;
import br.com.logic.pendotiba.logicbus.service.ViagemImportadaService;
import br.com.logic.pendotiba.logicbus.storage.ArquivoImportadoStorage;
import br.com.logic.pendotiba.logicbus.storage.ArquivoImportadoStorageRunnable;
import br.com.logic.pendotiba.logicbus.validation.ArquivoEscalaValidation;


@RestController
@RequestMapping("/importacao-arquivo-escala")
public class ImportacaoArquivoEscalaController {
	
	@Autowired
	ArquivoImportadoRepository arquivoImportadoRepository;
	
	@Autowired
	ArquivoImportadoRepositoryImpl arquivoImportadoRepositoryImpl;
	
	@Autowired
	ArquivoImportadoStorage programacaoStorage;
	
	@Autowired
	ArquivoEscalaImportadoService arquivoEscalaImportadoService;
	
	@Autowired
	ProgramacaoService programacaoService;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	ViagemRepository viagemRepository;
	
	@Autowired
	ViagemImportadaService viagemImportadaService;
	
	@Autowired
	ArquivoEscalaValidation arquivoEscalaValidation;
	
	@Autowired
	ImportacaoEscalaService importacaoEscalaService;
	
	
	
	@GetMapping
	public ModelAndView consultar(ArquivoImportado arquivoImportado, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("arquivo-importado-escala/ConsultaArquivoImportado");
		
		arquivoImportado.setTipoArquivo(TipoArquivoEnum.ESCALA);
		PageWrapper<ArquivoImportado> paginaWrapper = new PageWrapper<>(arquivoImportadoRepositoryImpl.filtrar(arquivoImportado, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	
	@GetMapping("/importar")
	public ModelAndView prepararImportar(ArquivoImportado arquivoImportado) {
		ModelAndView mv = new ModelAndView("arquivo-importado-escala/ImportacaoArquivo");
		return mv;
	}
	
	
	@PostMapping("/importar")
	public DeferredResult<ArquivoImportadoDTO> importar(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<ArquivoImportadoDTO> resultado = new DeferredResult<>();

		Thread thread = new Thread(new ArquivoImportadoStorageRunnable(files, resultado, programacaoStorage));
		thread.start();

		return resultado;
	}
	
	@PostMapping(value = { "/salvar", "{\\d+}" })
	public ModelAndView salvar(@Valid ArquivoImportado arquivoImportado, BindingResult result, Model model, RedirectAttributes attributes) {
		//arquivoEscalaValidation.validarArquivoEscala(arquivoImportado, result);
		if (result.hasErrors()) {
			return prepararImportar(arquivoImportado);
		}
		
		//ArquivoImportado arquivoSalvo = arquivoEscalaImportadoService.importar(arquivoImportado);
		
		importacaoEscalaService.getPostsPlainJSON(arquivoImportado.getDataCompetencia());
		
		programacaoService.gerarProgramacoesPorDataCompetencia(arquivoImportado.getDataCompetencia());
		
		//arquivoEscalaValidation.validarExistenciaDasViagens(arquivoImportado, result);
		if (result.hasErrors()) {
			arquivoEscalaImportadoService.limparBasesImportadasPorDataDeCompetencia(arquivoImportado);
			programacaoService.limparBasesProgramacaoViagensPorDataDeCompetencia(arquivoImportado.getDataCompetencia());
			arquivoImportado.setErro(true);
			return prepararImportar(arquivoEscalaImportadoService.salvar(arquivoImportado));
		}
		
		attributes.addFlashAttribute("mensagem", "Arquivo importado com sucesso!");
		return new ModelAndView("redirect:/importacao-arquivo-escala/importar");
	}

}
