package main.app.Vista;

import javax.swing.*;
import java.awt.*;

import main.app.Controlador.ControladorRetos;
import main.app.Controlador.ControladorUsuario;
import main.app.Modelo.Reto;
import main.app.Modelo.Usuario;

public class TarjetaMisReto 
{
    // Crear la tarjeta del reto
    public static JPanel crear(Reto reto) 
    {
        // ------------------------
        // PANEL DE LA TARJETA
        // ------------------------

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(15, 15, 15));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // ------------------------
        // BADGE ESTADO
        // ------------------------
        JLabel badge = new JLabel(textoEstado(reto.getEstado()));
        badge.setOpaque(true);
        badge.setForeground(Color.WHITE);
        badge.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        if (reto.getEstado() == Reto.Estado.COMPLETADO) badge.setBackground(new Color(20, 140, 20));
        else if (reto.getEstado() == Reto.Estado.EXPIRADO) badge.setBackground(new Color(90, 90, 90));
        else badge.setBackground(new Color(170, 0, 0)); // ACEPTADO -> EN PROGRESO

        // ------------------------
        // TITULO / DESCRIPCION
        // ------------------------
        JLabel titulo = new JLabel(reto.getNombreReto());
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));

        JLabel desc = new JLabel("<html><body style='width:240px'>" + reto.getDescripcion() + "</body></html>");
        desc.setForeground(new Color(220, 220, 220));

        // ------------------------
        // PROGRESO: TEXTO + BARRA
        // ------------------------
        int actual = Math.max(0, reto.getProgresoActual());
        int objetivo = Math.max(1, reto.getProgresoObjetivo());

        JLabel progresoTxt = new JLabel("Progreso: " + actual + " / " + objetivo);
        progresoTxt.setForeground(Color.WHITE);
        progresoTxt.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));

        JProgressBar barra = new JProgressBar(0, objetivo);
        barra.setValue(Math.min(actual, objetivo));
        barra.setStringPainted(true);
        barra.setString(actual + " / " + objetivo);
        barra.setPreferredSize(new Dimension(260, 16));
        barra.setMaximumSize(new Dimension(260, 16));

        // ------------------------
        // ACCIONES
        // ------------------------
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        acciones.setOpaque(false);

        JButton sumar = new JButton("+1 avance");
        JButton completar = new JButton("Completar");

        boolean activo = (reto.getEstado() == Reto.Estado.ACEPTADO); // Poner el estado del reto a aceptado cuando le de al botón
        sumar.setEnabled(activo); // Activa el botón para sumar progreso
        completar.setEnabled(activo); // Activa el botón para completar el reto

        // Se le da al botón de +1 avance (Actualiza el estado del reto)
        sumar.addActionListener(e -> 
        {
            Usuario u = ControladorUsuario.getUsuarioActivo();
            if (u == null) return;

            ControladorRetos.sumarProgreso(u, reto.getIdReto(), 1);
            refrescarPantalla("MisRetos", PanelMisRetos.crearPanel());
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "MisRetos");
        });

        // Se le da al botón de completar (Completa el reto)
        completar.addActionListener(e -> 
        {
            Usuario u = ControladorUsuario.getUsuarioActivo();
            if (u == null) 
            {
                return;
            }
            
            ControladorRetos.completar(u, reto.getIdReto());
            refrescarPantalla("MisRetos", PanelMisRetos.crearPanel());
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "MisRetos");
        });

        acciones.add(sumar);
        acciones.add(completar);

        // ------------------------
        // MONTAJE
        // ------------------------
        card.add(badge);
        card.add(titulo);
        card.add(desc);
        card.add(progresoTxt);
        card.add(barra);
        card.add(Box.createVerticalStrut(12));
        card.add(acciones);

        return card;
    }

    // Estado de los retos
    private static String textoEstado(Reto.Estado estado) 
    {
        if (estado == null) return "DESCONOCIDO";
        switch (estado) {
            case ACEPTADO: return "EN PROGRESO";
            case COMPLETADO: return "COMPLETADO";
            case EXPIRADO: return "EXPIRADO";
            case DISPONIBLE: return "DISPONIBLE";
            default: return estado.toString();
        }
    }

    // Refrescar la página de retos
    private static void refrescarPantalla(String nombre, JPanel nuevoPanel) {
        JPanel main = PanelGenerador.getMain();

        Component[] comps = main.getComponents();
        for (Component c : comps) 
        {
            if (nombre.equals(c.getName())) 
            {
                main.remove(c);
                break;
            }
        }

        nuevoPanel.setName(nombre);
        main.add(nuevoPanel, nombre);

        main.revalidate();
        main.repaint();
    }
}
