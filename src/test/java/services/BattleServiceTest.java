
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Battle;
import domain.GummiShip;
import domain.KeybladeWielder;
import domain.Recruited;
import domain.Troop;
import form.BattleForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BattleServiceTest extends AbstractTest {

	@Autowired
	private BattleService		battleService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RecruitedService	recruitedService;

	@Autowired
	private TroopService		troopService;

	@Autowired
	private GummiShipService	gummiShipService;


	public BattleForm getBattleForm(int test) {
		BattleForm bat = new BattleForm();
		String atacante = "player1";
		String defensor = "player2";

		KeybladeWielder b = (KeybladeWielder) this.actorService.findOne(super.getEntityId(atacante));
		final Collection<Troop> troops = this.troopService.findAll();
		final Collection<GummiShip> gummiShips = this.gummiShipService.findAll();

		ArrayList<Integer> tropas1 = new ArrayList<>();
		ArrayList<Integer> tropas2 = new ArrayList<>();
		ArrayList<Integer> tropas3 = new ArrayList<>();
		Collection<Recruited> recruites = this.recruitedService.getAllRecruited(b.getId());
		for (Troop t : troops) {
			tropas1.add(0);
			tropas2.add(0);
			tropas3.add(10);
		}
		for (GummiShip t : gummiShips) {
			tropas1.add(0);
			tropas2.add(0);
			tropas3.add(10);
		}
		int index = 0;

		for (Recruited r : recruites) {
			index = 0;
			if (r.getTroop() != null) {
				for (Troop t : this.troopService.findAll()) {
					int val = tropas1.get(index);
					if (r.getTroop().getName().equals(t.getName())) {
						tropas1.remove(index);
						tropas1.add(index, 1);
					}
					index++;
				}
			} else {
				index = troops.size();
				for (GummiShip t : this.gummiShipService.findAll()) {
					int val = tropas1.get(index);
					if (r.getGummiShip().getName().equals(t.getName())) {
						tropas1.remove(index);
						tropas1.add(index, 1);
					}
					index++;
				}
			}
		}

		if (test == 0)
			bat.setTroops(tropas1);
		else if (test == 1)
			bat.setTroops(tropas2);
		else if (test == 2)
			bat.setTroops(tropas3);

		bat.setEnemy(defensor);
		return bat;
	}
	// In this test we create an Item shield, and then, we activate him
	@Test
	public void BattleTest() {

		super.authenticate("player1");
		BattleForm bf = this.getBattleForm(0);
		Battle battle;

		battle = this.battleService.fight(bf);

		this.battleService.flush();
		//purchaseService.activeItem(pur);
		super.unauthenticate();

	}
	// We do a fight without any troop(restriction)
	@Test(expected = IllegalArgumentException.class)
	public void BattleNoTroopsTest() {

		super.authenticate("player1");
		BattleForm bf = this.getBattleForm(1);
		Battle battle;

		battle = this.battleService.fight(bf);

		this.battleService.flush();
		//purchaseService.activeItem(pur);
		super.unauthenticate();

	}

	// We do a fight with more troops that we have(restriction)
	@Test(expected = IllegalArgumentException.class)
	public void BattleMoreTroopsTest() {

		super.authenticate("player1");
		BattleForm bf = this.getBattleForm(2);
		Battle battle;

		battle = this.battleService.fight(bf);

		this.battleService.flush();
		//purchaseService.activeItem(pur);
		super.unauthenticate();

	}

}
