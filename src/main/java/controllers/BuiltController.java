
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BuildingService;
import services.BuiltService;
import services.GummiShipService;
import services.TroopService;
import domain.Building;
import domain.Built;
import domain.Defense;
import domain.GummiShip;
import domain.Livelihood;
import domain.Recruited;
import domain.Recruiter;
import domain.Troop;
import form.BuiltForm;
import form.RecruitedForm;

@Controller
@RequestMapping("/built")
public class BuiltController extends AbstractController {

	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private BuiltService		builtService;
	@Autowired
	private TroopService		troopService;
	@Autowired
	private GummiShipService	gummiShipService;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView res;
		Page<Built> builts;
		Pageable pageable;

		pageable = new PageRequest(page, 6);
		builts = this.builtService.getMyBuiltsPageable(pageable);
		res = new ModelAndView("built/list");

		res.addObject("builts", builts.getContent());
		res.addObject("page", page);
		res.addObject("requestURI", "built/list.do?page=");
		res.addObject("pageNum", builts.getTotalPages());

		return res;

	}

	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView res;
		Collection<Building> buildings = this.buildingService.getAvailableBuildings();
		BuiltForm form = new BuiltForm();

		res = new ModelAndView("built/edit");
		res.addObject("buildings", buildings);
		res.addObject("builtForm", form);

		return res;
	}

	@RequestMapping(value = "/create", params = "save")
	public ModelAndView save(@Valid BuiltForm builtForm, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(builtForm);
		else
			try {
				Built b = this.builtService.create(builtForm.getBuilding());
				this.builtService.save(b);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(builtForm, msg);
			}

		return res;
	}

	@RequestMapping("/upgrade")
	public ModelAndView update(@RequestParam Integer builtId) {
		ModelAndView res;
		Built b = this.builtService.findOne(builtId);
		if (b == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.builtService.upgrade(b);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createListModelAndView(msg);
			}
		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam Integer builtId) {
		ModelAndView res = new ModelAndView("built/display");

		Built b = this.builtService.findOne(builtId);
		Building building = b.getBuilding();

		if (!b.getKeybladeWielder().getUserAccount().equals(LoginService.getPrincipal()) || b.getLvl() == 0)
			res = new ModelAndView("redirect:list.do");

		else if (building instanceof Defense)
			res.addObject("defense", true);
		else if (building instanceof Recruiter) {

			res.addObject("recruiter", true);
			res.addObject("troops", this.troopService.getTroopsAvailableFromRecruiterAndLvl(building.getId(), b.getLvl()));
			res.addObject("gummiShips", this.gummiShipService.getGummiShipsAvailableFromRecruiterAndLvl(building.getId(), b.getLvl()));
		} else if (building instanceof Livelihood)
			res.addObject("livelihood", true);
		else {
			res.addObject("warehouse", true);
			res.addObject("storagedTroops", this.troopService.getStoragedTroops(builtId));
			res.addObject("storagedShips", this.gummiShipService.getStoragedGummiShip(builtId));
		}

		res.addObject("built", b);

		return res;
	}
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Integer builtId) {
		ModelAndView res;
		Built built = this.builtService.findOne(builtId);

		try {
			this.builtService.delete(built);
			res = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			String msg = this.getErrorMessage(oops);
			res = this.createListModelAndView(msg);
		}
		return res;
	}

	@RequestMapping("/startCollect")
	public ModelAndView startCollect(@RequestParam Integer builtId) {
		ModelAndView res;
		Built b = this.builtService.findOne(builtId);
		if (b == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.builtService.startToCollect(b);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createListModelAndView(msg);
			}

		return res;
	}

	@RequestMapping("/collect")
	public ModelAndView collect(@RequestParam Integer builtId) {
		ModelAndView res;
		Built b = this.builtService.findOne(builtId);
		if (b == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.builtService.collect(b);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createListModelAndView(msg);
			}

		return res;
	}

	@RequestMapping("/startRecruit")
	public ModelAndView startRecruit(@RequestParam Integer builtId) {
		ModelAndView res;

		Built b = this.builtService.findOne(builtId);
		if (b == null || !b.getKeybladeWielder().getUserAccount().equals(LoginService.getPrincipal()))
			res = new ModelAndView("redirect:list.do");
		else {
			res = new ModelAndView("built/recruit");
			RecruitedForm form = new RecruitedForm();
			form.setBuilt(b);
			res.addObject("recruitedForm", form);

		}

		return res;
	}

	@RequestMapping(value = "/startRecruit", params = "next", method = RequestMethod.POST)
	public ModelAndView nextRecruit(String wantRecruit, @Valid RecruitedForm recruitedForm, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = new ModelAndView("redirect:list.do");
		else {
			res = new ModelAndView("built/recruit");
			res.addObject("wantRecruit", wantRecruit);
			if (wantRecruit.equals("troop")) {
				Collection<Troop> troops = this.troopService.getTroopsAvailableFromRecruiterAndLvl(recruitedForm.getBuilt().getBuilding().getId(), recruitedForm.getBuilt().getLvl());
				res.addObject("troops", troops);
			} else if (wantRecruit.equals("ship")) {
				Collection<GummiShip> ships = this.gummiShipService.getGummiShipsAvailableFromRecruiterAndLvl(recruitedForm.getBuilt().getBuilding().getId(), recruitedForm.getBuilt().getLvl());
				res.addObject("ships", ships);
			}
			res.addObject("recruitedForm", recruitedForm);
		}

		return res;
	}
	@RequestMapping(value = "/startRecruit", params = "save", method = RequestMethod.POST)
	public ModelAndView saveRecruit(String wantRecruit, @Valid RecruitedForm recruitedForm, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors() || (!wantRecruit.equals("troop") && !wantRecruit.equals("ship")))
			res = this.createRecruitModelAndView(recruitedForm, wantRecruit, null);
		else
			try {
				if (wantRecruit.equals("troop")) {
					this.builtService.startToRecruitTroop(recruitedForm.getBuilt(), recruitedForm.getTroop());
					res = new ModelAndView("redirect:list.do");
				} else {
					this.builtService.startToRecruitGummiShip(recruitedForm.getBuilt(), recruitedForm.getGummiship());
					res = new ModelAndView("redirect:list.do");
				}
			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createRecruitModelAndView(recruitedForm, wantRecruit, msg);
			}

		return res;
	}

	@RequestMapping("/recruit")
	public ModelAndView recruit(@RequestParam Integer builtId) {
		ModelAndView res;
		Built built = this.builtService.findOne(builtId);
		Recruited recruited;

		if (built == null || !built.getKeybladeWielder().getUserAccount().equals(LoginService.getPrincipal()))
			res = new ModelAndView("redirect:list.do");
		else
			try {
				recruited = this.builtService.recruit(built);
				res = new ModelAndView("redirect:display.do?builtId=" + recruited.getStorageBuilding().getId());
			} catch (Throwable oops) {
				String msg = this.getErrorMessage(oops);
				res = this.createListModelAndView(msg);
			}

		return res;
	}
	protected ModelAndView createRecruitModelAndView(RecruitedForm form, String wantRecruit, String msg) {
		ModelAndView res;

		res = new ModelAndView("built/recruit");
		res.addObject("wantRecruit", wantRecruit);
		res.addObject("recruitedForm", form);
		res.addObject("message", msg);

		if (wantRecruit.equals("troop")) {
			Collection<Troop> troops = this.troopService.getTroopsAvailableFromRecruiterAndLvl(form.getBuilt().getBuilding().getId(), form.getBuilt().getLvl());
			res.addObject("troops", troops);
		} else if (wantRecruit.equals("ship")) {
			Collection<GummiShip> ships = this.gummiShipService.getGummiShipsAvailableFromRecruiterAndLvl(form.getBuilt().getBuilding().getId(), form.getBuilt().getLvl());
			res.addObject("ships", ships);
		}

		return res;
	}
	protected ModelAndView createListModelAndView(String msg) {
		ModelAndView res;
		Page<Built> builts;
		Pageable pageable;
		Integer page = 0;

		pageable = new PageRequest(page, 6);
		builts = this.builtService.getMyBuiltsPageable(pageable);

		res = new ModelAndView("built/list");

		res.addObject("builts", builts.getContent());
		res.addObject("page", page);
		res.addObject("requestURI", "built/list.do?page=");
		res.addObject("pageNum", builts.getTotalPages());

		res.addObject("message", msg);

		return res;
	}

	protected ModelAndView createEditModelAndView(BuiltForm form) {
		return this.createEditModelAndView(form, null);

	}
	protected ModelAndView createEditModelAndView(BuiltForm form, String msg) {
		ModelAndView res;
		Collection<Building> buildings = this.buildingService.getAvailableBuildings();

		res = new ModelAndView("built/edit");
		res.addObject("buildings", buildings);
		res.addObject("builtForm", form);
		res.addObject("message", msg);

		return res;

	}

}
