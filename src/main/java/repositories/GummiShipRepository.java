
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.GummiShip;

public interface GummiShipRepository extends JpaRepository<GummiShip, Integer> {

	@Query("select g from GummiShip g where g.recruiter.id=?1")
	Collection<GummiShip> getGummiShipsFromRecruiter(Integer recruiterId);

	@Query("select r.gummiShip from Recruited r where r.storageBuilding.id=?1 and r.gummiShip!=null")
	Collection<GummiShip> getStoragedGummiShip(Integer builtId);

	@Query("select g from GummiShip g where g.recruiterRequiredLvl<=?1")
	Collection<GummiShip> getGummiShipsAvailableForBuilt(Integer builtLevel);

	@Query("select g from GummiShip g where g.recruiter.id=?1 and g.recruiterRequiredLvl<=?2")
	Collection<GummiShip> getGummiShipsAvailableFromRecruiterAndLvl(Integer recruiterId, Integer lvl);

	@Query("select t from GummiShip t where t.name=?1")
	GummiShip getGummiShipByName(String name);

	@Query("select t.name from GummiShip t")
	Collection<String> getGummiShipsNames();

}
