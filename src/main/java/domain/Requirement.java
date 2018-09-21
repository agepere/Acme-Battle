
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;

/**
 * 
 * Esta clase tendrá el edificio que necesita este requisito (mainBuilding), y el edificio que requiere (requiredBuilding) junto a su lvl
 * 
 */
@Entity
@Table(indexes = {
	@Index(columnList = "lvl")
})
@Access(AccessType.PROPERTY)
public class Requirement extends DomainEntity {

	private Integer		lvl;

	//relations
	private Building	mainBuilding;
	private Building	requiredBuilding;


	/**
	 * 
	 * @return El nivel que debe de tener el edificio requerido para poder construir el principal
	 */
	@Range(min = 0)
	public Integer getLvl() {
		return this.lvl;
	}

	public void setLvl( Integer lvl) {
		this.lvl = lvl;
	}
	/**
	 * 
	 * @return El edificio que necesita este requisito
	 */
	@Valid
	@ManyToOne(optional = false)
	public Building getMainBuilding() {
		return this.mainBuilding;
	}

	public void setMainBuilding( Building mainBuilding) {
		this.mainBuilding = mainBuilding;
	}
	/**
	 * 
	 * @return El edificio que requiere el edificio principal para ser construido
	 */
	@Valid
	@ManyToOne(optional = false)
	public Building getRequiredBuilding() {
		return this.requiredBuilding;
	}

	public void setRequiredBuilding( Building requiredBuilding) {
		this.requiredBuilding = requiredBuilding;
	}

}
