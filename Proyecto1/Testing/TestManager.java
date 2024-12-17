// Archivo TestManager.java
package Proyecto1.Testing;

import Proyecto1.Persistence.PersistenceManager;
import Proyecto1.Product.ProductDAO;
import Proyecto1.Client.ClientDAO;
import Proyecto1.Sale.SaleDAO;
import Proyecto1.Reports.ReportGenerator;
public class TestManager {

    public static void main(String[] args) {
        System.out.println("--- Iniciando pruebas del sistema ---\n");

        // Pruebas de productos
        System.out.println("--- Pruebas de productos ---");
        ProductDAO productDAO = ProductDAO.getInstance();
        productDAO.addProduct(1, "Karategui", 50.0, 100);
        productDAO.addProduct(2, "Cinturón negro", 30.0, 50);
        System.out.println("Productos añadidos: " + productDAO.products.size());

        // Pruebas de clientes
        System.out.println("\n--- Pruebas de clientes ---");
        ClientDAO clientDAO = ClientDAO.getInstance();
        clientDAO.addClient("Daniel LaRusso", "1234567");
        clientDAO.addClient("Johnny Lawrence", "7654321");
        System.out.println("Clientes añadidos: " + clientDAO.clients.size());

        // Pruebas de ventas
        System.out.println("\n--- Pruebas de ventas ---");
        SaleDAO saleDAO = SaleDAO.getInstance();
        saleDAO.recordSale("Daniel LaRusso", "1234567", "Karategui", 2, 100.0);
        saleDAO.recordSale("Johnny Lawrence", "7654321", "Cinturón negro", 1, 30.0);
        System.out.println("Ventas registradas: " + saleDAO.sales.size());

        // Pruebas de persistencia
        System.out.println("\n--- Pruebas de persistencia ---");
        PersistenceManager.saveData();
        System.out.println("Datos guardados exitosamente.");

        // Simulación de carga de datos
        productDAO.products.clear();
        clientDAO.clients.clear();
        saleDAO.sales.clear();

        PersistenceManager.loadData();
        System.out.println("Datos cargados: Productos - " + productDAO.products.size() + ", Clientes - " + clientDAO.clients.size() + ", Ventas - " + saleDAO.sales.size());

        // Pruebas de reportes
        System.out.println("\n--- Pruebas de reportes ---");
        ReportGenerator.generateProductReport();
        ReportGenerator.generateClientReport();
        ReportGenerator.generateSalesReport();
        System.out.println("Reportes generados correctamente.");

        System.out.println("\n--- Fin de pruebas del sistema ---");
    }
}
