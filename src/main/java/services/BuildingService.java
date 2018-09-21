
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BuildingRepository;
import security.LoginService;
import domain.Building;
import domain.ContentManager;

@Service
@Transactional
public class BuildingService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BuildingRepository	BuildingRepository;
	@Autowired
	private ActorService		actorService;


	// CRUD methods

	public Building create() {
		Building building;

		building = new Building();
		building.setContentManager((ContentManager) this.actorService.findByPrincipal());
		building.setIsFinal(false);

		return building;
	}
	public Building save(Building building) {
		Assert.notNull(building);
		Assert.isTrue(building.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");

		Building saved;

		saved = this.BuildingRepository.save(building);

		return saved;
	}

	public Building findOne(int BuildingId) {
		Assert.notNull(BuildingId);

		Building Building;

		Building = this.BuildingRepository.findOne(BuildingId);

		return Building;
	}

	public Collection<Building> findAll() {
		Collection<Building> Buildings;

		Buildings = this.BuildingRepository.findAll();

		return Buildings;
	}

	public void delete(Building building) {
		Assert.notNull(building);
		Assert.isTrue(building.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");
		Assert.isTrue(!building.getIsFinal(), "error.message.building.");

		this.BuildingRepository.delete(building);
	}

	//other methods

	public Collection<Building> getMyCreatedBuildings() {
		return this.BuildingRepository.getMyCreatedBuildings(this.actorService.findByPrincipal().getId());
	}

	public Page<Building> getMyCreatedBuildingsPaginated(Pageable p) {
		return this.BuildingRepository.getMyCreatedBuildingsPaginated(this.actorService.findByPrincipal().getId(), p);
	}

	public Collection<Building> getAvailableBuildings() {
		return this.BuildingRepository.getAvailableBuildings();
	}

	public Page<Building> getAvailableBuildingsPaginated(Pageable p) {
		return this.BuildingRepository.getAvailableBuildingsPaginated(p);
	}

	public Collection<String> getAvailableBuildingsName() {
		return this.BuildingRepository.getAvailableBuildingsName();
	}

	public Building getBuildingByName(String name) {
		return this.BuildingRepository.getBuildingByName(name);
	}
}
