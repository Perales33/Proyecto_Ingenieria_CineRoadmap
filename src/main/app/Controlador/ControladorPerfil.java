package main.app.Controlador;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import main.app.Modelo.Logro;
import main.app.Modelo.Pelicula;
import main.app.Modelo.Usuario;
import main.app.Vista.PanelPeliculas;
import main.app.Vista.PanelGenerador;
import main.app.Vista.PanelPerfil;

public class ControladorPerfil
{
    // =========================
    // DATOS DEL USUARIO
    // =========================

    // Crea y muestra la imagen del perfil del usuario
    public static void crearImagenPerfil(JPanel panelDatos, GridBagConstraints datosBag)
    {
        ImageIcon icono;

        try 
        {
            // Carga la imagen desde los recursos
            icono = new ImageIcon(PanelPerfil.class.getResource(
                    "/main/resources/img/" + ControladorUsuario.getUsuarioActivo().getFoto()));
        } 
        catch(Exception e) 
        {
            // En caso de error, crea un icono vacío
            icono = new ImageIcon();
        }

        JLabel imagenLabel;

        // Comprueba si la imagen existe
        if(icono == null || icono.getImage() == null) 
        {
            imagenLabel = new JLabel("No fotos");
        } 
        else 
        {
            // Redimensiona la imagen
            Image imagen = icono.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
        }

        // Añade la imagen al panel
        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panelDatos.add(imagenLabel, datosBag);
    }

    // Comprueba si el nuevo email tiene una extensión válida
    public static boolean actualizarEmail(String nuevoEmail)
    {
        String extensiones[] = {"@gmail.com", "@yahoo.es", "@yahoo.com","@outlook.com", "@hotmail.com", "@live.com"};
        String emaillower = nuevoEmail.toLowerCase();

        // Recorre las extensiones permitidas
        for(String ext : extensiones) 
        {
            if(emaillower.endsWith(ext)) return true;
        }
        return false;
    }

    // Comprueba si el nombre tiene al menos 5 caracteres
    public static boolean actualizarNombre(String nuevoNombre) 
    {
        return nuevoNombre.length() >= 5;
    }

    // Cambia el nombre del usuario
    public static void cambiarNombre(String nuevoNombre, JLabel labelNombreUsuario) 
    {
        // Comprueba que el campo no esté vacío
        if(nuevoNombre != null && !nuevoNombre.trim().isEmpty()) 
        {
            // Comprueba que el nombre sea válido
            if(actualizarNombre(nuevoNombre)) 
            {
                ControladorUsuario.getUsuarioActivo().setnombreUsuario(nuevoNombre);
                labelNombreUsuario.setText("Nombre: " + nuevoNombre);
            } 
            else 
            {
                JOptionPane.showMessageDialog(PanelGenerador.getMain(), "El nuevo nombre debe tener al menos 5 dígitos", "Nombre inválido", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Cambia el email del usuario
    public static void cambiarEmail(String nuevoEmail,JLabel labelEmail) 
    {
        // Comprueba que el campo no esté vacío
        if(nuevoEmail != null && !nuevoEmail.trim().isEmpty()) 
        {
            // Comprueba que el email sea válido
            if(actualizarEmail(nuevoEmail)) 
            {
                ControladorUsuario.getUsuarioActivo().setEmail(nuevoEmail);
                labelEmail.setText("Email: " + nuevoEmail);
            } 
            else 
            {
                JOptionPane.showMessageDialog(PanelGenerador.getMain(), "El correo ingresado no tiene una extensión válida\n" + "Extensiones permitidas: @gmail.com, @yahoo.es, @yahoo.com, @outlook.com, @hotmail.com, @live.com", "Email erróneo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =========================
    // PELÍCULAS
    // =========================

    // Crea el panel con las películas vistas por el usuario
    public static void crearPanelPeliculasVistas(JPanel panelPeliculasVistas) 
    {
        // Obtiene la lista de películas vistas
        ArrayList<Pelicula> peliculasVistas = ControladorUsuario.getUsuarioActivo().getPeliculas();

        // Limpia el panel
        panelPeliculasVistas.removeAll();

        // Comprueba si hay películas vistas por el usuario
        if (peliculasVistas.isEmpty()) 
        {
            panelPeliculasVistas.add(new JLabel("Actualmente no tienes películas vistas"));
        } 
        else 
        {
            // Recorre las películas vistas
            for(Pelicula p : peliculasVistas) 
            {
                panelPeliculasVistas.add(crearPeliculasVistas(p));
            }
        }

        // Actualiza el panel
        panelPeliculasVistas.revalidate();
        panelPeliculasVistas.repaint();
    }

    // Crea una tarjeta con la imagen de una película vista
    private static JPanel crearPeliculasVistas(Pelicula p) 
    {
        // Panel principal donde se cargarán toda las películas vistas una por una
        JPanel panelPeliculaV = new JPanel();
        panelPeliculaV.setLayout(new BoxLayout(panelPeliculaV, BoxLayout.Y_AXIS));
        panelPeliculaV.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        try 
        {
            // Carga la imagen de la película
            ImageIcon icono = new ImageIcon(PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto()));
            Image imagen = icono.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
            panelPeliculaV.add(imagenLabel);
        } 
        catch(Exception e) 
        {
            // En caso de error
            panelPeliculaV.add(new JLabel("No hay imagen"));
        }
        return panelPeliculaV;
    }

    // =========================
    // LOGROS E INSIGNIAS
    // =========================

    // Crea el panel con los logros desbloqueados del usuario
    public static void crearPanelLogros(JPanel panelLogros) 
    {
        // Limpia el panel
        panelLogros.removeAll();

        // Usa un GridLayout de 4 columnas
        panelLogros.setLayout(new GridLayout(0, 4, 10, 10));

        Usuario usuario = ControladorUsuario.getUsuarioActivo();

        // Comprueba que haya un usuario activo
        if (usuario != null) 
        {
            ArrayList<Logro> logros = usuario.getLogros(); // Obtiene todos los logros del usuario

            // Recorre los logros
            for (Logro logro : logros) 
            {
                // Solo muestra los logros completados
                if (logro.isCompleto()) 
                {
                    // Panel por cada logro completo
                    JPanel card = new JPanel();
                    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                    card.setOpaque(false);
                    card.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    // Imagen del logro
                    JLabel iconLabel;

                    if (logro.getInsignia() != null && logro.getInsignia().getImagen() != null) 
                    {
                        Image img = logro.getInsignia().getImagen().getImage();
                        img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        iconLabel = new JLabel(new ImageIcon(img));
                    } 
                    else 
                    {
                        iconLabel = new JLabel("Sin imagen");
                        iconLabel.setPreferredSize(new Dimension(100, 100));
                    }

                    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    card.add(iconLabel);
                    panelLogros.add(card);
                }
            }
        }

        // Actualiza el panel
        panelLogros.revalidate();
        panelLogros.repaint();
    }
}
