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

import services.ActorService;
import services.InvitationService;
import services.KeybladeWielderService;
import services.OrganizationService;
import domain.Invitation;
import domain.KeybladeWielder;
import domain.OrgRange;
import domain.Organization;

@Controller
@RequestMapping("/organization")
public class OrganizationController extends AbstractController {

	// SERVICIOS PARA CONTROLADOR ---------------------------------------------
	@Autowired
	private KeybladeWielderService keybladeWielderService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public OrganizationController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Organization> all = this.organizationService.findAll();
		Boolean hasOrganization = false;
		result = new ModelAndView("organization/list");
		result.addObject("organizations", all);
		result.addObject("requestURI", "organization/list.do");
		
		if(this.actorService.findByPrincipal() instanceof KeybladeWielder){
			KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
			hasOrganization = this.organizationService.keybladeWielderHasOrganization(actual.getId());
		
			result.addObject("hasOrganization", hasOrganization);
			if(hasOrganization){
				result.addObject("organizationId", this.organizationService.findOrganizationByPlayer(actual.getId()).getId());
			}	
		}else{
			result.addObject("hasOrganization", hasOrganization);
		}
		return result;
	}
	
	

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/membersList")
	public ModelAndView membersList(@RequestParam String organizationId) {
		ModelAndView result;
		Boolean canChat = false;
		Boolean iAmMaster = false;
		int orgId = Integer.parseInt(organizationId);
		Invitation actual = null;
		Collection<Invitation> membersInvitations = this.invitationService.findAllMembersOfOrganization(orgId);
	
		if(this.actorService.findByPrincipal() instanceof KeybladeWielder){
			KeybladeWielder kw = (KeybladeWielder) this.actorService.findByPrincipal();
			actual = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(kw.getId(), orgId);
		}
		if(actual != null){
			canChat = membersInvitations.contains(actual);
			iAmMaster = actual.getOrgRange().equals(OrgRange.MASTER);
		}
		result = new ModelAndView("organization/membersList");
		result.addObject("requestURI", "organization/membersList.do");
		result.addObject("membersInvitations", membersInvitations);
		result.addObject("canChat", canChat);
		result.addObject("iAmMaster", iAmMaster);
		result.addObject("algo", orgId);
		
		return result;
	}
	
	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/edit")
	public ModelAndView create(@RequestParam(required = false) String organizationId) {
		ModelAndView result;
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		
		if(this.organizationService.keybladeWielderHasOrganization(actual.getId()) && organizationId==null)
			return new ModelAndView("redirect:/organization/list.do");
		
		if(organizationId!= null){
			Invitation invActual = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), Integer.parseInt(organizationId));
			if(invActual == null || !invActual.getOrgRange().equals(OrgRange.MASTER)){ //Si no es el master de la organización, lo echamos hacia fuera
				return new ModelAndView("redirect:/organization/list.do");
			}
			Organization o = this.organizationService.findOne(Integer.parseInt(organizationId));
			result = new ModelAndView("organization/edit");
			result.addObject("organization", o);
		}else{

			Organization o = this.organizationService.create();
			result = new ModelAndView("organization/edit");
			result.addObject("organization", o);
		}
		return result;
	}
		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Organization organization, BindingResult binding) {
		ModelAndView result;
		Organization org = this.organizationService.reconstruct(organization, binding);
		
		if(binding.hasErrors()){
			result = createEditModelAndView(organization);
		}else{
			try{
				Organization toSave = org;
				
				toSave = this.organizationService.save(org);
				
				result = new ModelAndView("redirect:/organization/membersList.do?organizationId="+toSave.getId());
			}catch(Throwable oops){
				result = createEditModelAndView(organization, "organization.commit.error");
			}
		}
		return result;
	}
	
	private ModelAndView createEditModelAndView(Organization organization) {
		
		return createEditModelAndView(organization, null);
	}

	private ModelAndView createEditModelAndView(Organization organization, String msg) {
		ModelAndView result;
		result = new ModelAndView("organization/edit");

		result.addObject("organization", organization);
		result.addObject("message", msg);
		return result;
	}

	
	
	// ------ LEAVE THE ORGANIZATION ------
	
	@RequestMapping("/leaveOrganization")
	public ModelAndView leave() {
		ModelAndView result;
		
		//TODO Comprobar que el actual de verdad tiene Organización y es la del parámetro. 
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		if(this.organizationService.keybladeWielderHasOrganization(actual.getId())){
			int orgId = this.organizationService.findOrganizationByPlayer(actual.getId()).getId();
			this.organizationService.leaveOrganization(orgId);
		}
		Collection<Organization> all = this.organizationService.findAll();
		
		result = new ModelAndView("redirect:/organization/list.do");
		result.addObject("organizations", all);
		result.addObject("requestURI", "organization/list.do");
		result.addObject("hasOrganization", false);
		return result;
	}
		
	
	// ------ DELETE ------
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam String organizationId) {
		ModelAndView result;
		Collection<Organization> all = this.organizationService.findAll();
		
		Integer id = Integer.parseInt(organizationId);
		
		this.organizationService.delete(this.organizationService.findOne(id));
		
		result = new ModelAndView("redirect:/organization/list.do");
		result.addObject("organizations", all);
		result.addObject("requestURI", "organization/list.do");
		result.addObject("hasOrganization", false);
		return result;
	}
}
