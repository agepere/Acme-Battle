
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Integer> {

	@Query("select r from Requirement r where r.mainBuilding.id=?1")
	Collection<Requirement> getRequirementsForABuilt(Integer buildingId);

	@Query("select r from Requirement r where r.mainBuilding.id=?1")
	Page<Requirement> getRequirementsForABuiltPaginated(Integer buildingId, Pageable pageable);

	@Query("select count(b) from Built b where b.building.id=?1 AND b.lvl>=?2 AND b.keybladeWielder=?3")
	Integer getIsTrueRequirement(Integer buildingId, Integer lvl, int playerId);

	@Query("select 1.0*(count(r)/(select count(z) from Requirement z where z.mainBuilding.id=?1)) from Requirement r where r.mainBuilding.id=?1 AND r.requiredBuilding in (select b.building from Built b where b.keybladeWielder.id=?2 AND b.lvl>=r.lvl)")
	Integer getBuildingFulfillsReqs(Integer buildingId, Integer playerId);

}
