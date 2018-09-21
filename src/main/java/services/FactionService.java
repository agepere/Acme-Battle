
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FactionRepository;
import domain.Actor;
import domain.Faction;

@Service
@Transactional
public class FactionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FactionRepository	FactionRepository;

	@Autowired
	private ActorService		actorService;


	// CRUD methods

	public Faction create() {
		Faction faction;

		faction = new Faction();
		faction.setExtraAttack(0.0);
		faction.setExtraDefense(0.0);
		faction.setExtraResources(0.0);
		faction.setGalaxy(0);

		return faction;
	}

	public Faction save(Faction Faction) {
		Assert.notNull(Faction);

		Faction saved;
		Actor contentManager = this.actorService.findByPrincipal();
		Assert.isTrue(contentManager.getUserAccount().isAuthority("MANAGER"));

		saved = this.FactionRepository.save(Faction);

		return saved;
	}

	public Faction findOne(Integer FactionId) {
		Assert.notNull(FactionId, "error.message.validFaction");

		Faction Faction;

		Faction = this.FactionRepository.findOne(FactionId);

		Assert.notNull(Faction, "error.message.validFaction");

		return Faction;
	}

	public Collection<Faction> findAll() {
		Collection<Faction> Factions;

		Factions = this.FactionRepository.findAll();

		return Factions;
	}

	public void delete(Faction Faction) {
		Assert.notNull(Faction);

		this.FactionRepository.delete(Faction);
	}

	public void flush() {
		this.FactionRepository.flush();
	}

}
