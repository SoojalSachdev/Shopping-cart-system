/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Soojal
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Order {
    private User user;
    private List<Product> products;
    private double totalAmount;

    public Order(User user, List<Product> products, double totalAmount) {
        this.user = user;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) {
            writer.write("User: " + user.getUsername() + ", Email: " + user.getEmail() + "\n");
            for (Product p : products) {
                writer.write(" - " + p.getName() + " : " + p.getPrice() + "\n");
            }
            writer.write("Total: " + totalAmount + "\n");
            writer.write("-----\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

