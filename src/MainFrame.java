

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Soojal
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class MainFrame extends javax.swing.JFrame {

    private User currentUser;
    DefaultListModel<String> cartModel = new DefaultListModel<>();
    DefaultListModel<String> productModel = new DefaultListModel<>();
private String formatProduct(String id, String name, String price, String description) {
    return String.format("%-10s| %-20s| %-25s| %-10s", id, name, description, price);
}
    private boolean isDarkMode = false;

    public MainFrame(User user) {
        this.currentUser = user;
        initComponents();
        setTitle("Online Shopping Cart");
        setSize(740, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jList1.setModel(cartModel);
        jList2.setModel(productModel);
        jList1.setFont(new Font("Monospaced", Font.PLAIN, 8));
        jList2.setFont(new Font("Monospaced", Font.PLAIN, 8));
        loadAllProducts();

        jButton1.addActionListener(e -> searchProducts());
        jButton2.addActionListener(e -> addToCart());
        jButton3.addActionListener(e -> checkout());

        // Toggle theme functionality
        jToggleButton1.addActionListener(e -> toggleTheme());
        
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;

        Color bgColor = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;
        Color buttonColor = isDarkMode ? new Color(70, 70, 70) : UIManager.getColor("Button.background");

        jToggleButton1.setText(isDarkMode ? "Light Mode" : "Dark Mode");

        Component[] components = {
            jPanel1, jPanel2, jPanel3,
            jButton1, jButton2, jButton3, jToggleButton1,
            jTextField1, jList1, jList2,
            jLabel1, jLabel2
        };

        for (Component comp : components) {
            comp.setBackground(bgColor);
            comp.setForeground(fgColor);
            if (comp instanceof JButton || comp instanceof JToggleButton) {
                comp.setBackground(buttonColor);
            }
        }

        jList1.setSelectionBackground(isDarkMode ? Color.GRAY : UIManager.getColor("List.selectionBackground"));
        jList1.setSelectionForeground(isDarkMode ? Color.WHITE : UIManager.getColor("List.selectionForeground"));

        jList2.setSelectionBackground(isDarkMode ? Color.GRAY : UIManager.getColor("List.selectionBackground"));
        jList2.setSelectionForeground(isDarkMode ? Color.WHITE : UIManager.getColor("List.selectionForeground"));
    }

    private void loadAllProducts() {
    productModel.clear();
    // Add header
    productModel.addElement(formatProduct("ID", "Name", "Price", "Description"));
    
    try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String id = parts[0];
                    String name = parts[1];
                    String price = parts[2];
                    String description = "This is the " + name;
                    productModel.addElement(formatProduct(id, name, price, description));
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading products file.");
    }
}

    private void searchProducts() {
    String searchTerm = jTextField1.getText().trim().toLowerCase();
    productModel.clear();
    productModel.addElement(formatProduct("ID", "Name", "Price", "Description"));

    if (searchTerm.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a search term.");
        loadAllProducts();
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
        String line;
        boolean found = false;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String id = parts[0];
                String name = parts[1];
                String price = parts[2];
                if (line.toLowerCase().contains(searchTerm)) {
                    String description = "This is the " + name;
                    productModel.addElement(formatProduct(id, name, price, description));
                    found = true;
                }
            }
        }

        if (!found) {
            productModel.addElement("No matching products found.");
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading product file.");
    }
}

    private void addToCart() {
        String selectedProduct = jList2.getSelectedValue();
        if (selectedProduct != null && !selectedProduct.equals("No matching products found.")) {
            cartModel.addElement(selectedProduct);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a valid product to add to cart.");
        }
    }

    private void checkout() {
        if (cartModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }

        StringBuilder receipt = new StringBuilder("You purchased:\n\n");
        List<String> orderItems = new ArrayList<>();

        for (int i = 0; i < cartModel.size(); i++) {
            String item = cartModel.getElementAt(i);
            receipt.append("- ").append(item).append("\n");
            orderItems.add(item);
        }

        JOptionPane.showMessageDialog(this, receipt.toString(), "Checkout Summary", JOptionPane.INFORMATION_MESSAGE);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) {
            writer.write("Order placed by: " + currentUser.getUsername() + " (" + currentUser.getEmail() + ")\n");
            for (String item : orderItems) {
                writer.write(item);
                writer.newLine();
            }
            writer.write("----------\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving order to file.");
        }

        cartModel.clear();
    }
  public static void main(String args[]) {
        User user = new User("Soojal_Sachdev", "soojasachdev2@gmail.com");
        SwingUtilities.invokeLater(() -> new MainFrame(user).setVisible(true));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Search Product:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 100, 20));

        jTextField1.setToolTipText("");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 180, 23));

        jButton1.setLabel("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, -1, -1));

        jButton2.setText("Add To Cart");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, -1, -1));

        jToggleButton1.setText("Dark Mode");
        jPanel1.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, -1, -1));

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jLabel2.setText("Cart");

        jScrollPane1.setName(""); // NOI18N

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setName("cartlist"); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jButton3.setLabel("Checkout");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables

}