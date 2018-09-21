
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/**
 * 
 * La clase batalla, leerse bien los requisitos para esta clase que tiene mucha casuistica, como el coste de gummi coal para crear una batalla.
 * 
 */
@Entity
@Access(AccessType.PROPERTY)
public class Battle extends DomainEntity {

	private Double			luckAttacker;
	private Double			luckDeffender;
	private Boolean			isWon;
	private Boolean			attackerOwner;
	private Materials		wonOrLostMaterials;
	private String			balance;

	//relations

	private KeybladeWielder	attacker;
	private KeybladeWielder	deffender;


	/**
	 * 
	 * @return Indicará un poco las tropas que se enviaron a la batalla y las que se perdieron
	 */
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBalance() {
		return this.balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * 
	 * @return factor aleatorio de suerte para el atacante
	 */
	@NotNull
	@Range(min = 0, max = 1)
	public Double getLuckAttacker() {
		return this.luckAttacker;
	}

	public void setLuckAttacker(Double luckAttacker) {
		this.luckAttacker = luckAttacker;
	}
	/**
	 * 
	 * @return factor aleatorio de suerte para el defensor
	 */
	@NotNull
	@Range(min = 0, max = 1)
	public Double getLuckDeffender() {
		return this.luckDeffender;
	}

	public void setLuckDeffender(Double luckDeffender) {
		this.luckDeffender = luckDeffender;
	}
	/**
	 * <b>IMPORTANTE:</b> tener en cuenta que se crean dos batallas una para el atacante y otra para el defensor, este atributo tendra valores opuestos en cada uno. Para saber el dueño se usa "attackerOwner".
	 * 
	 * @return true si el dueño de esta batalla ha ganado.
	 */
	@NotNull
	public Boolean getIsWon() {
		return this.isWon;
	}

	public void setIsWon(Boolean isWon) {
		this.isWon = isWon;
	}
	/**
	 * 
	 * @return true si el atacante es el dueño de esta batalla, false si es el defensor
	 * @see se usa porque se crean dos batallas por cada batalla que se realice, una para el atacante y otra para el defensor, y en vez de añadir una tercera relacion de "dueño" usaremos un boolean, puesto que atacante y defensor ya estan almacenados
	 */
	@NotNull
	public Boolean getAttackerOwner() {
		return this.attackerOwner;
	}

	public void setAttackerOwner(Boolean attackerOwner) {
		this.attackerOwner = attackerOwner;
	}
	/**
	 * 
	 * @return los materiales que seran enviados como premio si ha ganao la batalla o los que ha perdido en caso de derrota.
	 */
	@Valid
	public Materials getWonOrLostMaterials() {
		return this.wonOrLostMaterials;
	}

	public void setWonOrLostMaterials(Materials wonOrLostMaterials) {
		this.wonOrLostMaterials = wonOrLostMaterials;
	}
	/**
	 * 
	 * Persona que comienza la batalla (atacante)
	 */
	@Valid
	@ManyToOne(optional = false)
	public KeybladeWielder getAttacker() {
		return this.attacker;
	}

	public void setAttacker(KeybladeWielder attacker) {
		this.attacker = attacker;
	}
	/**
	 * 
	 * Persona que es atacada(defensor)
	 */
	@Valid
	@ManyToOne(optional = false)
	public KeybladeWielder getDeffender() {
		return this.deffender;
	}

	public void setDeffender(KeybladeWielder deffender) {
		this.deffender = deffender;
	}

}
