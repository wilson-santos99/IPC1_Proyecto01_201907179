package Proyecto1.GUI;

import Proyecto1.Componentes.StyledButton;
import Proyecto1.Sale.SalesActions;
import javax.swing.*;
import java.awt.*;

public class SalesModule {

    public static void salesView() {
        JFrame salesFrame = new JFrame("Gestión de Ventas");
        salesFrame.setLayout(null);
        salesFrame.setSize(800, 650);
        salesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        salesFrame.setLocationRelativeTo(null);
        salesFrame.getContentPane().setBackground(new Color(33, 40, 48));
        salesFrame.setResizable(false);

        // Título
        JLabel titleLabel = new JLabel("Módulo de Ventas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(new Color(230, 230, 230));
        titleLabel.setBounds(150, 20, 500, 30);
        salesFrame.add(titleLabel);

        // Botón Nueva Venta
        StyledButton newSaleButton = new StyledButton("Nueva Venta", new Color(77, 182, 172));
        newSaleButton.setBounds(50, 100, 200, 50);
        newSaleButton.addActionListener(e -> SalesActions.newSaleView());
        salesFrame.add(newSaleButton);

        // Botón Reporte de Ventas
        StyledButton salesReportButton = new StyledButton("Reporte de Ventas", new Color(255, 193, 7));
        salesReportButton.setBounds(300, 100, 200, 50);
        salesReportButton.addActionListener(e -> SalesActions.generateSalesReport());

        salesFrame.add(salesReportButton);

        // Botón Lista de Ventas
        StyledButton salesListButton = new StyledButton("Lista de Ventas", new Color(41, 121, 255));
        salesListButton.setBounds(550, 100, 200, 50);
        salesListButton.addActionListener(e -> {
            SalesActions.showSalesList();
            salesFrame.dispose();
        });

        salesFrame.add(salesListButton);

        // Botón Volver
        StyledButton backButton = new StyledButton("Volver", new Color(211, 47, 47));
        backButton.setBounds(300, 500, 200, 50);
        backButton.addActionListener(e -> {
            salesFrame.dispose();
            AdminView.adminView();
        });
        salesFrame.add(backButton);

        salesFrame.setVisible(true);
    }
}
