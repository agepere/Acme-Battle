
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
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * Esta clase indica que un jugador ha construido uno de los edifcios
 * 
 */
@Entity
@Table(indexes = {
	@Index(columnList = "lvl")
})
@Access(AccessType.PROPERTY)
public class Built extends DomainEntity {

	private Integer			lvl;
	private Date			creationDate;
	private Date			activationDate;

	//relations

	private KeybladeWielder	keybladeWielder;
	private Troop			troop;
	private GummiShip		gummiShip;
	private Building		building;


	/**
	 * 
	 * @return El nivel del edificio construido
	 */
	@NotNull
	@Range(min = 0)
	public Integer getLvl() {
		return this.lvl;
	}

	public void setLvl( Integer lvl) {
		this.lvl = lvl;
	}
	/**
	 * 
	 * @return La fecha de creación, usada para saber cuando terminará de contstruirse
	 */
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate( Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * 
	 * @return este atributo <b>SOLO</b> lo usaran los livelihoods y los recruiters para saber cuando empezó a recolectar/reclutar. En los demás edificios o si no esta realizando ninguna operación deberá ser null.
	 */
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getActivationDate() {
		return this.activationDate;
	}

	public void setActivationDate( Date activationDate) {
		this.activationDate = activationDate;
	}
	/**
	 * 
	 * @return dueño de la construcción
	 */
	@Valid
	@ManyToOne(optional = false)
	public KeybladeWielder getKeybladeWielder() {
		return this.keybladeWielder;
	}

	public void setKeybladeWielder( KeybladeWielder keybladeWielder) {
		this.keybladeWielder = keybladeWielder;
	}
	/**
	 * <b> USADA</b> solo por recruiters
	 * 
	 * @return tropa que se está creando
	 */
	@Valid
	@ManyToOne(optional = true)
	public Troop getTroop() {
		return this.troop;
	}

	public void setTroop( Troop troop) {
		this.troop = troop;
	}
	/**
	 * <b> USADA</b> solo por recruiters
	 * 
	 * @return nave que se está creando
	 */
	@Valid
	@ManyToOne(optional = true)
	public GummiShip getGummiShip() {
		return this.gummiShip;
	}

	public void setGummiShip( GummiShip gummiShip) {
		this.gummiShip = gummiShip;
	}
	/**
	 * 
	 * @return el edificio que ha sido construido (o esta en construcción según los timings).
	 */
	@Valid
	@ManyToOne(optional = false)
	public Building getBuilding() {
		return this.building;
	}

	public void setBuilding( Building building) {
		this.building = building;
	}

	@Transient
	public Boolean haTerminado( Integer minutos) {
		Boolean res = false;
		if (this.getActivationDate() != null) {
			 Date ahora = new Date(System.currentTimeMillis());
			 Date fechaFin = new Date(this.getActivationDate().getTime() + minutos * 60 * 1000);

			if (ahora.after(fechaFin))
				res = true;
		}
		return res;
	}
}
