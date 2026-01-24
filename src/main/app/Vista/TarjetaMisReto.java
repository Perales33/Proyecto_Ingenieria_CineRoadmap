package main.app.Vista;

import javax.swing.*;
import java.awt.*;

import main.app.Controlador.ControladorRetos;
import main.app.Controlador.ControladorUsuario;
import main.app.Modelo.Reto;

public class TarjetaMisReto {

    public static JPanel crear(Reto reto) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(15, 15, 15));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Badge estado
        JLabel badge = new JLabel(reto.getEstado().toString());
        badge.setOpaque(true);
        badge.setForeground(Color.WHITE);
        badge.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        if (reto.getEstado() == Reto.Estado.COMPLETADO) badge.setBackground(new Color(20, 140, 20));
        else if (reto.getEstado() == Reto.Estado.EXPIRADO) badge.setBackground(new Color(90, 90, 90));
        else badge.setBackground(new Color(170, 0, 0)); // ACEPTADO

        JLabel titulo = new JLabel(reto.getNombreReto());
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));

        JLabel desc = new JLabel("<html><body style='width:240px'>" + reto.getDescripcion() + "</body></html>");
        desc.setForeground(new Color(220, 220, 220));

        JLabel progreso = new JLabel("Progreso: " + reto.getProgresoActual() + " / " + reto.getProgresoObjetivo());
        progreso.setForeground(Color.WHITE);
        progreso.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        acciones.setOpaque(false);

        JButton sumar = new JButton("+1 avance");
        JButton completar = new JButton("Completar");

        // Si ya está completado/expirado, deshabilitamos
        boolean activo = (reto.getEstado() == Reto.Estado.ACEPTADO);
        sumar.setEnabled(activo);
        completar.setEnabled(activo);

        sumar.addActionListener(e -> {
            ControladorRetos.sumarProgreso(ControladorUsuario.getUsuarioActivo(), reto.getIdReto(), 1);
            PanelGenerador.getMain().add(PanelMisRetos.crearPanel(), "MisRetos");
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "MisRetos");
        });

        completar.addActionListener(e -> {
            ControladorRetos.completar(ControladorUsuario.getUsuarioActivo(), reto.getIdReto());
            PanelGenerador.getMain().add(PanelMisRetos.crearPanel(), "MisRetos");
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "MisRetos");
        });

        acciones.add(sumar);
        acciones.add(completar);

        card.add(badge);
        card.add(titulo);
        card.add(desc);
        card.add(progreso);
        card.add(Box.createVerticalStrut(10));
        card.add(acciones);

        return card;
    }
}
