package Proyecto1.Componentes;

import Proyecto1.Product.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TableUtils {
       // Método para calcular el ancho máximo del texto del nombre
    public static int calculateMaxNameWidth(List<Product> products, Font font) {
        FontMetrics metrics = new JLabel().getFontMetrics(font);
        int maxWidth = metrics.stringWidth("Nombre") + 20;

        for (Product product : products) {
            int width = metrics.stringWidth(product.getName());
            if (width > maxWidth) {
                maxWidth = width + 20;
            }
        }
        return maxWidth;
    }
}
