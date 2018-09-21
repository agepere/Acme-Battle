package controllers;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Item;
import domain.KeybladeWielder;
import domain.Purchase;

import services.ActorService;
import services.ItemService;
import services.PurchaseService;

@Controller
@RequestMapping("/item/player")
public class ItemUserController extends AbstractController{
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private PurchaseService purchaseService;
	
	// Listar los objetos que se pueden comprar en la tienda
	@RequestMapping(value = "/shopItemsList", method = RequestMethod.GET)
	public ModelAndView shopItemsList(@RequestParam(required=false, defaultValue="0") Integer page) {
		ModelAndView result;
		Page<Item> items;
		Pageable pageable;

		pageable = new PageRequest(page, 5);

		items = this.itemService.shopItemsPageable(pageable);
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		Integer playerMunny = player.getMaterials().getMunny();

		result = new ModelAndView("item/player/shopItemsList");
		result.addObject("items", items.getContent());
		result.addObject("page", page);
		result.addObject("requestURI", "item/player/shopItemsList.do?page=");
		result.addObject("pageNum", items.getTotalPages());
		result.addObject("playerMunny", playerMunny);
		//result.addObject("requestURI", "item/player/shopItemsList.do");
		return result;
	}
	

	// Comprar un item de la tienda
	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public ModelAndView buyItem(@RequestParam(value = "itemId", required = true) int itemId) {
		ModelAndView result;
		Item item;

		item = this.itemService.findOne(itemId);
		this.itemService.buyItem(item);

		result = new ModelAndView("redirect:/item/player/shopItemsList.do");
		
		return result;
	}
	
	// Listar los items que tengo comprados (y no estan caducados) para usarlos
	@RequestMapping(value = "/ownedItemsList", method = RequestMethod.GET)
	public ModelAndView myItemsList() {
		ModelAndView result;
		Collection<Purchase> purchases;

		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		// Con esta llamada al servicio tambien estamos eliminando las purchases que estan caducadas
		purchases = this.purchaseService.noActivePurchasesByPlayer(player.getId());

		result = new ModelAndView("item/player/ownedItemsList");
		result.addObject("purchases", purchases);
		
		return result;
	}
	
	// Activar un item
	@RequestMapping(value = "/activeItem", method = RequestMethod.GET)
	public ModelAndView activeItem(@RequestParam(value = "purchaseId", required = true) int purchaseId) {
		ModelAndView result;
		Purchase purchase = this.purchaseService.findOne(purchaseId);
		
		this.purchaseService.activeItem(purchase);
		
		result = new ModelAndView("redirect:/item/player/ownedItemsList.do");
		
		return result;
	}
	
	// Listar items que estan activos
	@RequestMapping(value = "/activeItemsList", method = RequestMethod.GET)
	public ModelAndView activeItemsList() {
		ModelAndView result;
		Collection<Purchase> purchases;
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		
		this.purchaseService.deleteActiveExpiredPurchasesByPlayer(player.getId());
		purchases = this.purchaseService.activePurchasesByPlayer(player.getId());
		
		result = new ModelAndView("item/player/activeItemsList");
		result.addObject("purchases", purchases);
		
		return result;
	}

}
