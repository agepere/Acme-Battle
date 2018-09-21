package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.GameMaster;

public interface GameMasterRepository extends JpaRepository<GameMaster, Integer> {
	

}
