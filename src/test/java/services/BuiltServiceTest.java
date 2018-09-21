
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Building;
import domain.Built;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BuiltServiceTest extends AbstractTest {

	@Autowired
	private BuiltService	builtService;
	@Autowired
	private BuildingService	buildingService;


	//El delete está en el test de building

	//==================TEST CREACION==================
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"player1", "recruiter1", null
			}, //Creando
			{
				"player4", "recruiter1", IllegalArgumentException.class
			},	//No cumple los requisitos
			{
				"player3", "defense1", IllegalArgumentException.class
			}
		// No tiene material

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.templateSave((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void templateSave(String username, String buildingId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Building b = this.buildingService.findOne(super.getEntityId(buildingId));
			Built built = this.builtService.create(b);
			this.builtService.save(built);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

	//==================TEST DELETE==================
	@Test
	public void deleteDriver() {
		final Object testingData[][] = {

			{
				"player1", "built1", null
			}, //Creando
			{
				"player4", "built2", IllegalArgumentException.class
			}
		//No es suyo (único error)

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.templateDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void templateDelete(String username, String buildingId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Built b = this.builtService.findOne(super.getEntityId(buildingId));
			this.builtService.delete(b);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

}
