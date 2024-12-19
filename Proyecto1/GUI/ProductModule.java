package Proyecto1.GUI;

import Proyecto1.Product.Product;
import Proyecto1.Product.ProductDAO;
import Proyecto1.Product.ProductActions;
import Proyecto1.Componentes.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductModule {

    public static void productView() {
        JFrame productFrame = createMainFrame();

        JLabel titleLabel = createTitleLabel();
        productFrame.add(titleLabel);

        // Tabla de productos
        JTable table = createProductTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 700, 300);
        productFrame.add(scrollPane);

        populateProductTable(model);

        // Botones de acción
        addProductButtons(productFrame, table, model);

        productFrame.setVisible(true);
    }

    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("Gestión de Productos");
        frame.setLayout(null);
        frame.setSize(800, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(33, 40, 48));
        frame.setResizable(false);
        return frame;
    }

    private static JLabel createTitleLabel() {
        JLabel label = new JLabel("Productos Disponibles", SwingConstants.CENTER);
        label.setFont(new Font("Roboto", Font.BOLD, 24));
        label.setForeground(new Color(230, 230, 230));
        label.setBounds(150, 20, 500, 30);
        return label;
    }

    private static JTable createProductTable() {
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

        // Ajustar el ancho de la columna "Nombre" dinámicamente
        int maxWidth = TableUtils.calculateMaxNameWidth(ProductDAO.getInstance().products, table.getFont());
        table.getColumnModel().getColumn(1).setPreferredWidth(maxWidth);

        return table;
    }

    private static void populateProductTable(DefaultTableModel model) {
        for (Product product : ProductDAO.getInstance().products) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock(),
                    product.getSold()
            });
        }
    }

    private static void addProductButtons(JFrame frame, JTable table, DefaultTableModel model) {
        StyledButton addButton = new StyledButton("Añadir Producto", new Color(41, 121, 255));
        addButton.setBounds(50, 450, 200, 50);
        addButton.addActionListener(e -> ProductActions.openAddProductWindow(model));
        frame.add(addButton);

        StyledButton editButton = new StyledButton("Editar Producto", new Color(77, 182, 172));
        editButton.setBounds(300, 450, 200, 50);
        editButton.addActionListener(e -> ProductActions.openEditProductWindow(table, model));
        frame.add(editButton);

        StyledButton deleteButton = new StyledButton("Eliminar Producto", new Color(255, 87, 34));
        deleteButton.setBounds(300, 520, 200, 50);
        deleteButton.addActionListener(e -> ProductActions.openDeleteProductWindow(table, model));
        frame.add(deleteButton);

        StyledButton loadButton = new StyledButton("Carga Masiva", new Color(255, 193, 7));
        loadButton.setBounds(50, 520, 200, 50);
        loadButton.addActionListener(e -> ProductActions.massiveLoadProducts(model));
        frame.add(loadButton);

        StyledButton reportButton = new StyledButton("Reporte Más Vendidos", new Color(255, 152, 0));
        reportButton.setBounds(550, 450, 200, 50);
        reportButton.addActionListener(e -> ProductActions.generateTopSoldReport());
        frame.add(reportButton);

        StyledButton backButton = new StyledButton("Volver", new Color(211, 47, 47));
        backButton.setBounds(550, 520, 200, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            AdminView.adminView();
        });
        frame.add(backButton);
    }
}