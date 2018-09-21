
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PurchaseRepository;
import domain.ItemType;
import domain.Purchase;

@Service
@Transactional
public class PurchaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PurchaseRepository	purchaseRepository;

	@Autowired
	private ShieldService		shieldService;


	// CRUD methods

	public Purchase create() {
		Purchase purchase;

		purchase = new Purchase();

		return purchase;
	}

	public Purchase save(Purchase purchase) {
		Assert.notNull(purchase);

		Purchase saved;

		saved = this.purchaseRepository.save(purchase);

		return saved;
	}

	public Purchase findOne(int purchaseId) {
		Assert.notNull(purchaseId);

		Purchase purchase;

		purchase = this.purchaseRepository.findOne(purchaseId);

		return purchase;
	}

	public Collection<Purchase> findAll() {
		Collection<Purchase> purchases;

		purchases = this.purchaseRepository.findAll();

		return purchases;
	}

	public void delete(Purchase purchase) {
		Assert.notNull(purchase);

		this.purchaseRepository.delete(purchase);
	}

	// OTROS METODOS -----------

	// Activar un item
	public void activeItem(Purchase purchase) {
		if (purchase.getItem().getType().equals(ItemType.SHIELD)) {
			this.shieldService.save(purchase.getItem());
		}
		purchase.setActivationDate(new Date(System.currentTimeMillis() + purchase.getItem().getDuration() * 60 * 1000));
		this.save(purchase);
	}
	// Lista de purchases sobre un item concreto que no estan activados
	public Collection<Purchase> noActivePurchases(int itemId) {
		return this.purchaseRepository.noActivePurchases(itemId);
	}

	public Collection<Purchase> noActivePurchasesByPlayer(int playerId) {
		Collection<Purchase> expiredPurchases = this.purchaseRepository.noActiveExpiredPurchasesByPlayer(playerId);
		for (Purchase p : expiredPurchases) {	// Eliminamos los Purchases que han caducado
			this.purchaseRepository.delete(p);
		}

		return this.purchaseRepository.noActivePurchasesByPlayer(playerId);
	}

	// Lista de items que estan activos
	public Collection<Purchase> activePurchasesByPlayer(int playerId) {
		return this.purchaseRepository.activePurchasesByPlayer(playerId);
	}

	// Lista de items que estan activos pero han expirados (para eliminar)
	public void deleteActiveExpiredPurchasesByPlayer(int playerId) {
		Collection<Purchase> activeExpiredPurchases = this.purchaseRepository.activeExpiredPurchasesByPlayer(playerId);
		for (Purchase p : activeExpiredPurchases) {
			this.delete(p);
		}
	}

}
