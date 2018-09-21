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
import services.OrganizationService;
import domain.Invitation;
import domain.InvitationStatus;
import domain.KeybladeWielder;
import domain.OrgRange;
import domain.Organization;

@Controller
@RequestMapping("/organization/invitation")
public class InvitationController extends AbstractController {

	// SERVICES -----------------------------------------------
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private InvitationService invitationService;
	
	
	// Constructors -----------------------------------------------------------

	public InvitationController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView action1() {
		ModelAndView result;
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Collection<Invitation> all = this.invitationService.findInvitationsByKeybladeWielderId(actual.getId());
		Boolean hasOrganization =this.organizationService.keybladeWielderHasOrganization(actual.getId());
		
		result = new ModelAndView("organization/invitation/list");
		result.addObject("invitations",all);
		result.addObject("hasOrganization", hasOrganization);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		
	@RequestMapping("/edit")
	public ModelAndView action2(@RequestParam String username) {
		ModelAndView result;
		KeybladeWielder invited = (KeybladeWielder) this.actorService.findByUserAccountUsername(username);
		Boolean hasOrganization = this.organizationService.keybladeWielderHasOrganization(invited.getId());
		
		if(hasOrganization){
			return new ModelAndView("redirect:profile/actor/display.do?username="+username);
		}
		
		Invitation i = this.invitationService.create(username);
		
		result = new ModelAndView("organization/invitation/edit");
		result.addObject("invitation", i);
		result.addObject("invitedId", invited.getId());
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Invitation invitation, BindingResult binding) {
		ModelAndView result;
		invitation = this.invitationService.reconstruct(invitation, binding);
		
		if(binding.hasErrors()){
			result = createEditModelAndView(invitation);
		}else{
			try{
				Invitation toSave = invitation;
				
				toSave = this.invitationService.save(invitation);
				
				result = new ModelAndView("redirect:/organization/invitation/list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(invitation, "invitation.commit.error");
			}
		}
		return result;
	}
	
	private ModelAndView createEditModelAndView(Invitation invitation) {
		
		return createEditModelAndView(invitation, null);
	}

	private ModelAndView createEditModelAndView(Invitation invitation, String msg) {
		ModelAndView result;
		result = new ModelAndView("organization/invitation/edit");

		result.addObject("invitation", invitation);
		result.addObject("message", msg);
		return result;
	}
	
	
	

	//  ------- ACCEPT INVITATION  -------
	@RequestMapping("/accept")
	public ModelAndView accept(@RequestParam String invitationId) {
		Integer id = Integer.parseInt(invitationId);
		Invitation inv = this.invitationService.findOne(id);
		
		// Comprobar que el actual de verdad tiene Organización y es la del parámetro. 
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		if(this.organizationService.keybladeWielderHasOrganization(actual.getId()) || !inv.getInvitationStatus().equals(InvitationStatus.PENDING)){
			return new ModelAndView("redirect:/organization/invitation/list.do");
		}
		if(inv.getKeybladeWielder().getUserAccount().equals(actual.getUserAccount())){
			this.invitationService.AcceptInvitation(id);
			return new ModelAndView("redirect:/organization/membersList.do?organizationId="+inv.getOrganization().getId());
		}else{
			return new ModelAndView("redirect:/organization/invitation/list.do");
		}
	}
	
	
//  ------- DECLINE INVITATION  -------
	@RequestMapping("/decline")
	public ModelAndView decline(@RequestParam String invitationId) {
		Integer id = Integer.parseInt(invitationId);
		Invitation inv = this.invitationService.findOne(id);
		
		// Comprobar que el actual de verdad tiene Organización y es la del parámetro. 
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		
		if(this.organizationService.keybladeWielderHasOrganization(actual.getId()) ||!inv.getInvitationStatus().equals(InvitationStatus.PENDING)){
			
			return new ModelAndView("redirect:/organization/invitation/list.do");
		}else{
			
			if(inv.getKeybladeWielder().getUserAccount().equals(actual.getUserAccount())){
				this.invitationService.declineInvitation(id);
		}
			return new ModelAndView("redirect:/organization/invitation/list.do");
		}
	}
	
	@RequestMapping("/changeRange")
	public ModelAndView changeRange(@RequestParam String invitationId) {
		Integer id = Integer.parseInt(invitationId);
		Invitation inv = this.invitationService.findOne(id);
		
		//Comprobar que el actual de verdad tiene Organización y es la del parámetro y yo soy MASTER
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Boolean soyMaster = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), inv.getOrganization().getId()).getOrgRange().equals(OrgRange.MASTER);
		Boolean mismaOrganizacion = this.organizationService.findOne(inv.getOrganization().getId()).equals(this.organizationService.findOrganizationByPlayer(actual.getId()));
		
		if(soyMaster && mismaOrganizacion && inv.getInvitationStatus().equals(InvitationStatus.ACCEPTED)){
			this.invitationService.changeRange(inv.getId());
			return new ModelAndView("redirect:/organization/membersList.do?organizationId="+inv.getOrganization().getId());
		}else{
			return new ModelAndView("redirect:/organization/membersList.do?organizationId="+this.organizationService.findOrganizationByPlayer(actual.getId()));
			
		}
	}
	
	@RequestMapping("/interchangeRange")
	public ModelAndView interchangeRange(@RequestParam String invitationId) {
		Integer id = Integer.parseInt(invitationId);
		Invitation inv = this.invitationService.findOne(id);
		
		//Comprobar que el actual de verdad tiene Organización y es la del parámetro y yo soy MASTER
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Boolean soyMaster = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), inv.getOrganization().getId()).getOrgRange().equals(OrgRange.MASTER);
		Boolean mismaOrganizacion = this.organizationService.findOne(inv.getOrganization().getId()).equals(this.organizationService.findOrganizationByPlayer(actual.getId()));
		
		if(soyMaster && mismaOrganizacion && inv.getInvitationStatus().equals(InvitationStatus.ACCEPTED)){
			
			this.invitationService.interchangeRange(this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), inv.getOrganization().getId()), inv);
			return new ModelAndView("redirect:/organization/membersList.do?organizationId="+inv.getOrganization().getId());
		}else{
			return new ModelAndView("redirect:/organization/membersList.do?organizationId="+this.organizationService.findOrganizationByPlayer(actual.getId()));
			
		}
	}
	
	
	@RequestMapping("/orgList")
	public ModelAndView orgList(@RequestParam String organizationId) {
		ModelAndView result;
		Integer id = Integer.decode(organizationId);
		Organization org = this.organizationService.findOne(id);
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		try{
			if(!this.organizationService.findOrganizationByPlayer(actual.getId()).equals(org)){
				return new ModelAndView("redirect:organization/invitation/list.do");
			}
		}catch(Throwable oops){}
		
		Collection<Invitation> all = this.invitationService.findInvitationsSentByOrganization(id);
		
		result = new ModelAndView("organization/invitation/orgList");
		result.addObject("invitations", all);

		return result;
	}
}
