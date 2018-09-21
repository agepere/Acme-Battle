
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import security.LoginService;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "name")
}, indexes = {
		@Index(columnList = "recruiterRequiredLvl")})
public class GummiShip extends DomainEntity {

	private Integer		slots;
	private Materials	cost;
	private Integer		timeToRecruit;
	private Integer		recruiterRequiredLvl;
	private String		name;

	//relations
	private Recruiter	recruiter;


	@Range(min = 0)
	public Integer getSlots() {
		return this.slots;
	}

	public void setSlots( Integer slots) {
		this.slots = slots;
	}

	@Valid
	public Materials getCost() {
		return this.cost;
	}

	public void setCost( Materials cost) {
		this.cost = cost;
	}
	@Range(min = 0)
	public Integer getTimeToRecruit() {
		return this.timeToRecruit;
	}

	public void setTimeToRecruit( Integer timeToRecruit) {
		this.timeToRecruit = timeToRecruit;
	}
	/**
	 * 
	 * @return el nivel que necesita el recruiter para poder crear esta nave
	 */
	@Range(min = 0)
	public Integer getRecruiterRequiredLvl() {
		return this.recruiterRequiredLvl;
	}

	public void setRecruiterRequiredLvl( Integer recruiterRequiredLvl) {
		this.recruiterRequiredLvl = recruiterRequiredLvl;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName( String name) {
		this.name = name;
	}

	@ManyToOne(optional = false)
	public Recruiter getRecruiter() {
		return this.recruiter;
	}

	public void setRecruiter( Recruiter recruiter) {
		this.recruiter = recruiter;
	}

	@Transient
	public Boolean soyDueno(){
		return LoginService.getPrincipal().equals(recruiter.getContentManager().getUserAccount());
	}
}
