package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Item;
import domain.ItemType;
import domain.Purchase;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PurchaseServiceTest extends AbstractTest {
	
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private ItemService itemService;
	
	// In this test we active a item purchased
	@Test
	public void ActivePurchaseTest() {

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

		Purchase purchase = this.purchaseService.create();

		purchase.setItem(saved);
		purchase.setPurchaseDate(new Date(System.currentTimeMillis() - 100));

		Purchase saved2 = this.purchaseService.save(purchase);
		
		this.purchaseService.activeItem(saved2);

		super.unauthenticate();
	}
	
}
