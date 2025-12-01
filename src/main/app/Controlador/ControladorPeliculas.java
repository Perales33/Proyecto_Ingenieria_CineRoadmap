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
    public static JPanel crearPeliculas(Pelicula p)
    {
        Usuario u = ControladorUsuario.getUsuarioActivo();

        if (u == null) { return null; }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(p.getnombrePelicula()));

        ImageIcon icono;

        try
        {
            icono = new ImageIcon(PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto()));
            if(icono.getImage() == null)
            {
                icono = new ImageIcon();
            }
        }
        catch(Exception e)
        {
            icono = new ImageIcon();
        }
        
        Image imagen = icono.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panel.add(imagenLabel, BorderLayout.WEST);

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
        Estilos.estilosTextArea(datosPelicula);

        panel.add(datosPelicula, BorderLayout.CENTER);
    
        return panel;
    }
}
