// Archivo LoginFrame.java
package Proyecto1.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class LoginFrame {
    static String[] adminData = { "ss", "dd" };

    public static void loginView() {
        // Frame principal
        JFrame loginFrame = new JFrame("Sistema Dojo");
        loginFrame.setSize(800, 650);
        loginFrame.setLayout(null);
        loginFrame.setResizable(false);
        loginFrame.getContentPane().setBackground(new Color(45, 52, 54)); // Fondo gris oscuro elegante
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel contenedor con ajuste al tamaño del frame
        JPanel panel = new JPanel();
        panel.setBackground(new Color(55, 71, 79)); // Fondo gris azulado oscuro
        panel.setBounds(200, 100, 400, 450); // Centrado con más ajuste al frame
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(new Color(100, 181, 246), 2)); // Borde azul claro
        loginFrame.add(panel);

        // Avatar
        JLabel avatarLabel = new JLabel();
        avatarLabel.setBounds(150, 20, 100, 100);
        ImageIcon avatarIcon = new ImageIcon(new ImageIcon("./Proyecto1/Resources/avatar.png").getImage()
                .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        avatarLabel.setIcon(avatarIcon);
        panel.add(avatarLabel);

        // Bienvenida
        JLabel welcomeLabel = new JLabel("¡Bienvenido a Dojo Store!");
        welcomeLabel.setForeground(new Color(224, 247, 250)); // Texto azul claro
        welcomeLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        welcomeLabel.setBounds(75, 130, 250, 30);
        panel.add(welcomeLabel);

        // Campos de texto
        JTextField usernameField = createRoundedTextField();
        usernameField.setBounds(50, 180, 300, 50);
        panel.add(usernameField);

        JPasswordField passwordField = createRoundedPasswordField();
        passwordField.setBounds(50, 250, 300, 50);
        panel.add(passwordField);

        // Botón de inicio de sesión
        JButton loginButton = createRoundedButton("Iniciar Sesión", new Color(100, 181, 246));
        loginButton.setBounds(100, 320, 200, 50);
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (usernameField.getText().equals(adminData[0])
                        && new String(passwordField.getPassword()).equals(adminData[1])) {
                    showCustomMessage(
                            "<html><center><h2 style='color:#4CAF50;'>Inicio de sesión exitoso</h2></center></html>",
                            "¡Éxito!", "/Proyecto1/Resources/check.png");
                    loginFrame.dispose();
                    AdminView.adminView();
                } else {
                    showCustomMessage(
                            "<html><center><h3 style='color:#FF5252;'>✘ Usuario o contraseña incorrectos</h3></center></html>",
                            "Error", "/Proyecto1/Resources/error.png");
                }
            }
        });
        panel.add(loginButton);

        loginFrame.setVisible(true);
    }

    private static JTextField createRoundedTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createLineBorder(new Color(100, 181, 246), 2, true));
        return textField;
    }

    private static JPasswordField createRoundedPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 14));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(100, 181, 246), 2, true));
        return passwordField;
    }

    private static JButton createRoundedButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? backgroundColor.brighter() : backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(backgroundColor.darker());
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            }
        };
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    private static void showCustomMessage(String message, String title, String iconPath) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(new ImageIcon(LoginFrame.class.getResource(iconPath))
                    .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Error cargando el ícono: " + iconPath);
        }

        JLabel label = new JLabel(message, icon, JLabel.CENTER);
        label.setIconTextGap(10);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        UIManager.put("OptionPane.background", new Color(255, 255, 255));
        UIManager.put("Panel.background", new Color(255, 255, 255));
        UIManager.put("OptionPane.messageFont", new Font("Roboto", Font.PLAIN, 14));
        JOptionPane.showMessageDialog(null, label, title, JOptionPane.PLAIN_MESSAGE);
    }
}
