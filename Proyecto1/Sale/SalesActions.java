package Proyecto1.Sale;

import Proyecto1.Client.Client;
import Proyecto1.Client.ClientDAO;
import Proyecto1.Product.Product;
import Proyecto1.Product.ProductDAO;
import Proyecto1.Componentes.SalesViewComponents;
import Proyecto1.Componentes.StyledButton;
import Proyecto1.Reports.ReportGenerator;
import Proyecto1.Factura.generarFactura;
import Proyecto1.Componentes.CustomDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SalesActions {

    public static void newSaleView() {
        JFrame newSaleFrame = new JFrame("Nueva Venta");
        newSaleFrame.setLayout(null);
        newSaleFrame.setSize(500, 400);
        newSaleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newSaleFrame.setLocationRelativeTo(null);
        newSaleFrame.getContentPane().setBackground(new Color(33, 40, 48));

        // Verificar si hay clientes y productos disponibles
        if (ClientDAO.getInstance().clients.isEmpty()) {
            CustomDialog.showErrorMessage("No hay clientes disponibles para realizar una venta.", "Advertencia");
            newSaleFrame.dispose();
            return;
        }

        if (ProductDAO.getInstance().products.isEmpty()) {
            CustomDialog.showErrorMessage("No hay productos disponibles para realizar una venta.", "Advertencia");
            newSaleFrame.dispose();
            return;
        }

        JLabel clientLabel = SalesViewComponents.createTitleLabel("Cliente:");
        clientLabel.setBounds(50, 50, 100, 30);
        newSaleFrame.add(clientLabel);

        JComboBox<Client> clientComboBox = new JComboBox<>();
        ClientDAO.getInstance().clients.forEach(clientComboBox::addItem);
        clientComboBox.setBounds(150, 50, 200, 30);
        newSaleFrame.add(clientComboBox);

        JLabel productLabel = SalesViewComponents.createTitleLabel("Producto:");
        productLabel.setBounds(50, 100, 100, 30);
        newSaleFrame.add(productLabel);

        JComboBox<Product> productComboBox = new JComboBox<>();
        ProductDAO.getInstance().products.forEach(productComboBox::addItem);
        productComboBox.setBounds(150, 100, 200, 30);
        newSaleFrame.add(productComboBox);

        JLabel quantityLabel = SalesViewComponents.createTitleLabel("Cantidad:");
        quantityLabel.setBounds(50, 150, 100, 30);
        newSaleFrame.add(quantityLabel);

        JTextField quantityField = new JTextField();
        quantityField.setBounds(150, 150, 200, 30);
        newSaleFrame.add(quantityField);

        StyledButton saveButton = new StyledButton("Registrar Venta", new Color(77, 182, 172));
        saveButton.setBounds(175, 250, 150, 30);
        saveButton.addActionListener(e -> registerSale(clientComboBox, productComboBox, quantityField, newSaleFrame));
        newSaleFrame.add(saveButton);

        newSaleFrame.setVisible(true);
    }

    public static void generateSalesReport() {
        try {
            if (SaleDAO.getInstance().sales.isEmpty()) {
                CustomDialog.showErrorMessage(
                        "No se han registrado ventas hasta el momento. No se puede generar el reporte.",
                        "Reporte de Ventas"
                );
                return;
            }

            ReportGenerator.generateSalesReport();
            CustomDialog.showSuccessMessage(
                    "Reporte de ventas generado exitosamente. Revisa el archivo sales_report.html",
                    "Reporte de Ventas"
            );
        } catch (Exception e) {
            CustomDialog.showErrorMessage(
                    "Error al generar el reporte de ventas: " + e.getMessage(),
                    "Error"
            );
        }
    }

    public static void showSalesList() {
        if (SaleDAO.getInstance().sales.isEmpty()) {
            CustomDialog.showErrorMessage(
                    "No se han registrado ventas hasta el momento. La lista está vacía.",
                    "Lista de Ventas"
            );
            return;
        }

        JFrame salesListFrame = new JFrame("Lista de Ventas");
        salesListFrame.setLayout(null);
        salesListFrame.setSize(800, 650);
        salesListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        salesListFrame.setLocationRelativeTo(null);
        salesListFrame.getContentPane().setBackground(new Color(33, 40, 48));

        JLabel titleLabel = SalesViewComponents.createTitleLabel("Lista de Ventas");
        salesListFrame.add(titleLabel);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Cliente", "Producto", "Cantidad", "Total", "Fecha"},
                0
        );
        table.setModel(model);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(69, 90, 100));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 700, 400);
        salesListFrame.add(scrollPane);

        populateSalesTable(model);

        StyledButton backButton = new StyledButton("Volver", new Color(211, 47, 47));
        backButton.setBounds(300, 520, 200, 50);
        backButton.addActionListener(e -> salesListFrame.dispose());
        salesListFrame.add(backButton);

        salesListFrame.setVisible(true);
    }

    private static void populateSalesTable(DefaultTableModel model) {
        SaleDAO.getInstance().sales.forEach(sale -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            model.addRow(new Object[]{
                    sale.getClientName(),
                    sale.getProductName(),
                    sale.getQuantity(),
                    String.format("Q%.2f", sale.getTotal()),
                    dateFormat.format(sale.getDate())
            });
        });
    }

    private static void registerSale(JComboBox<Client> clientComboBox, JComboBox<Product> productComboBox, JTextField quantityField, JFrame frame) {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        Product selectedProduct = (Product) productComboBox.getSelectedItem();

        String quantityText = quantityField.getText().trim();
        if (quantityText.isEmpty()) {
            CustomDialog.showErrorMessage("La cantidad no puede estar vacía.", "Error de Venta");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);

            if (quantity <= 0) {
                CustomDialog.showErrorMessage("La cantidad debe ser mayor que cero.", "Error de Venta");
                return;
            }

            if (selectedProduct == null || selectedProduct.getStock() < quantity) {
                CustomDialog.showErrorMessage("Stock insuficiente para realizar la venta.", "Error de Venta");
                return;
            }

            if (selectedClient == null) {
                CustomDialog.showErrorMessage("Debe seleccionar un cliente válido.", "Error de Venta");
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

            generarFactura.generar(selectedClient, selectedProduct, quantity, total);

            CustomDialog.showSuccessMessage("Venta registrada con éxito.", "Venta Exitosa");
            frame.dispose();
        } catch (NumberFormatException e) {
            CustomDialog.showErrorMessage("La cantidad debe ser un número entero.", "Error de Venta");
        } catch (Exception e) {
            CustomDialog.showErrorMessage("Error inesperado: " + e.getMessage(), "Error de Venta");
        }
    }
}
