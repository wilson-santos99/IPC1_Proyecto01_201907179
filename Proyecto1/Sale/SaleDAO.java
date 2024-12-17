// Archivo SaleDAO.java
package Proyecto1.Sale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class SaleDAO implements Serializable {
    public ArrayList<Sale> sales;
    private static SaleDAO instance;

    private SaleDAO() {
        this.sales = new ArrayList<>();
    }

    public static SaleDAO getInstance() {
        if (instance == null) {
            instance = new SaleDAO();
        }
        return instance;
    }

    public void recordSale(String clientName, String clientNit, String productName, int quantity, double total) {
        sales.add(new Sale(clientName, clientNit, productName, quantity, total, new Date()));
    }

    public Object[][] getSalesData() {
        Object[][] data = new Object[sales.size()][6];
        for (int i = 0; i < sales.size(); i++) {
            Sale sale = sales.get(i);
            data[i][0] = sale.getClientName();
            data[i][1] = sale.getClientNit();
            data[i][2] = sale.getProductName();
            data[i][3] = sale.getQuantity();
            data[i][4] = sale.getTotal();
            data[i][5] = sale.getDate();
        }
        return data;
    }
}