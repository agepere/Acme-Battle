
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class Materials {

	private Integer	munny;
	private Integer	mytrhil;
	private Integer	gummiCoal;


	@NotNull
	@Range(min = 0)
	public Integer getMunny() {
		return this.munny;
	}

	public void setMunny(final Integer munny) {
		this.munny = munny;
	}
	@NotNull
	@Range(min = 0)
	public Integer getMytrhil() {
		return this.mytrhil;
	}

	public void setMytrhil(final Integer mytrhil) {
		this.mytrhil = mytrhil;
	}
	@NotNull
	@Range(min = 0)
	public Integer getGummiCoal() {
		return this.gummiCoal;
	}

	public void setGummiCoal(final Integer gummiCoal) {
		this.gummiCoal = gummiCoal;
	}

	@Transient
	public Boolean isHigherThan(final Materials materials) {
		Boolean res = false;
		if (this.getGummiCoal() > materials.getGummiCoal() && this.getMunny() > materials.getMunny() && this.getMytrhil() > materials.getMytrhil())
			res = true;
		return res;

	}

	@Transient
	public Materials substract(final Materials materials) {
		final Integer munny = this.getMunny() - materials.getMunny();
		final Integer gummi = this.getGummiCoal() - materials.getGummiCoal();
		final Integer mythril = this.getMytrhil() - materials.getMytrhil();

		final Materials res = new Materials();
		res.setMunny(munny);
		res.setGummiCoal(gummi);
		res.setMytrhil(mythril);

		return res;

	}
	@Transient
	public Materials add(final Materials materials) {
		final Integer munny = this.getMunny() + materials.getMunny();
		final Integer gummi = this.getGummiCoal() + materials.getGummiCoal();
		final Integer mythril = this.getMytrhil() + materials.getMytrhil();

		final Materials res = new Materials();
		res.setMunny(munny);
		res.setGummiCoal(gummi);
		res.setMytrhil(mythril);

		return res;

	}

	@Transient
	public Materials increase(final Double extraPercentage) {
		final Integer munny = (int) (this.getMunny() * (1 + extraPercentage));
		final Integer gummi = (int) (this.getGummiCoal() * (1 + extraPercentage));
		final Integer mythril = (int) (this.getMytrhil() * (1 + extraPercentage));

		final Materials res = new Materials();
		res.setMunny(munny);
		res.setGummiCoal(gummi);
		res.setMytrhil(mythril);

		return res;

	}

	@Transient
	public Materials removeExcess(final Materials max) {
		final Materials res = new Materials();
		final Integer munny = this.getMunny();
		final Integer coal = this.getGummiCoal();
		final Integer mytrhil = this.getMytrhil();

		if (munny <= max.getMunny())
			res.setMunny(munny);
		else
			res.setMunny(max.getMunny());

		if (coal <= max.getGummiCoal())
			res.setGummiCoal(coal);
		else
			res.setGummiCoal(max.getGummiCoal());

		if (mytrhil <= max.getMytrhil())
			res.setMytrhil(mytrhil);
		else
			res.setMytrhil(max.getMytrhil());

		return res;

	}
	@Transient
	@Override
	public String toString() {
		return "Munny=" + this.munny + ", Mytrhil=" + this.mytrhil + ", Gummi Coal=" + this.gummiCoal;
	}
	@Transient
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.gummiCoal == null) ? 0 : this.gummiCoal.hashCode());
		result = prime * result + ((this.munny == null) ? 0 : this.munny.hashCode());
		result = prime * result + ((this.mytrhil == null) ? 0 : this.mytrhil.hashCode());
		return result;
	}
	@Transient
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Materials other = (Materials) obj;
		if (this.gummiCoal == null) {
			if (other.gummiCoal != null)
				return false;
		} else if (!this.gummiCoal.equals(other.gummiCoal))
			return false;
		if (this.munny == null) {
			if (other.munny != null)
				return false;
		} else if (!this.munny.equals(other.munny))
			return false;
		if (this.mytrhil == null) {
			if (other.mytrhil != null)
				return false;
		} else if (!this.mytrhil.equals(other.mytrhil))
			return false;
		return true;
	}

}
