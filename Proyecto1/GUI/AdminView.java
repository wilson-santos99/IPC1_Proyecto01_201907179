package Proyecto1.GUI;

import Proyecto1.Componentes.StyledButton;
import Proyecto1.Componentes.CustomDialog;
import Proyecto1.Componentes.Header;
import Proyecto1.Componentes.ImageLoader;
import Proyecto1.Persistence.PersistenceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        // Botón: Módulo de Productos
        StyledButton productButton = new StyledButton("Módulo de Productos", new Color(100, 181, 246));
        productButton.setBounds(50, 350, 200, 50);
        productButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminFrame.dispose();
                ProductModule.productView();
            }
        });
        adminFrame.add(productButton);

        // Botón: Módulo de Clientes
        StyledButton clientButton = new StyledButton("Módulo de Clientes", new Color(77, 182, 172));
        clientButton.setBounds(300, 350, 200, 50);
        clientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminFrame.dispose();
                ClientModule.clientView();
            }
        });
        adminFrame.add(clientButton);

        // Botón: Módulo de Ventas
        StyledButton salesButton = new StyledButton("Módulo de Ventas", new Color(255, 183, 77));
        salesButton.setBounds(550, 350, 200, 50);
        salesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminFrame.dispose();
                SalesModule.salesView();
            }
        });
        adminFrame.add(salesButton);

        // Botón: Guardar Datos
        StyledButton saveButton = new StyledButton("Guardar Datos", new Color(156, 39, 176));
        saveButton.setBounds(300, 420, 200, 50);
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    PersistenceManager.saveData();
                    CustomDialog.showSuccessMessage(" Datos guardados exitosamente", "¡Éxito!");
                } catch (Exception ex) {
                    CustomDialog.showSuccessMessage("Error al guardar datos: " + ex.getMessage(),"Error");
                }
            }
        });
        adminFrame.add(saveButton);

        // Botón: Cerrar Sesión
        StyledButton logoutButton = new StyledButton("Cerrar Sesión", new Color(244, 67, 54));
        logoutButton.setBounds(300, 490, 200, 50);
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminFrame.dispose();
                LoginFrame.loginView();
            }
        });
        adminFrame.add(logoutButton);

        adminFrame.setVisible(true);
    }
}
