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
    public static void crearImagenPerfil(JPanel panelDatos, GridBagConstraints datosBag)
    {
        ImageIcon icono;
        try {
            icono = new ImageIcon(PanelPerfil.class.getResource("/main/resources/img/" + ControladorUsuario.getUsuarioActivo().getFoto()));
        } catch(Exception e) {
            icono = new ImageIcon();
        }

        JLabel imagenLabel;
        if(icono == null || icono.getImage() == null) {
            imagenLabel = new JLabel("No fotos");
        } else {
            Image imagen = icono.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
        }
        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panelDatos.add(imagenLabel, datosBag);
    }

    public static boolean actualizarEmail(String nuevoEmail)
    {
        String extensiones[] = {"@gmail.com", "@yahoo.es", "@yahoo.com","@outlook.com", "@hotmail.com", "@live.com"};
        String emaillower = nuevoEmail.toLowerCase();
        for(String ext : extensiones) {
            if(emaillower.endsWith(ext)) return true;
        }
        return false;
    }

    public static boolean actualizarNombre(String nuevoNombre) {
        return nuevoNombre.length() >= 5;
    }

    public static void cambiarNombre(String nuevoNombre, JLabel labelNombreUsuario) {
        if(nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            if(actualizarNombre(nuevoNombre)) {
                ControladorUsuario.getUsuarioActivo().setnombreUsuario(nuevoNombre);
                labelNombreUsuario.setText("Nombre: " + nuevoNombre);
            } else {
                JOptionPane.showMessageDialog(PanelGenerador.getMain(), "El nuevo nombre debe tener al menos 5 dígitos", "Nombre inválido", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void cambiarEmail(String nuevoEmail,JLabel labelEmail) {
        if(nuevoEmail != null && !nuevoEmail.trim().isEmpty()) {
            if(actualizarEmail(nuevoEmail)) {
                ControladorUsuario.getUsuarioActivo().setEmail(nuevoEmail);
                labelEmail.setText("Email: " + nuevoEmail);
            } else {
                JOptionPane.showMessageDialog(PanelGenerador.getMain(), "El correo ingresado no tiene una extensión válida \nExtensiones permitidas: @gmail.com, @yahoo.es, @yahoo.com, @outlook.com, @hotmail.com, @live.com", "Email erróneo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // -------------------- Películas vistas --------------------
    public static void crearPanelPeliculasVistas(JPanel panelPeliculasVistas) {
        ArrayList<Pelicula> peliculasVistas = ControladorUsuario.getUsuarioActivo().getPeliculas();

        panelPeliculasVistas.removeAll();

        if (peliculasVistas.isEmpty()) {
            panelPeliculasVistas.add(new JLabel("Actualmente no tienes películas vistas"));
        } else {
            for(Pelicula p : peliculasVistas) {
                panelPeliculasVistas.add(crearPeliculasVistas(p));
            }
        }

        panelPeliculasVistas.revalidate();
        panelPeliculasVistas.repaint();
    }

    private static JPanel crearPeliculasVistas(Pelicula p) {
        JPanel panelPeliculaV = new JPanel();
        panelPeliculaV.setLayout(new BoxLayout(panelPeliculaV, BoxLayout.Y_AXIS));
        panelPeliculaV.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        try {
            ImageIcon icono = new ImageIcon(PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto()));
            Image imagen = icono.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
            panelPeliculaV.add(imagenLabel);
        } catch(Exception e) {
            panelPeliculaV.add(new JLabel("No image"));
        }
        
        return panelPeliculaV;
    }

// -------------------- Logros e insignias --------------------
public static void crearPanelLogros(JPanel panelLogros) {
    panelLogros.removeAll();

    // GridLayout con máximo 4 columnas, filas dinámicas
    panelLogros.setLayout(new GridLayout(0, 4, 10, 10)); // 0 filas, 4 columnas, hgap=10, vgap=10

    Usuario usuario = ControladorUsuario.getUsuarioActivo();
    if (usuario != null) {
        ArrayList<Logro> logros = usuario.getLogros();

        for (Logro logro : logros) {
            if (logro.isCompleto()) { // Solo logros desbloqueados
                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                card.setOpaque(false);
                card.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                // Imagen del logro
                JLabel iconLabel;
                if (logro.getInsignia() != null && logro.getInsignia().getImagen() != null) {
                    Image img = logro.getInsignia().getImagen().getImage();
                    img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    iconLabel = new JLabel(new ImageIcon(img));
                } else {
                    iconLabel = new JLabel("Sin imagen");
                    iconLabel.setPreferredSize(new Dimension(100, 100));
                }
                iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(iconLabel);

                panelLogros.add(card);
            }
        }
    }

    panelLogros.revalidate();
    panelLogros.repaint();
}
}
