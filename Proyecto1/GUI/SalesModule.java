package Proyecto1.GUI;

import Proyecto1.Reports.ReportGenerator;
import Proyecto1.Sale.SaleDAO;
import Proyecto1.Client.Client;
import Proyecto1.Client.ClientDAO;
import Proyecto1.Product.Product;
import Proyecto1.Product.ProductDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import Proyecto1.Factura.generarFactura;


public class SalesModule {
    public static void salesView() {
        JFrame salesFrame = new JFrame("Gestión de Ventas");
        salesFrame.setLayout(null);
        salesFrame.setSize(800, 650);
        salesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        salesFrame.setLocationRelativeTo(null);
        salesFrame.getContentPane().setBackground(new Color(33, 40, 48)); // Fondo oscuro elegante
        salesFrame.setResizable(false); // Esto bloquea el redimensionamiento de la ventana
        JLabel titleLabel = new JLabel("Módulo de Ventas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(new Color(230, 230, 230));
        titleLabel.setBounds(150, 20, 500, 30);
        salesFrame.add(titleLabel);

        JButton newSaleButton = createStyledButton("Nueva Venta", new Color(77, 182, 172)); // Verde
        newSaleButton.setBounds(50, 100, 200, 50);
        newSaleButton.addActionListener(e -> newSaleView());
        salesFrame.add(newSaleButton);

        JButton salesReportButton = createStyledButton("Reporte de Ventas", new Color(255, 193, 7)); // Amarillo
        salesReportButton.setBounds(300, 100, 200, 50);
        salesReportButton.addActionListener(e -> generateSalesReport());
        salesFrame.add(salesReportButton);

        JButton salesListButton = createStyledButton("Lista de Ventas", new Color(41, 121, 255)); // Azul
        salesListButton.setBounds(550, 100, 200, 50);
        salesListButton.addActionListener(e -> showSalesList());
        salesFrame.add(salesListButton);

        JButton backButton = createStyledButton("Volver", new Color(211, 47, 47)); // Rojo
        backButton.setBounds(300, 500, 200, 50);
        backButton.addActionListener(e -> {
            salesFrame.dispose();
            AdminView.adminView();
        });
        salesFrame.add(backButton);

        salesFrame.setVisible(true);
    }

private static JButton createStyledButton(String text, Color backgroundColor) {
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


    public static void newSaleView() {
        JFrame newSaleFrame = new JFrame("Nueva Venta");
        newSaleFrame.setLayout(null);
        newSaleFrame.setSize(500, 400);
        newSaleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newSaleFrame.setLocationRelativeTo(null);
        newSaleFrame.getContentPane().setBackground(new Color(33, 40, 48));

        JLabel clientLabel = new JLabel("Cliente:");
        clientLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        clientLabel.setForeground(Color.WHITE);
        clientLabel.setBounds(50, 50, 100, 30);
        newSaleFrame.add(clientLabel);

        JComboBox<Client> clientComboBox = new JComboBox<>();
        ClientDAO.getInstance().clients.forEach(clientComboBox::addItem);
        clientComboBox.setBounds(150, 50, 200, 30);
        clientComboBox.setFont(new Font("Roboto", Font.PLAIN, 14));
        newSaleFrame.add(clientComboBox);

        JLabel productLabel = new JLabel("Producto:");
        productLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        productLabel.setForeground(Color.WHITE);
        productLabel.setBounds(50, 100, 100, 30);
        newSaleFrame.add(productLabel);

        JComboBox<Product> productComboBox = new JComboBox<>();
        ProductDAO.getInstance().products.forEach(productComboBox::addItem);
        productComboBox.setBounds(150, 100, 200, 30);
        productComboBox.setFont(new Font("Roboto", Font.PLAIN, 14));
        newSaleFrame.add(productComboBox);

        JLabel quantityLabel = new JLabel("Cantidad:");
        quantityLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        quantityLabel.setForeground(Color.WHITE);
        quantityLabel.setBounds(50, 150, 100, 30);
        newSaleFrame.add(quantityLabel);

        JTextField quantityField = new JTextField();
        quantityField.setBounds(150, 150, 200, 30);
        quantityField.setFont(new Font("Roboto", Font.PLAIN, 14));
        newSaleFrame.add(quantityField);

        JButton saveButton = createStyledButton("Registrar Venta", new Color(77, 182, 172));
        saveButton.setBounds(175, 250, 150, 30);
        saveButton.addActionListener(e -> {
            Client selectedClient = (Client) clientComboBox.getSelectedItem();
            Product selectedProduct = (Product) productComboBox.getSelectedItem();
            String quantityText = quantityField.getText().trim();

            if (quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(newSaleFrame, "La cantidad no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(newSaleFrame, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedProduct == null || selectedProduct.getStock() < quantity) {
                    JOptionPane.showMessageDialog(newSaleFrame, "Stock insuficiente para realizar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedClient == null) {
                    JOptionPane.showMessageDialog(newSaleFrame, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double total = selectedProduct.getPrice() * quantity;
                selectedProduct.setStock(selectedProduct.getStock() - quantity);
                selectedProduct.incrementSold(quantity);
                selectedClient.addPurchase(total);

                SaleDAO.getInstance().recordSale(
                        selectedClient.getName(),
                        selectedClient.getNit(),
                        selectedProduct.getName(),
                        quantity,
                        total
                );

                generateInvoice(selectedClient, selectedProduct, quantity, total);

                JOptionPane.showMessageDialog(newSaleFrame, "Venta registrada con éxito.");
                newSaleFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(newSaleFrame, "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        newSaleFrame.add(saveButton);

        newSaleFrame.setVisible(true);
    }

    public static void generateInvoice(Client client, Product product, int quantity, double total) {
        try {
            generarFactura.generar(client, product, quantity, total);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar la Factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void generateSalesReport() {
        try {
            ReportGenerator.generateSalesReport();
            JOptionPane.showMessageDialog(null, "Reporte de ventas generado exitosamente. Revisa el archivo sales_report.html");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte de ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showSalesList() {
        JFrame salesListFrame = new JFrame("Lista de Ventas");
        salesListFrame.setLayout(null);
        salesListFrame.setSize(800, 650);
        salesListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        salesListFrame.setLocationRelativeTo(null);
        salesListFrame.getContentPane().setBackground(new Color(33, 40, 48));
    
        JLabel titleLabel = new JLabel("Lista de Ventas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(new Color(230, 230, 230));
        titleLabel.setBounds(150, 20, 500, 30);
        salesListFrame.add(titleLabel);
    
        // Columnas de la tabla con el precio individual añadido
        String[] columns = {"Cliente", "NIT", "Producto", "Cantidad", "Precio Unidad", "Total", "Fecha"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
    
        JTable table = new JTable(model);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(69, 90, 100));
        table.getTableHeader().setForeground(Color.WHITE);
    
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 700, 400);
        salesListFrame.add(scrollPane);
    
        // Llenar la tabla con datos de ventas
        SaleDAO.getInstance().sales.forEach(sale -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            String date = dateFormat.format(sale.getDate());
            double pricePerUnit = sale.getTotal() / sale.getQuantity(); // Calcular precio unitario
            model.addRow(new Object[]{
                    sale.getClientName(),
                    sale.getClientNit(),
                    sale.getProductName(),
                    sale.getQuantity(),
                    String.format("Q%.2f", pricePerUnit), // Precio individual
                    String.format("Q%.2f", sale.getTotal()), // Total
                    date
            });
        });
    
        // Botón Volver con estilo redondeado
        JButton backButton = createStyledButton("Volver", new Color(211, 47, 47));
        backButton.setBounds(300, 520, 200, 50);
        backButton.addActionListener(e -> salesListFrame.dispose());
        salesListFrame.add(backButton);
    
        salesListFrame.setVisible(true);
    }

}