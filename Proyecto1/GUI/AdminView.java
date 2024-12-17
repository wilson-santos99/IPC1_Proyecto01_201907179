// Archivo AdminView.java
package Proyecto1.GUI;

import Proyecto1.Persistence.PersistenceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView {
    public static void adminView() {
        JFrame adminFrame = new JFrame("Vista de Administrador");
        adminFrame.setLayout(null);
        adminFrame.setSize(800, 650);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.getContentPane().setBackground(new Color(45, 52, 54)); // Fondo gris oscuro elegante
        adminFrame.setResizable(false); // Esto bloquea el redimensionamiento de la ventana
        // Título del Panel
        JLabel titleLabel = new JLabel("Panel de Administración", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 26));
        titleLabel.setForeground(new Color(224, 247, 250)); // Azul claro para texto
        titleLabel.setBounds(150, 20, 500, 50);
        adminFrame.add(titleLabel);

        // Logo central
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(300, 100, 200, 200);
        ImageIcon logoIcon = new ImageIcon(new ImageIcon("./Proyecto1/Resources/avatar.png").getImage()
                .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        logoLabel.setIcon(logoIcon);
        adminFrame.add(logoLabel);

        // Botón: Módulo de Productos
        JButton productButton = createStyledRoundedButton("Módulo de Productos", new Color(100, 181, 246));
        productButton.setBounds(50, 350, 200, 50);
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                ProductModule.productView();
            }
        });
        adminFrame.add(productButton);

        // Botón: Módulo de Clientes
        JButton clientButton = createStyledRoundedButton("Módulo de Clientes", new Color(77, 182, 172));
        clientButton.setBounds(300, 350, 200, 50);
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                ClientModule.clientView();
            }
        });
        adminFrame.add(clientButton);

        // Botón: Módulo de Ventas
        JButton salesButton = createStyledRoundedButton("Módulo de Ventas", new Color(255, 183, 77));
        salesButton.setBounds(550, 350, 200, 50);
        salesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                SalesModule.salesView();
            }
        });
        adminFrame.add(salesButton);

        // Botón: Guardar Datos
        JButton saveButton = createStyledRoundedButton("Guardar Datos", new Color(156, 39, 176));
        saveButton.setBounds(300, 420, 200, 50);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PersistenceManager.saveData();
                    JOptionPane.showMessageDialog(adminFrame, "<html><center><h3 style='color:#4CAF50;'>✓ Datos guardados exitosamente</h3></center></html>");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(adminFrame, "<html><center><h3 style='color:#FF5252;'>✘ Error al guardar datos: " + ex.getMessage() + "</h3></center></html>", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        adminFrame.add(saveButton);

        // Botón: Cerrar Sesión
        JButton logoutButton = createStyledRoundedButton("Cerrar Sesión", new Color(244, 67, 54));
        logoutButton.setBounds(300, 490, 200, 50);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                LoginFrame.loginView();
            }
        });
        adminFrame.add(logoutButton);

        adminFrame.setVisible(true);
    }

    private static JButton createStyledRoundedButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? backgroundColor.brighter() : backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(backgroundColor.darker());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }
}
