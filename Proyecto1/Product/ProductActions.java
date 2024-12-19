package Proyecto1.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import Proyecto1.Componentes.StyledButton;
import Proyecto1.Reports.ReportGenerator;

public class ProductActions {

    public static void openAddProductWindow(DefaultTableModel model) {
        JFrame frame = new JFrame("Añadir Producto");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(33, 40, 48));
        frame.setLayout(null);
        frame.setResizable(false);
    
        // Título
        JLabel headerLabel = new JLabel("Añadir Nuevo Producto", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBounds(150, 20, 300, 40);
        frame.add(headerLabel);
    
        // Campo para el nombre del producto
        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(50, 80, 150, 30);
        frame.add(nameLabel);
    
        JTextField nameField = new JTextField();
        nameField.setBounds(225, 80, 300, 30);
        nameField.setBackground(new Color(230, 230, 230));
        nameField.setForeground(Color.BLACK);
        frame.add(nameField);
    
        // Campo para el precio del producto
        JLabel priceLabel = new JLabel("Precio:");
        priceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setBounds(50, 130, 150, 30);
        frame.add(priceLabel);
    
        JTextField priceField = new JTextField();
        priceField.setBounds(225, 130, 300, 30);
        priceField.setBackground(new Color(230, 230, 230));
        priceField.setForeground(Color.BLACK);
        frame.add(priceField);
    
        // Campo para el stock del producto
        JLabel stockLabel = new JLabel("Stock:");
        stockLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        stockLabel.setForeground(Color.WHITE);
        stockLabel.setBounds(50, 180, 150, 30);
        frame.add(stockLabel);
    
        JTextField stockField = new JTextField();
        stockField.setBounds(225, 180, 300, 30);
        stockField.setBackground(new Color(230, 230, 230));
        stockField.setForeground(Color.BLACK);
        frame.add(stockField);
    
        // Botón de guardar
        StyledButton saveButton = new StyledButton("Guardar", new Color(41, 121, 255));
        saveButton.setBounds(225, 250, 150, 40);
        frame.add(saveButton);
    
        saveButton.addActionListener(e -> {
            try {
                StringBuilder errorMessage = new StringBuilder();
    
                // Validación del nombre
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    errorMessage.append("El campo 'Nombre' no puede estar vacío.\n");
                }
    
                // Validación del precio
                double price = 0;
                try {
                    price = Double.parseDouble(priceField.getText().trim());
                    if (price <= 0) {
                        errorMessage.append("El campo 'Precio' debe ser mayor que 0.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("El campo 'Precio' debe ser un número válido.\n");
                }
    
                // Validación del stock
                int stock = 0;
                try {
                    stock = Integer.parseInt(stockField.getText().trim());
                    if (stock <= 0) {
                        errorMessage.append("El campo 'Stock' debe ser mayor que 0.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("El campo 'Stock' debe ser un número válido.\n");
                }
    
                // Mostrar errores si existen
                if (errorMessage.length() > 0) {
                    JOptionPane.showMessageDialog(frame, errorMessage.toString(), "Errores en los campos",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Validación de existencia del producto
                for (Product product : ProductDAO.getInstance().products) {
                    if (product.getName().equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(frame, "El producto '" + name + "' ya existe.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
    
                // Agregar producto al DAO y a la tabla
                ProductDAO.getInstance().addProduct(
                        ProductDAO.getInstance().products.size() + 1,
                        name, price, stock
                );
                model.addRow(new Object[]{
                        ProductDAO.getInstance().products.size(),
                        name, price, stock, 0
                });
    
                JOptionPane.showMessageDialog(frame, "Producto añadido con éxito.");
                frame.dispose();
    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error inesperado: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    }
    
    public static void openEditProductWindow(JTable table, DefaultTableModel model) {
        JFrame frame = new JFrame("Editar Producto");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(33, 40, 48));
        frame.setLayout(null);
        frame.setResizable(false);
    
        // Título
        JLabel headerLabel = new JLabel("Editar Producto", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBounds(150, 20, 300, 40);
        frame.add(headerLabel);
    
        // Seleccionar producto
        JLabel selectLabel = new JLabel("Seleccionar Producto:");
        selectLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setBounds(50, 80, 200, 30);
        frame.add(selectLabel);
    
        JComboBox<String> productComboBox = new JComboBox<>();
        for (Product product : ProductDAO.getInstance().products) {
            productComboBox.addItem(product.getName());
        }
        productComboBox.setBounds(270, 80, 250, 30);
        frame.add(productComboBox);
    
        // Precio actual
        JLabel priceLabel = new JLabel("Nuevo Precio:");
        priceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setBounds(50, 130, 200, 30);
        frame.add(priceLabel);
    
        JTextField priceField = new JTextField();
        priceField.setBounds(270, 130, 250, 30);
        priceField.setBackground(new Color(230, 230, 230));
        priceField.setForeground(Color.BLACK);
        frame.add(priceField);
    
        // Stock actual
        JLabel stockLabel = new JLabel("Nuevo Stock:");
        stockLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        stockLabel.setForeground(Color.WHITE);
        stockLabel.setBounds(50, 180, 200, 30);
        frame.add(stockLabel);
    
        JTextField stockField = new JTextField();
        stockField.setBounds(270, 180, 250, 30);
        stockField.setBackground(new Color(230, 230, 230));
        stockField.setForeground(Color.BLACK);
        frame.add(stockField);
    
        // Cargar datos del producto seleccionado
        productComboBox.addActionListener(e -> {
            String selectedName = (String) productComboBox.getSelectedItem();
            for (Product product : ProductDAO.getInstance().products) {
                if (product.getName().equals(selectedName)) {
                    priceField.setText(String.valueOf(product.getPrice()));
                    stockField.setText(String.valueOf(product.getStock()));
                    break;
                }
            }
        });
    
        // Mostrar datos del primer producto al abrir la ventana
        if (productComboBox.getItemCount() > 0) {
            productComboBox.setSelectedIndex(0);
            String selectedName = (String) productComboBox.getSelectedItem();
            for (Product product : ProductDAO.getInstance().products) {
                if (product.getName().equals(selectedName)) {
                    priceField.setText(String.valueOf(product.getPrice()));
                    stockField.setText(String.valueOf(product.getStock()));
                    break;
                }
            }
        }
    
        // Botón de guardar
        StyledButton saveButton = new StyledButton("Guardar", new Color(77, 182, 172));
        saveButton.setBounds(225, 250, 150, 40);
        frame.add(saveButton);
    
        saveButton.addActionListener(e -> {
            try {
                StringBuilder errorMessage = new StringBuilder();
                String selectedName = (String) productComboBox.getSelectedItem();
                double newPrice = 0;
                int newStock = 0;
    
                // Validación de precio
                try {
                    newPrice = Double.parseDouble(priceField.getText().trim());
                    if (newPrice <= 0) {
                        errorMessage.append("El campo 'Precio' debe ser mayor que 0.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("El campo 'Precio' debe ser un número válido.\n");
                }
    
                // Validación de stock
                try {
                    newStock = Integer.parseInt(stockField.getText().trim());
                    if (newStock <= 0) {
                        errorMessage.append("El campo 'Stock' debe ser mayor que 0.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("El campo 'Stock' debe ser un número válido.\n");
                }
    
                // Mostrar errores si existen
                if (errorMessage.length() > 0) {
                    JOptionPane.showMessageDialog(frame, errorMessage.toString(), "Errores en los campos",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Actualizar el producto
                for (Product product : ProductDAO.getInstance().products) {
                    if (product.getName().equals(selectedName)) {
                        product.setPrice(newPrice);
                        product.setStock(newStock);
                        updateTableModel(model, ProductDAO.getInstance().products);
                        JOptionPane.showMessageDialog(frame, "Producto actualizado con éxito.");
                        frame.dispose();
                        return;
                    }
                }
    
                JOptionPane.showMessageDialog(frame, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error inesperado: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    }
    
    public static void openDeleteProductWindow(JTable table, DefaultTableModel model) {
        JFrame frame = new JFrame("Eliminar Producto");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(33, 40, 48));
        frame.setLayout(null);
        frame.setResizable(false);
    
        // Título de la ventana
        JLabel headerLabel = new JLabel("Eliminar Producto", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBounds(150, 20, 300, 40);
        frame.add(headerLabel);
    
        // Etiqueta de selección de producto
        JLabel selectLabel = new JLabel("Seleccionar Producto:");
        selectLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setBounds(50, 100, 200, 30);
        frame.add(selectLabel);
    
        // ComboBox para seleccionar el producto a eliminar
        JComboBox<String> productComboBox = new JComboBox<>();
        for (Product product : ProductDAO.getInstance().products) {
            productComboBox.addItem(product.getProductId() + " - " + product.getName());
        }
        productComboBox.setBounds(250, 100, 300, 30);
        frame.add(productComboBox);
    
        // Botón de eliminar
        StyledButton deleteButton = new StyledButton("Eliminar", new Color(244, 67, 54));
        deleteButton.setBounds(250, 180, 150, 40);
        frame.add(deleteButton);
    
        deleteButton.addActionListener(e -> {
            String selectedItem = (String) productComboBox.getSelectedItem();
            if (selectedItem != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "¿Está seguro de eliminar el producto seleccionado?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION
                );
    
                if (confirm == JOptionPane.YES_OPTION) {
                    // Obtener ID del producto seleccionado
                    int productId = Integer.parseInt(selectedItem.split(" - ")[0]);
    
                    // Eliminar producto del DAO
                    boolean removed = ProductDAO.getInstance().products.removeIf(
                            product -> product.getProductId() == productId
                    );
    
                    if (removed) {
                        updateTableModel(model, ProductDAO.getInstance().products);
                        JOptionPane.showMessageDialog(frame, "Producto eliminado con éxito.");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No se ha seleccionado ningún producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    }
    
    private static void updateTableModel(DefaultTableModel model, java.util.List<Product> products) {
        model.setRowCount(0); // Limpiar filas existentes
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    product.getSold()
            });
        }
    }
    

    public static void massiveLoadProducts(DefaultTableModel model) {
        try {
            Carga.CargaMasiva(model);
          //  JOptionPane.showMessageDialog(null, "Productos cargados exitosamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void generateTopSoldReport() {
        try {
            ReportGenerator.generateProductReport();
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
