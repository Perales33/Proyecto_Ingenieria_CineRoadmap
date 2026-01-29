package main.app.Vista;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;


public class PanelFooter
{
    public static JPanel crearFooter(String texto, int ancho, int alto) 
    {
        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        footer.setPreferredSize(new Dimension(ancho, alto));
        footer.setMaximumSize(new Dimension(ancho, alto));
        footer.setMinimumSize(new Dimension(ancho, alto));
        footer.setBackground(Color.DARK_GRAY);

        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        footer.add(label);

        return footer;
    }
}