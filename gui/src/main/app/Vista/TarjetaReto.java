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
        tipo.setBackground(new Color(170, 0, 0));
        tipo.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        JLabel titulo = new JLabel(reto.getNombreReto());
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));

        JLabel desc = new JLabel("<html><body style='width:240px'>" + reto.getDescripcion() + "</body></html>");
        desc.setForeground(new Color(220, 220, 220));

        // ✅ Botón dinámico según estado del reto
        JButton accion = new JButton();
        configurarBotonSegunEstado(accion, reto);

        accion.addActionListener(e -> {

            if (ControladorUsuario.getUsuarioActivo() == null) {
                JOptionPane.showMessageDialog(card,
                        "Debe iniciar sesión para aceptar un reto.",
                        "Sin sesión",
                        JOptionPane.WARNING_MESSAGE);
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
                return;
            }

            // Si no está disponible, no hacemos nada
            if (reto.getEstado() != Reto.Estado.DISPONIBLE) {
                JOptionPane.showMessageDialog(card,
                        "Este reto ya no está disponible para aceptar.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            boolean ok = ControladorRetos.aceptarReto(ControladorUsuario.getUsuarioActivo(), reto);

            if (!ok) {
                JOptionPane.showMessageDialog(card,
                        "No se ha podido aceptar el reto (ya lo tienes aceptado o no está vigente).",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                // ✅ refresco igualmente por si venía cruzado y el estado ya cambió
                refrescarPantallaRetos();
                return;
            }

            JOptionPane.showMessageDialog(card,
                    "Reto aceptado. Ya aparece como aceptado en Retos y en “Mis Retos”.",
                    "Reto aceptado",
                    JOptionPane.INFORMATION_MESSAGE);

            // ✅ Refrescar pantalla de Retos para que el botón pase a “Ya aceptado”
            refrescarPantallaRetos();
        });

        card.add(tipo);
        card.add(titulo);
        card.add(desc);
        card.add(Box.createVerticalStrut(10));
        card.add(accion);

        return card;
    }

    private static void configurarBotonSegunEstado(JButton b, Reto reto) {

        // Estado por defecto
        b.setEnabled(true);

        if (reto.getEstado() == Reto.Estado.DISPONIBLE) {
            b.setText("Aceptar reto");
            b.setEnabled(true);
            return;
        }

        if (reto.getEstado() == Reto.Estado.ACEPTADO) {
            b.setText("Ya aceptado (ver en Mis Retos)");
            b.setEnabled(true); // lo dejamos habilitado para ir a Mis Retos
            b.addActionListener(e -> PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "MisRetos"));
            return;
        }

        if (reto.getEstado() == Reto.Estado.COMPLETADO) {
            b.setText("Completado ✓");
            b.setEnabled(false);
            return;
        }

        if (reto.getEstado() == Reto.Estado.EXPIRADO) {
            b.setText("Expirado");
            b.setEnabled(false);
        }
    }

    private static void refrescarPantallaRetos() {

        // Quitamos la tarjeta anterior "Retos" si existe y la recreamos
        JPanel main = PanelGenerador.getMain();

        Component[] comps = main.getComponents();
        for (Component c : comps) {
            if ("Retos".equals(c.getName())) {
                main.remove(c);
                break;
            }
        }

        JPanel nuevo = PanelRetosRecomendaciones.crearPanel();
        nuevo.setName("Retos");
        main.add(nuevo, "Retos");

        main.revalidate();
        main.repaint();

        PanelGenerador.getColocacion().show(main, "Retos");
    }
}
