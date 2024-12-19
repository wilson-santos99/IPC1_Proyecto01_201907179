package Proyecto1.GUI;

import Proyecto1.Client.Client;
import Proyecto1.Client.ClientDAO;
import Proyecto1.Componentes.StyledButton;
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
        clientFrame.getContentPane().setBackground(new Color(33, 40, 48));
        clientFrame.setResizable(false);

        // Título del Panel
        JLabel titleLabel = new JLabel("Clientes Registrados", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(new Color(230, 230, 230));
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
        table.getTableHeader().setBackground(new Color(69, 90, 100));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 700, 300);
        clientFrame.add(scrollPane);

        // Poblar la tabla con clientes existentes
        ClientDAO dao = ClientDAO.getInstance();
        if (dao.clients == null) {
            dao.clients = new ArrayList<>();
        }

        for (Client client : dao.clients) {
            model.addRow(new Object[]{
                    client.getName(),
                    client.getNit(),
                    client.getTotalPurchases(),
                    client.getPurchaseCount()
            });
        }

        // Botones de acción
        addClientButton(clientFrame, model);
        editClientButton(clientFrame, model);
        deleteClientButton(clientFrame, table, model);

        generateReportButton(clientFrame);
        backButton(clientFrame);

        clientFrame.setVisible(true);
    }

    // Botón: Añadir Cliente
    private static void addClientButton(JFrame frame, DefaultTableModel model) {
        StyledButton addButton = new StyledButton("Añadir Cliente", new Color(41, 121, 255));
        addButton.setBounds(50, 450, 200, 50);
        addButton.addActionListener(e -> openAddClientWindow(model));
        frame.add(addButton);
    }

    // Botón: Editar Cliente
    private static void editClientButton(JFrame frame, DefaultTableModel model) {
        StyledButton editButton = new StyledButton("Editar Cliente", new Color(77, 182, 172));
        editButton.setBounds(300, 450, 200, 50);
        editButton.addActionListener(e -> openEditClientWindow(frame, model));
        frame.add(editButton);
    }

    // Botón: Eliminar Cliente
    private static void deleteClientButton(JFrame frame, JTable table, DefaultTableModel model) {
        StyledButton deleteButton = new StyledButton("Eliminar Cliente", new Color(211, 47, 47));
        deleteButton.setBounds(550, 450, 200, 50);
        deleteButton.addActionListener(e -> deleteClient(table, model)); // Pasa JTable y DefaultTableModel
        frame.add(deleteButton);
    }
    

    // Botón: Generar Reporte
    private static void generateReportButton(JFrame frame) {
        StyledButton reportButton = new StyledButton("Generar Reporte", new Color(255, 167, 38));
        reportButton.setBounds(50, 520, 200, 50);
        reportButton.addActionListener(e -> generateClientReport());
        frame.add(reportButton);
    }

    // Botón: Volver
    private static void backButton(JFrame frame) {
        StyledButton backButton = new StyledButton("Volver", new Color(244, 67, 54));
        backButton.setBounds(300, 520, 200, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            AdminView.adminView();
        });
        frame.add(backButton);
    }

    // Método para abrir la ventana de añadir cliente
    private static void openAddClientWindow(DefaultTableModel model) {
        JFrame addClientFrame = new JFrame("Añadir Cliente");
        addClientFrame.setLayout(null);
        addClientFrame.setSize(400, 300);
        addClientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addClientFrame.setLocationRelativeTo(null);
        addClientFrame.getContentPane().setBackground(new Color(33, 40, 48));
        addClientFrame.setResizable(false);

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

        StyledButton saveButton = new StyledButton("Guardar", new Color(41, 121, 255));
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
        // Verificar si hay clientes disponibles
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parentFrame, "No hay clientes disponibles para editar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    
        JFrame editClientFrame = new JFrame("Editar Cliente");
        editClientFrame.setLayout(null);
        editClientFrame.setSize(400, 300);
        editClientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editClientFrame.setLocationRelativeTo(parentFrame);
        editClientFrame.getContentPane().setBackground(new Color(33, 40, 48));
        editClientFrame.setResizable(false);
    
        JLabel clientLabel = new JLabel("Seleccionar Cliente:");
        clientLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        clientLabel.setForeground(Color.WHITE);
        clientLabel.setBounds(50, 50, 150, 30);
        editClientFrame.add(clientLabel);
    
        JComboBox<String> clientComboBox = new JComboBox<>();
        ClientDAO.getInstance().clients.forEach(client -> clientComboBox.addItem(client.getName()));
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
    
        JButton saveButton = new StyledButton("Guardar", new Color(77, 182, 172));
        saveButton.setBounds(125, 200, 150, 40);
        saveButton.addActionListener(e -> {
            String selectedClientName = (String) clientComboBox.getSelectedItem();
            String newNit = nitField.getText().trim();
    
            if (newNit.isEmpty()) {
                JOptionPane.showMessageDialog(editClientFrame, "El campo NIT no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            boolean updated = ClientDAO.getInstance().clients.stream()
                    .filter(client -> client.getName().equalsIgnoreCase(selectedClientName))
                    .findFirst()
                    .map(client -> {
                        client.setNit(newNit);
                        return true;
                    })
                    .orElse(false);
    
            if (updated) {
                JOptionPane.showMessageDialog(editClientFrame, "NIT actualizado exitosamente.");
                updateTableModel(model, ClientDAO.getInstance().clients);
                editClientFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(editClientFrame, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        editClientFrame.add(saveButton);
    
        editClientFrame.setVisible(true);
    }
    
    // Método auxiliar para actualizar la tabla después de una edición
    private static void updateTableModel(DefaultTableModel model, java.util.List<Client> clients) {
        model.setRowCount(0);
        clients.forEach(client -> model.addRow(new Object[]{
                client.getName(),
                client.getNit(),
                client.getTotalPurchases(),
                client.getPurchaseCount()
        }));
    }
    
    
//Método para Eliminar Clientes
private static void deleteClient(JTable table, DefaultTableModel model) {
    // Verificar si hay clientes en el modelo
    if (model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "No hay clientes disponibles para eliminar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    int selectedRow = table.getSelectedRow(); // Obtener la fila seleccionada de la tabla

    if (selectedRow >= 0) {
        String clientName = model.getValueAt(selectedRow, 0).toString(); // Obtener el nombre del cliente de la fila seleccionada
        boolean removed = ClientDAO.getInstance().clients.removeIf(client -> client.getName().equals(clientName)); // Intentar eliminar el cliente

        if (removed) {
            model.removeRow(selectedRow); // Eliminar la fila de la tabla
            JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente: No encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "No hay clientes seleccionados para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
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
