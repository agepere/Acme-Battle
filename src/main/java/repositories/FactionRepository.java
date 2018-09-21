package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Faction;

public interface FactionRepository extends JpaRepository<Faction, Integer> {
	

}
