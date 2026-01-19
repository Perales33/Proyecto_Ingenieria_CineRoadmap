package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.app.Controlador.*;
import main.app.Modelo.Usuario;

public class PanelBanner {

    public static JPanel crearBanner() 
    {
        // Panel contenedor del banner
        JPanel bannerPanel = new JPanel(null); // layout absoluto para logo + menú
        bannerPanel.setPreferredSize(new Dimension(1200, 86));

        // ----------------------
        // Panel negro para el logo
        // ----------------------
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.BLACK);
        logoPanel.setBounds(0, 0, 100, 86);
        logoPanel.setLayout(null);
        bannerPanel.add(logoPanel);

        // Logo escalado manteniendo proporción
        Usuario usuario = ControladorUsuario.getUsuarioActivo();
        String fotoUsuario = (usuario != null && usuario.getFoto() != null)  ? usuario.getFoto() : "logoCineRoadmap.jpg";

        ImageIcon   logoIcon = new ImageIcon(PanelPerfil.class.getResource("/main/resources/img/" + fotoUsuario));
        Image logoImg = logoIcon.getImage().getScaledInstance(90, 86, Image.SCALE_SMOOTH); // tamaño original ajustado
        logoIcon = new ImageIcon(logoImg);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(5, 0, 90, 86); // centrado verticalmente dentro del logoPanel (150 alto)
        logoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoPanel.add(logoLabel);

        // Evento clic sobre logo → abrir perfil
        logoLabel.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                PanelGenerador.getMain().add(PanelPerfil.crearPanelPerfil(), "Perfil");
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Perfil");
            }
        });

        // ----------------------
        // Panel rojo del menú
        // ----------------------
        JPanel menuPanel = new JPanel(null); // layout absoluto
        menuPanel.setBackground(new Color(200, 0, 0));
        menuPanel.setBounds(100, 0, 1100, 86);
        bannerPanel.add(menuPanel);

        // Labels del menú con posición exacta
        JLabel inicioLabel = new JLabel("Inicio");
        JLabel peliculasLabel = new JLabel("Películas");
        JLabel insigniasLabel = new JLabel("Logros");
        JLabel retosLabel = new JLabel("Retos y Recomendaciones");
        JLabel comunidadLabel = new JLabel("Comunidad");
        JLabel cerrarSesionLabel = new JLabel("Cerrar Sesión");

        JLabel[] labels = {inicioLabel, peliculasLabel, insigniasLabel, retosLabel, comunidadLabel, cerrarSesionLabel};
        String[] pantallas = {"Inicio", "Peliculas", "LogrosInsignias", "Retos", "Comunidad" ,"Login"};

        // Fuente y estilo
        Font menuFont = new Font("Arial", Font.BOLD, 16);
        for (JLabel label : labels) 
        {
            label.setFont(menuFont);
            label.setForeground(Color.WHITE);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            menuPanel.add(label);
        }

        // Posiciones exactas (ajustar según diseño)
        inicioLabel.setBounds(50, 30, 50, 30);           
        peliculasLabel.setBounds(150, 30, 100, 30);       
        insigniasLabel.setBounds(250, 30, 90, 30);       
        retosLabel.setBounds(350, 30, 220, 30);          
        comunidadLabel.setBounds(600, 30, 100, 30);      
        cerrarSesionLabel.setBounds(950, 30, 150, 30); 


        // Eventos de click
        for (int i = 0; i < labels.length; i++) {
            JLabel label = labels[i];
            String pantalla = pantallas[i];
            label.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e) 
                {
                    if (pantalla.equals("LogrosInsignias") || pantalla.equals("Retos")) 
                    {
                        JOptionPane.showMessageDialog(PanelGenerador.getMain(),
                                "Sección todavía no implementada",
                                "Información",
                                JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else if (pantalla.equals("Login")) 
                    {
                        JOptionPane.showMessageDialog(PanelGenerador.getMain(),
                                "Cerrando Sesión " + ControladorUsuario.getUsuarioActivo().getnombreUsuario(),
                                "Cerrar Sesión",
                                JOptionPane.INFORMATION_MESSAGE);
                        ControladorUsuario.setUsuarioActivo();
                    } 
                    else if (pantalla.equals("Perfil")) 
                    {
                        PanelGenerador.getMain().add(PanelPerfil.crearPanelPerfil(), "Perfil");
                        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Perfil");
                    } 
                    else if (pantalla.equals("Peliculas")) 
                    {
                        PanelGenerador.getMain().add(PanelPeliculas.crearPanelPeliculas(), "Peliculas");
                        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Peliculas");
                    }
                    else if (pantalla.equals("Comunidad"))
                    {
                        PanelGenerador.getMain().add(PanelComunidad.crearPanelComunidad(), "Comunidad");
                        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Comunidad");
                    }

                    PanelGenerador.getColocacion().show(PanelGenerador.getMain(), pantalla);
                }
            });
        }

        return bannerPanel;
    }
}
