package Proyecto1.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Proyecto1.Componentes.CustomDialog;
import Proyecto1.Product.Carga;

public class Carga {

 public static void CargaMasiva(DefaultTableModel model) {
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

                CustomDialog.showSuccessMessage(
                        "Carga masiva completada.\nLíneas exitosas: " + successCount +
                                "\nLíneas con error: " + (totalLines - successCount - 1), "Carga Exitosa");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Error al cargar productos: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
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
