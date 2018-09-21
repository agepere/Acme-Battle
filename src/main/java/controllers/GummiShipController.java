/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BuildingService;
import services.GummiShipService;
import services.RecruiterService;
import domain.GummiShip;
import domain.Recruiter;

@Controller
@RequestMapping("/gummiShip/contentManager")
public class GummiShipController extends AbstractController {

	// SERVICES -----------------------------------------------
	
	@Autowired
	private GummiShipService gummiShipService;
	
	@Autowired
	private RecruiterService recruiterService;
	
	@Autowired
	private BuildingService buildingService;
	
	
	// Constructors -----------------------------------------------------------

	public GummiShipController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView action1() {
		ModelAndView result;
		Collection<GummiShip> all = this.gummiShipService.findAll();
		
		result = new ModelAndView("gummiShip/contentManager/list");
		result.addObject("gummis",all);
		return result;
	}

	// Action-2 ---------------------------------------------------------------		
	@RequestMapping("/edit")
	public ModelAndView create(String recruiterId, @RequestParam (required=false) String gummiShipId) {
		ModelAndView result;
		GummiShip g;
		int rId = Integer.parseInt(recruiterId);
		
		if(gummiShipId == null){
			Recruiter r = (Recruiter) this.buildingService.findOne(rId);
			g = this.gummiShipService.create(r);
			
		}else{
			int tId = Integer.parseInt(gummiShipId);
			g = this.gummiShipService.findOne(tId);
			
		}
		result = new ModelAndView("gummiShip/contentManager/edit");
		result.addObject("gummiShip", g);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(GummiShip g, BindingResult binding) {
		ModelAndView result;
		GummiShip gum = this.gummiShipService.reconstruct(g, binding);
		
		if(binding.hasErrors()){
			result = createEditModelAndView(g);
		}else{
			try{
				
				
				this.recruiterService.addGummiShip(gum.getRecruiter(), gum);
				
				result = new ModelAndView("redirect:/gummiShip/contentManager/list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(g, "gummiShip.commit.error");
			}
		}
		return result;
	}
	
	private ModelAndView createEditModelAndView(GummiShip gummi) {
		
		return createEditModelAndView(gummi, null);
	}

	private ModelAndView createEditModelAndView(GummiShip gummi, String msg) {
		ModelAndView result;
		result = new ModelAndView("gummiShip/contentManager/edit");

		result.addObject("gummiShip", gummi);
		result.addObject("message", msg);
		return result;
	}
	
	
	

	
	
}
