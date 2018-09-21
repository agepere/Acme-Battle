package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

	@Query("select i from Invitation i where (i.keybladeWielder.id=?1)")
	Collection<Invitation> findInvitationsByKeybladeWielderId(int playerId); 
	
	@Query("select i from Invitation i where (i.invitationStatus='ACCEPTED' and i.organization.id=?2 and i.keybladeWielder.id=?1)")
	Invitation findInvitationOfKeywielderInAnOrganization(int playerId, int organizationId);
	
	@Query("select i from Invitation i where (i.keybladeWielder.id=?1 and i.date>?2 and (i.invitationStatus='CANCELLED' or i.invitationStatus='PENDING'))")
	Collection<Invitation> findCorrectInvitationsByKeybladeWielder(int playerId, Date date);
	
	@Query("select i from Invitation i where (i.keybladeWielder.id=?1 and i.date<=?2 and (i.invitationStatus='CANCELLED' or i.invitationStatus='PENDING'))")
	Collection<Invitation> findDeleteableInvitationsByKeybladeWielder(int playerId, Date date);
	
	@Query("select i from Invitation i where (i.invitationStatus='ACCEPTED' and i.organization.id=?1 and i.orgRange='OFFICER') order by i.date DESC")
	Collection<Invitation> findOfficersInOrganization(int organizationId);

	@Query("select i from Invitation i where (i.invitationStatus='ACCEPTED' and i.organization.id=?1 and i.orgRange='GUEST') order by i.date DESC")
	Collection<Invitation> findGuestsInOrganization(int organizationId);

	@Query("select i from Invitation i where (i.invitationStatus='ACCEPTED' and i.organization.id=?1)")
	Collection<Invitation> findAllMembersOfOrganization(int orgId);

	@Query("select i from Invitation i where(i.invitationStatus='ACCEPTED' and (i.orgRange='OFFICER' or i.orgRange='MASTER') and i.keybladeWielder.id=?1)")
	Invitation getInvitationOfMasterOrOfficer(int id);
	
	@Query("select i from Invitation i where i.organization.id=?1")
	Collection<Invitation> toDeleteOrganization(int orgId);

	@Query("select i from Invitation i where i.organization.id=?1 and i.keybladeWielder.id=?2")
	Invitation findInvitationFromOrganizationAgain(int id, int playerId);

	@Query("select i from Invitation i where((i.organization.id=?1 and i.invitationStatus='CANCELLED' or i.invitationStatus='PENDING') and i.date>?2 )")
	Collection<Invitation> findCorrectInvitationsSentByOrganization(Integer id, Date d);
	
	@Query("select i from Invitation i where((i.organization.id=?1 and i.invitationStatus='CANCELLED' or i.invitationStatus='PENDING') and i.date<=?2 )")
	Collection<Invitation> findDeleteInvitationsSentByOrganization(Integer id, Date d);

	
}
