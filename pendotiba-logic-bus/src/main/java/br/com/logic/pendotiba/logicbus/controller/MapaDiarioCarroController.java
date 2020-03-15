package br.com.logic.pendotiba.logicbus.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

import br.com.logic.pendotiba.core.enums.TipoBombaEnum;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioCarroFilter;
import br.com.logic.pendotiba.logicbus.service.CarroService;
import br.com.logic.pendotiba.logicbus.service.ImpressaoService;
import br.com.logic.pendotiba.logicbus.service.MapaDiarioCarroService;
import br.com.logic.pendotiba.logicbus.service.UsuarioService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;
import br.com.logic.pendotiba.logicbus.storage.ArquivoImportadoStorage;
import br.com.logic.pendotiba.logicbus.validation.MapaDiarioCarroValidation;

@Controller
@RequestMapping("/mapa-diario-carro")
public class MapaDiarioCarroController {

	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;

	@Autowired
	MapaDiarioCarroService mapaDiarioCarroService;

	@Autowired
	CarroRepository carroRepository;

	@Autowired
	LinhaRepository linhaRepository;

	@Autowired
	CarroService carroService;

	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;

	@Autowired
	ImpressaoService impressaoService;

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ArquivoImportadoStorage storage;
	
	@Autowired
	MapaDiarioCarroValidation mapaDiarioCarroValidation;
	

	@GetMapping("/inicial")
	public ModelAndView prepararConsultar(MapaDiarioCarroFilter mapaDiarioCarroFilter, BindingResult result,@PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		mapaDiarioCarroFilter = (MapaDiarioCarroFilter) httpServletRequest.getSession().getAttribute("mapaDiarioCarroFilter");
		if (mapaDiarioCarroFilter == null)
			mapaDiarioCarroFilter = new MapaDiarioCarroFilter();
		ModelAndView mv = consultar(mapaDiarioCarroFilter, result, pageable, httpServletRequest);
		mv.addObject("mapaDiarioCarroFilter",
				mapaDiarioCarroFilter != null ? mapaDiarioCarroFilter : new MapaDiarioCarroFilter());
		return mv;
	}

