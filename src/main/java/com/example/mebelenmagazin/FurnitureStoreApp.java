package com.example.mebelenmagazin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FurnitureStoreApp extends JFrame {

    static final String DB_URL = "jdbc:postgresql://localhost/furniture_store?user=username&password=password";
    static final String USER = "username";
    static final String PASS = "password";

    private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameField = new JTextField(20);
    private JLabel descriptionLabel = new JLabel("Description:");
    private JTextArea descriptionArea = new JTextArea(5, 20);
    private JLabel priceLabel = new JLabel("Price:");
    private JTextField priceField = new JTextField(20);
    private JButton createButton = new JButton("Create");
    private JButton deleteButton = new JButton("Delete");
    private JButton showButton = new JButton("Show");

    public FurnitureStoreApp() {
        super("Furniture Store Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionArea);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        inputPanel.add(createButton);
        inputPanel.add(deleteButton);
        inputPanel.add(showButton);
        add(inputPanel, BorderLayout.CENTER);

        createButton.addActionListener(e -> createProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        showButton.addActionListener(e -> {
            try {
                showProducts();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Display the window
        pack();
        setVisible(true);
    }

    // Method to create a new product in the database
    private void createProduct() {
        String name = nameField.getText();
        String description = descriptionArea.getText();
        double price = Double.parseDouble(priceField.getText());

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO products (name, description, price) VALUES (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product created successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating product: " + ex.getMessage());
        }
    }

    // Method to delete a product from the database
    private void deleteProduct() {
        String name = nameField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM products WHERE name = ?")) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Product deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Product not found.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting product: " + ex.getMessage());
        }
    }

    // Method to show all products in the database
    private void showProducts() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, description, price FROM products")) {
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                sb.append("Name: ").append(name).append("\n");
                sb.append("Description: ").append(description).append("\n");
                sb.append("Price: ").append(price).append("\n\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error showing products: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        new FurnitureStoreApp();
    }
}
