package Proyecto1.Componentes;


import javax.swing.*;
import java.awt.*;

public class CustomDialog {

    public static void showSuccessMessage(String message, String title) {
        showMessage(
                "<html><div style='text-align: center;'>" + message + "</div></html>",
                title,
                new Color(76, 175, 80),
                "/Proyecto1/Resources/check.png"
        );
    }

    public static void showErrorMessage(String message, String title) {
        showMessage(
                "<html><div style='text-align: center;'>" + message + "</div></html>",
                title,
                new Color(244, 67, 54),
                "/Proyecto1/Resources/error.png"
        );
    }

    public static void showWarningMessage(String message, String title) {
        showMessage(
                "<html><div style='text-align: center;'>" + message + "</div></html>",
                title,
                new Color(255, 193, 7),
                "/Proyecto1/Resources/warning.png"
        );
    }

    private static void showMessage(String message, String title, Color color, String iconPath) {
        JLabel label = new JLabel(message, JLabel.CENTER);
        label.setFont(new Font("Roboto", Font.PLAIN, 16));
        label.setForeground(color);

        // Cargar el ícono
        ImageIcon icon = null;
        if (iconPath != null && !iconPath.isEmpty()) {
            try {
                icon = new ImageIcon(
                        new ImageIcon(CustomDialog.class.getResource(iconPath))
                                .getImage()
                                .getScaledInstance(50, 50, Image.SCALE_SMOOTH)
                );
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
            }
        }

        // Configuración de UI
        UIManager.put("OptionPane.messageFont", new Font("Roboto", Font.PLAIN, 14));
        UIManager.put("OptionPane.background", new Color(255, 255, 255));
        UIManager.put("Panel.background", new Color(255, 255, 255));

        // Mostrar el cuadro de diálogo
        JOptionPane.showMessageDialog(
                null,
                createMessagePanel(label, icon),
                title,
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private static JPanel createMessagePanel(JLabel label, ImageIcon icon) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(iconLabel, BorderLayout.WEST);
        }

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