	@GetMapping
	public ModelAndView consultar(MapaDiarioCarroFilter mapaDiarioCarroFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("mapa-diario-carro/ConsultaMapaDiarioCarro");
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("bombas", bombaAbastecimentoRepository.findByOrderByCodigo());

		PageWrapper<MapaDiarioCarro> paginaWrapper = new PageWrapper<>( mapaDiarioCarroService.filtrar(mapaDiarioCarroFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);

		httpServletRequest.getSession().setAttribute("mapaDiarioCarroFilter", mapaDiarioCarroFilter);
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(MapaDiarioCarro mapaDiarioCarro) {
		ModelAndView mv = new ModelAndView("mapa-diario-carro/CadastroMapaDiarioCarro");
		mv.addObject("carros", carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem());
		mv.addObject("linhas", linhaRepository.findByOrderByCodigo());
		mv.addObject("bombasDiesel", bombaAbastecimentoRepository.findByTipoBombaOrderByCodigo(TipoBombaEnum.DIESEL));
		mv.addObject("bombasArla", bombaAbastecimentoRepository.findByTipoBombaOrderByCodigo(TipoBombaEnum.ARLA));
		return mv;
	}

	// @Transactional
	@PostMapping({ "/novo", "{\\+d}" })
	public ModelAndView salvar(@Valid MapaDiarioCarro mapaDiarioCarro, BindingResult result, Model model, RedirectAttributes attributes) {
		mapaDiarioCarroValidation.validarMapa(mapaDiarioCarro, result);
		if (result.hasErrors())
			return prepararCadastrar(mapaDiarioCarro);

		try {
			mapaDiarioCarro.setFuncionarioAbastecimentoOdometro(usuarioService.getUsuarioLogado().getFuncionario());
			mapaDiarioCarro.setFuncionarioRoleta(usuarioService.getUsuarioLogado().getFuncionario());
			mapaDiarioCarro = mapaDiarioCarroService.salvar(mapaDiarioCarro);
			
			MapaDiarioCarro mapaPosterior = mapaDiarioCarroService.verificarAtualizandoProximoMapa(mapaDiarioCarro);
			if(mapaPosterior == null || mapaPosterior.getOdometro() == null) 
				carroService.atualizarPeloMapaDiario(mapaDiarioCarro.getCarro(), mapaDiarioCarro);

		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(mapaDiarioCarro);
		}

		attributes.addFlashAttribute("mensagem", "Carro salvo com sucesso!");
		return new ModelAndView("redirect:/mapa-diario-carro/inicial");
	}

	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") MapaDiarioCarro mapaDiarioCarro) {
		ModelAndView mv = prepararCadastrar(mapaDiarioCarro);
		mv.addObject(mapaDiarioCarro);
		return mv;
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") MapaDiarioCarro mapaDiarioCarro) {
		try {
			mapaDiarioCarroService.excluir(mapaDiarioCarro);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
/*
	@GetMapping(value = "/exportar", produces = MediaType.APPLICATION_PDF_VALUE)
	public ModelAndView exportar(MapaDiarioCarroFilter filtro, HttpServletRequest httpServletRequest, RedirectAttributes attributes) {
		filtro = (MapaDiarioCarroFilter) httpServletRequest.getSession().getAttribute("mapaDiarioCarroFilter");
		try {
			File arquivo = mapaDiarioCarroService.gerarArquivoAbastecimentoTxt(filtro);
			attributes.addFlashAttribute("mensagem", "Arquivo salvo com sucesso!");
			download(arquivo.getName());
		} catch (IOException e) {
			attributes.addFlashAttribute("error", "Erro ao gerar arquivo!");
		}

		return new ModelAndView("redirect:/mapa-diario-carro/inicial");
	}
*/
	@GetMapping(value = "/exportardiesel")
	public HttpEntity<byte[]> downloadDiesel(HttpServletRequest httpServletRequest) throws IOException {
		
		MapaDiarioCarroFilter filtro = (MapaDiarioCarroFilter) httpServletRequest.getSession().getAttribute("mapaDiarioCarroFilter");
		try {
			File arquivo = mapaDiarioCarroService.gerarArquivoAbastecimentoDieselTxt(filtro);
			byte[] array = readFileToByteArray(arquivo);

			HttpHeaders httpHeaders = new HttpHeaders();

			//httpHeaders.add("Content-Disposition", "attachment;filename=\"20190822.txt\"");
			httpHeaders.add("Content-Disposition", "attachment;filename=\"Diesel_" + DataUtil.getDataStringYYYYMMDD(filtro.getDataInicial()) + ".txt");

			HttpEntity<byte[]> entity = new HttpEntity<byte[]>(array, httpHeaders);
			return entity;
		} catch (IOException e) {
		
		}
		return null;
	}
	
	@GetMapping(value = "/exportararla")
	public HttpEntity<byte[]> downloadArla(HttpServletRequest httpServletRequest) throws IOException {
		
		MapaDiarioCarroFilter filtro = (MapaDiarioCarroFilter) httpServletRequest.getSession().getAttribute("mapaDiarioCarroFilter");
		try {
			File arquivo = mapaDiarioCarroService.gerarArquivoAbastecimentoArlaTxt(filtro);
			byte[] array = readFileToByteArray(arquivo);

			HttpHeaders httpHeaders = new HttpHeaders();

			//httpHeaders.add("Content-Disposition", "attachment;filename=\"20190822.txt\"");
			httpHeaders.add("Content-Disposition", "attachment;filename=\"Arla_" + DataUtil.getDataStringYYYYMMDD(filtro.getDataInicial()) + ".txt");

			HttpEntity<byte[]> entity = new HttpEntity<byte[]>(array, httpHeaders);
			return entity;
		} catch (IOException e) {
		
		}
		return null;
	}	
	
	
	/*
	@GetMapping(value = "/download/{nome}")
	public HttpEntity<byte[]> download(@PathVariable("nome") String nome) throws IOException {

		File file = storage.recarregarArquivoAbastecimentoTxt(nome);

		byte[] arquivo = readFileToByteArray(file);

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add("Content-Disposition", "attachment;filename=\"20190822.txt\"");

		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(arquivo, httpHeaders);

		return entity;
	}
	*/
	private static byte[] readFileToByteArray(File file){
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();        
            
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }
	
}
