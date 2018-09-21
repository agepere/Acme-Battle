
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

}
