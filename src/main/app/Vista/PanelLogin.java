package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.app.Controlador.*;
import main.app.util.*;

public class PanelLogin
{
    static protected JPanel crearloginPanel()
    {
        // Panel personalizado con fondo (sin static y sin reflexión)
        JPanel panelCentral = new JPanel(new GridBagLayout())
        {
            private Image fondo; // ✅ NO static

            public void setFondo(Image img)
            {
                this.fondo = img;
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if (fondo != null)
                {
                    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Cargar la imagen de fondo (sin reflexión)
        try 
        {
            Image icono = new ImageIcon(PanelLogin.class.getResource("/main/resources/img/logoFondoPantalla.jpg")).getImage();
            ((JPanel) panelCentral).getClass()
                .getDeclaredMethod("setFondo", Image.class)
                .invoke(panelCentral, icono); // asignamos usando reflexión o mejor con un panel custom
        } 
        catch (Exception e) 
        {
            panelCentral.setBackground(Color.DARK_GRAY);
        }

        // --- LOGIN MENU ---
        JPanel loginMenu = new JPanel(new GridBagLayout());
        loginMenu.setBackground(new Color(0,0,0,100));
        loginMenu.setBorder(BorderFactory.createEmptyBorder(40, 40,40,40));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("¡Bienvenido a CineRoadmap!", SwingConstants.CENTER);
        Estilos.estilosTitulosLRC(titulo);
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 3;
        loginMenu.add(titulo, bag);

        JLabel labelNombre = new JLabel("Usuario o Email:");
        Estilos.estiloLabelLRC(labelNombre);
        bag.gridwidth = 1; bag.gridy = 1; bag.gridx = 0;
        loginMenu.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        campoNombre.setText("Introduzca nombre o email");
        Estilos.estilosInputsDatos(campoNombre);
        loginMenu.add(campoNombre, bag);

        bag.gridwidth = 2; bag.gridy = 3; bag.gridx = 0;
        JLabel labelContrasena = new JLabel("Contraseña:");
        Estilos.estiloLabelLRC(labelContrasena);
        loginMenu.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        campoContrasena.setText("********");
        Estilos.estilosInputsContrasenas(campoContrasena);
        loginMenu.add(campoContrasena, bag);

        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        Estilos.estiloBotones(botonVer);
        botonVer.addActionListener(e -> ControladorBotones.verContrasena(campoContrasena));
        loginMenu.add(botonVer, bag);

        bag.gridwidth = 3; bag.gridy = 4; bag.gridx = 0;
        JButton botonInicio = new JButton("Iniciar Sesión");
        Estilos.estiloBotones(botonInicio);
        loginMenu.add(botonInicio, bag);

        bag.gridy = 5;
        JButton botonRegistro = new JButton("Registrarse");
        Estilos.estiloBotones(botonRegistro);
        loginMenu.add(botonRegistro, bag);

        bag.gridy = 6;
        JButton botonCambioContrasena = new JButton("Cambio de contraseña");
        Estilos.estiloBotones(botonCambioContrasena);
        loginMenu.add(botonCambioContrasena, bag);

        campoNombre.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                ControladorLogin.emailUsuarioRatonPresionado(e, campoNombre, campoContrasena);
            }
        });

        campoContrasena.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                ControladorLogin.contrasenaRatonPresionado(e, campoNombre, campoContrasena);
            }
        });

        botonInicio.addActionListener((ActionEvent e) ->
        {
            String mensaje = ControladorLogin.inicioSesion(
                campoNombre.getText(),
                new String(campoContrasena.getPassword())
            );
            ControladorLogin.pulsarBotonInicio(mensaje, campoNombre, campoContrasena);
        });

        botonRegistro.addActionListener(e -> ControladorLogin.pulsarBotonRegistro(campoNombre, campoContrasena));
        botonCambioContrasena.addActionListener(e -> ControladorLogin.pulsarBotonContrasena(campoNombre, campoContrasena));

        panelCentral.add(loginMenu);
        return panelCentral;
    }
}
