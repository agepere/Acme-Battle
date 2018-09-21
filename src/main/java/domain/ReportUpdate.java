
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
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
public class ReportUpdate extends DomainEntity {

	private ReportStatus	status;
	private String			content;
	private Date			date;
	private Boolean			isSuspicious;

	//relations

	private Administrator	administrator;
	private GameMaster		gameMaster;

	@NotNull
	@Enumerated(EnumType.STRING)
	public ReportStatus getStatus() {
		return this.status;
	}

	public void setStatus( ReportStatus status) {
		this.status = status;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getContent() {
		return this.content;
	}

	public void setContent( String content) {
		this.content = content;
	}
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
	
	@NotNull
	public Boolean getIsSuspicious() {
		return isSuspicious;
	}

	public void setIsSuspicious(Boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

	/**
	 * 
	 * es null si el creador es un gm
	 */
	@Valid
	@ManyToOne(optional = true)
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator( Administrator administrator) {
		this.administrator = administrator;
	}
	/**
	 * 
	 * es null si el creador es un admin
	 */
	@Valid
	@ManyToOne(optional = true)
	public GameMaster getGameMaster() {
		return this.gameMaster;
	}

	public void setGameMaster( GameMaster gameMaster) {
		this.gameMaster = gameMaster;
	}

	@Transient
	public Actor getCreator() {
		Actor a = this.getGameMaster();
		if (a == null)
			a = this.getAdministrator();
		return a;
	}
}
