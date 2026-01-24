package main.app.Vista;   // Ajuste el package si la mueve a vista/retos

import javax.swing.*;
import java.awt.*;

/**
 * Tarjeta visual para mostrar una recomendación de película.
 */
public class TarjetaRecomendacion {

    public static JPanel crear(String titulo, String genero, String textoBoton) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(15, 15, 15));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        card.setPreferredSize(new Dimension(200, 330));

        // --- Poster (placeholder) ---
        JLabel poster = new JLabel("POSTER");
        poster.setPreferredSize(new Dimension(160, 220));
        poster.setMaximumSize(new Dimension(160, 220));
        poster.setAlignmentX(Component.CENTER_ALIGNMENT);
        poster.setHorizontalAlignment(SwingConstants.CENTER);
        poster.setOpaque(true);
        poster.setBackground(new Color(35, 35, 35));
        poster.setForeground(new Color(170, 170, 170));
        poster.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));

        // --- Título ---
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 4, 0));

        // --- Género ---
        JLabel lblGenero = new JLabel(genero);
        lblGenero.setForeground(new Color(200, 200, 200));
        lblGenero.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblGenero.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Botón acción ---
        JButton btn = new JButton(textoBoton);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Si tiene su sistema de estilos, aplíquelo aquí:
        // Estilos.estiloBotones(btn);

        btn.addActionListener(e ->
                JOptionPane.showMessageDialog(card,
                        "Película añadida a tu lista (demo)",
                        "Recomendación",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        card.add(poster);
        card.add(lblTitulo);
        card.add(lblGenero);
        card.add(Box.createVerticalStrut(10));
        card.add(btn);

        return card;
    }
}
