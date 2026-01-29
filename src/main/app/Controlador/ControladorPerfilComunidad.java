package main.app.Controlador;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import main.app.Modelo.Calificacion;
import main.app.Modelo.Logro;
import main.app.Modelo.Pelicula;
import main.app.Vista.PanelPeliculas;
import main.app.Vista.PanelPerfilComunidad;
import main.app.Modelo.Usuario;
import main.app.util.WrapLayout;

public class ControladorPerfilComunidad
{
    // =========================
    // DATOS DEL USUARIO
    // =========================

    // Crea la imagen de perfil de un usuario de la comunidad
    public static void crearImagenPerfil(JPanel panelDatos, GridBagConstraints datosBag, Usuario usuario)
    {
        ImageIcon icono; // Variable que cargará la imagen

        try 
        {
            // Carga la imagen del usuario desde los recursos
            icono = new ImageIcon(PanelPerfilComunidad.class.getResource("/main/resources/img/" + usuario.getFoto()));
        } 
        catch(Exception e) 
        {
            // En caso de error, crea un icono vacío
            icono = new ImageIcon();
        }

        JLabel imagenLabel; // Convierte la imagen en un label para luego tener 

        // Comprueba si la imagen existe
        if(icono == null || icono.getImage() == null) 
        {
            imagenLabel = new JLabel("No hay foto");
        } 
        else 
        {
            // Dimensionar la imagen
            Image imagen = icono.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
        }

        // Añade la imagen al panel
        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panelDatos.add(imagenLabel, datosBag);
    }

    // Crea el panel con las películas vistas por un usuario
    public static void crearPanelPeliculasVistas(JPanel panelPeliculasVistas, Usuario usuario)
    {
        // Comprueba que el panel exista
        if (panelPeliculasVistas == null)
        {
            return;
        }
            

        // Limpia el panel
        panelPeliculasVistas.removeAll();
        panelPeliculasVistas.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

        // Obtiene las calificaciones del usuario activo
        ArrayList<Calificacion> calificacionesUsuario = new ArrayList<>();
        for (Calificacion c : Calificacion.getListaCalificaciones()) 
        {
            if (c.getUsuario().equals(usuario)) 
            {
                calificacionesUsuario.add(c);
            }
        }

        // Comprueba si el usuario tiene películas vistas
        if (calificacionesUsuario.isEmpty())
        {
            JLabel textoInicial = new JLabel("Este usuario no tiene películas vistas");
            panelPeliculasVistas.add(textoInicial);
        }
        else
        {
            // Recorre las películas vistas
            for (Calificacion c : calificacionesUsuario) 
            {
                panelPeliculasVistas.add(crearPanelPelicula(c));
            }
        }

        // Actualiza el panel
        panelPeliculasVistas.revalidate();
        panelPeliculasVistas.repaint();
    }

    // Crea un panel para cada una de las películas valoradas por el usuario
    private static JPanel crearPanelPelicula(Calificacion calif)
    {
        // Obtiene película valorada en esa iteración
        Pelicula p = calif.getPelicula();

        // Crear el panel de la película donde se va colocar los datos de la película
        JPanel panelPelicula = new JPanel();
        panelPelicula.setLayout(new BoxLayout(panelPelicula, BoxLayout.Y_AXIS));
        panelPelicula.setBorder(BorderFactory.createTitledBorder(p.getnombrePelicula()));
        panelPelicula.setPreferredSize(new Dimension(120, 190));
        panelPelicula.setMaximumSize(new Dimension(120, 190));
        panelPelicula.setMinimumSize(new Dimension(120, 190));

        // Imagen de la película
        try 
        {
            // En caso de que haya imagen la muestra
            ImageIcon icono = new ImageIcon(PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto()));
            Image imagen = icono.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
            imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPelicula.add(imagenLabel);
        } 
        catch (Exception e) 
        {
            // En caso de error, muestra texto
            JLabel imagenLabel = new JLabel("No imagen");
            imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPelicula.add(imagenLabel);
        }

        // Espacio flexible
        panelPelicula.add(Box.createVerticalGlue());

        // Muestra la valoración
        JLabel valoracionLabel = new JLabel("Valoración: " + calif.getCalificacion() + "/5", JLabel.CENTER);
        valoracionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        valoracionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPelicula.add(valoracionLabel);

        return panelPelicula;
    }

    // =========================
    // LOGROS E INSIGNIAS
    // =========================

    // Crea el panel con los logros del usuario
    public static void crearPanelLogros(JPanel panelLogros, Usuario usuario) 
    {
        // Limpia el panel
        panelLogros.removeAll();

        // Usa un GridLayout de 4 columnas
        panelLogros.setLayout(new GridLayout(0, 4, 10, 10));

        // Comprueba que haya usuario
        if (usuario != null) 
        {
            ArrayList<Logro> logros = usuario.getLogros();

            // Comprueba si tiene logros
            if (logros.isEmpty()) 
            {
                panelLogros.add(new JLabel("Este usuario no tiene logros desbloqueados"));
            } 
            else 
            {
                // Recorre los logros
                for (Logro logro : logros) 
                {
                    // Solo muestra los logros completados
                    if (logro.isCompleto()) 
                    {
                        // Crear un panel por cada logro completo
                        JPanel card = new JPanel();
                        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                        card.setOpaque(false);
                        card.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                        // Imagen del logro
                        JLabel iconLabel;

                        // Comprueba que el logro tenga una insignia asociada o que la insignia no tenga una imagen a null
                        if (logro.getInsignia() != null && logro.getInsignia().getImagen() != null) 
                        {
                            Image img = logro.getInsignia().getImagen().getImage();
                            img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                            iconLabel = new JLabel(new ImageIcon(img));
                        } 
                        else 
                        {
                            // En el caso de que no tenga imagen muestra un texto
                            iconLabel = new JLabel("Sin imagen");
                            iconLabel.setPreferredSize(new Dimension(100, 100));
                        }

                        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        card.add(iconLabel);
                        panelLogros.add(card);
                    }
                }
            }
        }

        // Actualiza el panel
        panelLogros.revalidate();
        panelLogros.repaint();
    }
}
