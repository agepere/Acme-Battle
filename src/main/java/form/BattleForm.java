
package form;

import java.util.ArrayList;

public class BattleForm {

	private ArrayList<Integer>	troops;
	private String				enemy;


	public ArrayList<Integer> getTroops() {
		return this.troops;
	}
	public void setTroops(final ArrayList<Integer> troops) {
		this.troops = troops;
	}

	public String getEnemy() {
		return this.enemy;
	}
	public void setEnemy(final String enemy) {
		this.enemy = enemy;
	}
}
