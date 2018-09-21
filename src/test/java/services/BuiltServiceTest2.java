
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Built;
import domain.GummiShip;
import domain.Troop;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BuiltServiceTest2 extends AbstractTest {

	@Autowired
	private BuiltService		builtService;
	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private TroopService		troopService;
	@Autowired
	private GummiShipService	gummiShipService;


	//==================TEST UPGRADE==================
	@Test
	public void upgradeDriver() {
		final Object testingData[][] = {

			{
				"player1", "built2", null
			}, //Creando
			{
				"player4", "built2", IllegalArgumentException.class
			},	//No es suyo (error)
			{
				"player1", "built1", IllegalArgumentException.class
			}
		// Lvl maximo (error)

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.templateUpgrade((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void templateUpgrade(String username, String buildingId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Built b = this.builtService.findOne(super.getEntityId(buildingId));
			this.builtService.upgrade(b);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
	//==================TEST USARLO (livelihood)================== 

	@Test
	public void startCollect() {
		final Object testingData[][] = {

			{
				"player2", "built2", IllegalArgumentException.class
			}, //No es suyo (error)
			{
				"player2", "built222", IllegalArgumentException.class
			},//Esta en uso
			{
				"player1", "built2", null
			}
		//OK
		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();

			this.templateStartCollect((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void templateStartCollect(String username, String buildingId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Built b = this.builtService.findOne(super.getEntityId(buildingId));

			this.builtService.startToCollect(b);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void startRecruitTroopDriver() {
		final Object testingData[][] = {

			{
				"player1", "built1", IllegalArgumentException.class, "troop1"
			}, //No es un recruiter (error)
			{
				"player4", "built4", IllegalArgumentException.class, "troop1"
			},
			//No es suyo ( error)
			{
				"player1", "built4", null, "troop1"
			}
		//OK
		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.templateStartRecruitTroop((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
		}
	}

	protected void templateStartRecruitTroop(String username, String buildingId, final Class<?> expected, String troopId) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Built b = this.builtService.findOne(super.getEntityId(buildingId));
			Troop t = this.troopService.findOne(super.getEntityId(troopId));

			this.builtService.startToRecruitTroop(b, t);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

	//==================TEST USARLO (SHIP)================== 

	@Test
	public void startRecruitShipDriver() {
		final Object testingData[][] = {

			{
				"player1", "built1", IllegalArgumentException.class, "gummiShip1"
			}, //No es un recruiter (error)
			{
				"player4", "built4", IllegalArgumentException.class, "gummiShip1"
			},
			//No es suyo ( error)
			{
				"player1", "built4", null, "gummiShip1"
			}
		//OK
		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			super.flushTransaction();
			this.templateStartRecruitGummi((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
		}
	}

	protected void templateStartRecruitGummi(String username, String buildingId, final Class<?> expected, String troopId) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Built b = this.builtService.findOne(super.getEntityId(buildingId));
			GummiShip t = this.gummiShipService.findOne(super.getEntityId(troopId));

			this.builtService.startToRecruitGummiShip(b, t);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
}
