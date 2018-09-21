
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

	@Query("select b from Building b where b.contentManager.id=?1")
	Collection<Building> getMyCreatedBuildings(Integer cmId);

	@Query("select b from Building b where b.contentManager.id=?1")
	Page<Building> getMyCreatedBuildingsPaginated(Integer cmId, Pageable p);

	@Query("select b from Building b where b.isFinal=1")
	Collection<Building> getAvailableBuildings();

	@Query("select b from Building b where b.isFinal=1")
	Page<Building> getAvailableBuildingsPaginated(Pageable p);

	@Query("select b.name from Building b where b.isFinal=1")
	Collection<String> getAvailableBuildingsName();

	@Query("select b from Building b where b.isFinal=1 and b.name = ?1")
	Building getBuildingByName(String name);

}
