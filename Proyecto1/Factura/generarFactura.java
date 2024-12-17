package Proyecto1.Factura;

import Proyecto1.Client.Client;
import Proyecto1.Product.Product;

import javax.swing.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class generarFactura {

    public static void generar(Client client, Product product, int quantity, double total) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            String date = dateFormat.format(new Date());

            String fileName = client.getName() + "_" + date.replace(" ", "_").replace("de", "").replace("'", "") + ".html";

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("<!DOCTYPE html>");
                writer.println("<html lang=\"es\">");
                writer.println("<head>");
                writer.println("    <meta charset=\"UTF-8\">");
                writer.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                writer.println("    <title>Factura de Venta</title>");
                writer.println("    <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" rel=\"stylesheet\">");
                writer.println("    <style>");
                writer.println("        @import url('https://fonts.googleapis.com/css2?family=Bree+Serif&display=swap');");
                writer.println("        body, h1, h2, h3, h4, h5, h6 { font-family: 'Bree Serif', serif; }");
                writer.println("        .invoice-container { margin: 20px; padding: 20px; background: #fff; border-radius: 10px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); }");
                writer.println("        .header { border-bottom: 2px solid #f4f4f9; padding-bottom: 10px; margin-bottom: 20px; text-align: center; }");
                writer.println("        .header img { max-width: 150px; }");
                writer.println("        .header h1 { color: #007BFF; }");
                writer.println("        .details p { margin: 5px 0; }");
                writer.println("        .table th, .table td { text-align: center; }");
                writer.println("        .footer { text-align: center; margin-top: 20px; font-size: 12px; color: #666; }");
                writer.println("    </style>");
                writer.println("</head>");
                writer.println("<body style=\"background-color: #f4f4f9;\">");
                writer.println("    <div class=\"container invoice-container\">");
                writer.println("        <div class=\"header\">");
                writer.println("            <img src=\"logo.png\" alt=\"Company Logo\">");
                writer.println("            <h1>Factura de Venta</h1>");
                writer.println("            <p>Fecha: " + date + "</p>");
                writer.println("        </div>");
                writer.println("        <div class=\"details\">");
                writer.println("            <p><strong>Cliente:</strong> " + client.getName() + "</p>");
                writer.println("            <p><strong>NIT:</strong> " + client.getNit() + "</p>");
                writer.println("        </div>");
                writer.println("        <table class=\"table table-bordered\">");
                writer.println("            <thead>");
                writer.println("                <tr>");
                writer.println("                    <th>Cantidad</th>");
                writer.println("                    <th>Producto</th>");
                writer.println("                    <th>Precio Unitario</th>");
                writer.println("                    <th>Subtotal</th>");
                writer.println("                </tr>");
                writer.println("            </thead>");
                writer.println("            <tbody>");
                writer.println("                <tr>");
                writer.println("                    <td>" + quantity + "</td>");
                writer.println("                    <td>" + product.getName() + "</td>");
                writer.println("                    <td>Q" + String.format("%.2f", product.getPrice()) + "</td>");
                writer.println("                    <td>Q" + String.format("%.2f", total) + "</td>");
                writer.println("                </tr>");
                writer.println("            </tbody>");
                writer.println("        </table>");
                writer.println("        <div class=\"text-right mt-4\">");
                writer.println("            <h5>Total a Pagar: <strong>Q" + String.format("%.2f", total) + "</strong></h5>");
                writer.println("        </div>");
                writer.println("        <div class=\"footer\">");
                writer.println("            <p>\" \"</p>");
                writer.println("        </div>");
                writer.println("    </div>");
                writer.println("</body>");
                writer.println("</html>");
            }

            JOptionPane.showMessageDialog(null, "Factura generada: " + fileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar la factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
