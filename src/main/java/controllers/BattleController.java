
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BattleService;
import services.GummiShipService;
import services.KeybladeWielderService;
import services.RecruitedService;
import services.TroopService;
import domain.Actor;
import domain.Battle;
import domain.GummiShip;
import domain.KeybladeWielder;
import domain.Recruited;
import domain.Troop;
import form.BattleForm;

@Controller
@RequestMapping("/battle")
public class BattleController extends AbstractController {

	@Autowired
	private BattleService			battleService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private TroopService			troopService;
	@Autowired
	private GummiShipService		gummiShipService;
	@Autowired
	private KeybladeWielderService	keybladeWielderService;
	@Autowired
	private RecruitedService		recruitedService;


	@RequestMapping("/recruited")
	public ModelAndView recruited(@RequestParam(required = true) final String nickname, Locale locale) {
		ModelAndView res;
		Collection<Troop> troops = this.troopService.findAll();
		Collection<GummiShip> gummiShips = this.gummiShipService.findAll();
		Collection<String> nombres = new ArrayList<String>();
		ArrayList<Integer> capacidadTropas = new ArrayList<Integer>();
		ArrayList<Integer> capacidadNaves = new ArrayList<Integer>();
		Collection<Recruited> attackRc = this.recruitedService.getAllRecruited(this.actorService.findByPrincipal().getId());
		if (nickname == "") {
			try {
				Actor actor = this.actorService.findByUserAccountUsername(nickname);
				Assert.notNull(actor, "error.message.notexist");
			} catch (Throwable oops) {
				res = new ModelAndView("redirect:listPlayers.do");
				res.addObject("message", this.showDetails(locale, this.getErrorMessage(oops)));
				return res;
			}
		}
		int index;
		int n;
		for (Troop a : troops) {
			capacidadTropas.add(0);
		}
		for (GummiShip a : gummiShips) {
			capacidadNaves.add(0);
		}

		for (Recruited ab : attackRc) {
			index = 0;
			if (ab.getTroop() != null) {
				for (Troop t : troops) {
					if (t.getName().equals(ab.getTroop().getName())) {
						n = capacidadTropas.get(index);
						capacidadTropas.remove(index);
						capacidadTropas.add(index, n + 1);
					}
					index++;
				}
			} else {
				for (GummiShip g : gummiShips) {
					if (g.getName().equals(ab.getGummiShip().getName())) {
						n = capacidadNaves.get(index);
						capacidadNaves.remove(index);
						capacidadNaves.add(index, n + 1);
					}
					index++;
				}
			}
		}
		for (Integer i : capacidadNaves) {
			capacidadTropas.add(i);
		}
		String nom;
		String capac = "";
		Integer numero = 0;
		for (final Troop t : troops) {
			nom = '"' + t.getName() + '"';
			nombres.add(nom);
			capac = capac + t.getName() + " : " + capacidadTropas.get(numero) + " ";
			numero++;
		}
		for (final GummiShip g : gummiShips) {
			nom = '"' + g.getName() + '"';
			nombres.add(nom);
			capac = capac + g.getName() + " : " + capacidadTropas.get(numero) + " ";
			numero++;
		}

		final BattleForm bf = new BattleForm();
		bf.setEnemy(nickname);
		final int numT = troops.size();
		final int numS = gummiShips.size();
		final int num = numT + numS;

		res = new ModelAndView("battle/recruited");
		res.addObject("troops", troops);
		res.addObject("gummiShips", gummiShips);
		res.addObject("num", num);
		res.addObject("nombres", nombres);
		res.addObject("enemy", nickname);
		res.addObject("capacidad", capac);
		res.addObject("battleForm", bf);

		return res;
	}
	@RequestMapping(value = "/recruited", params = "save")
	public ModelAndView save(@Valid final BattleForm battleForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(battleForm);
		} else
			try {
				Battle b = this.battleService.fight(battleForm);
				res = new ModelAndView("redirect:display.do?battleId=" + b.getId());
			} catch (final Throwable oops) {
				String msg = "error.message.commit";
				String a = oops.getMessage();
				String e = "message.error";
				if (a.contains(e))
					msg = a;
				res = this.createEditModelAndView(battleForm, msg);
			}

