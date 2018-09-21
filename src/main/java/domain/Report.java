
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private boolean				isBug;
	private ReportStatus		status;
	private String				title;
	private String				content;
	private Collection<String>	photos;
	private Date				date;

	//relations

	private KeybladeWielder		            keybladeWielder;
	private Collection<ReportUpdate>		reportUpdates;


	/**
	 * 
	 * @return devuelve si se esta reportando un bug o falso, si lo que se quiere es una asistencia técnica personalizada
	 */
	public boolean getIsBug() {
		return this.isBug;
	}

	public void setIsBug( boolean isBug) {
		this.isBug = isBug;
	}
	/**
	 * 
	 * <b> Status actualizados por el jugador: </b><br>
	 * -onHold: Recien creado <br>
	 * -suspicious: Una vez esta en resolved o irresolvable el jugador puede no estar de acuerdo y pedir que se lo revise un admin <br>
	 * <br>
	 * <b> Status actualizados por el GM: </b> <br>
	 * - working: Ha leido el report pero necesita tiempo para trabajar<br>
	 * - resolved: El report ha sido resuelto <br>
	 * - irresolvable: El report no puede ser resuelto
	 * 
	 */
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
	public String getTitle() {
		return this.title;
	}

	public void setTitle( String title) {
		this.title = title;
	}
	
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
	 * Tienen que ser urls pero no se si puedo poner esa restricción en el dominio al ser una lista
	 */
	
	@NotNull
	@ElementCollection
	public Collection<String> getPhotos() {
		return this.photos;
	}

	public void setPhotos( Collection<String> photos) {
		this.photos = photos;
	}
	
	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDate() {
		return this.date;
	}

	public void setDate( Date date) {
		this.date = date;
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
	@OneToMany
	public Collection<ReportUpdate> getReportUpdates() {
		return reportUpdates;
	}

	public void setReportUpdates(Collection<ReportUpdate> reportUpdates) {
		this.reportUpdates = reportUpdates;
	}
	
	

}
