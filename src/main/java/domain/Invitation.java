
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "date, invitationStatus, orgRange")
	})
public class Invitation extends DomainEntity {

	private Date				date;
	private InvitationStatus	invitationStatus;
	private OrgRange			orgRange;
	private String				content;

	//relations 

	private KeybladeWielder				keybladeWielder;
	private Organization		organization;


	/**
	 * Hay que guardarlo porque se elimina en unos dias las invitaciones pendientes o rechazadas
	 * 
	 * @return Fecha en la fue mandada la invitación
	 * @see "dd/MM/yyyy"
	 */
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDate() {
		return this.date;
	}

	
	public void setDate( Date date) {
		this.date = date;
	}
	
	
	/**
	 * 
	 * @return pending (Mandada) , cancelled (rechazada por el jugador que la recibe), accepted (aceptada por el jugador que la recibe).
	 */
	@NotNull
	@Enumerated(EnumType.STRING)
	public InvitationStatus getInvitationStatus() {
		return this.invitationStatus;
	}

	
	public void setInvitationStatus( InvitationStatus invitationStatus) {
		this.invitationStatus = invitationStatus;
	}
	
	
	/**
	 * 
	 * @return Master (jefe, puede editar la organización, cambiar rangos e invitar jugadores), Officer (puede invitar gente), Guest.
	 */
	@NotNull
	@Enumerated(EnumType.STRING)
	public OrgRange getOrgRange() {
		return this.orgRange;
	}

	
	public void setOrgRange( OrgRange orgRange) {
		this.orgRange = orgRange;
	}
	
	
	/**
	 * 
	 * @return Mensaje para intentar reclutar al jugador.
	 */
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getContent() {
		return this.content;
	}

	
	public void setContent( String content) {
		this.content = content;
	}
	
	
	@Valid
	@ManyToOne(optional = false)
	public KeybladeWielder getKeybladeWielder() {
		return this.keybladeWielder;
	}

	
	public void setKeybladeWielder( KeybladeWielder keybladeWielder) {
		this.keybladeWielder = keybladeWielder;
	}
	
	
	@Valid
	@ManyToOne(optional = false)
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization( Organization organization) {
		this.organization = organization;
	}
	
	@Transient
	public Boolean isPending(){
		return this.invitationStatus.equals(InvitationStatus.PENDING);
	}

}
