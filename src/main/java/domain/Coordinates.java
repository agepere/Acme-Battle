
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Range;

/**
 * 
 * <p>
 * Hay varias restricciones respecto al numero de mundos que puede haber por galaxias, para controlar ademas que las coordenadas no sea lejanas en cada galaxia x e y puede ser como mucho 10 cada mundo tiene que tener coordenadas diferente.
 * </p>
 * 
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class Coordinates {

	private Integer	x;
	private Integer	y;
	private Integer	z;


	/**
	 * 
	 * En cada galaxia va entre 0 y 10 y cada mundo tiene que tener una coordenada diferente
	 */
	@Range(min = 0, max = 10)
	public Integer getX() {
		return this.x;
	}

	public void setX( Integer x) {
		this.x = x;
	}
	/**
	 * 
	 * En cada galaxia va entre 0 y 10 y cada mundo tiene que tener una coordenada diferente
	 */
	@Range(min = 0, max = 10)
	public Integer getY() {
		return this.y;
	}

	public void setY( Integer y) {
		this.y = y;
	}
	/**
	 * <p>
	 * Devuelve la galaxia, hay que tener en cuenta las restricciones de en que galaxia puede estar determinada facción
	 * </p>
	 */
	@Range(min = 0)
	public Integer getZ() {
		return this.z;
	}

	public void setZ( Integer z) {
		this.z = z;
	}

}
