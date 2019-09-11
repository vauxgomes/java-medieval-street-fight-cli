package core;

import java.io.Serializable;
import java.util.ArrayList;

import core.weapon.Rest;

/**
 * 
 * @author Vaux Gomes
 * @since 2019.09.10
 */
public abstract class Character implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = -2871736694728844376L;

	/* Basic stats */
	private int hp;

	/* Stats altered by items */
	private int attack;
	private int magic;
	private int defense;
	private int swing;
	private int stamina;

	/* Weapons */
	private ArrayList<Weapon> weapons;

	/**
	 * @param hp
	 * @param attack
	 * @param magic
	 * @param defense
	 * @param swing
	 * @param stamina
	 */
	public Character(int hp, int attack, int magic, int defense, int swing, int stamina) {
		this.hp = hp;
		this.attack = attack;
		this.magic = magic;
		this.defense = defense;
		this.swing = swing;
		this.stamina = stamina;

		this.weapons = new ArrayList<Weapon>(2);
	}

	public void addWeapon(Weapon w) {
		this.weapons.add(w);
		
		this.defense += w.getDefense();
		this.swing += w.getSwing();
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public Weapon getWeapon(int index) {
		return this.weapons.get(index);
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = Math.max(0, hp);
	}

	public int getAttack() {
		return attack;
	}

	public int getMagic() {
		return magic;
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

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	
	public String getRace() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return String.format("%-7s {HP:%2d At:%2d Mg:%2d Df:%2d Sw:%2d St:%2d}", this.getClass().getSimpleName(),
				this.hp, this.attack, this.magic, this.defense, this.swing, this.stamina);
	}

}
