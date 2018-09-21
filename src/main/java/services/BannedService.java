
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannedRepository;
import security.UserAccount;
import domain.Actor;
import domain.Banned;

@Service
@Transactional
public class BannedService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BannedRepository	bannedRepository;

	@Autowired
	private ActorService		actorService;


	// CRUD methods

	public Banned create(Actor actor) {
		Banned banned;
		Assert.notNull(actor);

		banned = new Banned();
		banned.setActor(actor);
		banned.setIsValid(true);
		banned.setBanDate(new Date(System.currentTimeMillis() - 1000));

		Assert.isTrue(this.findBansByActor(banned.getActor().getId()) == 0, "error.message.alreadyBanned");
		Assert.isTrue(!banned.getActor().getUserAccount().isAuthority("ADMIN"), "error.message.ban.admin");

		return banned;
	}

	public Banned unban(Banned banned) {
		Assert.notNull(banned);

		banned.setIsValid(false);

		Banned saved;
		Actor actor;
		UserAccount ua;

		saved = this.bannedRepository.save(banned);
		actor = this.actorService.findOne(saved.getActor().getId());
		ua = actor.getUserAccount();
		ua.setEnabled(!saved.getIsValid());
		actor.setUserAccount(ua);
		this.actorService.save(actor);

		return saved;
	}

	public Banned save(Banned banned) {
		Assert.notNull(banned);
		Assert.isTrue(!banned.getActor().getUserAccount().isAuthority("ADMIN"), "error.message.ban.admin");
		Assert.isTrue(!banned.getActor().getUserAccount().isAuthority("GM"), "error.message.ban.admin");
		Assert.isTrue(!banned.getActor().getUserAccount().isAuthority("MANAGER"), "error.message.ban.admin");
		if (banned.getId() == 0)
			Assert.isTrue(this.findBansByActor(banned.getActor().getId()) == 0, "error.message.alreadyBanned");

		Banned saved;
		Actor actor;
		UserAccount ua;

		banned.setIsValid(true);
		banned.setBanDate(new Date(System.currentTimeMillis() - 1000));
		saved = this.bannedRepository.save(banned);
		actor = this.actorService.findOne(saved.getActor().getId());
		ua = actor.getUserAccount();
		ua.setEnabled(!saved.getIsValid());
		actor.setUserAccount(ua);
		this.actorService.save(actor);

		return saved;
	}
	public Banned findOne(int bannedId) {
		Assert.notNull(bannedId);

		Banned banned;

		banned = this.bannedRepository.findOne(bannedId);

		return banned;
	}

	public Collection<Banned> findAll() {
		Collection<Banned> banneds;

		banneds = this.bannedRepository.findAll();

		return banneds;
	}

	public void delete(Banned banned) {
		Assert.notNull(banned);

		this.bannedRepository.delete(banned);
	}

	public Integer findBansByActor(int actorId) {
		return this.bannedRepository.findBansByActor(actorId);
	}

	public Banned findToUnbanByActor(int actorId) {
		return this.bannedRepository.findToUnbanByActor(actorId);
	}

	public Collection<Banned> findAllBannedUsers() {
		return this.bannedRepository.findAllBannedUsers();
	}

	public Page<Banned> findAllBannedUsers(Pageable pageable) {
		return this.bannedRepository.findAllBannedUsers(pageable);
	}

}
