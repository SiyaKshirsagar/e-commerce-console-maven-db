package main.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import main.Login;

public class Productmanagement {

    public static void pmangement(Connection connection) throws SQLException {
        System.out.println("*******************welcome to product mangement***********");
        Scanner sc = new Scanner(System.in);
        ArrayList<Product> al = new ArrayList<>();

        Statement statement = connection.createStatement();
        loadProductsFromDatabase(statement, al);

        while (true) {
            System.out.println("1.Add product");
            System.out.println("2.Update product");
            System.out.println("3.Search product");
            System.out.println("4.Remove product");
            System.out.println("5. Print all products");
            System.out.println("6..n. Quit");

            int option = sc.nextInt();

            // Clear the newline character from the input buffer
            sc.nextLine();

            if (option == 1) {
                Product p = new Product();
                System.out.println("----------------------------------Add Product----------------------------------------");
                System.out.print("Enter product name: ");
                p.name = sc.nextLine(); // Using nextLine() to read full name
                System.out.print("Enter product price: ");
                p.price = sc.nextInt();
                System.out.print("Enter product quantity: ");
                p.quantity = sc.nextInt();
                sc.nextLine(); // Clear newline character after nextInt()

                al.add(p);
                String insertQuery = "INSERT INTO Product (name, price, quantity) VALUES ('" + p.name + "', " + p.price + ", " + p.quantity + ");";
                statement.executeUpdate(insertQuery);
                System.out.println("Product added successfully.\n");

            } else if (option == 2) {
                System.out.println("--------------------------Update Product-------------------------");
                System.out.print("Enter the name of the product to update: ");
                String nameToUpdate = sc.nextLine();
                Product productToUpdate = null;

                for (Product product : al) {
                    if (product.name.equalsIgnoreCase(nameToUpdate)) {
                        productToUpdate = product;
                        break;
                    }
                }
                if (productToUpdate != null) {
                    System.out.print("Enter new name (or press Enter to keep the current name): ");
                    String newName = sc.nextLine();
                    if (!newName.isEmpty()) {
                        productToUpdate.name = newName;
                    }
                    System.out.print("Enter new price: ");
                    productToUpdate.price = sc.nextInt();
                    System.out.print("Enter new quantity: ");
                    productToUpdate.quantity = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Product updated successfully.\n");

                    // Update database as well
                    String updateQuery = "UPDATE Product SET name = '" + productToUpdate.name + "', price = " + productToUpdate.price + ", quantity = " + productToUpdate.quantity + " WHERE name = '" + nameToUpdate + "';";
                    statement.executeUpdate(updateQuery);

                } else {
                    System.out.println("Product not found.\n");
                }

            } else if (option == 3) {
                System.out.println("^^^^^^^^^^^^^Search Product Details^^^^^^^^^^^^^^^^^^");
                System.out.print("Enter the product name to search: ");
                String searchname = sc.nextLine().trim();
                boolean found = false;

                for (Product product : al) {
                    if (product.name.equalsIgnoreCase(searchname)) {
                        System.out.println("Product found: " + product.name + ", Price: " + product.price + ", Quantity: " + product.quantity);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Product not found.\n");
                }

            } else if (option == 4) {
                System.out.print("Enter the name of the product to remove: ");
                String nameToRemove = sc.nextLine().trim(); // Use nextLine() here to ensure we get the full input
                boolean removed = false;

                // Use iterator to safely remove the product while iterating
                Iterator<Product> iterator = al.iterator();
                while (iterator.hasNext()) {
                    Product product = iterator.next();
                    if (product.name.equalsIgnoreCase(nameToRemove)) {
                        iterator.remove(); // Safe removal using iterator
                        System.out.println("Product removed successfully.\n");
                        removed = true;
                        break; // Exit after removal
                    }
                }

                if (!removed) {
                    System.out.println("Product not found.\n");
                }

                if (removed) {
                    // Remove product from the database as well
                    String deleteQuery = "DELETE FROM Product WHERE name = '" + nameToRemove + "';";
                    statement.executeUpdate(deleteQuery);
                }

            } else if (option == 5) {

                System.out.println("************** Product List **************");
                if (al.isEmpty()) {
                    System.out.println("No products to display.");
                } else {
                    for (Product product : al) {
                        System.out.println("Name: " + product.name + ", Price: " + product.price + ", Quantity: " + product.quantity);
                    }
                }
                System.out.println();

            } else if (option == 6) {
                System.out.println("Exiting Product Management System. Goodbye!\n");
                break; // Exits and returns to the Ecommerce class
            } else {
                System.out.println("Invalid option. Please try again.\n");
                return; // Returns to the Ecommerce class
            }
        }
    }

    private static void loadProductsFromDatabase(Statement statement, ArrayList<Product> al) throws SQLException {
        String selectQuery = "SELECT * FROM Product;";
        ResultSet rs = statement.executeQuery(selectQuery);

        // Populate ArrayList with products from database
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            int quantity = rs.getInt("quantity");

            Product product = new Product();
            product.name = name;
            product.price = price;
            product.quantity = quantity;

            al.add(product); // Add to the in-memory list
        }
    }
}
