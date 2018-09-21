
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecruitedRepository;
import domain.Built;
import domain.Recruited;

@Service
@Transactional
public class RecruitedService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecruitedRepository	RecruitedRepository;

	@Autowired
	private BuiltService		builtService;


	// CRUD methods

	public Recruited create(final Built built) {
		Recruited recruited;
		recruited = new Recruited();

		if (built.getTroop() != null)
			recruited.setTroop(built.getTroop());
		else
			recruited.setGummiShip(built.getGummiShip());

		return recruited;
	}

	public Recruited save(final Built built) {
		List<Built> warehouses;
		final Recruited recruited = this.create(built);

		Assert.notNull(built);
		Assert.isTrue(!(built.getGummiShip() == null && built.getTroop() == null));
		Assert.isTrue(!(built.getGummiShip() != null && built.getTroop() != null));

		//Voy a reclutar una tropa
		if (built.getTroop() != null) {
			warehouses = new ArrayList<Built>(this.builtService.getMyFreeWarehousesTroop());

			Assert.isTrue(warehouses != null && warehouses.size() > 0, "error.message.recruited.warehouses");

			recruited.setStorageBuilding(warehouses.get(0));

		} else { //Voy a reclutar una nave

			warehouses = new ArrayList<Built>(this.builtService.getMyFreeWarehousesGummi());

			Assert.isTrue(warehouses != null && warehouses.size() > 0, "error.message.recruited.warehouses");

			recruited.setStorageBuilding(warehouses.get(0));
		}

		Recruited saved;
		saved = this.RecruitedRepository.save(recruited);

		return saved;
	}
	public Recruited findOne(final int RecruitedId) {
		Assert.notNull(RecruitedId);

		Recruited Recruited;

		Recruited = this.RecruitedRepository.findOne(RecruitedId);

		return Recruited;
	}

	public Collection<Recruited> findAll() {
		Collection<Recruited> Recruiteds;

		Recruiteds = this.RecruitedRepository.findAll();

		return Recruiteds;
	}

	public void delete(final Recruited Recruited) {
		Assert.notNull(Recruited);

		this.RecruitedRepository.delete(Recruited);
	}

	// OTHER METHODS

	public Collection<Recruited> getMyStoragedRecruitedTroops(final Integer builtId) {
		return this.RecruitedRepository.getMyStoragedRecruitedTroops(builtId);
	}

	public Collection<Recruited> getMyStoragedRecruitedGummiShip(final Integer builtId) {
		return this.RecruitedRepository.getMyStoragedRecruitedGummiShip(builtId);
	}

	public Collection<Recruited> getMyRecruited(final Integer builtId) {
		return this.RecruitedRepository.getMyRecruited(builtId);
	}

	public Collection<Recruited> getAllRecruited(final int actorId) {
		return this.RecruitedRepository.getAllRecruited(actorId);
	}

	public Collection<Recruited> findAllRecruitedOfTroop(int id) {
		return this.RecruitedRepository.findAllRecruitedOfTroop(id);
	}

}
