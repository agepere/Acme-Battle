package services;

import java.util.Date;

import javax.transaction.Transactional;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chatty;

import domain.Invitation;


import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChattyServiceTest extends AbstractTest {


	
	@Autowired
	private InvitationService invitationService;
	

	@Autowired
	private ChattyService chattyService;
	
	
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"player1", "Description", "invitation1",null, "chatear"
			},	//Chatear correctamente
			{
				"player3", null, "invitation2", NullPointerException.class, "chatear"
			},	//Chatear sin contenido
			{
				"player4", "Description", "invitation100", AssertionError.class, "chatear"
			}	//Chatear sin invitación
			

		};
		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			super.flushTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3], (String) testingData[i][4]);
		}
	}
	// In this test we edit the Configuration
	
	protected void template(String username1, String description, String invitation, final Class<?> expected, String operation) {
		Class<?> caught;

		caught = null;
		try {
			if(operation.equals("chatear")){
				super.authenticate(username1);
				Invitation i =  this.invitationService.findOne(this.getEntityId(invitation));
				
				Chatty c = this.chattyService.create();
				c.setContent(description);
				c.setDate(new Date(System.currentTimeMillis()-10000));
				c.setInvitation(i);
				
				c = this.chattyService.save(c);
			}
			
				
				
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	
	}
	

}
