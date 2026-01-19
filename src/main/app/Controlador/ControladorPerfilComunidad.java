package main.app.Controlador;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import main.app.Modelo.Calificacion;
import main.app.Modelo.Pelicula;
import main.app.Vista.PanelPeliculas;
import main.app.Vista.PanelPerfilComunidad;
import main.app.Modelo.Usuario;

public class ControladorPerfilComunidad
{
    // Crear la imagen de perfil de un usuario
    public static void crearImagenPerfil(JPanel panelDatos, GridBagConstraints datosBag, Usuario usuario)
    {
        ImageIcon icono;
        try {
            icono = new ImageIcon(PanelPerfilComunidad.class.getResource("/main/resources/img/" + usuario.getFoto()));
        } catch(Exception e) {
            icono = new ImageIcon();
        }

        JLabel imagenLabel;
        if(icono == null || icono.getImage() == null) {
            imagenLabel = new JLabel("No hay foto");
        } else {
            Image imagen = icono.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
        }

        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panelDatos.add(imagenLabel, datosBag);
    }

    // Crear panel de películas vistas de un usuario usando listaCalificaciones
    public static void crearPanelPeliculasVistas(JPanel panelPeliculasVistas, Usuario usuario)
    {
        if (panelPeliculasVistas == null) return;

        panelPeliculasVistas.removeAll();

        // Filtrar las calificaciones que pertenecen a este usuario
        ArrayList<Calificacion> calificacionesUsuario = new ArrayList<>();
        for (Calificacion c : Calificacion.getListaCalificaciones()) {
            if (c.getUsuario().equals(usuario)) {
                calificacionesUsuario.add(c);
            }
        }

        if (calificacionesUsuario.isEmpty())
        {
            JLabel textoInicial = new JLabel("Este usuario no tiene películas vistas");
            panelPeliculasVistas.add(textoInicial);
        }
        else
        {
            // Panel contenedor con GridLayout de 5 columnas (filas automáticas)
            int columnas = 5;
            int filas = (int) Math.ceil(calificacionesUsuario.size() / (double) columnas);
            JPanel contenedor = new JPanel(new GridLayout(filas, columnas, 10, 10));
            contenedor.setBackground(Color.WHITE);

            // Agregar cada película al contenedor
            for (Calificacion c : calificacionesUsuario) {
                contenedor.add(crearPanelPelicula(c));
            }

            // Scroll vertical
            JScrollPane scrollPeliculas = new JScrollPane(contenedor);
            scrollPeliculas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPeliculas.setPreferredSize(new Dimension(580, 400)); // Ajusta según diseño

            panelPeliculasVistas.setLayout(new BorderLayout());
            panelPeliculasVistas.add(scrollPeliculas, BorderLayout.CENTER);
        }

        panelPeliculasVistas.revalidate();
        panelPeliculasVistas.repaint();
    }

    // Crear panel individual de película con imagen y valoración
    private static JPanel crearPanelPelicula(Calificacion calif)
    {
        Pelicula p = calif.getPelicula();
        JPanel panelPelicula = new JPanel();
        panelPelicula.setLayout(new BoxLayout(panelPelicula, BoxLayout.Y_AXIS));
        panelPelicula.setBorder(BorderFactory.createTitledBorder(p.getnombrePelicula()));
        panelPelicula.setPreferredSize(new Dimension(100, 180));

        // Imagen de la película
        try {
            ImageIcon icono = new ImageIcon(PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto()));
            Image imagen = icono.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
            imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPelicula.add(imagenLabel);
        } catch (Exception e) {
            JLabel imagenLabel = new JLabel("No imagen");
            imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPelicula.add(imagenLabel);
        }

        // Valoración
        JLabel valoracionLabel = new JLabel("Valoración: " + calif.getCalificacion() + " ★");
        valoracionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPelicula.add(Box.createVerticalStrut(5));
        panelPelicula.add(valoracionLabel);

        return panelPelicula;
    }
}
