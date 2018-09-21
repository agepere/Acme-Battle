package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Item;
import domain.ItemType;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemServiceTest extends AbstractTest {

	@Autowired
	private ItemService itemService;

	// In this test we create an Item
	@Test
	public void ItemCreateTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(0.2);
		item.setMunnyCost(100);
		item.setType(ItemType.ATTACKBOOST);
		item.setName("Ataque");
		item.setDescription("Potencia ataque");
		item.setOnSell(true);

		this.itemService.save(item);
		this.itemService.flush();

		super.unauthenticate();
	}

	// We create an item with the name in blank (restriction)
	@Test(expected = ConstraintViolationException.class)
	public void ItemWrongNameCreateTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(0.2);
		item.setMunnyCost(100);
		item.setType(ItemType.ATTACKBOOST);
		item.setName("");
		item.setDescription("Potencia ataque");
		item.setOnSell(true);

		this.itemService.save(item);
		this.itemService.flush();

		super.unauthenticate();
	}

	// Extra must be in the range (0,1)
	@Test(expected = ConstraintViolationException.class)
	public void ItemWrongExtraCreateTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(-0.2);
		item.setMunnyCost(100);
		item.setType(ItemType.ATTACKBOOST);
		item.setName("Ataque");
		item.setDescription("Potencia ataque");
		item.setOnSell(true);

		this.itemService.save(item);
		this.itemService.flush();

		super.unauthenticate();
	}

	// Positive test for buying an item
	@Test
	public void BuyItemTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(0.2);
		item.setMunnyCost(100);
		item.setType(ItemType.ATTACKBOOST);
		item.setName("Ataque");
		item.setDescription("Potencia ataque");
		item.setOnSell(true);

		Item saved = this.itemService.save(item);
		super.unauthenticate();

		super.authenticate("player1");
		this.itemService.buyItem(saved);
		this.itemService.flush();

		super.unauthenticate();
	}

	// Negative test Buy an item : You must have the enough quantity of munny
	@Test(expected = IllegalArgumentException.class)
	public void BuyItemWrongMunnyTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(0.2);
		item.setMunnyCost(9999999);
		item.setType(ItemType.ATTACKBOOST);
		item.setName("Ataque");
		item.setDescription("Potencia ataque");
		item.setOnSell(true);

		Item saved = this.itemService.save(item);
		super.unauthenticate();

		super.authenticate("player1");
		this.itemService.buyItem(saved);
		this.itemService.flush();

		super.unauthenticate();
	}

	// Negative test Buy an item : The item must be on sell
	@Test(expected = IllegalArgumentException.class)
	public void BuyItemNotOnSellTest() {

		super.authenticate("manager1");
		Item item = this.itemService.create();

		item.setDuration(10);
		item.setExpiration(20);
		item.setExtra(0.2);
		item.setMunnyCost(120);
		item.setType(ItemType.ATTACKBOOST);
		item.setName("Ataque");
		item.setDescription("Potencia ataque");
		item.setOnSell(false);

		Item saved = this.itemService.save(item);
		super.unauthenticate();

		super.authenticate("player1");
		this.itemService.buyItem(saved);
		this.itemService.flush();

		super.unauthenticate();
	}

}
