package Proyecto1.Componentes;


import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {
    public RoundedTextField() {
        setFont(new Font("Roboto", Font.PLAIN, 14));
        setForeground(Color.WHITE);
        setCaretColor(Color.WHITE);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(new Color(100, 181, 246), 2, true));
    }
}
