package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
	
	@Query("select w.name from Warehouse w where w.isFinal = 1")
	Collection<String> getWarehouseNames();
	

}
