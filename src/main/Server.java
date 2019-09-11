package main;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import core.Character;
import core.Message;
import core.Message.Type;
import core.weapon.Rest;
import core.Weapon;
import util.Util;

public class Server {
	public static void main(String[] args) {

		Random rnd = new Random();

		try {
			ServerSocket server = new ServerSocket(Util.Connection.PORT);
			System.out.println("Server is up on port " + Util.Connection.PORT);

			// Clients
			Socket c1 = server.accept();
			Socket c2 = server.accept();

			// Objects
			ObjectOutputStream[] outputs = new ObjectOutputStream[2];
			ObjectInputStream[] inputs = new ObjectInputStream[2];

			outputs[0] = new ObjectOutputStream(c1.getOutputStream());
			inputs[0] = new ObjectInputStream(c1.getInputStream());
			outputs[1] = new ObjectOutputStream(c2.getOutputStream());
			inputs[1] = new ObjectInputStream(c2.getInputStream());

			// Chars
			Character[] chs = new Character[] { (Character) inputs[0].readObject(),
					(Character) inputs[1].readObject() };

			int turn = rnd.nextInt(2);
			int counterTurn = (turn + 1) % 2;

			System.out.println("Starting duel");
			boolean ever = true;
			for (; ever;) {
				System.out.println("Char " + chs[turn].getRace() + "(" + turn + ") attacking");

				// Request
				outputs[turn].writeObject(new Message(Type.ATTACK));
				Weapon w = (Weapon) inputs[turn].readObject();

				// Validate Weapon Usage
				if (w.getStamina() > chs[turn].getStamina()) {
					outputs[turn].writeObject(new Message(Type.MESSAGE, "Invalid move! You'll rest for this round."));
					w = new Rest();
				}

				System.out.println("Attack:\n " + w);

				// Attack
				chs[turn].setStamina(chs[turn].getStamina() - w.getStamina());

				if (!(w instanceof Rest)) {
					if (chs[counterTurn].getSwing() < rnd.nextInt(30)) {
						int attack = w.getAttack();

						if (w.isPhysical())
							attack += chs[turn].getAttack();
						else
							attack += chs[turn].getMagic();

						chs[counterTurn].setHp(chs[counterTurn].getHp() - attack);
						outputs[counterTurn].writeObject(new Message(Type.MESSAGE, "You're under attack!"));
					} else {
						outputs[turn].writeObject(new Message(Type.MESSAGE, "You missed!"));
					}
				}

				// Natural rest of the counterTurn
				chs[counterTurn].setStamina(chs[counterTurn].getStamina() + 2);

				System.out.println("Status: ");
				System.out.println(" " + chs[0]);
				System.out.println(" " + chs[1]);

				// Update
				for (int i = 0; i < chs.length; i++) {
					outputs[i].writeObject(new Message(Type.UPDATE, chs[i]));
				}

				// The end
				if (chs[counterTurn].getHp() <= 0) {
					outputs[turn].writeObject(new Message(Type.MESSAGE, "You win!"));
					outputs[counterTurn].writeObject(new Message(Type.MESSAGE, "You lose!"));

					for (int i = 0; i < chs.length; i++) {
						outputs[i].writeObject(new Message(Type.END));
					}

					ever = false;

					System.out.println("\nTHE GAME ENDED");
					System.out.println("WIN: " + chs[turn].getRace());
					System.out.println("LOSE: " + chs[turn].getRace());
				}

				turn = (turn + 1) % 2;
				counterTurn = (turn + 1) % 2;
			}

			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
