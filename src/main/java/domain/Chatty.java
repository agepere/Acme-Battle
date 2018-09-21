
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * Chat de una organización
 * 
 * 
 * 
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "date")
	})
public class Chatty extends DomainEntity {

	private String		content;
	private Date		date;

	//relations

	private Invitation	invitation;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getContent() {
		return this.content;
	}

	public void setContent( String content) {
		this.content = content;
	}
	/**
	 * 
	 * Almacenado para ordenar por oden de escritura y para saber cuando borrar cuando superen el limite dicho en los requisitos
	 */
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDate() {
		return this.date;
	}

	public void setDate( Date date) {
		this.date = date;
	}

	@ManyToOne( optional = false)
	public Invitation getInvitation() {
		return this.invitation;
	}

	public void setInvitation( Invitation invitation) {
		this.invitation = invitation;
	}

}
