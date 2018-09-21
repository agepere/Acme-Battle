package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Chatty;

public interface ChattyRepository extends JpaRepository<Chatty, Integer> {
	
	@Query("select c from Chatty c where (c.invitation.organization.id=?1) order by c.date asc")
	Collection<Chatty> getChattyFromAnOrganization(int organizationId);

	@Query("select c from Chatty c where c.invitation.id=?1")
	Collection<Chatty> findForLeavingOrganization(int id);

}
