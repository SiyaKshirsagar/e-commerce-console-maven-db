package main.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;



public class Usermanagement {
	public static void usermanagement(Connection connection) throws SQLException {
		Scanner sc =new Scanner(System.in);
		ArrayList<User> ul =new ArrayList<>();
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Welcome to User Management^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		while(true) {
			System.out.println("1.Add User details");
			System.out.println("2.Print user");
			System.out.println("3.Update user");
			System.out.println("4.Search user");
			System.out.println("5.Remove user");
			
			System.out.println("6..n.Quite");
			
			int option =sc.nextInt();
			
			if (option == 1) {
				User u = new User();
				System.out.println("Add User Details\n");
				System.out.println("Enter user name :");
				u.name = sc.next();
				System.out.println("Enter user email : ");
				u.email = sc.next();
				System.out.println("Enter user password : ");
				u.password = sc.next();
                ul.add(u);
                
                String insertQuery = "INSERT INTO User (name, email, password) VALUES ('" + u.name + "', '" + u.email + "', '" + u.password + "')";
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(insertQuery);
               
               
                System.out.println("User added successfully!\n");
                
				System.out.println("user Name" + u.name);
				System.out.println("user emailId" + u.email);
				System.out.println("usr password" + u.password + "\n");

			}else if (option == 2) {
				 System.out.println("************** User List **************");
				 ArrayList<User> usersFromDB = getUsersFromDatabase(connection); 
				 ul.clear();
	             ul.addAll(usersFromDB);
				 
			if (ul.isEmpty()) {
                System.out.println("No user to display.");
            }else {
                for (User user : ul) {
                    System.out.println("Name: " +user.name + ", Email Id: " + user.email + ", Password: " + user.password);
                }
            }
            System.out.println();
		}else if (option == 3) {
				System.out.println("To be implemented...");
			}else if (option == 4) {
				System.out.println("To be implemented...");
			}
		      else {
				System.out.println("Invalid option slected!!!");
				return;
			}
		}
	}
	
	      private static ArrayList<User> getUsersFromDatabase(Connection connection) throws SQLException {
	        ArrayList<User> userList = new ArrayList<>();
	        String query = "SELECT * FROM User";
	        Statement stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        while (rs.next()) {
	            User user = new User();
	            user.name = rs.getString("name");
	            user.email = rs.getString("email");
	            user.password = rs.getString("password");
	            userList.add(user);
	        }
			return userList;

}
	      }
