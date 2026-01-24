package main.app.Vista;

import javax.swing.*;
import java.awt.*;

import main.app.Controlador.ControladorRetos;
import main.app.Controlador.ControladorUsuario;
import main.app.Modelo.Reto;

public class TarjetaReto {

    public static JPanel crear(Reto reto) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(15, 15, 15));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel tipo = new JLabel(reto.getTipo().toString());
        tipo.setOpaque(true);
        tipo.setForeground(Color.WHITE);
        tipo.setBackground(new Color(170, 0, 0)); // rojo
        tipo.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        JLabel titulo = new JLabel(reto.getNombreReto());
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));

        JLabel desc = new JLabel("<html><body style='width:240px'>" + reto.getDescripcion() + "</body></html>");
        desc.setForeground(new Color(220, 220, 220));

        JButton aceptar = new JButton("Aceptar reto");

        aceptar.addActionListener(e -> {

            if (ControladorUsuario.getUsuarioActivo() == null) {
                JOptionPane.showMessageDialog(card,
                        "Debe iniciar sesión para aceptar un reto.",
                        "Sin sesión",
                        JOptionPane.WARNING_MESSAGE);
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
                return;
            }

            boolean ok = ControladorRetos.aceptarReto(ControladorUsuario.getUsuarioActivo(), reto);

            if (!ok) {
                JOptionPane.showMessageDialog(card,
                        "No se ha podido aceptar el reto (ya lo tienes aceptado o no está vigente).",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(card,
                    "Reto aceptado. Puedes verlo en “Mis Retos”.",
                    "Reto aceptado",
                    JOptionPane.INFORMATION_MESSAGE);

            // Ir a Mis Retos
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "MisRetos");
        });

        card.add(tipo);
        card.add(titulo);
        card.add(desc);
        card.add(Box.createVerticalStrut(10));
        card.add(aceptar);

        return card;
    }
}
