
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import security.LoginService;
import domain.ContentManager;
import domain.Item;
import domain.KeybladeWielder;
import domain.Purchase;

@Service
@Transactional
public class ItemService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemRepository			itemRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private PurchaseService			purchaseService;
	@Autowired
	private KeybladeWielderService	keybladeWielderService;
	@Autowired
	private Validator				validator;


	// CRUD methods

	public Item create() {
		Item item;

		item = new Item();
		ContentManager contentManager = (ContentManager) this.actorService.findByPrincipal();
		item.setContentManager(contentManager);
		Assert.isTrue(item.getContentManager().getUserAccount().isAuthority("MANAGER"));

		return item;
	}

	public Item save(Item item) {
		Assert.notNull(item);

		// Solo el que ha creado el item puede modificarlo
		Assert.isTrue(LoginService.getPrincipal().equals(item.getContentManager().getUserAccount()),"error.message.itemOwner");

		Item saved = this.itemRepository.save(item);

		return saved;
	}

	public Item findOne(final int itemId) {
		Assert.notNull(itemId);

		Item Item;

		Item = this.itemRepository.findOne(itemId);

		return Item;
	}

	// Con esto puedo saber todos los items que hay, lo usare para mostrarlos en
	// la tienda
	public Collection<Item> findAll() {
		Collection<Item> Items;
		Items = this.itemRepository.findAll();

		return Items;
	}

	public void delete(final Item item) {
		Assert.notNull(item);

		this.itemRepository.delete(item);
	}

	// OTROS METODOS -----------------

	// Items de la tienda
	public Collection<Item> shopItems() {
		return this.itemRepository.shopItems();
	}
	
	public Page<Item> shopItemsPageable(Pageable p) {
		return this.itemRepository.shopItemsPageable(p);
	}

	// Comprar un item de la tienda
	public Purchase buyItem(Item item) {
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		// Solo lo podremos comprar si disponemos del Munny que cuesta el item
		Assert.isTrue(item.getMunnyCost() <= player.getMaterials().getMunny());
		Assert.isTrue(item.getOnSell() == true);
		Purchase p = this.purchaseService.create();
		Date currentDate = new Date(System.currentTimeMillis() - 100);

		p.setPlayer(player);
		p.setPurchaseDate(currentDate);
		p.setItem(item);
		player.getMaterials().setMunny(player.getMaterials().getMunny() - item.getMunnyCost());

		// Hacemos set de la que sera la fecha en la que el objeto expirara
		Date expirationDate = new Date(currentDate.getTime() + p.getItem().getExpiration() * 24 * 60 * 60 * 1000);
		p.setExpirationDate(expirationDate);

		this.purchaseService.save(p);

		this.keybladeWielderService.save(player);

		return p;

	}

	// Items que han sido comprados
	public Collection<Item> itemsPurchased() {
		return this.itemRepository.itemsPurchased();
	}

	// Items que he comprado en la tienda y los puedo usar (no han caducado)
	public Collection<Item> myItems(final int playerId) {
		return this.itemRepository.myItems(playerId);
	}

	// Items que ha creado un Content Manager en especifico
	public Collection<Item> itemsByManager(final int managerId) {
		return this.itemRepository.itemsByManager(managerId);
	}

	public Item reconstruct(final Item item, final BindingResult binding) {
		Item result;
		final Item original = this.itemRepository.findOne(item.getId());

		if (item.getId() == 0) {
			result = item;
			result.setContentManager((ContentManager) this.actorService.findByPrincipal());
		} else {
			result = item;
			result.setContentManager(original.getContentManager());

		}
		this.validator.validate(result, binding);

		return result;

	}
	
	public void flush(){
		this.itemRepository.flush();
	}

	//dashboard

	public Integer maxCreatedItem() {
		return this.itemRepository.maxCreatedItem();

	}
	public Integer minCreatedItem() {
		return this.itemRepository.minCreatedItem();

	}
	public Double avgCreatedItem() {
		return this.itemRepository.avgCreatedItem();

	}
	public Double stddevCreatedItem() {
		return this.itemRepository.stddevCreatedItem();

	}

}
