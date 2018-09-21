/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.PromptService;

@Controller
@RequestMapping("/gm/prompt")
public class PromptController extends AbstractController {

	@Autowired
	private PromptService promptService;
	
	// Constructors -----------------------------------------------------------

	public PromptController() {
		super();
	}

	// Index ------------------------------------------------------------------	
	
	@RequestMapping(value = "/show")
	public ModelAndView showPrompt() {
		ModelAndView result;
		
		result = new ModelAndView("prompt");

		return result;
	}

	@RequestMapping(value = "/interpret", produces="text/plain")
	@ResponseBody
	public String interpret(@RequestParam("command") String command) {
		String res;
		
		try{
			res = promptService.interpret(command);
		}catch(Throwable e){
			res = "Error while executing command";
		}

		return res;
	}
}
