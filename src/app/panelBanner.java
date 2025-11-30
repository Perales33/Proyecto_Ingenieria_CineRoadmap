package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class panelBanner 
{
    static protected JPanel crearBanner()
    {
        JPanel panelBanner = new JPanel(new GridLayout(1, 5, 10, 10));
        panelBanner.setBackground(new Color(200, 0, 0));

        JLabel labelNombre = new JLabel("Inicio", SwingConstants.CENTER);
        JLabel labelPeliculas = new JLabel("Peliculas", SwingConstants.CENTER);
        JLabel labelLogrInsig = new JLabel("Logros e Insignias", SwingConstants.CENTER);
        JLabel labelRetos = new JLabel("Retos", SwingConstants.CENTER);
        JLabel labelPerfil = new JLabel("Perfil", SwingConstants.CENTER);
        JLabel labelCerrarSesion = new JLabel("Cerrar Sesión", SwingConstants.CENTER);

        JLabel labels[] = {labelNombre, labelPeliculas, labelLogrInsig, labelRetos, labelPerfil, labelCerrarSesion};

        String pantallas[] = {"Inicio", "Peliculas", "LogrosInsignias", "Retos", "Perfil", "Login"};

        for(int i = 0; i < labels.length; i++)
        {
            JLabel label = labels[i];
            String pantalla = pantallas[i];

            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) 
                {
                    if(pantalla.equals("LogrosInsignias") || pantalla.equals("Retos"))
                    {
                        JOptionPane.showMessageDialog(panelGenerador.mainPanel, "Sección todavía no implementada","Información" ,JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (pantalla.equals("Login"))
                    {
                        JOptionPane.showMessageDialog(panelGenerador.mainPanel, ("Cerrando Sesión " + Controlador.getUsuarioActivo().getnombreUsuario()),"CerrarSesion" ,JOptionPane.INFORMATION_MESSAGE);
                        Controlador.setUsuarioActivo();
                    }
                    else if(pantalla.equals("Perfil"))
                    {
                        panelGenerador.mainPanel.add(panelPerfil.crearPanelPerfil(), "Perfil");
                        panelGenerador.colocacion.show(panelGenerador.mainPanel, "Perfil");
                    }
                    else if (pantalla.equals("Peliculas"))
                    {
                        panelGenerador.mainPanel.add(panelPeliculas.crearPanelPeliculas(), "Peliculas");
                        panelGenerador.colocacion.show(panelGenerador.mainPanel, "Peliculas");
                    }
                    
                    panelGenerador.colocacion.show(panelGenerador.mainPanel, pantalla);
                }
            });
            panelBanner.add(label);
        }

        return panelBanner;
    }
}
