package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.app.Controlador.ControladorRetos;
import main.app.Controlador.ControladorUsuario;
import main.app.Modelo.Pelicula;
import main.app.Modelo.Reto;

public class PanelRetosRecomendaciones {

    public static JPanel crearPanel() {

        // Contenido vertical
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(Color.BLACK);

        // HERO
        PanelConFondo hero = new PanelConFondo(new GridBagLayout());
        hero.setPreferredSize(new Dimension(1200, 260));
        hero.setMaximumSize(new Dimension(1200, 260));
        hero.setMinimumSize(new Dimension(1200, 260));

        try {
            Image img = new ImageIcon(PanelRetosRecomendaciones.class
                    .getResource("/main/app/resources/img/heroRetos.jpg")).getImage();
            hero.setFondo(img);
        } catch (Exception e) {
            hero.setBackground(new Color(20, 20, 20));
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titulo = new JLabel("Retos y Recomendaciones");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 44));
        hero.add(titulo, gbc);

        gbc.gridy = 1;
        JLabel subt = new JLabel("<html>Completa retos diarios y semanales y descubre películas<br>que encajan con tus gustos y tus objetivos.</html>");
        subt.setForeground(Color.WHITE);
        subt.setFont(new Font("SansSerif", Font.PLAIN, 16));
        hero.add(subt, gbc);

        contenido.add(hero);

        // SECCIÓN RETOS
        contenido.add(crearTituloSeccion("Retos activos"));
        contenido.add(wrapAncho(crearGridRetos()));

        // SECCIÓN RECOMENDACIONES
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(crearTituloSeccion("Recomendaciones para ti"));
        contenido.add(wrapAncho(crearGridRecomendaciones()));

        contenido.add(Box.createVerticalStrut(30));

        // ✅ FOOTER (en el flujo del BoxLayout)
        try {
            JPanel footer = PanelFooter.crearFooter("© CineRoadmap", 1200, 30);
            footer.setMaximumSize(new Dimension(1200, 30));
            contenido.add(footer);
        } catch (Exception ignore) {
            // si no existe PanelFooter aún, no rompe el panel
        }

        // Scroll del contenido
        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getViewport().setBackground(Color.BLACK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // ✅ Contenedor final con header + contenido
        JPanel contenedorFinal = new JPanel(new BorderLayout());
        contenedorFinal.setBackground(Color.BLACK);

        // ✅ HEADER / NAVEGACIÓN
        JPanel banner = PanelBanner.crearBanner();
        contenedorFinal.add(banner, BorderLayout.NORTH);

        // ✅ CONTENIDO
        contenedorFinal.add(scroll, BorderLayout.CENTER);

        return contenedorFinal;
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
        wrap.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        wrap.setMaximumSize(new Dimension(1200, Integer.MAX_VALUE));
        wrap.add(inner, BorderLayout.CENTER);
        return wrap;
    }

    private static JComponent crearGridRetos() {
        JPanel grid = new JPanel(new GridLayout(0, 3, 30, 30));
        grid.setBackground(Color.BLACK);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        try {
            // ✅ IMPORTANTE: lista cruzada con los retos aceptados del usuario
            List<Reto> retos = ControladorRetos.obtenerRetosParaUsuario(ControladorUsuario.getUsuarioActivo());

            if (retos == null || retos.isEmpty()) {
                grid.add(crearCardVacia("No hay retos disponibles", "Todavía no se han cargado retos para este usuario."));
                return grid;
            }

            for (Reto r : retos) {
                grid.add(TarjetaReto.crear(r));
            }

        } catch (Exception ex) {
            grid.add(crearCardVacia("Error cargando retos", "Revise ControladorRetos: " + ex.getMessage()));
        }

        return grid;
    }

    private static JComponent crearGridRecomendaciones() {
        JPanel grid = new JPanel(new GridLayout(0, 4, 25, 25));
        grid.setBackground(Color.BLACK);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        try {
            ArrayList<Pelicula> catalogoOriginal = Pelicula.getCatalogo();
            ArrayList<Pelicula> catalogo = new ArrayList<>(catalogoOriginal);

            if (catalogo == null || catalogo.isEmpty()) {
                grid.add(crearCardVacia("Sin recomendaciones", "No hay películas en el catálogo."));
                return grid;
            }

            Collections.shuffle(catalogo);

            int limite = Math.min(4, catalogo.size());
            for (int i = 0; i < limite; i++) {
                Pelicula p = catalogo.get(i);
                grid.add(TarjetaRecomendacion.crear(p, "Añadir a lista"));
            }

        } catch (Exception e) {
            grid.add(crearCardVacia("Error", e.getMessage()));
        }

        return grid;
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
