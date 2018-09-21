
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Defense extends Building {

	private Integer	defense;
	private Double	extraDefensePerLvl;


	@NotNull
	@Range(min = 0)
	public Integer getDefense() {
		return this.defense;
	}

	public void setDefense(Integer defense) {
		this.defense = defense;
	}
	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("1.0")
	public Double getExtraDefensePerLvl() {
		return this.extraDefensePerLvl;
	}

	public void setExtraDefensePerLvl(Double extraDefensePerLvl) {
		this.extraDefensePerLvl = extraDefensePerLvl;
	}
	/**
	 * 
	 * <b> Recibe: </b>El nivel del edificio al que se le quiere calcular la defensa <br>
	 * 
	 * @return La defensa total de este edificio de nivel "lvl"
	 */
	@Transient
	public Integer getTotalDefense(Integer lvl) {

		Integer res = (int) (this.defense + this.defense * (lvl - 1) * this.extraDefensePerLvl);

		return res;

	}

}
