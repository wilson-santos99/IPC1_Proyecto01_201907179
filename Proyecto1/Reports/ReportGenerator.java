package Proyecto1.Reports;

import Proyecto1.Product.Product;
import Proyecto1.Product.ProductDAO;
import Proyecto1.Client.Client;
import Proyecto1.Client.ClientDAO;
import Proyecto1.Componentes.CustomDialog;
import Proyecto1.Sale.Sale;
import Proyecto1.Sale.SaleDAO;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class ReportGenerator {
    public static void generateProductReport() {
        try {
            // Obtener los productos y ordenarlos por la cantidad vendida
            List<Product> sortedProducts = new ArrayList<>(ProductDAO.getInstance().products);
            sortedProducts.sort((p1, p2) -> Integer.compare(p2.getSold(), p1.getSold()));

            // Limitar a los 5 más vendidos
            int limit = Math.min(5, sortedProducts.size());
            List<Product> top5Products = sortedProducts.subList(0, limit);

            // Generar el reporte en HTML
            String fileName = "top5_products_report.html";
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {

                writer.println("<!DOCTYPE html>");
                writer.println("<html lang='es'>");
                writer.println("<head>");
                writer.println("    <meta charset='UTF-8'>");
                writer.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                writer.println("    <title>Top 5 Productos Más Vendidos</title>");
                writer.println("    <style>");
                writer.println("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f9; color: #333; }");
                writer.println("        .container { max-width: 800px; margin: auto; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
                writer.println("        h1 { text-align: center; color: #007BFF; }");
                writer.println("        table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
                writer.println("        th, td { padding: 10px; text-align: center; border: 1px solid #ddd; }");
                writer.println("        th { background-color: #007BFF; color: white; }");
                writer.println("    </style>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println("    <div class='container'>");
                writer.println("        <h1>Top 5 Productos Más Vendidos</h1>");
                writer.println("        <table>");
                writer.println("            <thead>");
                writer.println("                <tr>");
                writer.println("                    <th>ID</th>");
                writer.println("                    <th>Nombre</th>");
                writer.println("                    <th>Precio</th>");
                writer.println("                    <th>Stock</th>");
                writer.println("                    <th>Vendidos</th>");
                writer.println("                </tr>");
                writer.println("            </thead>");
                writer.println("            <tbody>");
                for (Product product : top5Products) {
                    writer.println("                <tr>");
                    writer.println("                    <td>" + product.getProductId() + "</td>");
                    writer.println("                    <td>" + product.getName() + "</td>");
                    writer.println("                    <td>Q" + String.format("%.2f", product.getPrice()) + "</td>");
                    writer.println("                    <td>" + product.getStock() + "</td>");
                    writer.println("                    <td>" + product.getSold() + "</td>");
                    writer.println("                </tr>");
                }
                writer.println("            </tbody>");
                writer.println("        </table>");
                writer.println("    </div>");
                writer.println("</body>");
                writer.println("</html>");
            }

          CustomDialog.showSuccessMessage("Reporte generado: " + fileName,"Reporte de Top Productos Exitoso");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void generateClientReport() {
        try {
            String fileName = "client_report.html";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("<!DOCTYPE html>");
                writer.println("<html lang='es'>");
                writer.println("<head>");
                writer.println("    <meta charset='UTF-8'>");
                writer.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                writer.println("    <title>Reporte de Clientes</title>");
                writer.println("    <style>");
                writer.println("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f9; color: #333; }");
                writer.println("        .container { max-width: 800px; margin: auto; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
                writer.println("        h1 { text-align: center; color: #007BFF; }");
                writer.println("        table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
                writer.println("        th, td { padding: 10px; text-align: center; border: 1px solid #ddd; }");
                writer.println("        th { background-color: #007BFF; color: white; }");
                writer.println("    </style>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println("    <div class='container'>");
                writer.println("        <h1>Reporte de Clientes</h1>");
                writer.println("        <table>");
                writer.println("            <thead>");
                writer.println("                <tr>");
                writer.println("                    <th>Nombre</th>");
                writer.println("                    <th>NIT</th>");
                writer.println("                    <th>Total Compras</th>");
                writer.println("                    <th>Veces Comprado</th>");
                writer.println("                </tr>");
                writer.println("            </thead>");
                writer.println("            <tbody>");
                for (Client client : ClientDAO.getInstance().clients) {
                    writer.println("                <tr>");
                    writer.println("                    <td>" + client.getName() + "</td>");
                    writer.println("                    <td>" + client.getNit() + "</td>");
                    writer.println("                    <td>Q" + String.format("%.2f", client.getTotalPurchases()) + "</td>");
                    writer.println("                    <td>" + client.getPurchaseCount() + "</td>");
                    writer.println("                </tr>");
                }
                writer.println("            </tbody>");
                writer.println("        </table>");
                writer.println("    </div>");
                writer.println("</body>");
                writer.println("</html>");
            }
           CustomDialog.showSuccessMessage("Reporte generado: " + fileName,"Reporte Clientes Exitoso");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void generateSalesReport() {
        try {
            String fileName = "sales_report.html";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("<!DOCTYPE html>");
                writer.println("<html lang='es'>");
                writer.println("<head>");
                writer.println("    <meta charset='UTF-8'>");
                writer.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                writer.println("    <title>Reporte de Ventas</title>");
                writer.println("    <style>");
                writer.println("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f9; color: #333; }");
                writer.println("        .container { max-width: 800px; margin: auto; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
                writer.println("        h1 { text-align: center; color: #007BFF; }");
                writer.println("        table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
                writer.println("        th, td { padding: 10px; text-align: center; border: 1px solid #ddd; }");
                writer.println("        th { background-color: #007BFF; color: white; }");
                writer.println("    </style>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println("    <div class='container'>");
                writer.println("        <h1>Historial de Ventas</h1>");
                writer.println("        <table>");
                writer.println("            <thead>");
                writer.println("                <tr>");
                writer.println("                    <th>Cliente</th>");
                writer.println("                    <th>NIT</th>");
                writer.println("                    <th>Producto</th>");
                writer.println("                    <th>Cantidad</th>");
                writer.println("                    <th>Precio Unidad</th>");
                writer.println("                    <th>Total</th>");
                writer.println("                    <th>Fecha</th>");
                writer.println("                </tr>");
                writer.println("            </thead>");
                writer.println("            <tbody>");

                List<Sale> sales = SaleDAO.getInstance().sales;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
                for (Sale sale : sales) {
                    double pricePerUnit = sale.getTotal() / sale.getQuantity(); // Calcular precio por unidad
                    writer.println("                <tr>");
                    writer.println("                    <td>" + sale.getClientName() + "</td>");
                    writer.println("                    <td>" + sale.getClientNit() + "</td>");
                    writer.println("                    <td>" + sale.getProductName() + "</td>");
                    writer.println("                    <td>" + sale.getQuantity() + "</td>");
                    writer.println("                    <td>Q" + String.format("%.2f", pricePerUnit) + "</td>");
                    writer.println("                    <td>Q" + String.format("%.2f", sale.getTotal()) + "</td>");
                    writer.println("                    <td>" + dateFormat.format(sale.getDate()) + "</td>");
                    writer.println("                </tr>");
                }
                writer.println("            </tbody>");
                writer.println("        </table>");
                writer.println("    </div>");
                writer.println("</body>");
                writer.println("</html>");
            }
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
