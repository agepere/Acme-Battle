package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Defense;

public interface DefenseRepository extends JpaRepository<Defense, Integer> {
	

}
