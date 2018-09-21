package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.ContentManager;

public interface ContentManagerRepository extends JpaRepository<ContentManager, Integer> {
	

}
