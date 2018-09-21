
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RecruiterRepository;
import security.LoginService;
import domain.ContentManager;
import domain.GummiShip;
import domain.Recruiter;
import domain.Troop;

@Service
@Transactional
public class RecruiterService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecruiterRepository	RecruiterRepository;
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private TroopService		troopService;
	@Autowired
	private GummiShipService	gummiShipService;


	// CRUD methods

	public Recruiter create() {
		Recruiter recruiter;

		recruiter = new Recruiter();
		recruiter.setGummiShips(new ArrayList<GummiShip>());
		recruiter.setTroops(new ArrayList<Troop>());
		recruiter.setContentManager((ContentManager) this.actorService.findByPrincipal());
		recruiter.setIsFinal(false);

		return recruiter;
	}

	public Recruiter save(Recruiter Recruiter) {
		Assert.notNull(Recruiter);
		Assert.isTrue(Recruiter.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");
		Assert.isTrue(!Recruiter.getIsFinal() || (Recruiter.getTroops() != null && Recruiter.getTroops().size() > 0) || (Recruiter.getGummiShips() != null && Recruiter.getGummiShips().size() > 0), "error.message.recruiter.empty");

		Recruiter saved;

		saved = this.RecruiterRepository.save(Recruiter);

		return saved;
	}
	public Recruiter findOne(int RecruiterId) {
		Assert.notNull(RecruiterId);

		Recruiter Recruiter;

		Recruiter = this.RecruiterRepository.findOne(RecruiterId);

		return Recruiter;
	}

	public Collection<Recruiter> findAll() {
		Collection<Recruiter> Recruiters;

		Recruiters = this.RecruiterRepository.findAll();

		return Recruiters;
	}

	public void addTroop(Recruiter r, Troop t) {
		Assert.isTrue(r.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");
		Assert.isTrue(r.equals(t.getRecruiter()), "error.message.recruiter.addTroop");
		Assert.isTrue(t.getRecruiterRequiredLvl() <= r.getMaxLvl(), "error.message.recruiter.highLevel");

		t = this.troopService.save(t);

		Collection<Troop> troops = r.getTroops();
		troops.add(t);

		this.RecruiterRepository.save(r);

	}

	public void addGummiShip(Recruiter r, GummiShip gs) {
		Assert.isTrue(r.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");
		Assert.isTrue(r.equals(gs.getRecruiter()), "error.message.recruiter.addGummiShip");
		Assert.isTrue(gs.getRecruiterRequiredLvl() <= r.getMaxLvl(), "error.message.recruiter.highLevel");
		gs = this.gummiShipService.save(gs);

		Collection<GummiShip> gummies = r.getGummiShips();
		gummies.add(gs);

		this.RecruiterRepository.save(r);

	}

	public void removeTroop(Troop t) {
		Recruiter r = t.getRecruiter();
		Collection<Troop> troops = r.getTroops();
		troops.remove(t);

		r.setTroops(troops);

		this.RecruiterRepository.save(r);
	}

	public Recruiter reconstruct(Recruiter recruiter, BindingResult binding) {
		Recruiter original = this.findOne(recruiter.getId());

		if (recruiter.getId() == 0) {
			recruiter.setGummiShips(new ArrayList<GummiShip>());
			recruiter.setTroops(new ArrayList<Troop>());
			recruiter.setContentManager((ContentManager) this.actorService.findByPrincipal());
			recruiter.setIsFinal(false);

		} else if (!original.getIsFinal()) {
			recruiter.setGummiShips(original.getGummiShips());
			recruiter.setTroops(original.getTroops());
			recruiter.setContentManager(original.getContentManager());
			if (recruiter.getIsFinal() == null)
				recruiter.setIsFinal(false);

		} else
			recruiter = original;

		recruiter.setPhoto("./images/buildings/recruiter.png");
		this.validator.validate(recruiter, binding);

		return recruiter;
	}

	public void flush() {
		this.RecruiterRepository.flush();
	}
}
