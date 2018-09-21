package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Recruiter;

public interface RecruiterRepository extends JpaRepository<Recruiter, Integer> {
	
	@Query("select r.name from Recruiter r where r.isFinal = 1")
	Collection<String> getRecruiterNames();
	

}
