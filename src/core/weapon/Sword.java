package core.weapon;

import core.Weapon;

public class Sword extends Weapon {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	public Sword() {
		super(5, 2, 5, 3, Type.Physical);
	}
}
