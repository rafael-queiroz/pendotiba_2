package br.com.logic.pendotiba.logicbus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.logic.pendotiba.core.model.DispositivoMovel;
import br.com.logic.pendotiba.core.repository.DispositivoMovelRepository;
import br.com.logic.pendotiba.core.repository.HistoricoDispositivoMovelRepository;
import br.com.logic.pendotiba.core.repository.PontoRepository;
import br.com.logic.pendotiba.logicbus.service.DispositivoMovelService;
import br.com.logic.pendotiba.logicbus.service.UsuarioService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/dispositivo-movel")
public class DispositivoMovelController {

	@Autowired
	DispositivoMovelService dispositivoMovelService;
	
	@Autowired
	UsuarioService usuarioService;

	@Autowired
	DispositivoMovelRepository dispositivoMovelRepository;
	
	@Autowired
	PontoRepository pontoRepository;
	
	@Autowired
	HistoricoDispositivoMovelRepository historicoDispositivoMovelRepository;
	
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("dispositivo-movel/ConsultaDispositivoMovel");
		mv.addObject("dispositivos", dispositivoMovelRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(DispositivoMovel dispositivoMovel) {
		ModelAndView mv = new ModelAndView("dispositivo-movel/CadastroDispositivoMovel");
		mv.addObject("pontos", pontoRepository.findAll());
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid DispositivoMovel dispositivoMovel, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(dispositivoMovel);
		
		try {
			dispositivoMovelService.salvar(dispositivoMovel, usuarioService.getUsuarioLogado());
		} catch (NegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return prepararCadastrar(dispositivoMovel);
		}
		
		attributes.addFlashAttribute("mensagem", "Dispositivo móvel salva com sucesso!");
		return new ModelAndView("redirect:/dispositivo-movel/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") DispositivoMovel dispositivoMovel) {
		ModelAndView mv = prepararCadastrar(dispositivoMovel);
		mv.addObject(dispositivoMovel);
		mv.addObject("historico", historicoDispositivoMovelRepository.findByDispositivoMovel(dispositivoMovel));
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") DispositivoMovel dispositivoMovel) {
		try {
			dispositivoMovelService.excluir(dispositivoMovel);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}
