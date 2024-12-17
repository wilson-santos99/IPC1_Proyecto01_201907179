// Archivo ProductModule.java
package Proyecto1.GUI;

import Proyecto1.Product.Product;
import Proyecto1.Product.ProductDAO;
import Proyecto1.Reports.ReportGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ProductModule {
    public static void productView() {
        JFrame productFrame = new JFrame("Gestión de Productos");
        productFrame.setLayout(null);
        productFrame.setSize(800, 650);
        productFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productFrame.setLocationRelativeTo(null);
        productFrame.getContentPane().setBackground(new Color(33, 40, 48));
        productFrame.setResizable(false);
    
        JLabel titleLabel = new JLabel("Productos Disponibles", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(new Color(230, 230, 230));
        titleLabel.setBounds(150, 20, 500, 30);
        productFrame.add(titleLabel);
    
        // Tabla de productos
        String[] columns = {"ID", "Nombre", "Precio", "Stock", "Vendidos"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    
        JTable table = new JTable(model);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(69, 90, 100));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 700, 300);
        productFrame.add(scrollPane);
    
        // Obtener productos y calcular el ancho de columna dinámico
        ProductDAO dao = ProductDAO.getInstance();
        int maxWidth = calculateMaxNameWidth(dao.products, table.getFont());
    
        for (Product product : dao.products) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    product.getSold()
            });
        }
    
        // Ajustar el ancho de la columna "Nombre"
        table.getColumnModel().getColumn(1).setPreferredWidth(maxWidth);
    
        // Botones con estilo redondeado
        JButton addButton = createRoundedButton("Añadir Producto", new Color(41, 121, 255));
        addButton.setBounds(50, 450, 200, 50);
        productFrame.add(addButton);
    
        JButton editButton = createRoundedButton("Editar Producto", new Color(77, 182, 172));
        editButton.setBounds(300, 450, 200, 50);
        productFrame.add(editButton);
    
        JButton deleteButton = createRoundedButton("Eliminar Producto", new Color(255, 87, 34));
        deleteButton.setBounds(300, 520, 200, 50);
        productFrame.add(deleteButton);
    
    
        JButton loadButton = createRoundedButton("Carga Masiva", new Color(255, 193, 7));
        loadButton.setBounds(50, 520, 200, 50);
        productFrame.add(loadButton);
    
        JButton reportButton = createRoundedButton("Reporte Más Vendidos", new Color(255, 152, 0));
        reportButton.setBounds(550, 450, 200, 50);
        productFrame.add(reportButton);
    
        JButton backButton = createRoundedButton("Volver", new Color(211, 47, 47));
        backButton.setBounds(550, 520, 200, 50);
        productFrame.add(backButton);
    
        // Acciones de botones
        addButton.addActionListener(e -> openAddProductWindow(model));
        editButton.addActionListener(e -> openEditProductWindow(model));
        deleteButton.addActionListener(e -> openDeleteProductWindow(model));
        loadButton.addActionListener(e -> loadProductsInBulk(model));
        reportButton.addActionListener(e -> generateTopSoldReport());
        backButton.addActionListener(e -> {
            productFrame.dispose();
            AdminView.adminView();
        });
    
        productFrame.setVisible(true);
    }
    
    // Método para calcular el ancho máximo del texto del nombre
    private static int calculateMaxNameWidth(java.util.List<Product> products, Font font) {
        FontMetrics metrics = new JLabel().getFontMetrics(font);
        int maxWidth = metrics.stringWidth("Nombre") + 20; // Ancho base para el encabezado
    
        for (Product product : products) {
            int width = metrics.stringWidth(product.getName());
            if (width > maxWidth) {
                maxWidth = width + 20; // Añadir margen adicional
            }
        }
        return maxWidth;
    }
    

    
    // Método auxiliar para crear botones redondeados

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

    private static void openAddProductWindow(DefaultTableModel model) {
        JFrame frame = new JFrame("Añadir Producto");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(33, 40, 48));
        frame.setLayout(null);
        frame.setResizable(false);

        JLabel headerLabel = new JLabel("Añadir Nuevo Producto", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBounds(150, 20, 300, 40);
        frame.add(headerLabel);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(50, 80, 150, 30);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(225, 80, 300, 30);
        frame.add(nameField);

        JLabel priceLabel = new JLabel("Precio:");
        priceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setBounds(50, 130, 150, 30);
        frame.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(225, 130, 300, 30);
        frame.add(priceField);

        JLabel stockLabel = new JLabel("Stock:");
        stockLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        stockLabel.setForeground(Color.WHITE);
        stockLabel.setBounds(50, 180, 150, 30);
        frame.add(stockLabel);

        JTextField stockField = new JTextField();
        stockField.setBounds(225, 180, 300, 30);
        frame.add(stockField);

        JButton saveButton = createRoundedButton("Guardar", new Color(41, 121, 255));
        saveButton.setBounds(225, 250, 150, 40);
        frame.add(saveButton);

        saveButton.addActionListener(e -> {
            try {
                StringBuilder errorMessage = new StringBuilder();

                // Validación de nombre
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    errorMessage.append("El campo 'Nombre' no puede estar vacío.\n");
                }

                // Validación de precio
                double price = 0;
                try {
                    price = Double.parseDouble(priceField.getText().trim());
                    if (price <= 0) {
                        errorMessage.append("El campo 'Precio' debe ser mayor que 0.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("El campo 'Precio' debe ser un número válido.\n");
                }

                // Validación de stock
                int stock = 0;
                try {
                    stock = Integer.parseInt(stockField.getText().trim());
                    if (stock <= 0) {
                        errorMessage.append("El campo 'Stock' debe ser mayor que 0.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("El campo 'Stock' debe ser un número válido.\n");
                }

                // Mostrar mensaje si hay errores
                if (errorMessage.length() > 0) {
                    JOptionPane.showMessageDialog(frame, errorMessage.toString(), "Errores en los campos",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación de existencia
                for (Product product : ProductDAO.getInstance().products) {
                    if (product.getName().equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(frame, "El producto '" + name + "' ya existe.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Agregar producto si todo está correcto
                ProductDAO.getInstance().addProduct(
                        ProductDAO.getInstance().products.size() + 1,
                        name, price, stock);
                model.addRow(new Object[] {
                        ProductDAO.getInstance().products.size(),
                        name, price, stock, 0
                });
                JOptionPane.showMessageDialog(frame, "Producto añadido con éxito.");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private static void openEditProductWindow(DefaultTableModel model) {
        JFrame frame = new JFrame("Editar Producto");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(33, 40, 48));
        frame.setLayout(null);
        frame.setResizable(false);
    
        JLabel headerLabel = new JLabel("Editar Producto", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBounds(150, 20, 300, 40);
        frame.add(headerLabel);
    
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
    
        // Cargar datos al seleccionar un producto
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
    
        JButton saveButton = createRoundedButton("Guardar", new Color(77, 182, 172));
        saveButton.setBounds(225, 250, 150, 40);
        frame.add(saveButton);
    
        saveButton.addActionListener(e -> {
            try {
                StringBuilder errorMessage = new StringBuilder();
    
                String selectedName = (String) productComboBox.getSelectedItem();
    
                // Validación de precio
                double newPrice = 0;
                try {
                    newPrice = Double.parseDouble(priceField.getText().trim());
                    if (newPrice <= 0) {
                        errorMessage.append("El campo Precio debe ser mayor que 0.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("El campo Precio debe ser un número válido.\n");
                }
    
                // Validación de stock
                int newStock = 0;
                try {
                    newStock = Integer.parseInt(stockField.getText().trim());
                    if (newStock <= 0) {
                        errorMessage.append("El campo Stock debe ser mayor que 0.\n");
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("El campo Stock debe ser un número válido.\n");
                }
    
                // Mostrar errores si existen
                if (errorMessage.length() > 0) {
                    JOptionPane.showMessageDialog(frame, errorMessage.toString(), "Errores en los campos", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(frame, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    }
    

    private static void openDeleteProductWindow(DefaultTableModel model) {
        JFrame frame = new JFrame("Eliminar Producto");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(33, 40, 48));
        frame.setLayout(null);
        frame.setResizable(false);

        JLabel headerLabel = new JLabel("Eliminar Producto", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBounds(150, 20, 300, 40);
        frame.add(headerLabel);

        JLabel selectLabel = new JLabel("Seleccionar Producto:");
        selectLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setBounds(50, 100, 200, 30);
        frame.add(selectLabel);

        JComboBox<String> productComboBox = new JComboBox<>();
        for (Product product : ProductDAO.getInstance().products) {
            productComboBox.addItem(product.getProductId() + " - " + product.getName());
        }
        productComboBox.setBounds(250, 100, 300, 30);
        frame.add(productComboBox);

        JButton deleteButton = new JButton("Eliminar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(244, 67, 54));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(new Color(244, 67, 54).darker());
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            }
        };
        deleteButton.setBounds(250, 180, 100, 40);
        deleteButton.setFont(new Font("Roboto", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setFocusPainted(false);
        frame.add(deleteButton);

        deleteButton.addActionListener(e -> {
            String selectedItem = (String) productComboBox.getSelectedItem();
            if (selectedItem != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        frame, "¿Está seguro de eliminar el producto seleccionado?",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ProductDAO.getInstance().products.removeIf(
                            product -> selectedItem.contains(String.valueOf(product.getProductId())));
                    updateTableModel(model, ProductDAO.getInstance().products);
                    JOptionPane.showMessageDialog(frame, "Producto eliminado con éxito.");
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private static void loadProductsInBulk(DefaultTableModel model) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            int totalLines = 0;
            int successCount = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean isFirstLine = true;

                while ((line = reader.readLine()) != null) {
                    totalLines++;
                    if (isFirstLine) {
                        isFirstLine = false; // Saltamos la primera línea (encabezado)
                        continue;
                    }

                    String[] parts = line.split(";");
                    if (parts.length == 3) {
                        Map<String, String> campos = new HashMap<>();
                        campos.put("Nombre", parts[0]);
                        campos.put("Precio", parts[1]);
                        campos.put("Stock", parts[2]);

                        try {
                            verificar(campos); // Validación de los campos

                            // Si pasa la validación, se añade el producto
                            String name = parts[0];
                            double price = Double.parseDouble(parts[1]);
                            int stock = Integer.parseInt(parts[2]);

                            ProductDAO.getInstance().addProduct(
                                    ProductDAO.getInstance().products.size() + 1, name, price, stock);
                            model.addRow(
                                    new Object[] { ProductDAO.getInstance().products.size(), name, price, stock, 0 });
                            successCount++;
                        } catch (Exception ex) {
                            System.err.println("Error en la línea " + totalLines + ": " + ex.getMessage());
                        }
                    } else {
                        System.err.println("Formato incorrecto en la línea " + totalLines + ": " + line);
                    }
                }

                JOptionPane.showMessageDialog(null,
                        "Carga masiva completada.\nLíneas exitosas: " + successCount +
                                "\nLíneas con error: " + (totalLines - successCount - 1));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Error al cargar productos: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void generateTopSoldReport() {
        try {
            ReportGenerator.generateProductReport();
            JOptionPane.showMessageDialog(null, "Reporte de productos más vendidos generado exitosamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void updateTableModel(DefaultTableModel model, java.util.List<Product> products) {
        model.setRowCount(0);
        for (Product product : products) {
            model.addRow(new Object[] {
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    product.getSold()
            });
        }
    }

    private static void verificar(Map<String, String> campos) throws Exception {
        StringBuilder errores = new StringBuilder();
        for (Map.Entry<String, String> campo : campos.entrySet()) {
            switch (campo.getKey()) {
                case "Nombre":
                    if (campo.getValue().isEmpty())
                        errores.append("El campo Nombre no puede estar vacío.\n");
                    break;
                case "Precio":
                    try {
                        double precio = Double.parseDouble(campo.getValue());
                        if (precio <= 0)
                            errores.append("El Precio debe ser mayor a 0.\n");
                    } catch (NumberFormatException ex) {
                        errores.append("El Precio debe ser un número válido.\n");
                    }
                    break;
                case "Stock":
                    try {
                        int stock = Integer.parseInt(campo.getValue());
                        if (stock <= 0)
                            errores.append("El Stock debe ser mayor a 0.\n");
                    } catch (NumberFormatException ex) {
                        errores.append("El Stock debe ser un número válido.\n");
                    }
                    break;
            }
        }
        if (errores.length() > 0)
            throw new Exception(errores.toString());
    }
}