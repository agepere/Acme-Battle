
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.KeybladeWielder;

public interface KeybladeWielderRepository extends JpaRepository<KeybladeWielder, Integer> {

	@Query("select i.keybladeWielder from Invitation i where (i.invitationStatus='ACCEPTED' and i.organization.id=?1)")
	Collection<KeybladeWielder> findMembersOfOrganization(int organizationId);

	@Query("select a from KeybladeWielder a where a.worldName = ?1")
	KeybladeWielder findByWorldName(String worldName);

	@Query("select count(k) from KeybladeWielder k where k.worldCoordinates.z=?1")
	Integer checkIfGalaxyHas10Worlds(int g);

	@Query("select count(k) from KeybladeWielder k where k.worldCoordinates.x = ?1 and k.worldCoordinates.y = ?2 and k.worldCoordinates.z = ?3")
	Integer checkIfCoodinatesAreInUseinGalaxy(int x, int y, int g);

	//Dashboard

	@Query("select 1.0*(select count(k)/(select count(u) from KeybladeWielder u) from KeybladeWielder k where k.faction.id=f.id) from Faction f")
	Collection<Double> ratioOfUserPerFaction();

	@Query("select k.nickname from KeybladeWielder k order by wins desc")
	Collection<String> getTopWinsPlayers();

	@Query("select k.nickname from KeybladeWielder k where k.wins>10 order by (wins/(wins+loses)) desc")
	Collection<String> getTopWinRatioPlayers();

	@Query("select k.nickname from KeybladeWielder k order by materials.munny desc")
	Collection<String> getTopMunnyPlayers();

	@Query("select k.nickname from KeybladeWielder k order by materials.mytrhil desc")
	Collection<String> getTopMythrilPlayers();

	@Query("select avg(1.0*(k.wins/(k.loses+k.wins))) from KeybladeWielder k")
	Double avgOfWinRatio();

	@Query("select k from KeybladeWielder k where k.faction.name != ?1")
	Collection<KeybladeWielder> playersToAttackt(String faction);

}
