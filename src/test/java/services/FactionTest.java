
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Faction;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FactionTest extends AbstractTest {

	@Autowired
	private FactionService	factionService;


	// In this test we create an Item shield, and then, we activate him
	@Test
	public void CreateFactionTest() {

		super.authenticate("manager1");
		Faction faction = this.factionService.create();

		faction.setName("Blood");
		faction.setPowerUpDescription("You get an attack boost");
		faction.setExtraAttack(0.5);
		this.factionService.save(faction);
		super.unauthenticate();

		this.factionService.flush();

	}
	// We are goint to create a new faction as another rol (restriction)
	@Test(expected = IllegalArgumentException.class)
	public void CreateFactionNoManagerTest() {

		super.authenticate("player1");
		Faction faction = this.factionService.create();

		faction.setName("Blood");
		faction.setPowerUpDescription("You get an attack boost");
		faction.setExtraAttack(0.5);
		this.factionService.save(faction);
		super.unauthenticate();

		this.factionService.flush();

	}

	// We create a faction without name (restriction)
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void CreateFactionWithoutNameTest() {

		super.authenticate("manager1");
		Faction faction = this.factionService.create();

		faction.setName("");
		faction.setPowerUpDescription("You get an attack boost");
		faction.setExtraAttack(0.5);
		this.factionService.save(faction);
		super.unauthenticate();

		this.factionService.flush();

	}

}
