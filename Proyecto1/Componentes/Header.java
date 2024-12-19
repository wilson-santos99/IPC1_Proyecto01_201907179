package Proyecto1.Componentes;

import javax.swing.*;
import java.awt.*;

public class Header extends JLabel {
    public Header(String text) {
        super(text, SwingConstants.CENTER);
        setFont(new Font("Roboto", Font.BOLD, 26));
        setForeground(new Color(224, 247, 250)); // Azul claro para texto
    }
}
