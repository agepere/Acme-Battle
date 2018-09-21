
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "name")
})
public class Item extends DomainEntity {

	private Integer			duration;
	private Integer			expiration;
	private Double			extra;
	private Integer			munnyCost;
	private ItemType		type;
	private String			name;
	private String			description;
	private boolean			onSell;

	//relations

	private ContentManager	contentManager;


	/**
	 * 
	 * Tiempo que durará el objeto <b>EN MINUTOS<b>
	 */
	@Range(min = 0)
	@NotNull
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	/**
	 * 
	 * Tiempo de caducidad del objeto despues de haberlo comprado <b>EN DIAS<b>
	 */
	@Range(min = 0)
	@NotNull
	public Integer getExpiration() {
		return this.expiration;
	}

	public void setExpiration(Integer expiration) {
		this.expiration = expiration;
	}
	/**
	 * 
	 * Beneficio extra que dependará del tipo. Recordar que los "features" hay que programarlos, no se pueden crear features.
	 */
	@DecimalMin("0.1")
	@DecimalMax("0.9")
	@NotNull
	public Double getExtra() {
		return this.extra;
	}

	public void setExtra(Double extra) {
		this.extra = extra;
	}
	@Range(min = 1)
	@NotNull
	public Integer getMunnyCost() {
		return this.munnyCost;
	}

	public void setMunnyCost(Integer munnyCost) {
		this.munnyCost = munnyCost;
	}
	/**
	 * 
	 * <b> Feature: </b> Hay que programarlos, como el nombre será unico podremos ver por ahi que feature se esta usando, ademas no se podrán crear en tiempo de ejecución. <br>
	 * <b> Shield: </b> El escudo que se creará tendrá el nombre y duración que tenga el objeto <br>
	 * <b> Boost: </b> Segun el boost pues se aplicará en un sitio u otro.
	 */
	@Enumerated(EnumType.STRING)
	public ItemType getType() {
		return this.type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

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
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * Es usado por si un objeto se quiere dejar de vender para borrarlo una vez no lo este usando ningun jugador
	 */
	public boolean getOnSell() {
		return this.onSell;
	}

	public void setOnSell(boolean onSell) {
		this.onSell = onSell;
	}
	@Valid
	@ManyToOne(optional = false)
	public ContentManager getContentManager() {
		return this.contentManager;
	}

	public void setContentManager(ContentManager contentManager) {
		this.contentManager = contentManager;
	}

}
