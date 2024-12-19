package Proyecto1.Product;
import java.io.Serializable;
import java.util.ArrayList;
public class ProductDAO implements Serializable {
    public ArrayList<Product> products;
    private static ProductDAO instance;
    private ProductDAO() {
        this.products = new ArrayList<>();
    }
    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }
    public Object[][] getProductsData() {
        Object[][] data = new Object[products.size()][4];
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getName();
            data[i][2] = product.getPrice();
            data[i][3] = product.getStock();
        }
        return data;
    }
    public boolean addProduct(int id, String name, double price, int stock) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        products.add(new Product(id, name, price, stock));
        return true;
    }
    public boolean updateProductStock(String name, int stock) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                p.setStock(stock);
                return true;
            }
        }
        return false;
    }
    public boolean updateProductPrice(String name, double price) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                p.setPrice(price);
                return true;
            }
        }
        return false;
    }
    public boolean deleteProduct(String name) {
        return products.removeIf(p -> p.getName().equalsIgnoreCase(name));
    }

    public Product findProduct(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}