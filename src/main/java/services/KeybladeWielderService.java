
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.KeybladeWielderRepository;
import domain.Actor;
import domain.Coordinates;
import domain.Faction;
import domain.KeybladeWielder;
import domain.Materials;
import domain.Organization;

@Service
@Transactional
public class KeybladeWielderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private KeybladeWielderRepository	KeybladeWielderRepository;

	@Autowired
	private OrganizationService			organizationService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private FactionService				factionService;


	// CRUD methods

	public KeybladeWielder create() {
		KeybladeWielder KeybladeWielder;

		KeybladeWielder = new KeybladeWielder();

		return KeybladeWielder;
	}

	public KeybladeWielder save(final KeybladeWielder KeybladeWielder) {
		Assert.notNull(KeybladeWielder);

		KeybladeWielder saved;

		saved = this.KeybladeWielderRepository.save(KeybladeWielder);

		return saved;
	}

	public KeybladeWielder saveFromCreate(final Actor user, final String worldName, final String factionId) {
		KeybladeWielder result;

		Assert.isTrue(this.actorService.findByUserAccountUsername(user.getUserAccount().getUsername()) == null, "error.message.duplicatedUsername");
		Assert.isTrue(this.actorService.findByNickname(user.getNickname()) == null, "error.message.duplicatedNickname");
		Assert.isTrue(!worldName.isEmpty(), "error.message.worldName.empty");
		Assert.isTrue(this.findByWorldName(worldName) == null, "error.message.worldExist");
		Assert.isTrue(!factionId.isEmpty(), "error.message.validFaction");

		//Password
		Md5PasswordEncoder encoder;
		String hash;
		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(user.getUserAccount().getPassword(), null);

		user.getUserAccount().setPassword(hash);

		//UserAccount
		user.setConfirmMoment(new Date(System.currentTimeMillis() - 1000));
		user.setHasConfirmedTerms(true);

		//Actor to KW
		final KeybladeWielder kw = new KeybladeWielder();
		kw.setAvatar(user.getAvatar());
		kw.setConfirmMoment(user.getConfirmMoment());
		kw.setEmail(user.getEmail());
		kw.setHasConfirmedTerms(user.getHasConfirmedTerms());
		kw.setName(user.getName());
		kw.setNickname(user.getNickname());
		kw.setPhone(user.getPhone());
		kw.setSurname(user.getSurname());
		kw.setUserAccount(user.getUserAccount());
		kw.setVersion(user.getVersion());
		kw.setId(user.getId());

		//KW
		Faction faction;
		faction = this.factionService.findOne(Integer.parseInt(factionId));
		kw.setFaction(faction);

		kw.setWins(0);
		kw.setLoses(0);
		final Materials materials = new Materials();
		materials.setGummiCoal(0);
		materials.setMunny(0);
		materials.setMytrhil(0);
		kw.setMaterials(materials);
		kw.setLastConnection(new Date(System.currentTimeMillis() - 1000));
		final Coordinates coordinates = new Coordinates();

		Integer g = this.randomGalaxy(kw.getFaction());
		while (this.checkIfGalaxyHas10Worlds(g) >= 10)
			g = this.randomGalaxy(kw.getFaction());

		Integer x = new Random().nextInt(10);
		Integer y = new Random().nextInt(10);
		while (this.checkIfCoodinatesAreInUseinGalaxy(x, y, g) > 1) {
			x = new Random().nextInt(10);
			y = new Random().nextInt(10);
		}

		coordinates.setX(x);
		coordinates.setY(y);
		coordinates.setZ(g);

		kw.setWorldCoordinates(coordinates);
		kw.setWorldName(worldName);

		result = this.save(kw);
		return result;
	}
	public KeybladeWielder findOne(final int KeybladeWielderId) {
		Assert.notNull(KeybladeWielderId);

		KeybladeWielder KeybladeWielder;

		KeybladeWielder = this.KeybladeWielderRepository.findOne(KeybladeWielderId);

		return KeybladeWielder;
	}

	public Collection<KeybladeWielder> findAll() {
		Collection<KeybladeWielder> KeybladeWielders;

		KeybladeWielders = this.KeybladeWielderRepository.findAll();

		return KeybladeWielders;
	}
	
	public Page<KeybladeWielder> findAll(Pageable p) {
		Page<KeybladeWielder> KeybladeWielders;

		KeybladeWielders = this.KeybladeWielderRepository.findAll(p);

		return KeybladeWielders;
	}

	public void delete(final KeybladeWielder KeybladeWielder) {
		Assert.notNull(KeybladeWielder);

		this.KeybladeWielderRepository.delete(KeybladeWielder);
	}

	public Collection<KeybladeWielder> findMembersOfOrganization(final int organizationId) {
		final Collection<KeybladeWielder> members = this.KeybladeWielderRepository.findMembersOfOrganization(organizationId);
		return members;
	}

	public Boolean getTieneOrganizacion() {
		Boolean res = true;
		final KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		final Organization org = this.organizationService.findOrganizationByPlayer(actual.getId());
		if (org == null)
			res = false;
		return res;
	}

	public Integer checkIfGalaxyHas10Worlds(int g) {
		return this.KeybladeWielderRepository.checkIfGalaxyHas10Worlds(g);
	}

	public Integer checkIfCoodinatesAreInUseinGalaxy(int x, int y, int g) {
		return this.KeybladeWielderRepository.checkIfCoodinatesAreInUseinGalaxy(x, y, g);
	}

	public Integer randomGalaxy(Faction faction) {
		Integer g = (int) (Math.random() * Integer.MAX_VALUE / 3);

		switch (faction.getGalaxy()) {
		case 0:
			//cualquier galaxia
		case 1:
			//galaxias impares
			g += (g % 2 == 0 ? 1 : 0);
		case 2:
			//galaxias pares
			g += (g % 2 == 0 ? 0 : 1);
		case 3:
			//galaxias multiplo de 3
			g *= (g % 3 == 0 ? 1 : 3);
		}
		return g;
	}

	//Dashboard

	public KeybladeWielder findByWorldName(final String worldName) {
		return this.KeybladeWielderRepository.findByWorldName(worldName);
	}

	public Collection<Double> ratioOfUserPerFaction() {
		return this.KeybladeWielderRepository.ratioOfUserPerFaction();
	}
	public Collection<String> getTopWinsPlayers() {
		return this.KeybladeWielderRepository.getTopWinsPlayers();
	}

	public Collection<String> getTopWinRatioPlayers() {
		return this.KeybladeWielderRepository.getTopWinRatioPlayers();
	}

	public Collection<String> getTopMunnyPlayers() {
		return this.KeybladeWielderRepository.getTopMunnyPlayers();
	}

	public Collection<String> getTopMytrhilPlayers() {
		return this.KeybladeWielderRepository.getTopMythrilPlayers();
	}

	public Double avgOfWinRatio() {
		return this.KeybladeWielderRepository.avgOfWinRatio();
	}
	public Collection<KeybladeWielder> playersToAttackt() {
		final KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		final String faction = player.getFaction().getName();
		return this.KeybladeWielderRepository.playersToAttackt(faction);
	}

}
