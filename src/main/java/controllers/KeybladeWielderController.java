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

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;
import domain.KeybladeWielder;
import form.ActorForm;

@Controller
@RequestMapping("/keybladewielder")
public class KeybladeWielderController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	// Actor ---------------------------------------------------------------	

	@RequestMapping(value = "/world", method = RequestMethod.GET)
	public ModelAndView world(@RequestParam(required = false) String username, Locale locale) {
		ModelAndView result;
		Actor actor;

		if (username == null)
			actor = this.actorService.findByPrincipal();
		else
			try {
				actor = this.actorService.findByUserAccountUsername(username);
				Assert.notNull(actor, "error.message.notexist");
			} catch (Throwable oops) { //Si mete un username invalido (nulo o no dentro de los limites [3, 32]), mostrar error o alternativa
				result = new ModelAndView("redirect:/profile/actor/list.do");
				result.addObject("message", this.showDetails(locale, this.getErrorMessage(oops)));
				return result;
			}

		result = new ModelAndView("keybladewielder/world");
		if (actor instanceof KeybladeWielder) {
			KeybladeWielder user = (KeybladeWielder) actor;

			result.addObject("user", user);
			result.addObject("usernameInvitation", user.getUserAccount().getUsername());
		} else
			result.addObject("user", actor);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Actor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Actor actor, String message) {
		ModelAndView result;

		result = new ModelAndView("profile/actor/register");
		result.addObject("actor", actor);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndViewEdit(ActorForm actor, String message) {
		ModelAndView result;

		result = new ModelAndView("profile/actor/edit");
		result.addObject("actorForm", actor);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndViewForm(ActorForm formServicio) {
		ModelAndView result;
		result = this.createEditModelAndViewForm(formServicio, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewForm(ActorForm form, String message) {
		ModelAndView result;

		result = new ModelAndView("profile/actor/edit");
		result.addObject("actorForm", form);
		result.addObject("message", message);

		return result;
	}

	//Update materials panels

	@RequestMapping(value = "/updateMaterialsPanel", method = RequestMethod.GET)
	public ModelAndView updateMaterialsPanel() {
		ModelAndView res;
		KeybladeWielder player;

		player = (KeybladeWielder) this.actorService.findByPrincipal();

		res = new ModelAndView("keybladewielder/materials-panel");
		res.addObject("playerFromAbstract", player);

		return res;
	}
}
