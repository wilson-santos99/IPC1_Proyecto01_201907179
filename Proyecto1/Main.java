// Archivo Main.java
package Proyecto1;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import Proyecto1.GUI.AdminView;
import Proyecto1.GUI.LoginFrame;
import Proyecto1.Product.Product;
import Proyecto1.Product.ProductDAO;
import Proyecto1.Client.Client;
import Proyecto1.Client.ClientDAO;
import Proyecto1.Sale.Sale;
import Proyecto1.Sale.SaleDAO;

import java.util.ArrayList;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ProductDAO productHandler = ProductDAO.getInstance();
        ClientDAO clientHandler = ClientDAO.getInstance();
        SaleDAO saleHandler = SaleDAO.getInstance();
    
        try {
            ObjectInputStream recoveredData = new ObjectInputStream(new FileInputStream("data.ipc1"));
            productHandler.products = (ArrayList<Product>) recoveredData.readObject();
            clientHandler.clients = (ArrayList<Client>) recoveredData.readObject();
            saleHandler.sales = (ArrayList<Sale>) recoveredData.readObject();
            recoveredData.close();
        } catch (Exception e) {
            System.out.println("No hay ningún dato previo.");
            productHandler.products = new ArrayList<>();
            clientHandler.clients = new ArrayList<>();
            saleHandler.sales = new ArrayList<>();
        }
        AdminView.adminView();
       //LoginFrame.loginView();
    }
    
}
