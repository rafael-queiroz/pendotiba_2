package br.com.logic.pendotiba.logicbus.controller.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public String exception(Exception exception, Model model){
           model.addAttribute("exception", exception);
           return "error";
    }
}
