package Proyecto1.Persistence;

import Proyecto1.Product.ProductDAO;
import Proyecto1.Client.ClientDAO;
import Proyecto1.Sale.SaleDAO;

import java.io.*;
import java.util.ArrayList;

public class PersistenceManager {

    private static final String DATA_FILE = "data.ipc1";

    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(ProductDAO.getInstance().products);
            oos.writeObject(ClientDAO.getInstance().clients);
            oos.writeObject(SaleDAO.getInstance().sales);
            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            ProductDAO.getInstance().products = (ArrayList) ois.readObject();
            ClientDAO.getInstance().clients = (ArrayList) ois.readObject();
            SaleDAO.getInstance().sales = (ArrayList) ois.readObject();
            System.out.println("Datos cargados exitosamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de datos no encontrado. Se usará una base de datos vacía.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: Error de entrada/salida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar los datos: Clase no encontrada: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar los datos: " + e.getMessage());
        }
    }
}
