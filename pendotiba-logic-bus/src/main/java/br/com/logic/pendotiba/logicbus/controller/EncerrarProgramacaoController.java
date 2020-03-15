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
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.logicbus.resources.dto.ProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.service.CarroService;
import br.com.logic.pendotiba.logicbus.service.ProgramacaoService;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;


@RestController
@RequestMapping("/encerrar-programacao")
public class EncerrarProgramacaoController {
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	ProgramacaoService programacaoService;
	
	@Autowired
	CarroService carroService;
	
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararEncerrar(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("programacao/EncerraProgramacao");
		Programacao programacao = programacaoRepository.carregarProgramacao(id);
		mv.addObject(new ProgramacaoDTO(programacao));
		mv.addObject("viagens", programacao.getViagens());
		return mv;
	}
	
	
	@PostMapping
	public ModelAndView encerrar(@Valid ProgramacaoDTO programacaoDTO, BindingResult result, Model model, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("programacao/EncerraProgramacao");
		if (result.hasErrors()) {
			return mv.addObject(programacaoDTO);
		}
		
		try {
			programacaoService.encerrar(programacaoDTO);
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			mv.addObject("viagens", programacaoRepository.carregarProgramacao(programacaoDTO.getId()).getViagens());
			return mv.addObject(programacaoDTO);
		}
		
		attributes.addFlashAttribute("mensagem", "Programação encerrada com sucesso!");
		return new ModelAndView("redirect:/encerrar-programacao/" + programacaoDTO.getId());
	}
	
}
