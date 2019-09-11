package core;

import java.io.Serializable;

public abstract class Weapon implements Serializable {

	public enum Type {
		Physical(), Magic(), Void()
	}

	/** SERIAL ID */
	private static final long serialVersionUID = 1385174153122134065L;

	/* Stats */
	private int attack;
	private int defense;
	private int swing;
	private int stamina;
	private Type type;

	/**
	 * @param attack
	 * @param magic   - fail
	 * @param defense
	 * @param swing
	 * @param stamina
	 * @param type
	 */
	public Weapon(int attack, int defense, int swing, int stamina, Type type) {
		super();
		this.attack = attack;
		this.defense = defense;
		this.swing = swing;
		this.stamina = stamina;
		this.type = type;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getSwing() {
		return swing;
	}

	public int getStamina() {
		return stamina;
	}

	public boolean isPhysical() {
		return this.type == Type.Physical;
	}

	@Override
	public String toString() {
		return String.format("%-13s {At:%2d Df:%2d Sw:%2d St:%2d}", this.getClass().getSimpleName(), this.attack,
				this.defense, this.swing, this.stamina);
	}

}
