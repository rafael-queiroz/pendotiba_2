package br.com.logic.pendotiba.logicbus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import br.com.logic.pendotiba.core.model.Perfil;
import br.com.logic.pendotiba.core.repository.PerfilRepository;
import br.com.logic.pendotiba.core.repository.PermissaoRepository;
import br.com.logic.pendotiba.logicbus.controller.page.PageWrapper;
import br.com.logic.pendotiba.logicbus.repo.PerfilRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.PerfilService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
	
	@Autowired
	PerfilService perfilService;
	
	@Autowired
	PerfilRepository perfilRepository;
	
	@Autowired
	PerfilRepositoryImpl perfilRepositoryImpl;
	
	@Autowired
	PermissaoRepository permissaoRepository;
	

	
	@GetMapping
	public ModelAndView consultar(Perfil perfil, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/perfil/ConsultaPerfil");
		
		PageWrapper<Perfil> paginaWrapper = new PageWrapper<>(perfilRepositoryImpl.filtrar(perfil, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	

	@RequestMapping("/novo")
	public ModelAndView prepararCadastrar(Perfil perfil) {
		ModelAndView mv = new ModelAndView("perfil/CadastroPerfil");
		
		// CADASTROS
		mv.addObject("permissoesBombaAbastecimento", permissaoRepository.findByModuloAcessoNome("BOMBA ABASTECIMENTO"));
		mv.addObject("permissoesCarro", permissaoRepository.findByModuloAcessoNome("CARRO"));
		mv.addObject("permissoesDispositivoMovel", permissaoRepository.findByModuloAcessoNome("DISPOSITIVO MÓVEL"));
		mv.addObject("permissoesFuncionario", permissaoRepository.findByModuloAcessoNome("FUNCIONÁRIO"));
		mv.addObject("permissoesLinha", permissaoRepository.findByModuloAcessoNome("LINHA"));
		mv.addObject("permissoesMotivosTrocaCarro", permissaoRepository.findByModuloAcessoNome("MOTIVO TROCA CARRO"));
		mv.addObject("permissoesMotivosPuloViagem", permissaoRepository.findByModuloAcessoNome("MOTIVO PULO VIAGEM"));
		mv.addObject("permissoesParametrosConsumo", permissaoRepository.findByModuloAcessoNome("PARÂMETROS DE CONSUMO"));
		mv.addObject("permissoesPonto", permissaoRepository.findByModuloAcessoNome("PONTO"));
		mv.addObject("permissoesPontoLinha", permissaoRepository.findByModuloAcessoNome("PONTO LINHA"));
		mv.addObject("permissoesTipoChassi", permissaoRepository.findByModuloAcessoNome("TIPO DE CHASSI"));
		mv.addObject("permissoesTipoObservacao", permissaoRepository.findByModuloAcessoNome("TIPO DE OBSERVAÇÃO"));
		mv.addObject("permissoesTipoReclamacao", permissaoRepository.findByModuloAcessoNome("TIPO DE RECLAMAÇÃO"));
		mv.addObject("permissoesTipoViagemPerdida", permissaoRepository.findByModuloAcessoNome("TIPO DE VIAGEM PERDIDA"));
		
		// MAPAS
		mv.addObject("permissoesMapaDiarioCarro", permissaoRepository.findByModuloAcessoNome("MAPA DIARIO CARRO")); //TROCOU
		mv.addObject("permissoesMapaDiarioBomba", permissaoRepository.findByModuloAcessoNome("MAPA DIARIO BOMBA")); //TROCOU

		// IMPORTAÇÃO DE ARQUIVOS
		mv.addObject("permissoesImportarArquivoProgramacao", permissaoRepository.findByModuloAcessoNome("IMPORTAR ARQUIVO PROGRAMACAO"));//TROCOU
		mv.addObject("permissoesImportarArquivoEscala", permissaoRepository.findByModuloAcessoNome("IMPORTAR ARQUIVO ESCALA"));//TROCOU
		
		// OPERAÇÃO
		mv.addObject("permissoesProgramacao", permissaoRepository.findByModuloAcessoNome("PROGRAMAÇÃO"));
		mv.addObject("permissoesViagem", permissaoRepository.findByModuloAcessoNome("VIAGEM"));
		mv.addObject("permissoesEntradaSaidaDeCarrosDaGaragem", permissaoRepository.findByModuloAcessoNome("ENTRADA E SAIDA DE CARROS DA GARAGEM"));
		
		// RELATÓRIOS
		mv.addObject("permissoesRelatorios", permissaoRepository.findByModuloAcessoNome("RELATORIOS"));
		//mv.addObject("permissoesReclamacao", permissaoRepository.findByModuloAcessoNome("RECLAMAÇÃO"));
		//mv.addObject("permissoesObservacao", permissaoRepository.findByModuloAcessoNome("OBSERVAÇÃO"));
		
		// APP
		mv.addObject("permissoesAppMapaDiarioCarro", permissaoRepository.findByModuloAcessoNome("APP MAPA DIARIO CARRO"));
		mv.addObject("permissoesAppMapaDiarioBombaAbastecimento", permissaoRepository.findByModuloAcessoNome("APP MAPA DIARIO BOMBA ABASTECIMENTO"));
		
		// SEGURANÇA
		mv.addObject("permissoesPerfil", permissaoRepository.findByModuloAcessoNome("PERFIL"));
		mv.addObject("permissoesPermissao", permissaoRepository.findByModuloAcessoNome("PERMISSÃO"));
		mv.addObject("permissoesUsuario", permissaoRepository.findByModuloAcessoNome("USUÁRIO"));
		
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Perfil perfil, BindingResult result, Model model,  RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return prepararCadastrar(perfil);
		}
		
		try {
			perfilService.salvar(perfil);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(perfil);
		}
		
		attributes.addFlashAttribute("mensagem", "Perfil salvo com sucesso.");
		attributes.addFlashAttribute("alerta", "É necessário reiniciar a aplicação para as alterações entrarem em vigor.");
		return new ModelAndView("redirect:/perfil/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararEditar(@PathVariable Long id) {
		Perfil perfil = perfilRepository.carregarPerfil(id);
		ModelAndView mv = prepararCadastrar(perfil);
		mv.addObject(perfil);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Perfil perfil) {
		try {
			perfilService.excluir(perfil);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
