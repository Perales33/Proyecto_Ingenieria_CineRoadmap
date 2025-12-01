package main.app.Controlador;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.Image;

import main.app.Modelo.Pelicula;
import main.app.Vista.PanelApp;
import main.app.Vista.PanelGenerador;
import main.app.Vista.PanelPeliculas;
import main.app.Vista.PanelPerfil;

public class ControladorPerfil
{
    public static void crearImagenPerfil(JPanel panelDatos, GridBagConstraints datosBag)
    {
        ImageIcon icono;

        try
        {
            icono = new ImageIcon(PanelPerfil.class.getResource("/main/resources/img/" + ControladorUsuario.getUsuarioActivo().getFoto()));
        }
        catch(Exception e)
        {
            icono = new ImageIcon();
        }

        JLabel imagenLabel;
        Image imagen;

        if(icono == null || icono.getImage() == null)
        {
            imagenLabel = new JLabel("No fotos");
        }
        else
        {
            imagen = icono.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
        }
        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panelDatos.add(imagenLabel, datosBag);
    }
    public static boolean actualizarEmail(String nuevoEmail)
    {
        String extensiones[] = 
        {
            "@gmail.com", "@yahoo.es", "@yahoo.com",
            "@outlook.com", "@hotmail.com", "@live.com"
        };

        String emaillower = nuevoEmail.toLowerCase();
        for(String ext : extensiones)
        {
            if(emaillower.endsWith(ext))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean actualizarNombre(String nuevoNombre)
    {
        if(nuevoNombre.length() >= 5)
        {
            return true;
        }
        return false;
    }

    public static void cambiarNombre(String nuevoNombre, JLabel labelNombreUsuario)
    {
        if(nuevoNombre != null && !nuevoNombre.trim().isEmpty())
                {
                    boolean actualizarNombre = ControladorPerfil.actualizarNombre(nuevoNombre); 

                    if(actualizarNombre)
                    {
                        ControladorUsuario.getUsuarioActivo().setnombreUsuario(nuevoNombre);
                        labelNombreUsuario.setText("Nombre: " + nuevoNombre);
                        ControladorApp.actualizarTitulo(PanelApp.getTitutloMain());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(PanelGenerador.getMain(), "El nuevo nombre debe tener al menos 5 dígitos", "Nombre invalido", JOptionPane.ERROR_MESSAGE);
                    }
                }
    }

    public static void cambiarEmail(String nuevoEmail,JLabel labelEmail)
    {
        if(nuevoEmail != null && !nuevoEmail.trim().isEmpty())
        {
            boolean extensionValida = ControladorPerfil.actualizarEmail(nuevoEmail);

            if(extensionValida)
            {
                ControladorUsuario.getUsuarioActivo().setEmail(nuevoEmail);
                labelEmail.setText("Email: " + nuevoEmail);
            }
            else
            {
                JOptionPane.showMessageDialog(PanelGenerador.getMain(), "El correo ingresado no tiene una extensión válida \n" + 
                "Extensiones permitidas: @gmail.com, @yahoo.es, @yahoo.com, @outlook.com, @hotmail.com, @live.com",
                "Email erroneo", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }

    public static void crearPanelPeliculasVistas(JPanel panelPeliculasVistas)
    {
        ArrayList<Pelicula> peliculasVistas = ControladorUsuario.getUsuarioActivo().getPeliculas();

        if (panelPeliculasVistas == null || peliculasVistas.isEmpty()) 
        {
            JLabel textoInicial = new JLabel("Actualmente no tienes películas vistas");
            panelPeliculasVistas.add(textoInicial);
        }

        panelPeliculasVistas.removeAll();
        if (peliculasVistas.isEmpty()) 
        {
            JLabel textoInicial = new JLabel("Actualmente no tienes películas vistas");
            panelPeliculasVistas.add(textoInicial);
        } 
        else 
        {
            for(Pelicula p : peliculasVistas)
            {
                panelPeliculasVistas.add(crearPeliculasVistas(p));
            }
        }
        panelPeliculasVistas.revalidate();
        panelPeliculasVistas.repaint();
    }

    private static JPanel crearPeliculasVistas(Pelicula p)
    {
        JPanel panelPeliculaV = new JPanel();
        panelPeliculaV.setLayout(new BoxLayout(panelPeliculaV, BoxLayout.Y_AXIS));
        panelPeliculaV.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        ImageIcon icono;
        JLabel imagenLabel;
        try
        {
            icono = new ImageIcon(PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto()));
            Image imagen = icono.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
            imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            panelPeliculaV.add(imagenLabel);
        }
        catch(Exception e)
        {
            icono = new ImageIcon();
        }
        
        return panelPeliculaV;
    }
}