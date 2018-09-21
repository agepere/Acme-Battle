
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

	// Devuelve los purchases dado un item que no estan activos
	@Query("select p from Purchase p where p.item.id =?1 and (CURRENT_TIMESTAMP < p.expirationDate) and p.activationDate = null")
	Collection<Purchase> noActivePurchases(int itemId);

	// Lista de Purchases que no estan activos y no estan caducados
	@Query("select p from Purchase p where p.player.id =?1 and (CURRENT_TIMESTAMP < p.expirationDate) and p.activationDate = null")
	Collection<Purchase> noActivePurchasesByPlayer(int playerId);

	// Lista de Purchases que no estan activos y que SI estan caducados
	@Query("select p from Purchase p where p.player.id =?1 and (CURRENT_TIMESTAMP > p.expirationDate) and p.activationDate = null")
	Collection<Purchase> noActiveExpiredPurchasesByPlayer(int playerId);

	// Los purchases que estan activados y en uso
	@Query("select p from Purchase p where p.player.id =?1 and p.activationDate != null and (CURRENT_TIMESTAMP < p.activationDate)")
	Collection<Purchase> activePurchasesByPlayer(int playerId);

	// Los purchases que estan activados pero se ha acabado su duracion
	@Query("select p from Purchase p where p.player.id =?1 and p.activationDate != null and (CURRENT_TIMESTAMP > p.activationDate)")
	Collection<Purchase> activeExpiredPurchasesByPlayer(int playerId);

}
