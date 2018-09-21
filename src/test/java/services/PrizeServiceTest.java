
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.KeybladeWielder;
import domain.Materials;
import domain.Prize;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PrizeServiceTest extends AbstractTest {

	@Autowired
	private PrizeService			prizeService;
	@Autowired
	private KeybladeWielderService	kwService;


	//El delete está en el test de building
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"player1", null, 10, "a"
			}, //Creando un premio
			{
				"player2", ConstraintViolationException.class, 10, null
			},	//PRemio sin decripscion (error)
			{
				"player1", ConstraintViolationException.class, -1, "a"
			}
		//PRemio con cantidad negativa (error)

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.templateSave((String) testingData[i][0], (Class<?>) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3]);
		}
	}

	protected void templateSave(String player, final Class<?> expected, Integer munny, String description) {
		Class<?> caught;

		caught = null;
		try {
			KeybladeWielder kw = this.kwService.findOne(super.getEntityId(player));
			Prize p = this.prizeService.create();

			p.setKeybladeWielder(kw);
			p.setDescription(description);
			Materials m = new Materials();
			m.setMunny(munny);
			m.setMytrhil(10);
			m.setGummiCoal(10);
			p.setMaterials(m);

			this.prizeService.save(p);
			this.prizeService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
	//=============
	@Test
	public void openDriver() {
		final Object testingData[][] = {
			{
				"player2", "prize1", IllegalArgumentException.class
			},	//Intentando abrir un premio que no es tuyo (unico error posible en el open)
			{
				"player1", "prize1", null
			}
		//Abrir el premio

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void template(String player, String prizeId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(player);
			Prize p = this.prizeService.findOne(super.getEntityId(prizeId));
			this.prizeService.open(p);

		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
}
