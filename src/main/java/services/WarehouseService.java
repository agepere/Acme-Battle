
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.WarehouseRepository;
import security.LoginService;
import domain.ContentManager;
import domain.Warehouse;

@Service
@Transactional
public class WarehouseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private WarehouseRepository	WarehouseRepository;
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;


	// CRUD methods

	public Warehouse create() {
		Warehouse Warehouse;

		Warehouse = new Warehouse();
		Warehouse.setContentManager((ContentManager) this.actorService.findByPrincipal());
		Warehouse.setIsFinal(false);

		return Warehouse;
	}
	public Warehouse save(Warehouse Warehouse) {
		Assert.notNull(Warehouse);
		Assert.isTrue(Warehouse.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");
		Assert.notNull(Warehouse.getMaterialsSlots().getGummiCoal(), "error.message.warehouse.nullMaterials");
		Assert.notNull(Warehouse.getMaterialsSlots().getMunny(), "error.message.warehouse.nullMaterials");
		Assert.notNull(Warehouse.getMaterialsSlots().getMytrhil(), "error.message.warehouse.nullMaterials");

		Warehouse saved;

		saved = this.WarehouseRepository.save(Warehouse);

		return saved;
	}
	public Warehouse findOne(int WarehouseId) {
		Assert.notNull(WarehouseId);

		Warehouse Warehouse;

		Warehouse = this.WarehouseRepository.findOne(WarehouseId);

		return Warehouse;
	}

	public Collection<Warehouse> findAll() {
		Collection<Warehouse> Warehouses;

		Warehouses = this.WarehouseRepository.findAll();

		return Warehouses;
	}

	public void delete(Warehouse Warehouse) {
		Assert.notNull(Warehouse);

		this.WarehouseRepository.delete(Warehouse);
	}

	public Warehouse reconstruct(Warehouse w, BindingResult binding) {
		Warehouse original = this.findOne(w.getId());

		if (w.getId() == 0) {
			w.setContentManager((ContentManager) this.actorService.findByPrincipal());
			w.setIsFinal(false);

		} else if (!original.getIsFinal()) {
			w.setContentManager(original.getContentManager());
			if (w.getIsFinal() == null)
				w.setIsFinal(false);
		}

		else
			w = original;

		w.setPhoto("./images/buildings/warehouse.png");
		this.validator.validate(w, binding);

		return w;
	}

	public Collection<String> getWarehouseNames() {
		return this.WarehouseRepository.getWarehouseNames();
	}

	public void flush() {
		this.WarehouseRepository.flush();
	}
}
