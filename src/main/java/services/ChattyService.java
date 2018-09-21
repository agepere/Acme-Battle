package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChattyRepository;
import domain.Chatty;
import domain.Invitation;
import domain.InvitationStatus;
import domain.KeybladeWielder;
import domain.Organization;

@Service
@Transactional
public class ChattyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChattyRepository chattyRepository;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private ConfigurationService configurationService;

	// CRUD methods
	
	public Chatty create(){
		Chatty chatty;
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Organization org = this.organizationService.findOrganizationByPlayer(actual.getId());
		chatty = new Chatty();
		
		chatty.setContent("");
		chatty.setDate(new Date(System.currentTimeMillis()-1000));
		chatty.setInvitation(this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), org.getId()));
		
		return chatty;
	}
	
	
	public Chatty save(Chatty chatty){
		Assert.notNull(chatty);
		Assert.isTrue(chatty.getId()==0);
		Assert.notNull(chatty.getInvitation(), "error.message.chatty.invitation");
		Assert.isTrue(!chatty.getContent().isEmpty());
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Organization org = this.organizationService.findOrganizationByPlayer(actual.getId());
		Invitation invActual = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), chatty.getInvitation().getOrganization().getId());
		
		Assert.isTrue(org.equals(chatty.getInvitation().getOrganization()) && chatty.getInvitation().getInvitationStatus().equals(InvitationStatus.ACCEPTED), "error.message.chatty.sameOrganization"); // Deben ser de la misma organización.
		Assert.isTrue(chatty.getInvitation().equals(invActual) && invActual.getInvitationStatus().equals(InvitationStatus.ACCEPTED));
		chatty.setDate(new Date(System.currentTimeMillis()-1000));
		
		Chatty saved;
		
		saved = chattyRepository.save(chatty);
		
		return saved;
	}
	
	
	public Chatty findOne(int chattyId){
		Assert.notNull(chattyId);
		
		Chatty chatty;
		
		chatty = chattyRepository.findOne(chattyId);
		
		return chatty;
	}
	
	public Collection<Chatty> findAll(){
		Collection<Chatty> chattys;
		
		chattys = chattyRepository.findAll();
		
		return chattys;
	}
	
	public void delete(Chatty chatty){
		Assert.notNull(chatty);
		
		chattyRepository.delete(chatty);
	}

	
	public Collection<Chatty> getChattyFromAnOrganization(int organizationId){
		List<Chatty> all = (List<Chatty>) this.chattyRepository.getChattyFromAnOrganization(organizationId);
		Integer chattyLimit = this.configurationService.getConfiguration().getOrgMessages();
		Collection<Chatty> availables = new ArrayList<Chatty>();
		
		if(all.size() >= chattyLimit){
			availables = all.subList(all.size()-chattyLimit, all.size());
			List<Chatty> toDelete= all.subList(0, all.size()-chattyLimit);
			
			for(Chatty c : toDelete){
				this.chattyRepository.delete(c);
			}
			return availables;
		}else{
			return all;
		}
		
	}
	
	public void deleteExtraChattyFromAnOrganization(int organizationId){
		List<Chatty> all = (List<Chatty>) this.chattyRepository.getChattyFromAnOrganization(organizationId);
		Integer chattyLimit = this.configurationService.getConfiguration().getOrgMessages();
		
		if(all.size() >= chattyLimit){
			this.chattyRepository.delete(all.get(chattyLimit));
		}
	}


	public void deleteAll(Collection<Chatty> chattys) {
		
		if(chattys!=null)
			for(Chatty c: chattys){
				this.chattyRepository.delete(c);
			}
		
	}
	
	public Collection<Chatty> getToDeleteOrganiation(int orgId){
		return this.chattyRepository.getChattyFromAnOrganization(orgId);
	}


	public Collection<Chatty> findForLeavingOrganization(int id) {
		return this.chattyRepository.findForLeavingOrganization(id);
	}
}