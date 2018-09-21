
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/**
 * 
 * Cada facción aportará una serie de beneficios que deben ser explicados en la descripción y tendrán atributos correspondiente al extra
 * 
 */
@Entity
@Access(AccessType.PROPERTY)
public class Faction extends DomainEntity {

	private String	name;
	private String	powerUpDescription;
	private Double	extraResources;
	private Double	extraAttack;
	private Double	extraDefense;
	private Integer	galaxy;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPowerUpDescription() {
		return this.powerUpDescription;
	}

	public void setPowerUpDescription(String powerUpDescription) {
		this.powerUpDescription = powerUpDescription;
	}
	@NotNull
	@Range(min = 0, max = 1)
	public Double getExtraResources() {
		return this.extraResources;
	}

	public void setExtraResources(Double extraResources) {
		this.extraResources = extraResources;
	}
	@NotNull
	@Range(min = 0, max = 1)
	public Double getExtraAttack() {
		return this.extraAttack;
	}

	public void setExtraAttack(Double extraAttack) {
		this.extraAttack = extraAttack;
	}
	@NotNull
	@Range(min = 0, max = 1)
	public Double getExtraDefense() {
		return this.extraDefense;
	}

	public void setExtraDefense(Double extraDefense) {
		this.extraDefense = extraDefense;
	}
	/**
	 * 
	 * 0: puede estar en cualquier galaxia <br>
	 * 1: puede estar en galaxias impares <br>
	 * 2: puede estar en galaxias pares <br>
	 * 3: puede estar en galaxias que sean multiplos de 3
	 */
	@NotNull
	@Range(min = 0, max = 3)
	public Integer getGalaxy() {
		return this.galaxy;
	}

	public void setGalaxy(Integer galaxy) {
		this.galaxy = galaxy;
	}

}
