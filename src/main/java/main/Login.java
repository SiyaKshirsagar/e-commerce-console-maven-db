package main;

import java.util.Scanner;

public class Login {
	public static void login() {
	Scanner sc = new Scanner(System.in);
	System.out.println("Enter your username : ");
	String username = sc.next();
	System.out.println("Enter your Password : ");
	String pass = sc.next();

	if (username.equals("Siya")) {
		if (pass.equals("Sk2022")) {
			System.out.println("Login Succefully..!\n");
			return;
		}
	}
	System.out.println("Login fail...!\n");
	System.exit(0);
   }
}