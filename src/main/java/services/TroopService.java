
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TroopRepository;
import domain.Built;
import domain.Materials;
import domain.Prize;
import domain.Recruited;
import domain.Recruiter;
import domain.Troop;

@Service
@Transactional
public class TroopService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TroopRepository		TroopRepository;

	@Autowired
	private BuiltService		builtService;

	@Autowired
	private RecruitedService	recruitedService;
	@Autowired
	private RecruiterService	recruiterService;

	@Autowired
	private PrizeService		prizeService;

	@Autowired
	Validator					validator;


	// CRUD methods

	public Troop create(Recruiter recruiter) {
		Troop troop;
		troop = new Troop();
		Materials cost = new Materials();

		troop.setAttack(0);
		troop.setDefense(0);
		troop.setName("");
		troop.setTimeToRecruit(0);
		troop.setRecruiter(recruiter);
		troop.setRecruiterRequiredLvl(0);

		cost.setGummiCoal(0);
		cost.setMunny(0);
		cost.setMytrhil(0);

		troop.setCost(cost);

		return troop;
	}

	public Troop save(Troop troop) {

		Troop saved = this.TroopRepository.save(troop);

		return saved;
	}

	public Troop findOne(int TroopId) {
		Assert.notNull(TroopId);

		Troop Troop;

		Troop = this.TroopRepository.findOne(TroopId);

		return Troop;
	}

	public Collection<Troop> findAll() {
		Collection<Troop> Troops;

		Troops = this.TroopRepository.findAll();

		return Troops;
	}

	public void delete(Troop Troop) {
		Assert.notNull(Troop);

		this.TroopRepository.delete(Troop);
	}

	//Other methods

	public Collection<Troop> getTroopsFromRecruiter(Integer recruiterId) {
		return this.TroopRepository.getTroopsFromRecruiter(recruiterId);
	}

	public Collection<Troop> getStoragedTroops(Integer builtId) {
		return this.TroopRepository.getStoragedTroops(builtId);
	}

	public Collection<Troop> getTroopsAvailableForBuilt(Integer builtLevel) {
		return this.TroopRepository.getTroopsAvailableForBuilt(builtLevel);
	}

	public Collection<Troop> getTroopsAvailableFromRecruiterAndLvl(Integer recruiterId, Integer lvl) {
		return this.TroopRepository.getTroopsAvailableFromRecruiterAndLvl(recruiterId, lvl);
	}

	// ------ RECONSTRUCT -----
	public Troop reconstruct(Troop t, BindingResult binding) {
		Troop result;
		Troop original = this.TroopRepository.findOne(t.getId());

		if (t.getId() == 0)
			result = t;
		else {
			//Aquí van los atributos hidden
			result = t;
			result.setRecruiter(original.getRecruiter());

		}
		this.validator.validate(result, binding);

		return result;

	}

	public Troop getTroopsFromRecruiter(String name) {
		return this.TroopRepository.getTroopByName(name);
	}

	public Collection<String> getTroopsNames() {
		return this.TroopRepository.getTroopsNames();
	}

	public Troop getTroopByName(String name) {
		return this.TroopRepository.getTroopByName(name);
	}

	public void deleteCompleto(Troop t) {
		Assert.isTrue((t.getRecruiter().getIsFinal() && t.getRecruiter().getTroops().size() > 1) || !t.getRecruiter().getIsFinal(), "error.message.restrictionsDeleteTroop");

		if (t.getRecruiter().getIsFinal() && t.getRecruiter().getTroops().size() > 1) {
			Collection<Recruited> recs = this.recruitedService.findAllRecruitedOfTroop(t.getId());
			Materials toAdd = new Materials();
			toAdd.setMunny((int) (t.getCost().getMunny() * 1.1));
			toAdd.setMytrhil((int) (t.getCost().getMytrhil() * 1.1));
			toAdd.setGummiCoal((int) (t.getCost().getGummiCoal() * 1.1));

			Collection<Built> recsBults = this.builtService.findAllBuiltWithTroop(t.getId());
			for (Built b : recsBults) {
				b.setTroop(null);
				b.setActivationDate(null);
				this.builtService.saveForTroopDeleting(b);
			}

			for (Recruited r : recs) {
				Prize p = this.prizeService.create();
				p.setKeybladeWielder(r.getStorageBuilding().getKeybladeWielder());
				p.setDescription("Sorry, we have deleted " + r.getTroop().getName() + "from the game. We have given you a 110% of the cost that it had.");
				p.setMaterials(toAdd);
				this.prizeService.save(p);
				this.recruitedService.delete(r);
			}

		}
		this.recruiterService.removeTroop(t);
		this.TroopRepository.delete(t);
	}

}
