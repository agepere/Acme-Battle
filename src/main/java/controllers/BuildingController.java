
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BuildingService;
import services.GummiShipService;
import services.RequirementService;
import services.TroopService;
import domain.Building;
import domain.Defense;
import domain.Livelihood;
import domain.Recruiter;
import domain.Requirement;

@Controller
@RequestMapping("/building")
public class BuildingController extends AbstractController {

	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private RequirementService	requirementService;
	@Autowired
	private TroopService		troopService;
	@Autowired
	private GummiShipService	gummiShipService;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView res;

		Pageable p = new PageRequest(page, 5);
		Page<Building> buildings = this.buildingService.getAvailableBuildingsPaginated(p);

		res = new ModelAndView("building/list");
		res.addObject("buildings", buildings.getContent());
		res.addObject("pageNum", buildings.getTotalPages());
		res.addObject("page", page);
		res.addObject("requestURI", "building/list.do?page=");

		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam Integer buildingId, @RequestParam(required = false, defaultValue = "0") Integer page) {
		Page<Requirement> requirements;
		Pageable pageable;

		ModelAndView res = new ModelAndView("building/display");

		Building b = this.buildingService.findOne(buildingId);
		try {
			//Si no existe el edificio o no es  y no soy el dueño pa fuera
			if (b == null || (!b.getIsFinal() && !b.getContentManager().getUserAccount().equals(LoginService.getPrincipal())))
				res = new ModelAndView("redirect:list.do");
			else if (b instanceof Defense)
				res.addObject("defense", true);
			else if (b instanceof Recruiter) {

				res.addObject("recruiter", true);
				res.addObject("troops", this.troopService.getTroopsFromRecruiter(b.getId()));
				res.addObject("gummiShips", this.gummiShipService.getGummiShipFromRecruiter(b.getId()));
			} else if (b instanceof Livelihood)
				res.addObject("livelihood", true);
			else
				res.addObject("warehouse", true);

		} catch (Throwable oops) {
			res = new ModelAndView("redirect:list.do");
		}
		pageable = new PageRequest(page, 5);
		requirements = this.requirementService.getRequirementsByBuilding(buildingId, pageable);

		res.addObject("building", b);
		res.addObject("requestURI", "building/display.do?buildingId=" + buildingId + "&page=");
		res.addObject("pageNum", requirements.getTotalPages());
		res.addObject("page", page);
		res.addObject("requirements", requirements.getContent());

		return res;
	}
}
