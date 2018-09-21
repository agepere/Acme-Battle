
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Defense;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DefenseServiceTest extends AbstractTest {

	@Autowired
	private DefenseService	defenseService;


	//El delete está en el test de building
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"manager1", "defense3", null, "a"
			}, //Editando
			{
				"manager2", "defense3", IllegalArgumentException.class, "a"
			},	//Intentando borrar un recruiter que no es tuyo (error)
			{
				"manager1", "defense3", ConstraintViolationException.class, ""
			}
		//Descripción errónea

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
		}
	}

	protected void template(String username, String buildingId, final Class<?> expected, String description) {
		Class<?> caught;
		Defense d;

		caught = null;
		try {
			super.authenticate(username);
			d = this.defenseService.findOne(super.getEntityId(buildingId));
			d.setDescription(description);

			this.defenseService.save(d);
			this.defenseService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
}
