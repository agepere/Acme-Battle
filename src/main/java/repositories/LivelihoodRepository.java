package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Livelihood;

public interface LivelihoodRepository extends JpaRepository<Livelihood, Integer> {
	

}
