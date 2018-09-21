
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PrizeRepository;
import domain.KeybladeWielder;
import domain.Materials;
import domain.Prize;

@Service
@Transactional
public class PrizeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PrizeRepository			PrizeRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private KeybladeWielderService	keybladeWielderService;
	@Autowired
	private BuiltService			builtService;
	@Autowired
	private ConfigurationService	configurationService;


	// CRUD methods

	public Prize create() {
		Prize prize;

		prize = new Prize();
		Long days = (long) 2592000;
		Date expiration = new Date(System.currentTimeMillis() + days * 1000);
		prize.setDate(expiration);

		return prize;
	}

	public Prize save(Prize prize) {
		Assert.notNull(prize);

		Prize saved;
		Double extra = prize.getKeybladeWielder().getFaction().getExtraResources();
		if (extra != null && extra > 0) {
			Materials oldMaterials = prize.getMaterials();
			Materials newMaterials = oldMaterials.increase(extra);

			prize.setMaterials(newMaterials);
		}

		saved = this.PrizeRepository.save(prize);

		return saved;
	}

	public Prize findOne(int PrizeId) {
		Assert.notNull(PrizeId);

		Prize Prize;

		Prize = this.PrizeRepository.findOne(PrizeId);

		return Prize;
	}

	public Collection<Prize> findAll() {
		Collection<Prize> Prizes;

		Prizes = this.PrizeRepository.findAll();

		return Prizes;
	}

	public void delete(Prize Prize) {
		Assert.notNull(Prize);

		this.PrizeRepository.delete(Prize);
	}

	public void open(Prize prize) {
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		Assert.isTrue(prize.getKeybladeWielder().equals(player), "error.message.owner");

		if (prize.getDate().before(new Date(System.currentTimeMillis())))
			this.PrizeRepository.delete(prize);
		else {

			Materials max = this.builtService.maxMaterials();
			Materials old = player.getMaterials();
			Materials news = old.add(prize.getMaterials());
			Materials sinExceso = news.removeExcess(max);

			player.setMaterials(sinExceso);
			this.keybladeWielderService.save(player);

			this.PrizeRepository.delete(prize);
		}
	}

	public void createDailyPrizeForPrincipal() {
		Prize p = this.create();

		p.setKeybladeWielder((KeybladeWielder) this.actorService.findByPrincipal());
		p.setMaterials(this.configurationService.getConfiguration().getDailyMaterials());
		p.setDescription("prize.daily.defaultDescription");

		this.PrizeRepository.save(p);

	}
	// Other methods

	public Collection<Prize> getMyPrizes() {
		Integer playerId = this.actorService.findByPrincipal().getId();

		Collection<Prize> trash = this.PrizeRepository.getTrashPrizeFromKeybladeWielder(playerId);

		for (Prize p : trash)
			this.PrizeRepository.delete(p.getId());

		return this.PrizeRepository.getPrizeFromKeybladeWielder(playerId);

	}
	
	public Page<Prize> getMyPrizesPageable(Pageable page) {
		Integer playerId = this.actorService.findByPrincipal().getId();

		Collection<Prize> trash = this.PrizeRepository.getTrashPrizeFromKeybladeWielder(playerId);

		for (Prize p : trash)
			this.PrizeRepository.delete(p.getId());

		return this.PrizeRepository.getPrizeFromKeybladeWielderPageable(playerId, page);

	}

	// Method to send prizes from prompt 
	// Same as open() but without restrictions
	public void sendPrize(Prize prize) {
		KeybladeWielder player;

		player = prize.getKeybladeWielder();

		Materials max = this.builtService.maxMaterials();
		Materials old = player.getMaterials();
		Materials news = old.add(prize.getMaterials());
		Materials sinExceso = news.removeExcess(max);

		player.setMaterials(sinExceso);
		this.keybladeWielderService.save(player);

		this.PrizeRepository.delete(prize);
	}

	public void flush() {
		this.PrizeRepository.flush();
	}
}
