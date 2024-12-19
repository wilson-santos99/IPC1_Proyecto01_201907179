package Proyecto1.Componentes;

import javax.swing.*;
import java.awt.*;

public class SalesViewComponents {

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Roboto", Font.BOLD, 24));
        label.setForeground(new Color(230, 230, 230));
        label.setBounds(150, 20, 500, 30);
        return label;
    }
}
