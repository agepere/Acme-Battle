
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Building;
import domain.Requirement;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequirementServiceTest extends AbstractTest {

	@Autowired
	private RequirementService	reqirementService;
	@Autowired
	private BuildingService		buildingService;


	//El delete está en el test de building
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"manager1", "requirement1", IllegalArgumentException.class
			},
			//edit un requisito (erroneo es  final)
			{
				"manager2", "requirement2", IllegalArgumentException.class
			},
			//edit un requisito (erroneo es de otro)
			{
				"manager1", null, null
			}
		//edit un requisito 

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();

			this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			super.flushTransaction();
		}
	}

	protected void template(String username, String requirementId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Requirement r;
			if (requirementId != null)
				r = this.reqirementService.findOne(super.getEntityId(requirementId));
			else {

				Building b = this.buildingService.findOne(super.getEntityId("defense3"));
				Building b2 = this.buildingService.findOne(super.getEntityId("defense2"));

				r = this.reqirementService.create(b);
				r.setRequiredBuilding(b2);
			}
			r.setLvl(2);
			this.reqirementService.save(r);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

}
