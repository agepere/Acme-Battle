
package form;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Building;

public class BuiltForm {

	private Building	building;


	@NotNull
	@Valid
	public Building getBuilding() {
		return this.building;
	}

	public void setBuilding( Building building) {
		this.building = building;
	}

}
