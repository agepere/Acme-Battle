package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Configuration;
import domain.Invitation;
import domain.InvitationStatus;
import domain.Livelihood;
import domain.Materials;
import domain.OrgRange;
import domain.Organization;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InvitationServiceTest extends AbstractTest {

	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private KeybladeWielderService keybladeWielderService;
	
	@Autowired
	private ActorService actorService;
	
	
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"player1", "player6", "Description", "guest", null, "enviar y aceptar"
			},	//Enviar una invitación siendo MASTER y aceptarla
			{
				"player3", "player6", "Description", "officer", null, "enviar y rechazar"
			},	//Enviar una invitación siendo OFFICER y rechazarla
			{
				"player4", "player6", "Description", "guest", IllegalArgumentException.class, "enviar y aceptar"
			},	//Enviar una invitación sin ser MASTER u OFFICER
			{
				"player1", "player3", "Description", "guest", IllegalArgumentException.class, "enviar y aceptar"
			},	//Enviar una invitación a alguien con organización
			{
				"player1", "player6", "Description", "master", IllegalArgumentException.class, "enviar y aceptar no-auth"
			},	//Enviar con orgRange MASTER
			{
				"player3", "player6", null, "guest", IllegalArgumentException.class, "enviar y aceptar"
			},	//Enviar sin descripción
			{
				"player3", null, "Description", "guest", AssertionError.class, "enviar y rechazar"
			},   //Enviar a un player nulo
			

		};
		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			super.flushTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4], (String) testingData[i][5]);
		}
	}
	// In this test we edit the Configuration
	
	protected void template(String username1, String username2, String description, String orgRange, final Class<?> expected, String operation) {
		Class<?> caught;

		caught = null;
		try {
			if(operation.equals("enviar y aceptar")){
				super.authenticate(username1);
				Organization o = this.organizationService.findOrganizationByPlayer(this.actorService.findByPrincipal().getId());
				
				Invitation i = this.invitationService.create(this.keybladeWielderService.findOne(this.getEntityId(username2)).getUserAccount().getUsername());
				i.setOrganization(o);
				i.setDate(new Date(System.currentTimeMillis()-10000));
				i.setContent(description);
				i.setInvitationStatus(InvitationStatus.PENDING);
				
				
				if(orgRange.equals("guest"))
					i.setOrgRange(OrgRange.GUEST);
				else if(orgRange.equals("officer"))
					i.setOrgRange(OrgRange.OFFICER);
				else if(orgRange.equals("master"))
					i.setOrgRange(OrgRange.MASTER);
				
				i = this.invitationService.save(i);
				super.unauthenticate();
				
				
				super.authenticate(username2);
				this.invitationService.AcceptInvitation(i.getId());
				super.unauthenticate();
			}
			if(operation.equals("enviar y rechazar")){
				super.authenticate(username1);
				Organization o = this.organizationService.findOrganizationByPlayer(this.actorService.findByPrincipal().getId());
				
				Invitation i = this.invitationService.create(this.keybladeWielderService.findOne(this.getEntityId(username2)).getUserAccount().getUsername());
				i.setOrganization(o);
				i.setDate(new Date(System.currentTimeMillis()-10000));
				i.setContent(description);
				i.setInvitationStatus(InvitationStatus.PENDING);
	
				
				if(orgRange.equals("guest"))
					i.setOrgRange(OrgRange.GUEST);
				else if(orgRange.equals("officer"))
					i.setOrgRange(OrgRange.OFFICER);
				else if(orgRange.equals("master"))
					i.setOrgRange(OrgRange.MASTER);
				
				i = this.invitationService.save(i);
				super.unauthenticate();
				
				
				super.authenticate(username2);
				this.invitationService.declineInvitation(i.getId());
				super.unauthenticate();
			}
			if(operation.equals("enviar y aceptar no-auth")){
				
				Organization o = this.organizationService.findOrganizationByPlayer(this.actorService.findByPrincipal().getId());
				
				Invitation i = this.invitationService.create(this.keybladeWielderService.findOne(this.getEntityId(username2)).getUserAccount().getUsername());
				i.setOrganization(o);
				i.setDate(new Date(System.currentTimeMillis()-10000));
				i.setContent(description);
				i.setInvitationStatus(InvitationStatus.PENDING);
		
				
				if(orgRange.equals("guest"))
					i.setOrgRange(OrgRange.GUEST);
				else if(orgRange.equals("officer"))
					i.setOrgRange(OrgRange.OFFICER);
				else if(orgRange.equals("master"))
					i.setOrgRange(OrgRange.MASTER);
				
				i = this.invitationService.save(i);
				
				
				
				super.authenticate(username2);
				this.invitationService.AcceptInvitation(i.getId());
				super.unauthenticate();super.authenticate(username1);
				Organization o1 = this.organizationService.findOrganizationByPlayer(this.actorService.findByPrincipal().getId());
				
				Invitation i1 = this.invitationService.create(this.keybladeWielderService.findOne(this.getEntityId(username2)).getUserAccount().getUsername());
				i1.setOrganization(o1);
				i1.setDate(new Date(System.currentTimeMillis()-10000));
				i1.setContent(description);
				i1.setInvitationStatus(InvitationStatus.PENDING);
				if(orgRange.equals("guest"))
					i1.setOrgRange(OrgRange.GUEST);
				else if(orgRange.equals("officer"))
					i1.setOrgRange(OrgRange.OFFICER);
				else if(orgRange.equals("master"))
					i1.setOrgRange(OrgRange.MASTER);
				
				this.organizationService.save(o);
				super.unauthenticate();
				
				
				super.authenticate(username2);
				this.invitationService.AcceptInvitation(i.getId());
				super.unauthenticate();
			}
				
				
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	
	}
	

}
