package Proyecto1.Client;
import java.io.Serializable;
public class Client implements Serializable {
    private String name;
    private String nit;
    private double totalPurchases;
    private int purchaseCount; 

    public Client(String name, String nit) {
        this.name = name;
        this.nit = (nit == null || nit.isEmpty()) ? "CF" : nit;
        this.totalPurchases = 0.0;
        this.purchaseCount = 0; // Inicializar en 0
    }

    public String getName() {
        return name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public double getTotalPurchases() {
        return totalPurchases;
    }

    public void addPurchase(double amount) {
        this.totalPurchases += amount;
        this.purchaseCount++; // Incrementar el contador de compras
    }

    // Nuevo método para obtener el número de compras
    public int getPurchaseCount() {
        return purchaseCount;
    }
    @Override
    public String toString() {
        return name + " (" + nit + ")";
    }
}
