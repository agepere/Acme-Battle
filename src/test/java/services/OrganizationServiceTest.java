package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Configuration;
import domain.Livelihood;
import domain.Materials;
import domain.Organization;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OrganizationServiceTest extends AbstractTest {

	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private KeybladeWielderService keybladeWielderService;
	
	
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"player2", "OrganizationName", "OrganizationDescription", null, null, "crear"
			},	//Crear una organización correctamente y salir de ella siendo MÁSTER
			{
				"player2", null, "OrganizationDescription", null,NullPointerException.class, "crear"
			},	//Crear una organización sin nombre
			{
				"player2", "OrganizationName", null, null, NullPointerException.class, "crear"
			},	//Crear una organización sin descripción
			{
				"player1", "OrganizationName", "OrganizationDescription", null, IllegalArgumentException.class, "crear"
			},	//Intentar crear una organización teniendo ya una 
			{
				null, "OrganizationName", null, null, NullPointerException.class, "crear-NonAuth"
			},	//Intentar crear una organización sin autenticarse
			{
				"player3", null, null, "organization1", null, "salir"
			},	//Salir de una organización siendo OFFICER
			{
				"player3", null, null, "organization1", null, "salir"
			},   //Salir de una organización siendo GUEST
			{
				"player1", null, null, "organization1", null, "salir"
			}	//Salir de una organización siendo MASTER
			

		};
		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			super.flushTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],(String) testingData[i][3], (Class<?>) testingData[i][4], (String) testingData[i][5]);
		}
	}
	// In this test we edit the Configuration
	
	protected void template(String username, String name, String description, String organization, final Class<?> expected, String operation) {
		Class<?> caught;
		Organization o;

		caught = null;
		try {
			if(operation.equals("crear")){
				super.authenticate(username);
				
				o = this.organizationService.create();
				o.setName(name);
				o.setDescription(description);
				
				this.organizationService.save(o);
				super.unauthenticate();
			}
			if(operation.equals("crear-NonAuth")){
				o = this.organizationService.create();
				o.setName(name);
				o.setDescription(description);
				
				this.organizationService.save(o);
			}
			if(operation.equals("salir")){
				super.authenticate(username);
				Organization o1 = this.organizationService.findOne(this.getEntityId(organization));
				
				this.organizationService.leaveOrganization(o1.getId());
				
				super.unauthenticate();
			}
				
				
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	
	}
	

}
