
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Purchase extends DomainEntity {

	private Date	purchaseDate;
	private Date	activationDate;
	
	private Date	expirationDate;

	//relations

	private Item	item;
	private KeybladeWielder player;


	/**
	 * 
	 * usada para controlar la caducidad
	 */
	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate( Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	/**
	 * 
	 * usada para controlar el tiempo de activación, es hasta cuando dura el item, null si no se ha utilizado aún
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getActivationDate() {
		return this.activationDate;
	}

	public void setActivationDate( Date activationDate) {
		this.activationDate = activationDate;
	}
	@Valid
	@ManyToOne(optional = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem( Item item) {
		this.item = item;
	}

	@ManyToOne(optional=false)
	public KeybladeWielder getPlayer() {
		return player;
	}

	public void setPlayer(KeybladeWielder player) {
		this.player = player;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	

}
