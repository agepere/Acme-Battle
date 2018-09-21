
package domain;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

@Entity
@Table(indexes = {
	@Index(columnList = "date")
})
@Access(AccessType.PROPERTY)
public class Prize extends DomainEntity {

	private Materials		materials;
	private String			description;
	private Date			date;

	//relations

	private KeybladeWielder	keybladeWielder;


	/**
	 * 
	 * Los materiales que recibirá el jugador al coger el premio (eliminarlo tras cogerlo)
	 */
	@Valid
	public Materials getMaterials() {
		return this.materials;
	}

	public void setMaterials(Materials materials) {
		this.materials = materials;
	}
	/**
	 * 
	 * Por si algun empleado quiere mandar premios a los jugadores o eventos... (en batallas poner una descripción tipo en plan, has ganado contra: X y por tanto recibes: Y)
	 */
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * Hay que almacenar la fecha porque se almacena durante un tiempo determinado, devuelve la fecha de expiración
	 */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne(optional = false)
	public KeybladeWielder getKeybladeWielder() {
		return this.keybladeWielder;
	}

	public void setKeybladeWielder(KeybladeWielder keybladeWielder) {
		this.keybladeWielder = keybladeWielder;
	}

	@Transient
	public String getPrizeImage() {
		Md5PasswordEncoder encoder;
		String hash;
		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(this.getDescription() + this.getDate() + this.getMaterials(), null);
		hash = hash.replaceAll("\\D+", "");
		hash = hash.replaceAll("0", "");
		BigInteger prizeId = new BigInteger(hash);
		prizeId = prizeId.mod(new BigInteger("21")).add(new BigInteger("1"));

		return "chest" + prizeId;
	}

}
