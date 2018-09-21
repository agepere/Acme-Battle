
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Livelihood;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LivelihoodServiceTest extends AbstractTest {

	@Autowired
	private LivelihoodService	livelihoodService;


	//El delete está en el test de building
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"manager1", "livelihood1", null, "a"
			}, //Editando
			{
				"manager2", "livelihood1", IllegalArgumentException.class, "a"
			},	//Intentando borrar un livelihood que no es tuyo (error)
			{
				"manager1", "livelihood1", ConstraintViolationException.class, ""
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
		Livelihood d;

		caught = null;
		try {
			super.authenticate(username);
			d = this.livelihoodService.findOne(super.getEntityId(buildingId));
			d.setDescription(description);

			this.livelihoodService.save(d);
			this.livelihoodService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
}
