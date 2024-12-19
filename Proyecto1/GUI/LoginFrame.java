package Proyecto1.GUI;

import Proyecto1.Componentes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame {
    static String[] adminData = {"sensei_201907179", "ipc1_201907179"};

    public static void loginView() {
        JFrame loginFrame = new JFrame("Sistema Dojo");
        loginFrame.setSize(800, 650);
        loginFrame.setLayout(null);
        loginFrame.setResizable(false);
        loginFrame.getContentPane().setBackground(new Color(45, 52, 54));
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = createMainPanel();
        loginFrame.add(panel);

        // Avatar
        JLabel avatarLabel = createAvatar();
        panel.add(avatarLabel);

        // Bienvenida
        JLabel welcomeLabel = createWelcomeLabel();
        panel.add(welcomeLabel);

        // Campos de texto
        JTextField usernameField = new RoundedTextField();
        usernameField.setBounds(75, 200, 300, 50);
        panel.add(usernameField);

        JPasswordField passwordField = new RoundedPasswordField();
        passwordField.setBounds(75, 270, 300, 50);
        panel.add(passwordField);

        // Botón de inicio de sesión
        StyledButton loginButton = new StyledButton("Iniciar Sesión", new Color(100, 181, 246));
        loginButton.setBounds(125, 350, 200, 50);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (usernameField.getText().equals(adminData[0]) &&
                        new String(passwordField.getPassword()).equals(adminData[1])) {
                    CustomDialog.showSuccessMessage("¡Inicio de sesión exitoso!", "¡Éxito!");
                    loginFrame.dispose();
                    AdminView.adminView();
                } else {
                    CustomDialog.showErrorMessage("Usuario o contraseña incorrectos", "Error");
                }
            }
        });
        panel.add(loginButton);

        loginFrame.setVisible(true);
    }

    private static JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(55, 71, 79));
        panel.setBounds(175, 50, 450, 500);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(new Color(100, 181, 246), 2));
        return panel;
    }

    private static JLabel createAvatar() {
        JLabel avatarLabel = new JLabel();
        avatarLabel.setBounds(175, 20, 100, 100);
        ImageIcon avatarIcon = new ImageIcon(new ImageIcon("./Proyecto1/Resources/avatar.png")
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        avatarLabel.setIcon(avatarIcon);
        return avatarLabel;
    }

    private static JLabel createWelcomeLabel() {
        JLabel welcomeLabel = new JLabel("¡Bienvenido a Dojo Store!");
        welcomeLabel.setForeground(new Color(224, 247, 250));
        welcomeLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        welcomeLabel.setBounds(85, 140, 300, 30);
        return welcomeLabel;
    }
}
