package Proyecto1.Componentes;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {
    public static ImageIcon loadImage(String path, int width, int height) {
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}
