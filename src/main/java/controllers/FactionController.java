/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FactionService;
import domain.Faction;

@Controller
@RequestMapping("/faction/manager")
public class FactionController extends AbstractController {

	@Autowired
	private FactionService	factionService;


	// Actor ---------------------------------------------------------------	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Faction> factions;

		factions = this.factionService.findAll();

		result = new ModelAndView("faction/manager/list");
		result.addObject("factions", factions);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam String factionId) {
		ModelAndView result;
		Faction faction;

		try {
			faction = this.factionService.findOne(Integer.parseInt(factionId));
		} catch (Throwable o) {
			return new ModelAndView("redirect:list.do");
		}

		result = new ModelAndView("faction/manager/display");
		result.addObject("faction", faction);

		return result;
	}

	// Edition  -------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Faction faction;

		faction = this.factionService.create();
		result = this.createEditModelAndViewForm(faction);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam String factionId) {
		ModelAndView result;
		Faction faction;

		try {
			faction = this.factionService.findOne(Integer.parseInt(factionId));
		} catch (Throwable o) {
			return new ModelAndView("redirect:list.do");
		}

		result = this.createEditModelAndViewForm(faction);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Faction faction, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewForm(faction);
		else
			try {
				faction = this.factionService.save(faction);
				result = new ModelAndView("redirect:display.do?factionId=" + faction.getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndViewForm(faction, this.getErrorMessage(oops));
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndViewForm(Faction faction) {
		ModelAndView result;
		result = this.createEditModelAndViewForm(faction, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewForm(Faction faction, String message) {
		ModelAndView result;

		result = new ModelAndView("faction/manager/edit");
		result.addObject("faction", faction);
		result.addObject("message", message);

		return result;
	}

}
