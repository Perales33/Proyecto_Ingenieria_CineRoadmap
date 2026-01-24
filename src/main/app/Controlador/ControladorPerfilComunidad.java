package main.app.Controlador;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import main.app.Modelo.Calificacion;
import main.app.Modelo.Pelicula;
import main.app.Vista.PanelPeliculas;
import main.app.Vista.PanelPerfilComunidad;
import main.app.Modelo.Usuario;
import main.app.util.WrapLayout;

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

    // Crear panel de películas vistas
    public static void crearPanelPeliculasVistas(JPanel panelPeliculasVistas, Usuario usuario)
    {
        if (panelPeliculasVistas == null) return;

        panelPeliculasVistas.removeAll();
        panelPeliculasVistas.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

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
            for (Calificacion c : calificacionesUsuario) {
                panelPeliculasVistas.add(crearPanelPelicula(c));
            }
        }

        panelPeliculasVistas.revalidate();
        panelPeliculasVistas.repaint();
    }

    // Panel individual de película
    private static JPanel crearPanelPelicula(Calificacion calif)
    {
        Pelicula p = calif.getPelicula();

        JPanel panelPelicula = new JPanel();
        panelPelicula.setLayout(new BoxLayout(panelPelicula, BoxLayout.Y_AXIS));
        panelPelicula.setBorder(BorderFactory.createTitledBorder(p.getnombrePelicula()));
        panelPelicula.setPreferredSize(new Dimension(120, 190));
        panelPelicula.setMaximumSize(new Dimension(120, 190));
        panelPelicula.setMinimumSize(new Dimension(120, 190));

        // Imagen
        try {
            ImageIcon icono = new ImageIcon(
                PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto())
            );
            Image imagen = icono.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
            imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPelicula.add(imagenLabel);
        } catch (Exception e) {
            JLabel imagenLabel = new JLabel("No imagen");
            imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPelicula.add(imagenLabel);
        }

        // Espacio flexible
        panelPelicula.add(Box.createVerticalGlue());

        // Valoración
        JLabel valoracionLabel = new JLabel("★ " + calif.getCalificacion() + "/5");
        valoracionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        valoracionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPelicula.add(valoracionLabel);

        return panelPelicula;
    }
}
