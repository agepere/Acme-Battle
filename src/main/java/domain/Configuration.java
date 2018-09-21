package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private Materials dailyMaterials;
	private Materials baseMaterials;
	private Integer orgMessages;
	private Double percentageWinAttacker;
	private Double percentageWinDefender;
	private Integer worldSlots;

	/**
	 * 
	 * @return los materiales que seran otorgado a los jugadores por iniciar
	 *         sesión cada dia
	 */
	@AttributeOverrides({
			@AttributeOverride(name = "munny", column = @Column(name = "dailyMunny")),
			@AttributeOverride(name = "mytrhil", column = @Column(name = "dailyMytrhil")),
			@AttributeOverride(name = "gummiCoal", column = @Column(name = "dailyGummiCoal"))

	})
	@Valid
	public Materials getDailyMaterials() {
		return this.dailyMaterials;
	}

	public void setDailyMaterials(Materials dailyMaterials) {
		this.dailyMaterials = dailyMaterials;
	}

	/**
	 * 
	 * @return los materiales que puede conservar un jugador por defecto, a este
	 *         valor se le ha de sumar el valor de cada jugador según sus
	 *         edifcios.
	 */
	@AttributeOverrides({
			@AttributeOverride(name = "munny", column = @Column(name = "baseMunny")),
			@AttributeOverride(name = "mytrhil", column = @Column(name = "baseMytrhil")),
			@AttributeOverride(name = "gummiCoal", column = @Column(name = "baseGummiCoal"))

	})
	@Valid
	public Materials getBaseMaterials() {
		return this.baseMaterials;
	}

	public void setBaseMaterials(Materials baseMaterials) {
		this.baseMaterials = baseMaterials;
	}

	/**
	 * 
	 * @return número de mensajes que serán almacenados en una organización
	 */
	@Range(min = 5)
	@NotNull
	public Integer getOrgMessages() {
		return this.orgMessages;
	}

	public void setOrgMessages(Integer orgMessages) {
		this.orgMessages = orgMessages;
	}

	/**
	 * 
	 * @return porcentaje de los recursos que se llevara el atacante del
	 *         defensor si gana la batalla
	 */
	@DecimalMin("0.1")
	@DecimalMax("0.9")
	@NotNull
	public Double getPercentageWinAttacker() {
		return this.percentageWinAttacker;
	}

	public void setPercentageWinAttacker(Double percentageWinAttacker) {
		this.percentageWinAttacker = percentageWinAttacker;
	}

	/**
	 * 
	 * @return porcentaje de los recursos que se llevara el defensor de los
	 *         recursos que envie el atacante si gana la batalla
	 */
	@DecimalMin("0.1")
	@DecimalMax("0.9")
	@NotNull
	public Double getPercentageWinDefender() {
		return this.percentageWinDefender;
	}

	public void setPercentageWinDefender(Double percentageWinDefender) {
		this.percentageWinDefender = percentageWinDefender;
	}

	/**
	 * 
	 * @return los huecos que tiene un mundo para edificios
	 * @see esta propiedad solo puede ir aumentando
	 */
	@Range(min = 0)
	@NotNull
	public Integer getWorldSlots() {
		return this.worldSlots;
	}

	public void setWorldSlots(Integer worldSlots) {
		this.worldSlots = worldSlots;
	}

}
