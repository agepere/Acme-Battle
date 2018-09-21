
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Built;

public interface BuiltRepository extends JpaRepository<Built, Integer> {

	@Query("select b from Built b where b.keybladeWielder.id=?1")
	Collection<Built> getMyBuildings(Integer playerId);

	@Query("select count(b) from Built b where b.keybladeWielder.id=?1")
	Integer getPlayerBuildings(Integer playerId);

	@Query("select b from Built b where b.keybladeWielder.id=?1")
	Page<Built> getMyBuildingsPageable(Integer playerId, Pageable p);

	@Query("select sum(b.building.defense*(1+(b.lvl-1)*b.building.extraDefensePerLvl)) from Built b where b.keybladeWielder.id=?1 AND b.lvl>0 AND b.building in (select d from Defense d)")
	Integer myDefenseByBuildings(Integer playerId);

	@Query("select b from Built b where b.keybladeWielder.id=?1 AND b.lvl>0 AND b.building in (select w from Warehouse w ) AND (b.building.troopSlots*(1+(b.lvl-1)*b.building.extraSlotsPerLvl))>(select count(t) from Recruited t where t.storageBuilding.id=b.id AND t.troop is not null)")
	Collection<Built> getMyFreeWarehousesTroop(Integer playerId);

	@Query("select b from Built b where b.keybladeWielder.id=?1 AND b.lvl>0 AND b.building in (select w from Warehouse w ) AND (b.building.gummiSlots*(1+(b.lvl-1)*b.building.extraSlotsPerLvl))>(select count(t) from Recruited t where t.storageBuilding.id=b.id AND t.troop is null)")
	Collection<Built> getMyFreeWarehousesGummi(Integer playerId);

	@Query("select sum(b.building.materialsSlots.munny * (1+(b.lvl-1)*b.building.extraSlotsPerLvl)) from Built b where b.keybladeWielder.id=?1 AND b.lvl>0 AND b.building in (select w from Warehouse w) ")
	Integer getExtraMunny(Integer playerId);
	@Query("select sum(b.building.materialsSlots.mytrhil * (1+(b.lvl-1)*b.building.extraSlotsPerLvl)) from Built b where b.keybladeWielder.id=?1 AND b.lvl>0  AND b.building in (select w from Warehouse w) ")
	Integer getExtraMythril(Integer playerId);
	@Query("select sum(b.building.materialsSlots.gummiCoal * (1+(b.lvl-1)*b.building.extraSlotsPerLvl)) from Built b where b.keybladeWielder.id=?1 AND b.lvl>0  AND b.building in (select w from Warehouse w) ")
	Integer getExtraGummiCoal(Integer playerId);

	@Query("select b from Built b where b.troop.id=?1")
	Collection<Built> findAllBuiltWithTroop(int id);

}
