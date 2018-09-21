
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BuildingService;
import services.DefenseService;
import services.LivelihoodService;
import services.RecruiterService;
import services.WarehouseService;
import domain.Building;
import domain.Defense;
import domain.Livelihood;
import domain.Recruiter;
import domain.Warehouse;

@Controller
@RequestMapping("/building/contentManager")
public class BuildingManagerController extends AbstractController {

	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private DefenseService		defenseService;
	@Autowired
	private RecruiterService	recruiterService;
	@Autowired
	private WarehouseService	warehouseService;
	@Autowired
	private LivelihoodService	livelihoodService;


	@RequestMapping("/myList")
	public ModelAndView myList(@RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView res;

		Pageable p = new PageRequest(page, 5);
		Page<Building> buildings = this.buildingService.getMyCreatedBuildingsPaginated(p);

		res = new ModelAndView("building/list");
		res.addObject("buildings", buildings.getContent());
		res.addObject("pageNum", buildings.getTotalPages());
		res.addObject("page", page);
		res.addObject("requestURI", "building/contentManager/myList.do?page=");

		return res;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(required = false) Integer buildingId, @RequestParam(required = false) String buildingType) {
		ModelAndView res = new ModelAndView("building/edit");
		Defense defense;
		Recruiter recruiter;
		Livelihood livelihood;
		Warehouse warehouse;

		//============= EDITANDO UNO EXISTENTE ==============
		if (buildingId != null)
			switch (buildingType) {
			case "defense":
				defense = this.defenseService.findOne(buildingId);
				if (defense.getIsFinal() || !defense.getContentManager().getUserAccount().equals(LoginService.getPrincipal()))
					res = new ModelAndView("redirect:myList.do");
				res.addObject("buildingType", "defense");
				res.addObject("defense", defense);
				break;
			case "recruiter":
				recruiter = this.recruiterService.findOne(buildingId);
				if (recruiter.getIsFinal() || !recruiter.getContentManager().getUserAccount().equals(LoginService.getPrincipal()))
					res = new ModelAndView("redirect:myList.do");
				res.addObject("buildingType", "recruiter");
				res.addObject("recruiter", recruiter);
				break;
			case "livelihood":
				livelihood = this.livelihoodService.findOne(buildingId);
				if (livelihood.getIsFinal() || !livelihood.getContentManager().getUserAccount().equals(LoginService.getPrincipal()))
					res = new ModelAndView("redirect:myList.do");
				res.addObject("buildingType", "livelihood");
				res.addObject("livelihood", livelihood);
				break;
			case "warehouse":
				warehouse = this.warehouseService.findOne(buildingId);
				if (warehouse.getIsFinal() || !warehouse.getContentManager().getUserAccount().equals(LoginService.getPrincipal()))
					res = new ModelAndView("redirect:myList.do");
				res.addObject("buildingType", "warehouse");
				res.addObject("warehouse", warehouse);
				break;
			default:
				res = new ModelAndView("redirect:edit.do");
				break;
			}//========== FIN EDITANDO ========================
		//============CREANDO DESDE 0 =============
		else if (buildingType != null && (buildingType.equals("defense") || buildingType.equals("recruiter") || buildingType.equals("livelihood") || buildingType.equals("warehouse")))
			switch (buildingType) {
			case "defense":
				defense = this.defenseService.create();
				res.addObject("buildingType", "defense");
				res.addObject("defense", defense);
				break;
			case "recruiter":
				recruiter = this.recruiterService.create();
				res.addObject("buildingType", "recruiter");
				res.addObject("recruiter", recruiter);
				break;
			case "livelihood":
				livelihood = this.livelihoodService.create();
				res.addObject("buildingType", "livelihood");
				res.addObject("livelihood", livelihood);
				break;
			default:
				warehouse = this.warehouseService.create();
				res.addObject("buildingType", "warehouse");
				res.addObject("warehouse", warehouse);
				break;
			} // ======== FIN DEL CREATE DESDE 0 =========
		// EN OTRO CASO SE IRÁ AL FORMULARIO PARA SELECCIONAR LO QUE SER

		return res;
	}

