
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
public class ReportUpdateServiceTest extends AbstractTest {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ReportUpdateService	reportUpdateService;
	@Autowired
	private ActorService	actorService;

	@Test
	public void driver() {
		final Object testingData[][] = {

			{
				"gamemaster1", null, "report3", "create", null
			}, //A gm creates a report update
			{
				"gamemaster1", null, "report3", "createNoStatus", ConstraintViolationException.class
			}, //A report update can't be created without a status
			{
				"gamemaster1", null, "report3", "createPastDate", ConstraintViolationException.class
			}, //Creation date must be in the past
			{
				"gamemaster1", "reportUpdate3", "report3", "edit", null
			}, //A gm edits a report update
			{
				"gamemaster1", "reportUpdate3", "report1", "edit", IllegalArgumentException.class
			}, //ReportUpdate must be in the list of updates of the report 
			{
				"gamemaster2", "reportUpdate3", "report1", "edit", IllegalArgumentException.class
			}, //A game master only can edit his own updates
			{
				"player1", "reportUpdate3", "report3", "setSuspicious", null
			}, //A player set a report as suspicious
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

	protected void template(String username, String reportUpdateId, String reportId, String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			
			if(operation.equals("create")){
				ReportUpdate reportUpdate;
				
				reportUpdate = reportUpdateService.create();
				reportUpdate.setGameMaster((GameMaster) actorService.findByPrincipal());
				reportUpdate.setContent("test");
				reportUpdate.setDate(new Date(System.currentTimeMillis()));
				reportUpdate.setIsSuspicious(false);
				reportUpdate.setStatus(ReportStatus.IRRESOLVABLE);
				
				reportUpdateService.save(reportUpdate, this.getEntityId(reportId));
			}else if(operation.equals("createNoStatus")){
				ReportUpdate reportUpdate;
				
				reportUpdate = reportUpdateService.create();
				reportUpdate.setGameMaster((GameMaster) actorService.findByPrincipal());
				reportUpdate.setContent("test");
				reportUpdate.setDate(new Date(System.currentTimeMillis()));
				reportUpdate.setIsSuspicious(false);
				
				reportUpdateService.save(reportUpdate, this.getEntityId(reportId));
			}else if(operation.equals("createPastDate")){
				ReportUpdate reportUpdate;
				
				reportUpdate = reportUpdateService.create();
				reportUpdate.setGameMaster((GameMaster) actorService.findByPrincipal());
				reportUpdate.setContent("test");
				reportUpdate.setDate(new Date(System.currentTimeMillis()+2000));
				reportUpdate.setIsSuspicious(false);
				reportUpdate.setStatus(ReportStatus.IRRESOLVABLE);
				
				reportUpdateService.save(reportUpdate, this.getEntityId(reportId));
			}else if(operation.equals("edit")){
				ReportUpdate reportUpdate;
				
				reportUpdate = reportUpdateService.findOneToEdit(this.getEntityId(reportUpdateId));
				reportUpdate.setContent("test");
				reportUpdateService.save(reportUpdate,this.getEntityId(reportId));
			}else if(operation.equals("setSuspicious")){
				ReportUpdate reportUpdate;
				
				reportUpdate = reportUpdateService.findOne(this.getEntityId(reportUpdateId));
				reportUpdateService.markSuspicious(reportUpdate);
			}
			
			reportService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

}
