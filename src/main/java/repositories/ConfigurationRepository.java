package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Configuration;

public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
	

	@Query("select c from Configuration c")
	Configuration getConfiguration();

}