	@RequestMapping(value = "/edit", params = "savedefense", method = RequestMethod.POST)
	public ModelAndView saveDefense(Defense defense, BindingResult binding, Boolean saveFinal) {
		ModelAndView res;

		this.defenseService.reconstruct(defense, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(defense, null, null, null, "defense");
		else
			try {
				if (defense.getId() != 0)
					Assert.isTrue(!this.defenseService.findOne(defense.getId()).getIsFinal(), "error.message.building.final");
				if (saveFinal != null && saveFinal)
					defense.setIsFinal(true);
				this.defenseService.save(defense);
				res = new ModelAndView("redirect:myList.do");

			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(defense, null, null, null, "defense", msg);

			}

		return res;
	}

	@RequestMapping(value = "/edit", params = "saverecruiter", method = RequestMethod.POST)
	public ModelAndView saveRecruiter(Recruiter recruiter, BindingResult binding, Boolean saveFinal) {
		ModelAndView res;

		this.recruiterService.reconstruct(recruiter, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(null, recruiter, null, null, "recruiter");
		else
			try {
				if (recruiter.getId() != 0)
					Assert.isTrue(!this.recruiterService.findOne(recruiter.getId()).getIsFinal(), "error.message.building.final");
				if (saveFinal != null && saveFinal)
					recruiter.setIsFinal(true);
				this.recruiterService.save(recruiter);
				res = new ModelAndView("redirect:myList.do");

			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(null, recruiter, null, null, "recruiter", msg);

			}

		return res;
	}

	@RequestMapping(value = "/edit", params = "savewarehouse", method = RequestMethod.POST)
	public ModelAndView saveWarehouse(Warehouse warehouse, BindingResult binding, Boolean saveFinal) {
		ModelAndView res;

		this.warehouseService.reconstruct(warehouse, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(null, null, warehouse, null, "warehouse");
		else
			try {
				if (warehouse.getId() != 0)
					Assert.isTrue(!this.warehouseService.findOne(warehouse.getId()).getIsFinal(), "error.message.building.final");
				if (saveFinal != null && saveFinal)
					warehouse.setIsFinal(true);
				this.warehouseService.save(warehouse);
				res = new ModelAndView("redirect:myList.do");

			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(null, null, warehouse, null, "warehouse", msg);

			}

		return res;
	}

	@RequestMapping(value = "/edit", params = "savelivelihood", method = RequestMethod.POST)
	public ModelAndView saveLivelihood(Livelihood livelihood, BindingResult binding, Boolean saveFinal) {
		ModelAndView res;

		this.livelihoodService.reconstruct(livelihood, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(null, null, null, livelihood, "livelihood");
		else
			try {
				if (livelihood.getId() != 0)
					Assert.isTrue(!this.livelihoodService.findOne(livelihood.getId()).getIsFinal(), "error.message.building.final");
				if (saveFinal != null && saveFinal)
					livelihood.setIsFinal(true);
				this.livelihoodService.save(livelihood);
				res = new ModelAndView("redirect:myList.do");

			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(null, null, null, livelihood, "livelihood", msg);

			}

		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam Integer buildingId) {
		ModelAndView res;

		Building building = this.buildingService.findOne(buildingId);

		try {
			Assert.notNull(building, "error.message.building.noExist");
			this.buildingService.delete(building);
			res = new ModelAndView("redirect:myList.do");
		} catch (Throwable oops) {
			String msg = this.getErrorMessage(oops);
			Collection<Building> buildings = this.buildingService.getMyCreatedBuildings();

			res = new ModelAndView("building/list");
			res.addObject("buildings", buildings);
			res.addObject("requestURI", "building/contentManager/myList.do");
			res.addObject("message", msg);
		}

		return res;
	}
	protected ModelAndView createEditModelAndView(Defense defense, Recruiter recruiter, Warehouse warehouse, Livelihood livelihood, String buildingType) {
		return this.createEditModelAndView(defense, recruiter, warehouse, livelihood, buildingType, null);
	}
	protected ModelAndView createEditModelAndView(Defense defense, Recruiter recruiter, Warehouse warehouse, Livelihood livelihood, String buildingType, String message) {
		ModelAndView res;

		res = new ModelAndView("building/edit");

		switch (buildingType) {
		case "defense":
			res.addObject("buildingType", "defense");
			res.addObject("defense", defense);
			break;
		case "recruiter":
			res.addObject("buildingType", "recruiter");
			res.addObject("recruiter", recruiter);
			break;
		case "livelihood":
			res.addObject("buildingType", "livelihood");
			res.addObject("livelihood", livelihood);
			break;
		default:
			res.addObject("buildingType", "warehouse");
			res.addObject("warehouse", warehouse);
			break;
		}

		res.addObject("message", message);

		return res;
	}

}
