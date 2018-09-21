
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.KeybladeWielder;
import domain.Report;
import domain.ReportStatus;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportServiceTest extends AbstractTest {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ActorService	actorService;

	@Test
	public void driver() {
		final Object testingData[][] = {

			{
				"player1", "player1", null, "create", null
			}, //A player creates a report
			{
				"player1", null, null, "create", AssertionError.class
			}, //Report can't be created without player
			{
				"player1", "player1", null, "createNoStatus", ConstraintViolationException.class
			}, // Report can't be created without status
			{
				"player2", "player1", null, "create", IllegalArgumentException.class
			}, //The creator of a report must be the principal
			{
				"player1", "player1", null, "createPastDate", ConstraintViolationException.class
			}, //Date must be in the past
			{
				"gamemaster1", null, "report1", "edit", null
			}, //A gamemaster edits a report
			{
				"gamemaster1", null, "report1", "editONHOLD", IllegalArgumentException.class
			}, //When editing a report, status cannot be set to "ONHOLD"
			{
				"player2", null, "report1", "edit", IllegalArgumentException.class
			} //A player can't edit a report that doesn't belong to him
		};

		for (int i = 0; i < testingData.length; i++) {
			try {
				super.startTransaction();
				this.template((String) testingData[i][0],
						(String) testingData[i][1], (String) testingData[i][2],
						(String) testingData[i][3],
						(Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
		}
	}

	protected void template(String username, String keybladeWielder, String reportId, String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			
			if(operation.equals("create")){
				Report report;
				KeybladeWielder player;
				
				report = reportService.create();
				player = (KeybladeWielder) actorService.findOne(this.getEntityId(keybladeWielder));
				report.setDate(new Date(System.currentTimeMillis()-1000));
				report.setStatus(ReportStatus.ONHOLD);
				report.setKeybladeWielder(player);
				report.setContent("test");
				report.setTitle("test");
				
				reportService.save(report);
			}else if(operation.equals("createNoStatus")){
				Report report;
				KeybladeWielder player;
				
				report = reportService.create();
				player = (KeybladeWielder) actorService.findOne(this.getEntityId(keybladeWielder));
				report.setKeybladeWielder(player);
				report.setStatus(ReportStatus.ONHOLD);
				report.setDate(null);
				report.setContent("test");
				report.setTitle("test");
				
				reportService.save(report);
			}else if(operation.equals("createPastDate")){
				Report report;
				KeybladeWielder player;
				
				report = reportService.create();
				player = (KeybladeWielder) actorService.findOne(this.getEntityId(keybladeWielder));
				report.setKeybladeWielder(player);
				report.setStatus(ReportStatus.ONHOLD);
				report.setDate(new Date(System.currentTimeMillis()+2000));
				report.setContent("test");
				report.setTitle("test");
				
				reportService.save(report);
			}else if(operation.equals("edit")){
				Report report;
				
				report = reportService.findOne(this.getEntityId(reportId));
				report.setStatus(ReportStatus.IRRESOLVABLE);
				
				reportService.save(report);
			}else if(operation.equals("editONHOLD")){
				Report report;
				
				report = reportService.findOne(this.getEntityId(reportId));
				report.setStatus(ReportStatus.ONHOLD);
				
				reportService.save(report);
			}
			
			reportService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

}
