
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Item;
import domain.ItemType;
import domain.Purchase;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ShieldServiceTest extends AbstractTest {

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ShieldService	shieldService;

	@Autowired
	private PurchaseService	purchaseService;


	// In this test we create an Item shield, and then, we activate him
	@Test
	public void ShieldActiveTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(0.2);
		item.setMunnyCost(100);
		item.setType(ItemType.SHIELD);
		item.setName("Shield");
		item.setDescription("Escudo");
		item.setOnSell(true);

		Item itemShield = this.itemService.save(item);
		//purchaseService.activeItem(pur);
		super.unauthenticate();
		super.authenticate("player1");
		Purchase pur = this.itemService.buyItem(itemShield);
		this.shieldService.save(itemShield);
		this.shieldService.flush();
		this.itemService.flush();

	}
	// We activate an item which is other type of boost (restriction)
	@Test(expected = IllegalArgumentException.class)
	public void ShieldOtherBoostTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(0.2);
		item.setMunnyCost(100);
		item.setType(ItemType.ATTACKBOOST);
		item.setName("Attack Boost");
		item.setDescription("Boost that give you more attack");
		item.setOnSell(true);

		Item itemShield = this.itemService.save(item);
		super.unauthenticate();

		super.authenticate("player1");

		Purchase pur = this.itemService.buyItem(itemShield);
		//purchaseService.activeItem(pur);
		this.shieldService.save(itemShield);
		this.shieldService.flush();
		this.itemService.flush();

		super.unauthenticate();
	}

	// We activate an item which is other tipy of boost (restriction)
	@Test(expected = IllegalArgumentException.class)
	public void ShieldNotBuyTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(0.2);
		item.setMunnyCost(100);
		item.setType(ItemType.SHIELD);
		item.setName("Shield");
		item.setDescription("Escudo");
		item.setOnSell(true);

		Item itemShield = this.itemService.save(item);
		super.unauthenticate();

		super.authenticate("player1");

		//Purchase pur = this.itemService.buyItem(itemShield);
		//purchaseService.activeItem(pur);
		this.shieldService.save(itemShield);
		this.shieldService.flush();
		this.itemService.flush();

		super.unauthenticate();
	}

}
