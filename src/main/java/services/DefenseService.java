
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.DefenseRepository;
import security.LoginService;
import domain.ContentManager;
import domain.Defense;

@Service
@Transactional
public class DefenseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private DefenseRepository	DefenseRepository;
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;


	// CRUD methods

	public Defense create() {
		Defense Defense;

		Defense = new Defense();
		Defense.setContentManager((ContentManager) this.actorService.findByPrincipal());
		Defense.setIsFinal(false);

		return Defense;
	}
	public Defense save(Defense Defense) {
		Assert.notNull(Defense);
		Assert.isTrue(Defense.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");

		Defense saved;

		saved = this.DefenseRepository.save(Defense);

		return saved;
	}

	public Defense findOne(int DefenseId) {
		Assert.notNull(DefenseId);

		Defense Defense;

		Defense = this.DefenseRepository.findOne(DefenseId);

		return Defense;
	}

	public Collection<Defense> findAll() {
		Collection<Defense> Defenses;

		Defenses = this.DefenseRepository.findAll();

		return Defenses;
	}

	public void delete(Defense Defense) {
		Assert.notNull(Defense);

		this.DefenseRepository.delete(Defense);
	}

	public Defense reconstruct(Defense defense, BindingResult binding) {
		Defense original = this.findOne(defense.getId());

		if (defense.getId() == 0) {
			defense.setContentManager((ContentManager) this.actorService.findByPrincipal());
			defense.setIsFinal(false);

		} else if (!original.getIsFinal()) {
			defense.setContentManager(original.getContentManager());
			if (defense.getIsFinal() == null)
				defense.setIsFinal(false);
		}

		else
			defense = original;

		defense.setPhoto("./images/buildings/defense.png");
		this.validator.validate(defense, binding);

		return defense;
	}

	public void flush() {
		this.DefenseRepository.flush();
	}
}
