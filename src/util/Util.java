package util;

import java.util.Scanner;

public class Util {

	public static int showMenu(String title, Object... items) {
		while (true) {
			System.out.println("\n" + title.toUpperCase());
			System.out.println("------------------------------------------------");
			for (int i = 0; i < items.length; i++)
				System.out.println((i + 1) + ": " + items[i]);
			System.out.println("\n0: Cancel");
			System.out.println("------------------------------------------------");
			System.out.print("Choose: ");

			try {
				@SuppressWarnings("resource")
				int n = Integer.parseInt(new Scanner(System.in).nextLine());
				if (n >= 0 && n <= items.length) {
					System.out.println("------------------------------------------------");
					return n;
				}
			} catch (Exception e) {
				System.out.println("[!]: Invalid");
			}
		}
	}
	
	public static class Connection {
		public static String HOST = "localhost";
		public static int PORT = 5000;
	}
	
	public static class Game {
		
	}
}
