
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShieldRepository;
import domain.Item;
import domain.ItemType;
import domain.KeybladeWielder;
import domain.Shield;

@Service
@Transactional
public class ShieldService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShieldRepository	ShieldRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ItemService			itemService;


	// CRUD methods

	public Shield create() {
		Shield Shield;

		Shield = new Shield();

		return Shield;
	}

	public Shield save(final Item item) {
		Assert.notNull(item);
		Assert.isTrue(this.itemService.myItems(this.actorService.findByPrincipal().getId()).contains(item));
		Assert.isTrue(item.getType().equals(ItemType.SHIELD));

		final Shield res = this.create();
		res.setName(item.getName());
		res.setDate(new Date(System.currentTimeMillis() - 1000));
		res.setDuration(item.getDuration());
		Shield saved;

		KeybladeWielder keyBlader = (KeybladeWielder) this.actorService.findByPrincipal();
		if (keyBlader.getShield() != null) {
			this.delete(keyBlader.getShield());
		}
		saved = this.ShieldRepository.save(res);
		keyBlader.setShield(saved);
		this.actorService.save(keyBlader);
		return saved;
	}

	public Shield saveForAttack(KeybladeWielder keyblader) {
		Assert.notNull(keyblader);
		//Assert.isTrue(this.itemService.myItems(this.actorService.findByPrincipal().getId()).contains(item));
		final Shield res = this.create();
		res.setName("Traversed Town Area");
		res.setDate(new Date(System.currentTimeMillis() - 1000));
		res.setDuration(60);
		Shield saved;

		//KeybladeWielder keyBlader = (KeybladeWielder) this.actorService.findByPrincipal();

		saved = this.ShieldRepository.save(res);
		keyblader.setShield(saved);
		this.actorService.save(keyblader);
		return saved;
	}

	public Shield findOne(final int ShieldId) {
		Assert.notNull(ShieldId);

		Shield Shield;

		Shield = this.ShieldRepository.findOne(ShieldId);

		return Shield;
	}

	public Collection<Shield> findAll() {
		Collection<Shield> Shields;

		Shields = this.ShieldRepository.findAll();

		return Shields;
	}

	public void delete(final Shield Shield) {
		Assert.notNull(Shield);
		KeybladeWielder keyW = (KeybladeWielder) this.actorService.findByPrincipal();
		keyW.setShield(null);
		this.actorService.save(keyW);
		this.ShieldRepository.delete(Shield);
	}

	public void deleteDefender(final Shield Shield, KeybladeWielder defensor) {
		Assert.notNull(Shield);
		defensor.setShield(null);
		this.actorService.save(defensor);
		this.ShieldRepository.delete(Shield);
	}

	public void flush() {
		this.ShieldRepository.flush();
	}

}
