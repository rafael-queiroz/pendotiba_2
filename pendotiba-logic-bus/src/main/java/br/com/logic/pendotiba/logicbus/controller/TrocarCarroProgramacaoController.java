package br.com.logic.pendotiba.logicbus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.MotivoTrocaCarroRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.logicbus.resources.dto.TrocaCarroProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.service.CarroProgramacaoService;
import br.com.logic.pendotiba.logicbus.service.EntradaSaidaDeCarroDaGaragemService;
import br.com.logic.pendotiba.logicbus.service.ProgramacaoService;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;


@RestController
@RequestMapping("/trocar-carro-programacao")
public class TrocarCarroProgramacaoController {
	
	@Autowired
	CarroProgramacaoService carroProgramacaoService;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	ProgramacaoService programacaoService;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	MotivoTrocaCarroRepository motivoTrocaCarroRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemService movimentacaoCarroService;
	
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararTrocarCarro(@PathVariable("id") Programacao programacao) {
		ModelAndView mv = new ModelAndView("programacao/TrocaCarroProgramacao");
		TrocaCarroProgramacaoDTO trocaCarroProgramacaoDTO = new TrocaCarroProgramacaoDTO(programacao);
		mv.addObject("trocaCarroProgramacaoDTO", trocaCarroProgramacaoDTO);
		mv.addObject("viagens", programacao.getViagens());
		mv.addObject("carros", carroRepository.findAll());
		mv.addObject("motivos", motivoTrocaCarroRepository.findAll());
		return mv;
	}
	
	
	@PostMapping
	public ModelAndView trocarCarro(@Valid TrocaCarroProgramacaoDTO dto, BindingResult result, Model model, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("programacao/TrocaCarroProgramacao");
		if (result.hasErrors()) 
			return prepararCadastrar(dto);
		
		mv.addObject("viagens", programacaoRepository.carregarProgramacao(dto.getIdProgramacao()).getViagens());
		try {
			programacaoService.realizarTrocaCarroProgramacao(dto);
			carroProgramacaoService.salvarTrocaCarroProgramadoDTO(dto);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(dto);
		}
		
		attributes.addFlashAttribute("mensagem", "Carro trocado com sucesso!");
		return new ModelAndView("redirect:/trocar-carro-programacao/" + dto.getIdProgramacao());
	}
	
	
	@RequestMapping("/novo")
	public ModelAndView prepararCadastrar(TrocaCarroProgramacaoDTO trocaCarroProgramacaoDTO) {
		ModelAndView mv = new ModelAndView("programacao/TrocaCarroProgramacao");
		Programacao programacao = programacaoRepository.carregarProgramacao(trocaCarroProgramacaoDTO.getIdProgramacao());
		mv.addObject("viagens", programacao.getViagens());
		mv.addObject("carros", carroRepository.findAll());
		mv.addObject("motivos", motivoTrocaCarroRepository.findAll());
		return mv;
	}
	
}
