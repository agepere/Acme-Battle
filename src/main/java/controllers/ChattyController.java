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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChattyService;
import services.OrganizationService;
import domain.Chatty;
import domain.KeybladeWielder;
import domain.Organization;

@Controller
@RequestMapping("/organization/chatty")
public class ChattyController extends AbstractController {

	// SERVICIOS PARA CONTROLADOR ---------------------------------------------
	@Autowired
	private ChattyService		chattyService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private OrganizationService	organizationService;


	// Constructors -----------------------------------------------------------

	public ChattyController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam String organizationId) {
		ModelAndView result;
		int orgId = Integer.parseInt(organizationId);
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		if(this.organizationService.findOrganizationByPlayer(actual.getId()) ==null)
			return new ModelAndView("redirect:/organization/list.do");
		Collection<Chatty> all = this.chattyService.getChattyFromAnOrganization(orgId);

		result = new ModelAndView("organization/chatty/list");
		result.addObject("chattys", all);
		result.addObject("organizationId", orgId);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/edit")
	public ModelAndView create() {
		ModelAndView result;
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();

		if (!this.organizationService.keybladeWielderHasOrganization(actual.getId()))
			return new ModelAndView("organization/list");

		Chatty c = this.chattyService.create();
		result = new ModelAndView("organization/chatty/edit");
		result.addObject("chatty", c);
		result.addObject("organizationId", this.organizationService.findOrganizationByPlayer(actual.getId()).getId());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Chatty chatty, BindingResult binding) {
		ModelAndView result;
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Organization org = this.organizationService.findOrganizationByPlayer(actual.getId());
		if (binding.hasErrors())
			result = this.createEditModelAndView(chatty);
		else
			try {
				chatty = this.chattyService.save(chatty);
				result = new ModelAndView("redirect:/organization/chatty/list.do?organizationId=" + org.getId());
				result.addObject("organizationId", this.organizationService.findOrganizationByPlayer(actual.getId()));

			} catch (Throwable oops) {
				result = this.createEditModelAndView(chatty, "organization.commit.error");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(Chatty chatty) {

		return this.createEditModelAndView(chatty, null);
	}

	private ModelAndView createEditModelAndView(Chatty chatty, String msg) {
		ModelAndView result;
		result = new ModelAndView("organization/chatty/edit");

		result.addObject("chatty", chatty);
		result.addObject("message", msg);
		return result;
	}

}
