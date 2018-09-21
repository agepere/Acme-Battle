
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Battle;

public interface BattleRepository extends JpaRepository<Battle, Integer> {

	@Query("select b from Battle b where b.attackerOwner = true and b.attacker.id=?1 ")
	public Collection<Battle> myBattlesAttack(Integer actorId);

	@Query("select b from Battle b where b.attackerOwner = false and b.attacker.id=?1 ")
	public Collection<Battle> myBattlesDefense(Integer actorId);
}
