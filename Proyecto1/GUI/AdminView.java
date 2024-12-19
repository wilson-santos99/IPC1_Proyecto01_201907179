package Proyecto1.GUI;

import Proyecto1.Componentes.StyledButton;
import Proyecto1.Componentes.Header;
import Proyecto1.Componentes.ImageLoader;
import Proyecto1.Persistence.PersistenceManager;

import javax.swing.*;
import java.awt.*;

public class AdminView {
    public static void adminView() {
        JFrame adminFrame = new JFrame("Vista de Administrador");
        adminFrame.setLayout(null);
        adminFrame.setSize(800, 650);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.getContentPane().setBackground(new Color(45, 52, 54));
        adminFrame.setResizable(false);

        // Título del Panel
        Header titleLabel = new Header("Panel de Administración");
        titleLabel.setBounds(150, 20, 500, 50);
        adminFrame.add(titleLabel);

        // Logo central
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(300, 100, 200, 200);
        logoLabel.setIcon(ImageLoader.loadImage("./Proyecto1/Resources/avatar.png", 200, 200));
        adminFrame.add(logoLabel);

        // Botones
        adminFrame.add(createButton("Módulo de Productos", new Color(100, 181, 246), 50, 350, () -> {
            adminFrame.dispose();
            ProductModule.productView();
        }));
        adminFrame.add(createButton("Módulo de Clientes", new Color(77, 182, 172), 300, 350, () -> {
            adminFrame.dispose();
            ClientModule.clientView();
        }));
        adminFrame.add(createButton("Módulo de Ventas", new Color(255, 183, 77), 550, 350, () -> {
            adminFrame.dispose();
            SalesModule.salesView();
        }));
        adminFrame.add(createButton("Guardar Datos", new Color(156, 39, 176), 300, 420, () -> {
            try {
                PersistenceManager.saveData();
                JOptionPane.showMessageDialog(adminFrame, "<html><center><h3 style='color:#4CAF50;'>✓ Datos guardados exitosamente</h3></center></html>");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(adminFrame, "<html><center><h3 style='color:#FF5252;'>✘ Error al guardar datos: " + ex.getMessage() + "</h3></center></html>", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }));
        adminFrame.add(createButton("Cerrar Sesión", new Color(244, 67, 54), 300, 490, () -> {
            adminFrame.dispose();
            LoginFrame.loginView();
        }));

        adminFrame.setVisible(true);
    }

    private static StyledButton createButton(String text, Color color, int x, int y, Runnable action) {
        StyledButton button = new StyledButton(text, color);
        button.setBounds(x, y, 200, 50);
        button.addActionListener(e -> action.run());
        return button;
    }
}
