package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import main.app.Controlador.ControladorRetos;
import main.app.Controlador.ControladorUsuario;
import main.app.Modelo.Reto;
import main.app.Modelo.Usuario;

public class PanelMisRetos {

    public static JPanel crearPanel() {

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(Color.BLACK);

        // =========================
        // TÍTULO
        // =========================
        JPanel tituloWrap = new JPanel(new BorderLayout());
        tituloWrap.setBackground(Color.BLACK);
        tituloWrap.setBorder(BorderFactory.createEmptyBorder(30, 40, 10, 40));
        tituloWrap.setMaximumSize(new Dimension(1200, 120));

        JLabel titulo = new JLabel("Mis Retos");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 44));

        JLabel subt = new JLabel("Aquí verás los retos que has aceptado y su progreso.");
        subt.setForeground(new Color(220, 220, 220));
        subt.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JPanel t = new JPanel();
        t.setBackground(Color.BLACK);
        t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
        t.add(titulo);
        t.add(Box.createVerticalStrut(8));
        t.add(subt);

        tituloWrap.add(t, BorderLayout.WEST);
        contenido.add(tituloWrap);

        // =========================
        // DATOS
        // =========================
        Usuario u = ControladorUsuario.getUsuarioActivo();
        List<Reto> lista = ControladorRetos.getMisRetos(u);

        // Si no hay usuario logueado
        if (u == null) {
            contenido.add(crearTituloSeccion("Sesión no iniciada"));
            contenido.add(wrapAncho(crearCardVacia(
                    "Debe iniciar sesión",
                    "Inicie sesión para ver sus retos aceptados."
            )));
            return construirConScrollYHeader(contenido);
        }

        // Si no hay retos
        if (lista == null || lista.isEmpty()) {
            contenido.add(crearTituloSeccion("Aún no tienes retos"));
            contenido.add(wrapAncho(crearCardVacia(
                    "Aún no tienes retos aceptados",
                    "Vuelve a “Retos y Recomendaciones” y pulsa “Aceptar reto”."
            )));
            contenido.add(Box.createVerticalStrut(40));
            return construirConScrollYHeader(contenido);
        }

        // Separar por estados
        List<Reto> enProgreso = new ArrayList<>();
        List<Reto> completados = new ArrayList<>();
        List<Reto> expirados = new ArrayList<>();

        for (Reto r : lista) {
            if (r.getEstado() == Reto.Estado.ACEPTADO) enProgreso.add(r);
            else if (r.getEstado() == Reto.Estado.COMPLETADO) completados.add(r);
            else if (r.getEstado() == Reto.Estado.EXPIRADO) expirados.add(r);
            else enProgreso.add(r); // fallback seguro
        }

        // =========================
        // SECCIÓN: EN PROGRESO
        // =========================
        contenido.add(crearTituloSeccion("En progreso"));
        if (enProgreso.isEmpty()) {
            contenido.add(wrapAncho(crearCardVacia(
                    "No tienes retos en progreso",
                    "Acepta un reto desde “Retos y Recomendaciones”."
            )));
        } else {
            contenido.add(wrapAncho(crearGrid(enProgreso)));
        }

        // =========================
        // SECCIÓN: COMPLETADOS
        // =========================
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(crearTituloSeccion("Completados"));
        if (completados.isEmpty()) {
            contenido.add(wrapAncho(crearCardVacia(
                    "Aún no has completado retos",
                    "Cuando completes uno, aparecerá aquí."
            )));
        } else {
            contenido.add(wrapAncho(crearGrid(completados)));
        }

        // =========================
        // SECCIÓN: EXPIRADOS
        // =========================
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(crearTituloSeccion("Expirados"));
        if (expirados.isEmpty()) {
            contenido.add(wrapAncho(crearCardVacia(
                    "No tienes retos expirados",
                    "Perfecto: significa que vas al día."
            )));
        } else {
            contenido.add(wrapAncho(crearGrid(expirados)));
        }

        contenido.add(Box.createVerticalStrut(40));

        return construirConScrollYHeader(contenido);
    }

    private static JPanel construirConScrollYHeader(JPanel contenido) {
        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getViewport().setBackground(Color.BLACK);

        JPanel contenedorFinal = new JPanel(new BorderLayout());
        contenedorFinal.setBackground(Color.BLACK);

        contenedorFinal.add(PanelBanner.crearBanner(), BorderLayout.NORTH);
        contenedorFinal.add(scroll, BorderLayout.CENTER);

        return contenedorFinal;
    }

    private static JComponent crearGrid(List<Reto> lista) {
        JPanel grid = new JPanel(new GridLayout(0, 3, 30, 30));
        grid.setBackground(Color.BLACK);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        for (Reto r : lista) {
            grid.add(TarjetaMisReto.crear(r));
        }

        return grid;
    }

    private static JComponent crearTituloSeccion(String texto) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.BLACK);
        p.setBorder(BorderFactory.createEmptyBorder(25, 40, 10, 40));
        p.setMaximumSize(new Dimension(1200, 70));

        JLabel l = new JLabel(texto);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("SansSerif", Font.BOLD, 22));

        p.add(l, BorderLayout.WEST);
        return p;
    }

    private static JComponent wrapAncho(JComponent inner) {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(Color.BLACK);
        wrap.setMaximumSize(new Dimension(1200, Integer.MAX_VALUE));
        wrap.add(inner, BorderLayout.CENTER);
        return wrap;
    }

    private static JPanel crearCardVacia(String titulo, String descripcion) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(15, 15, 15));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel t = new JLabel(titulo);
        t.setForeground(Color.WHITE);
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JLabel d = new JLabel("<html><body style='width:240px'>" + descripcion + "</body></html>");
        d.setForeground(new Color(220, 220, 220));

        card.add(t);
        card.add(d);

        return card;
    }
}
