package main.app.Controlador;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Image;

import main.app.Modelo.Pelicula;
import main.app.Modelo.Usuario;
import main.app.Vista.PanelPeliculas;
import main.app.util.Estilos;

public class ControladorPeliculas 
{
    // Crea un panel con la información de una película
    public static JPanel crearPeliculas(Pelicula p)
    {
        // Obtiene el usuario activo
        Usuario u = ControladorUsuario.getUsuarioActivo();

        // Comprueba que haya un usuario activo
        if (u == null) { return null; }

        // Crea el panel principal de la película
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(p.getnombrePelicula()));

        ImageIcon icono;

        try
        {
            // Carga la imagen de la película desde los recursos del repositorio
            icono = new ImageIcon(PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto()));

            // Comprueba si la imagen existe
            if(icono.getImage() == null)
            {
                icono = new ImageIcon();
            }
        }
        catch(Exception e)
        {
            // En caso de error, crea un icono vacío
            icono = new ImageIcon();
        }

        // Redimensiona la imagen
        Image imagen = icono.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);

        // Crea el JLabel con la imagen
        JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panel.add(imagenLabel, BorderLayout.WEST);

        // Crea el área de texto con los datos de la película
        JTextArea datosPelicula = new JTextArea();
        datosPelicula.setText
        (
            "\n" +
            "Año: " + p.getAnio() + "\n\n" +
            "Géneros: " + p.getGeneros() + "\n\n" +
            "Directores: " + p.getDirectores() + "\n\n" +
            "Actores: " + p.getActores() + "\n\n" +
            "Descripción: " + p.getDescripcion() + "\n\n"
        );

        // Aplica los estilos al JTextArea
        Estilos.estilosTextArea(datosPelicula);

        // Añade los datos al centro del panel
        panel.add(datosPelicula, BorderLayout.CENTER);
    
        return panel;
    }
}

