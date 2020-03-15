package br.com.logic.pendotiba.logicbus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.logic.pendotiba.core.model.Garagem;
import br.com.logic.pendotiba.core.repository.GaragemRepository;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Controller
@RequestMapping("/garagem")
public class GaragemController {

	//private static final Logger logger = LoggerFactory.getLogger(VeiculoController.class);
	
	@Autowired
	GaragemRepository garagemRepository;
	
	
	@GetMapping
	public ModelAndView consultar() {
		ModelAndView mv = new ModelAndView("garagem/ConsultaGaragem");
		mv.addObject("garagens", garagemRepository.findAll());
		return mv;
	}
	
	
	@GetMapping("/novo")
	public ModelAndView prepararCadastrar(Garagem garagem) {
		ModelAndView mv = new ModelAndView("garagem/CadastroGaragem");
		return mv;
	}
	
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Garagem garagem, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) 
			return prepararCadastrar(garagem);
		
		try {
			//salvar garagem
		} catch (NegocioException e) {
			result.rejectValue("codigo", e.getMessage(), e.getMessage());
			return prepararCadastrar(garagem);
		}
		
		
		attributes.addFlashAttribute("mensagem", "Garagem salva com sucesso!");
		return new ModelAndView("redirect:/garagem/novo");
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView prepararAlterar(@PathVariable("id") Garagem garagem) {
		ModelAndView mv = prepararCadastrar(garagem);
		mv.addObject(garagem);
		return mv;
	}
	
	
	@DeleteMapping("/{id}")
	public ModelAndView excluir(@PathVariable("id") Garagem garagem, RedirectAttributes attributes) {
		try {
			//excluir garagem
			attributes.addFlashAttribute("mensagem", "Garagem removida com sucesso");
		} catch (ImpossivelExcluirEntidadeException e) {
			attributes.addFlashAttribute("erro", e.getMessage());
		}
		return new ModelAndView("redirect:/garagem");
	}
	
}
