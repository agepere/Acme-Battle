
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "isFinal")
})
public class Building extends DomainEntity {

	private String			name;
	private String			description;
	private String			photo;
	private Integer			maxLvl;
	private Boolean			isFinal;
	private Materials		cost;
	private Double			extraCostPerLvl;
	private Integer			timeToConstruct;

	//relations

	private ContentManager	contentManager;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = ".+.(jpg|jpeg|gif|png)", message = "(jpg, jpeg, gif, png)")
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * 
	 * Nivel máximo que puede subirse el edificio
	 */
	@NotNull
	@Range(min = 1)
	public Integer getMaxLvl() {
		return this.maxLvl;
	}

	public void setMaxLvl(Integer maxLvl) {
		this.maxLvl = maxLvl;
	}
	/**
	 * 
	 * Ya esta guardado para usarse (true), se puede editar (false)
	 */
	@NotNull
	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}
	/**
	 * 
	 * Devuelve los materiales que son necesarios para adquirir el edificio
	 */
	@Valid
	public Materials getCost() {
		return this.cost;
	}

	public void setCost(Materials cost) {
		this.cost = cost;
	}
	/**
	 * 
	 * Cuanto más cuesta mejorar un edificio<b> SIEMPRE </b>se aplica al de nivel 1
	 */
	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("1.0")
	public Double getExtraCostPerLvl() {
		return this.extraCostPerLvl;
	}

	public void setExtraCostPerLvl(Double extraCostPerLvl) {
		this.extraCostPerLvl = extraCostPerLvl;
	}
	/**
	 * 
	 * EN MINUTOS
	 */
	@NotNull
	@Range(min = 0)
	public Integer getTimeToConstruct() {
		return this.timeToConstruct;
	}

	public void setTimeToConstruct(Integer timeToConstruct) {
		this.timeToConstruct = timeToConstruct;
	}
	@Valid
	@ManyToOne(optional = false)
	public ContentManager getContentManager() {
		return this.contentManager;
	}

	public void setContentManager(ContentManager contentManager) {
		this.contentManager = contentManager;
	}
	@Transient
	public Materials getTotalMaterials(Integer currentLvL) {
		Materials res = new Materials();

		Integer munny = this.getCost().getMunny();
		Integer mythril = this.getCost().getMytrhil();
		Integer coal = this.getCost().getGummiCoal();

		res.setMunny((int) (munny + this.extraCostPerLvl * munny * (currentLvL)));
		res.setMytrhil((int) (mythril + this.extraCostPerLvl * mythril * (currentLvL)));
		res.setGummiCoal((int) (coal + this.extraCostPerLvl * coal * (currentLvL)));

		return res;

	}
	@Transient
	public String getReduceDescription() {
		String res = this.getDescription();
		if (res.length() > 105) {
			res = res.substring(0, 100);
			res = res + "...";
		}

		return res;
	}

}
