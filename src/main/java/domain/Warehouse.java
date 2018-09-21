
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Warehouse extends Building {

	private Materials	materialsSlots;
	private Integer		troopSlots;
	private Integer		gummiSlots;
	private Double		extraSlotsPerLvl;


	/**
	 * 
	 * Devuelve cuanto aumenta los materiales que se pueden tener
	 */
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "munny", column = @Column(name = "munnySlots")), @AttributeOverride(name = "mytrhil", column = @Column(name = "mytrhilSlots")), @AttributeOverride(name = "gummiCoal", column = @Column(name = "gummiCoalSlots"))

	})
	public Materials getMaterialsSlots() {
		return this.materialsSlots;
	}

	public void setMaterialsSlots(Materials materialsSlots) {
		this.materialsSlots = materialsSlots;
	}
	/**
	 * 
	 * Cuantas tropas puede almacenar
	 */
	@NotNull
	@Range(min = 0)
	public Integer getTroopSlots() {
		return this.troopSlots;
	}

	public void setTroopSlots(Integer troopSlots) {
		this.troopSlots = troopSlots;
	}
	/**
	 * 
	 * Cuantas naves puede almacenar
	 */
	@NotNull
	@Range(min = 0)
	public Integer getGummiSlots() {
		return this.gummiSlots;
	}

	public void setGummiSlots(Integer gummiSlots) {
		this.gummiSlots = gummiSlots;
	}
	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("1.0")
	public Double getExtraSlotsPerLvl() {
		return this.extraSlotsPerLvl;
	}

	public void setExtraSlotsPerLvl(Double extraSlotsPerLvl) {
		this.extraSlotsPerLvl = extraSlotsPerLvl;
	}
	/**
	 * 
	 * 
	 * 
	 * @return Cuantos materiales puede almacenar en total el edificio de nivel "lvl"
	 */
	@Transient
	public Materials getTotalSlotsMaterials(Integer lvl) {
		Materials res = new Materials();

		Integer munny = this.getMaterialsSlots().getMunny();
		Integer mythril = this.getMaterialsSlots().getMytrhil();
		Integer coal = this.getMaterialsSlots().getGummiCoal();

		res.setMunny((int) (munny + this.extraSlotsPerLvl * munny * (lvl - 1)));
		res.setMytrhil((int) (mythril + this.extraSlotsPerLvl * mythril * (lvl - 1)));
		res.setGummiCoal((int) (coal + this.extraSlotsPerLvl * coal * (lvl - 1)));

		return res;

	}
	/**
	 * 
	 * @return Cuantas tropas puede almacenar en total el edificio de nivel "lvl"
	 */
	@Transient
	public Integer getTotalTroopSlots(Integer lvl) {

		Integer res = (int) (this.troopSlots + this.troopSlots * (lvl - 1) * this.extraSlotsPerLvl);

		return res;

	}
	/**
	 * 
	 * @return Cuantas tropas puede almacenar en total el edificio de nivel "lvl"
	 */
	@Transient
	public Integer getTotalGummiSlots(Integer lvl) {

		Integer res = (int) (this.gummiSlots + this.gummiSlots * (lvl - 1) * this.extraSlotsPerLvl);

		return res;

	}

}
