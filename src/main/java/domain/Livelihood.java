
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

@Entity
@Access(AccessType.PROPERTY)
public class Livelihood extends Building {

	private Materials	materials;
	private Double		extraMaterialsPerLvl;
	private Integer		timeToRecollect;
	private Double		LessTimePerLvl;


	/**
	 * 
	 * Materiales que recolecta este edificio una vez acabe el tiempo
	 */
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "munny", column = @Column(name = "collectedMunny")), @AttributeOverride(name = "mytrhil", column = @Column(name = "collectedMytrhil")), @AttributeOverride(name = "gummiCoal", column = @Column(name = "collectedGummiCoal"))

	})
	public Materials getMaterials() {
		return this.materials;
	}

	public void setMaterials(Materials materials) {
		this.materials = materials;
	}
	/**
	 * 
	 * Devuelve el extra de materiales que dará por cada nivel, <b>SIEMPRE</b> se aplica al de nivel 1.
	 */
	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("1.0")
	public Double getExtraMaterialsPerLvl() {
		return this.extraMaterialsPerLvl;
	}

	public void setExtraMaterialsPerLvl(Double extraMaterialsPerLvl) {
		this.extraMaterialsPerLvl = extraMaterialsPerLvl;
	}
	/**
	 * 
	 * Devuelve el tiempo <b>EN MINUTOS</b> que hay que esperar para recolectar todos los materiales que consiga el edificio
	 */
	@NotNull
	public Integer getTimeToRecollect() {
		return this.timeToRecollect;
	}

	public void setTimeToRecollect(Integer timeToRecollect) {
		this.timeToRecollect = timeToRecollect;
	}
	/**
	 * 
	 * Devuelve cuanto se reducirá el tiempo de recolección cada vez que se sube el nivel
	 */
	@DecimalMin("0.0")
	@DecimalMax("1.0")
	@NotNull
	public Double getLessTimePerLvl() {
		return this.LessTimePerLvl;
	}

	public void setLessTimePerLvl(Double lessTimePerLvl) {
		this.LessTimePerLvl = lessTimePerLvl;
	}
	/**
	 * 
	 * <b> Recibe: </b>El nivel del edificio al que se le quiere calcular los materiales <br>
	 * 
	 * @return Los materiales totales este edificio de nivel "lvl" recolecta
	 */
	@Transient
	public Materials getTotalCollectMaterials(Integer lvl) {
		Materials res = new Materials();

		Integer munny = this.getMaterials().getMunny();
		Integer mythril = this.getMaterials().getMytrhil();
		Integer coal = this.getMaterials().getGummiCoal();

		res.setMunny((int) (munny + this.extraMaterialsPerLvl * munny * (lvl - 1)));
		res.setMytrhil((int) (mythril + this.extraMaterialsPerLvl * mythril * (lvl - 1)));
		res.setGummiCoal((int) (coal + this.extraMaterialsPerLvl * coal * (lvl - 1)));

		return res;

	}
	/**
	 * 
	 * <b> Recibe: </b>El nivel del edificio al que se le quiere calcular el tiempo de recolección <br>
	 * 
	 * @return El tiempo total de este edificio de nivel "lvl" tarda en recolectar
	 */
	@Transient
	public Integer getTotalTime(Integer lvl) {

		Integer res = (int) (this.getTimeToRecollect() - (this.getTimeToRecollect() * (lvl - 1) * this.getLessTimePerLvl()));
		if (res < 0)
			res = 0;

		return res;
	}
}
