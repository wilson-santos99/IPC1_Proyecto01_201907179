// Archivo ClientModule.java
package Proyecto1.GUI;

import Proyecto1.Client.Client;
import Proyecto1.Client.ClientDAO;
import Proyecto1.Reports.ReportGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ClientModule {
    public static void clientView() {
        JFrame clientFrame = new JFrame("Gestión de Clientes");
        clientFrame.setLayout(null);
        clientFrame.setSize(800, 650);
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.setLocationRelativeTo(null);
        clientFrame.getContentPane().setBackground(new Color(33, 40, 48)); // Fondo oscuro moderno
        clientFrame.setResizable(false); // Esto bloquea el redimensionamiento de la ventana
        // Título del Panel
        JLabel titleLabel = new JLabel("Clientes Registrados", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(new Color(230, 230, 230)); // Texto en gris claro
        titleLabel.setBounds(150, 20, 500, 30);
        clientFrame.add(titleLabel);

        // Tabla de Clientes
        String[] columns = {"Nombre", "NIT", "Total Compras", "Veces Comprado"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que las celdas sean editables
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(69, 90, 100)); // Azul oscuro para el encabezado
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 700, 300);
        clientFrame.add(scrollPane);

        ClientDAO dao = ClientDAO.getInstance();
        if (dao.clients == null) {
            dao.clients = new ArrayList<>();
        }

        // Poblar la tabla con los clientes existentes
        for (Client client : dao.clients) {
            model.addRow(new Object[]{
                    client.getName(),
                    client.getNit(),
                    client.getTotalPurchases(),
                    client.getPurchaseCount()
            });
        }

        // Botón: Añadir Cliente
        JButton addButton = createStyledRoundedButton("Añadir Cliente", new Color(41, 121, 255)); // Azul brillante
        addButton.setBounds(50, 450, 200, 50);
        addButton.addActionListener(e -> openAddClientWindow(model));
        clientFrame.add(addButton);

        // Botón: Editar NIT
        JButton editButton = createStyledRoundedButton("Editar NIT", new Color(77, 182, 172)); // Verde esmeralda
        editButton.setBounds(300, 450, 200, 50);
        editButton.addActionListener(e -> openEditClientWindow(clientFrame, model));
        clientFrame.add(editButton);

        // Botón: Generar Reporte
        JButton reportButton = createStyledRoundedButton("Generar Reporte", new Color(255, 167, 38)); // Naranja vibrante
        reportButton.setBounds(550, 450, 200, 50);
        reportButton.addActionListener(e -> generateClientReport());
        clientFrame.add(reportButton);

        // Botón: Volver
        JButton backButton = createStyledRoundedButton("Volver", new Color(211, 47, 47)); // Rojo vibrante
        backButton.setBounds(50, 520, 100, 30);
        backButton.addActionListener(e -> {
            clientFrame.dispose(); // Cerrar la ventana actual
            AdminView.adminView(); // Volver al menú principal
        });
        clientFrame.add(backButton);

        clientFrame.setVisible(true);
    }

    // Método para crear botones estilizados y redondeados
    private static JButton createStyledRoundedButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? backgroundColor.brighter() : backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(backgroundColor.darker());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    // Método para abrir la ventana de añadir cliente
    private static void openAddClientWindow(DefaultTableModel model) {
        JFrame addClientFrame = new JFrame("Añadir Cliente");
        addClientFrame.setLayout(null);
        addClientFrame.setSize(400, 300);
        addClientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addClientFrame.setLocationRelativeTo(null);
        addClientFrame.getContentPane().setBackground(new Color(33, 40, 48));
        addClientFrame.setResizable(false); // Esto bloquea el redimensionamiento de la ventana
        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(50, 50, 100, 30);
        addClientFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        addClientFrame.add(nameField);

        JLabel nitLabel = new JLabel("NIT:");
        nitLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        nitLabel.setForeground(Color.WHITE);
        nitLabel.setBounds(50, 100, 100, 30);
        addClientFrame.add(nitLabel);

        JTextField nitField = new JTextField();
        nitField.setBounds(150, 100, 200, 30);
        addClientFrame.add(nitField);

        JButton saveButton = createStyledRoundedButton("Guardar", new Color(41, 121, 255)); // Azul brillante
        saveButton.setBounds(150, 200, 100, 30);
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String nit = nitField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(addClientFrame, "El campo Nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nit.isEmpty()) {
                nit = "CF";
            }

            boolean added = ClientDAO.getInstance().addClient(name, nit);
            if (added) {
                model.addRow(new Object[]{name, nit, 0.0, 0});
                JOptionPane.showMessageDialog(addClientFrame, "Cliente añadido con éxito.");
                addClientFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(addClientFrame, "El cliente con ese NIT ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addClientFrame.add(saveButton);
        addClientFrame.setVisible(true);
    }

    // Método para abrir la ventana de editar cliente
    private static void openEditClientWindow(JFrame parentFrame, DefaultTableModel model) {
        JFrame editClientFrame = new JFrame("Editar Cliente");
        editClientFrame.setLayout(null);
        editClientFrame.setSize(400, 300);
        editClientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editClientFrame.setLocationRelativeTo(parentFrame);
        editClientFrame.getContentPane().setBackground(new Color(33, 40, 48));
        editClientFrame.setResizable(false); // Esto bloquea el redimensionamiento de la ventana
        JLabel clientLabel = new JLabel("Seleccionar Cliente:");
        clientLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        clientLabel.setForeground(Color.WHITE);
        clientLabel.setBounds(50, 50, 150, 30);
        editClientFrame.add(clientLabel);

        JComboBox<String> clientComboBox = new JComboBox<>();
        for (Client client : ClientDAO.getInstance().clients) {
            clientComboBox.addItem(client.getName());
        }
        clientComboBox.setBounds(200, 50, 150, 30);
        editClientFrame.add(clientComboBox);

        JLabel nitLabel = new JLabel("Nuevo NIT:");
        nitLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        nitLabel.setForeground(Color.WHITE);
        nitLabel.setBounds(50, 100, 100, 30);
        editClientFrame.add(nitLabel);

        JTextField nitField = new JTextField();
        nitField.setBounds(200, 100, 150, 30);
        editClientFrame.add(nitField);

        JButton saveButton = createStyledRoundedButton("Guardar", new Color(77, 182, 172)); // Verde esmeralda
        saveButton.setBounds(150, 200, 100, 30);
        saveButton.addActionListener(e -> {
            String selectedClientName = (String) clientComboBox.getSelectedItem();
            String newNit = nitField.getText().trim();

            if (newNit.isEmpty()) {
                newNit = "CF";
            }

            ClientDAO dao = ClientDAO.getInstance();
            for (Client client : dao.clients) {
                if (client.getName().equalsIgnoreCase(selectedClientName)) {
                    client.setNit(newNit);
                    JOptionPane.showMessageDialog(editClientFrame, "NIT actualizado con éxito.");
                    updateTableModel(model, dao.clients);
                    editClientFrame.dispose();
                    return;
                }
            }
        });

        editClientFrame.add(saveButton);
        editClientFrame.setVisible(true);
    }

    // Método para actualizar la tabla
    private static void updateTableModel(DefaultTableModel model, ArrayList<Client> clients) {
        model.setRowCount(0);
        for (Client client : clients) {
            model.addRow(new Object[]{
                    client.getName(),
                    client.getNit(),
                    client.getTotalPurchases(),
                    client.getPurchaseCount()
            });
        }
    }

    // Método para generar reporte de clientes
    private static void generateClientReport() {
        try {
            ReportGenerator.generateClientReport();
            JOptionPane.showMessageDialog(null, "Reporte de Clientes generado exitosamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
