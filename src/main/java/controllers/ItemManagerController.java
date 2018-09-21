package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.ContentManager;
import domain.Item;
import services.ActorService;
import services.ItemService;

@Controller()
@RequestMapping("/item/manager")
public class ItemManagerController extends AbstractController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ActorService actorService;
	
	// Lista de items que ha creado el Content Manager logeado
	@RequestMapping(value = "/createdItems", method = RequestMethod.GET)
	public ModelAndView shopItemsList() {
		ModelAndView result;
		Collection<Item> items;

		ContentManager manager = (ContentManager) this.actorService.findByPrincipal();
		items = this.itemService.itemsByManager(manager.getId());
	

		result = new ModelAndView("item/manager/createdItems");
		result.addObject("items", items);
		
		return result;
	}
	
	// Creation ---------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Item item;

			item = itemService.create();
			result = createEditModelAndView(item);

			return result;
		}
	
	
	// EDITION -----------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int itemId) {
		ModelAndView result;
		Item item;

		item = this.itemService.findOne(itemId);
		Assert.notNull(item);
		
		Collection<Item> itemsPurchased = this.itemService.itemsPurchased();
		List<Item> itemsPurchasedList = new ArrayList<>(itemsPurchased);
		boolean contiene = false;
		
		if(itemsPurchasedList.contains(item)){
			contiene = true;
		}
		

		result = createEditModelAndView(item);
		result.addObject("contiene", contiene);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Item item, BindingResult binding) {
		ModelAndView result;
		
		item = this.itemService.reconstruct(item, binding);
		
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(item);
		} else
			try {
				this.itemService.save(item);
				result = new ModelAndView("redirect:/item/manager/createdItems.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(item, this.getErrorMessage(oops));

			}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Item item, BindingResult binding) {
		ModelAndView result;
		item = this.itemService.reconstruct(item, binding);

		try {
			
			itemService.delete(item);
			result = new ModelAndView("redirect:/item/manager/createdItems.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(item, "master.page.commit.error");
		}

		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(Item item) {
		return this.createEditModelAndView(item, null);
	}

	protected ModelAndView createEditModelAndView(Item item, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("item/manager/edit");
		result.addObject("item", item);
		result.addObject("message", messageCode);

		return result;
	}



}
