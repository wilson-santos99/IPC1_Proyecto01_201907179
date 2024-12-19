package Proyecto1.Product;

import java.io.Serializable;

public class Product implements Serializable {
    private int productId;
    private String name;
    private double price;
    private int stock;
    private int sold; 

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sold = 0; // Inicializa en 0
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getSold() {
        return sold; // Devuelve la cantidad vendida
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void incrementSold(int quantity) {
        this.sold += quantity; // Incrementa la cantidad vendida
    }

    @Override
    public String toString() {
        return name + " - Q" + price;
    }

}
