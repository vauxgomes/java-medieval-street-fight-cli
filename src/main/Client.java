package main;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import core.Character;
import core.Message;
import core.Weapon;
import core.chars.Alien;
import core.chars.Dwarf;
import core.chars.Elf;
import core.chars.Human;
import core.chars.Tetujin;
import core.weapon.BowArrow;
import core.weapon.Dagger;
import core.weapon.MagicBookFire;
import core.weapon.MagicBookWind;
import core.weapon.Rest;
import core.weapon.Shield;
import core.weapon.Sword;
import util.Util;

public class Client {
	public static void main(String[] args) {
		//
		Character[] chars = new Character[] { new Alien(), new Dwarf(), new Elf(), new Human(), new Tetujin() };
		Weapon[] weapons = new Weapon[] { new BowArrow(), new Dagger(), new MagicBookFire(), new MagicBookWind(),
				new Shield(), new Sword() };

		System.out.println("Welcome to MEDIEVAL STREET FIGHT");
		System.out.println("================================");

		int charType = -1;
		int firstWeaponType = 1;
		int secondWeaponType = 2;

		do {
			charType = Util.showMenu("Choose your character", (Object[]) chars);
		} while (charType < 0);

		if (charType == 0) {
			exit(0);
		}

		firstWeaponType = Util.showMenu("Choose your first weapon", (Object[]) weapons);
		secondWeaponType = Util.showMenu("Choose your second weapon", (Object[]) weapons);

		while (firstWeaponType == secondWeaponType && secondWeaponType != 0) {
			try {
				System.out.println("[!]: You can't choose the same weapon twice");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}

			secondWeaponType = Util.showMenu("Choose your second weapon", (Object[]) weapons);
		}

		//
		Character c = chars[charType - 1];

		if (firstWeaponType > 0)
			c.addWeapon(weapons[firstWeaponType - 1]);

		if (secondWeaponType > 0)
			c.addWeapon(weapons[secondWeaponType - 1]);

		// Extra "weapon"
		c.addWeapon(new Rest());

		System.out.println("Character:");
		System.out.println(" " + c);
		System.out.println("Weapons:");
		for (Weapon w : c.getWeapons()) {
			System.out.println(" " + w);
		}

		// Socket
		Socket socket;

		// Objects
		ObjectInputStream input = null;
		ObjectOutputStream output = null;

		try {
			System.out.println("\n\nTrying connection with Server");
			System.out.println("Info:");
			System.out.println(" Host: " + Util.Connection.HOST);
			System.out.println(" Port: " + Util.Connection.PORT);

			socket = new Socket(Util.Connection.HOST, Util.Connection.PORT);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

			System.out.println("[!]: Connected!\n");
		} catch (Exception e) {
			System.out.println("[!]: Connection lost!");
			exit(1);
		}

		// Game start
		try {
			output.writeObject(c);

			boolean ever = true;
			for (; ever;) {
				Message m = (Message) input.readObject();

				switch (m.getType()) {
				case MESSAGE:
					System.out.println(m.getData());
					break;

				case UPDATE:
					System.out.println("Character update");
					System.out.println("Before:");
					System.out.println(" " + c);

					System.out.println("After:");
					c = (Character) m.getData();
					System.out.println(" " + c);
					
					break;

				case END:
					ever = false;
					break;

				case ATTACK:
					int index = Util.showMenu("Choose the weapon to attack", c.getWeapons().toArray());
					Weapon w = c.getWeapon(index - 1);

					output.writeObject(w);
				default:
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("[!]: Error during normal game");
		}

		exit(0);
	}

	private static void exit(int e) {
		System.out.println("Game ended");
		System.exit(e);
	}
}
