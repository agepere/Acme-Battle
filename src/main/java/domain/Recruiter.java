
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Recruiter extends Building {

	private Collection<GummiShip>	gummiShips;
	private Collection<Troop>		troops;


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recruiter")
	public Collection<GummiShip> getGummiShips() {
		return this.gummiShips;
	}

	public void setGummiShips( Collection<GummiShip> gummiShips) {
		this.gummiShips = gummiShips;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recruiter")
	public Collection<Troop> getTroops() {
		return this.troops;
	}

	public void setTroops( Collection<Troop> troops) {
		this.troops = troops;
	}

}
