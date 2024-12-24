package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import main.product.Productmanagement;
import main.user.Usermanagement;

public class Ecommerce {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ecommercedb_maven";
        String username = "Ecommerce";
        String password = "@ecommercedb24";

        // Establish database connection
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Database connected successfully!");

        // Setup database
        setupDatabase(connection);

        System.out.println("****************************************** Welcome to Ecommerce Services ******************************************");
        Scanner sc = new Scanner(System.in);
        Login.login();

        while (true) {
            System.out.println("What would you like to do today?");
            System.out.println("1. Product Management");
            System.out.println("2. User Management");
            System.out.println("3. Another Services");
            System.out.println("4. Exit");
            int option = sc.nextInt();

            if (option == 1) {
                Productmanagement.pmangement(connection);
            } else if (option == 2) {
                Usermanagement.usermanagement(connection);
            } else if (option == 3) {
                System.out.println("To be implemented");
            } else if (option == 4) {
                System.out.println("Thank you!");
                return;
            } else {
                System.out.println("Invalid option selected. Exiting.");
                return;
            }
        }
    }

    private static void setupDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String loginTable = "CREATE TABLE IF NOT EXISTS LOGIN (" +
                "username VARCHAR(50) NOT NULL, " +
                "password VARCHAR(50) NOT NULL, " +
                "PRIMARY KEY(username)" +
                ");";
        statement.execute(loginTable);

        String insertLogin = "INSERT IGNORE INTO LOGIN (username, password) VALUES ('Siya', 'Sk2022');";
        statement.execute(insertLogin);

        String productTable = "CREATE TABLE IF NOT EXISTS Product (" +
                "name VARCHAR(50) NOT NULL, " +
                "price INT NOT NULL, " +
                "quantity INT NOT NULL, " +
                "PRIMARY KEY(name)" +
                ");";
        statement.execute(productTable);

        String userTable = "CREATE TABLE IF NOT EXISTS User (" +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL, " +
                "password VARCHAR(50) NOT NULL, " +
                "PRIMARY KEY(email)" +
                ");";
        statement.execute(userTable);

        System.out.println("Database setup completed successfully.");
    }
}
