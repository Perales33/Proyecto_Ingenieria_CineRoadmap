package main.app.Vista;   // Ajuste el package si la mueve a vista/retos

import javax.swing.*;

import main.app.Modelo.Pelicula;

import java.awt.*;
import java.util.Vector;

    /**
     * Tarjeta visual para mostrar una recomendación de película.
     */
public class TarjetaRecomendacion {
    public static JPanel crear(Pelicula p, String textoBoton) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(15, 15, 15));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        card.setPreferredSize(new Dimension(200, 330));

        // --- Poster real ---
        JLabel poster;
        try {
            ImageIcon icono = new ImageIcon(
                PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto())
            );

            Image img = icono.getImage().getScaledInstance(160, 230, Image.SCALE_SMOOTH);
            poster = new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            poster = new JLabel("Sin imagen");
        }

        poster.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Título ---
        JLabel lblTitulo = new JLabel(p.getnombrePelicula());
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 4, 0));

        // --- Año ---
        JLabel lblAnio = new JLabel(String.valueOf(p.getAnio()));
        lblAnio.setForeground(new Color(200, 200, 200));
        lblAnio.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblAnio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Botón ---
        JButton btn = new JButton(textoBoton);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(poster);
        card.add(lblTitulo);
        card.add(lblAnio);
        card.add(Box.createVerticalStrut(10));
        card.add(btn);

        return card;
    }

}
