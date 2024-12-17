package Proyecto1.Sale;

import java.io.Serializable;
import java.util.Date;

public class Sale implements Serializable {
    private String clientName;
    private String clientNit;
    private String productName;
    private int quantity;
    private double total;
    private Date date;

    public Sale(String clientName, String clientNit, String productName, int quantity, double total, Date date) {
        this.clientName = clientName;
        this.clientNit = clientNit;
        this.productName = productName;
        this.quantity = quantity;
        this.total = total;
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientNit() {
        return clientNit;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }
}
