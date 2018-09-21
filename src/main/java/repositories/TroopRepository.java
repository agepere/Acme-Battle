
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Troop;

public interface TroopRepository extends JpaRepository<Troop, Integer> {

	@Query("select t from Troop t where t.recruiter.id=?1")
	Collection<Troop> getTroopsFromRecruiter(Integer recruiterId);

	@Query("select r.troop from Recruited r where r.storageBuilding.id=?1 and r.troop!=null")
	Collection<Troop> getStoragedTroops(Integer builtId);

	@Query("select t from Troop t where t.recruiterRequiredLvl<=?1")
	Collection<Troop> getTroopsAvailableForBuilt(Integer builtLevel);

	@Query("select t from Troop t where t.recruiter.id=?1 and t.recruiterRequiredLvl<=?2")
	Collection<Troop> getTroopsAvailableFromRecruiterAndLvl(Integer recruiterId, Integer lvl);
	
	@Query("select t from Troop t where t.name=?1")
	Troop getTroopByName(String name);
	
	@Query("select t.name from Troop t")
	Collection<String> getTroopsNames();

}