		return res;
	}
	@RequestMapping("/listBattlesAttack")
	public ModelAndView listBattlesAttack() {
		final ModelAndView res;
		final KeybladeWielder keyblader = (KeybladeWielder) this.actorService.findByPrincipal();
		final Collection<Battle> battles = this.battleService.myBattlesAttack(keyblader.getId());

		res = new ModelAndView("battle/listBattles");
		res.addObject("battles", battles);
		res.addObject("requestURI", "battle/listBattles.do");

		return res;
	}
	@RequestMapping("/listBattlesDefense")
	public ModelAndView listBattlesDefense() {
		final ModelAndView res;
		final KeybladeWielder keyblader = (KeybladeWielder) this.actorService.findByPrincipal();
		final Collection<Battle> battles = this.battleService.myBattlesDefense(keyblader.getId());

		res = new ModelAndView("battle/listBattles");
		res.addObject("battles", battles);
		res.addObject("requestURI", "battle/listBattles.do");

		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam(required = true) final Integer battleId) {
		final ModelAndView res;
		final Battle battle = this.battleService.findOne(battleId);

		res = new ModelAndView("battle/display");
		res.addObject("battle", battle);
		res.addObject("requestURI", "battle/display.do");

		return res;
	}
	@RequestMapping("/listPlayers")
	public ModelAndView list() {
		final ModelAndView res;
		final Collection<KeybladeWielder> players = this.keybladeWielderService.playersToAttackt();

		res = new ModelAndView("battle/listPlayers");
		res.addObject("players", players);
		res.addObject("requestURI", "faction/list.do");

		return res;
	}
	protected ModelAndView createEditModelAndView(final BattleForm battle) {
		return this.createEditModelAndView(battle, null);
	}

	protected ModelAndView createEditModelAndView(final BattleForm battle, final String messageCode) {
		ModelAndView res;
		Collection<Troop> troops = this.troopService.findAll();
		Collection<GummiShip> gummiShips = this.gummiShipService.findAll();
		Collection<String> nombres = new ArrayList<String>();
		ArrayList<Integer> capacidadTropas = new ArrayList<Integer>();
		ArrayList<Integer> capacidadNaves = new ArrayList<Integer>();
		Collection<Recruited> attackRc = this.recruitedService.getAllRecruited(this.actorService.findByPrincipal().getId());
		int index;
		int n;
		for (Troop a : troops) {
			capacidadTropas.add(0);
		}
		for (GummiShip a : gummiShips) {
			capacidadNaves.add(0);
		}

		for (Recruited ab : attackRc) {
			index = 0;
			if (ab.getTroop() != null) {
				for (Troop t : troops) {
					if (t.getName().equals(ab.getTroop().getName())) {
						n = capacidadTropas.get(index);
						capacidadTropas.remove(index);
						capacidadTropas.add(index, n + 1);
					}
					index++;
				}
			} else {
				for (GummiShip g : gummiShips) {
					if (g.getName().equals(ab.getGummiShip().getName())) {
						n = capacidadNaves.get(index);
						capacidadNaves.remove(index);
						capacidadNaves.add(index, n + 1);
					}
					index++;
				}
			}
		}
		for (Integer i : capacidadNaves) {
			capacidadTropas.add(i);
		}
		String nom;
		String capac = "";
		Integer numero = 0;
		for (final Troop t : troops) {
			nom = '"' + t.getName() + '"';
			nombres.add(nom);
			capac = capac + t.getName() + " : " + capacidadTropas.get(numero) + " ";
			numero++;
		}
		for (final GummiShip g : gummiShips) {
			nom = '"' + g.getName() + '"';
			nombres.add(nom);
			capac = capac + g.getName() + " : " + capacidadTropas.get(numero) + " ";
			numero++;
		}

		final int numT = troops.size();
		final int numS = gummiShips.size();
		final int num = numT + numS;

		res = new ModelAndView("battle/recruited");
		res.addObject("troops", troops);
		res.addObject("gummiShips", gummiShips);
		res.addObject("num", num);
		res.addObject("nombres", nombres);
		res.addObject("enemy", battle.getEnemy());
		res.addObject("capacidad", capac);
		res.addObject("battleForm", battle);
		res.addObject("message", messageCode);

		return res;
	}

}
