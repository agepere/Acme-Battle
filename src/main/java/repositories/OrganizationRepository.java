package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	@Query("select i.organization from Invitation i where (i.keybladeWielder.id=?1 and i.invitationStatus='ACCEPTED')")
	Organization findOrganizationByPlayer(int playerId);
	
}
