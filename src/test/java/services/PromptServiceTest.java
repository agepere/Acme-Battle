
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.GameMaster;
import domain.KeybladeWielder;
import domain.Report;
import domain.ReportStatus;
import domain.ReportUpdate;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PromptServiceTest extends AbstractTest {

	@Autowired
	private PromptService	promptService;
	@Autowired
	private ActorService	actorService;

	@Test
	public void driver() {
		final Object testingData[][] = {

			{
				"gamemaster1", "help", null
			}, //A gm executes a command
			{
				"player1", "help", IllegalArgumentException.class
			}, //Only gms can execute commands
			{
				"gamemaster1", "set player1 -ww", null
			}, //A gm executes an incorrect command
			{
				"gamemaster1", "set player1 -mn 90", null
			}, //A gm sends a prize to player1
			{
				"gamemaster1", "set player1 -b Livelihood Number 1", IllegalArgumentException.class
			}, //A gm sends a building to player1
			{
				"gamemaster1", "set player1 -b Warehouse Number 1", null
			}, //A gm sends a building to player1
			{
				"gamemaster1", "rm player1 -mn 10", null
			}, //A gm removes materials from player1
			{
				"gamemaster1", "set player1 -rc >TEST", IllegalArgumentException.class
			}, //A gm sends a warehouse that doesn't exist
		};

		for (int i = 0; i < testingData.length; i++) {
			try {
				super.startTransaction();
				this.template((String) testingData[i][0],
						(String) testingData[i][1],
						(Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
		}
	}

	protected void template(String username, String command, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			
			if(command.equals("set player1 -ww")){
				String output;
				
				output = promptService.interpret(command);
				
				Assert.isTrue(output.equals("Command not understood"));
			}if(command.equals("set player1 -mn 90")){
				String output;
				
				output = promptService.interpret(command);
				
				Assert.isTrue(output.equals("Prize sent"));
			}if(command.equals("set player1 -b Livelihood Number 1")){
				String output;
				
				output = promptService.interpret(command);
				
				Assert.isTrue(output.equals("Test"));
			}if(command.equals("set player1 -b Warehouse Number 1")){
				String output;
				
				output = promptService.interpret(command);
				
				Assert.isTrue(output.equals("Building sent"));
			}if(command.equals("rm player1 -mn 10")){
				String output;
				
				output = promptService.interpret(command);
				
				Assert.isTrue(output.equals("Materials removed"));
			}if(command.equals("set player1 -rc >TEST")){
				String output;
				
				output = promptService.interpret(command);
				
				Assert.isTrue(output.equals("Warehouse doesn't exist"));
			}
			else{
				promptService.interpret(command);
			}
			
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

}
