
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

/**
 * 
 * Clase que guarda la nave o la tropa que ha sido reclutada y la warehouse que lo almacena
 * 
 */
@Entity
@Access(AccessType.PROPERTY)
public class Recruited extends DomainEntity {

	private Built		storageBuilding;
	private GummiShip	gummiShip;
	private Troop		troop;


	/**
	 * 
	 * @return la warehouse que almacena este recrutado
	 */
	@Valid
	@ManyToOne(optional = false)
	public Built getStorageBuilding() {
		return this.storageBuilding;
	}

	public void setStorageBuilding( Built storageBuilding) {
		this.storageBuilding = storageBuilding;
	}
	/**
	 * 
	 * @return la nave que ha sido recrutada, si el recrutado es una tropa la nave es null
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
	 * @return la tropa que ha sido recrutada, si el recrutado es una nave la tropa es null
	 */
	@Valid
	@ManyToOne(optional = true)
	public Troop getTroop() {
		return this.troop;
	}

	public void setTroop( Troop troop) {
		this.troop = troop;
	}

}
