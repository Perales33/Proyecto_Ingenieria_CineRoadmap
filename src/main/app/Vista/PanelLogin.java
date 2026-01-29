package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.app.Controlador.*;
import main.app.util.*;

public class PanelLogin
{
    // -------------------------
    // CREAR PANEL DE LOGIN
    // -------------------------
    static protected JPanel crearloginPanel()
    {
        // -------------------------
        // PANEL PRINCIPAL CON FONDO
        // -------------------------
        JPanel panelCentral = new JPanel(new GridBagLayout())
        {
            private Image fondo; // Imagen de fondo

            // Permite establecer el fondo
            public void setFondo(Image img)
            {
                this.fondo = img;
                repaint();
            }

            // Dibuja la imagen de fondo escalada
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

        // Carga la imagen de fondo
        try 
        {
            Image icono = new ImageIcon(PanelLogin.class.getResource("/main/resources/img/logoFondoPantalla.jpg")).getImage();
            ((JPanel) panelCentral).getClass().getDeclaredMethod("setFondo", Image.class).invoke(panelCentral, icono);
        } 
        catch (Exception e) 
        {
            // Si falla la imagen, usa un fondo oscuro
            panelCentral.setBackground(Color.DARK_GRAY);
        }

        // -------------------------
        // PANEL DEL FORMULARIO
        // -------------------------
        JPanel loginMenu = new JPanel(new GridBagLayout());
        loginMenu.setBackground(new Color(0,0,0,100));
        loginMenu.setBorder(BorderFactory.createEmptyBorder(40, 40,40,40));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL;

        // -------------------------
        // TÍTULO
        // -------------------------
        JLabel titulo = new JLabel("¡Bienvenido a CineRoadmap!", SwingConstants.CENTER);
        Estilos.estilosTitulosLRC(titulo);
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 3;
        loginMenu.add(titulo, bag);

        // -------------------------
        // CAMPO USUARIO / EMAIL
        // -------------------------
        JLabel labelNombre = new JLabel("Usuario o Email:");
        Estilos.estiloLabelLRC(labelNombre);
        bag.gridwidth = 1; bag.gridy = 1; bag.gridx = 0;
        loginMenu.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        campoNombre.setText("Introduzca nombre o email");
        Estilos.estilosInputsDatos(campoNombre);
        loginMenu.add(campoNombre, bag);

        // -------------------------
        // CAMPO CONTRASEÑA
        // -------------------------
        bag.gridy = 3; bag.gridx = 0;
        JLabel labelContrasena = new JLabel("Contraseña:");
        Estilos.estiloLabelLRC(labelContrasena);
        loginMenu.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        campoContrasena.setText("********");
        Estilos.estilosInputsContrasenas(campoContrasena);
        loginMenu.add(campoContrasena, bag);

        // Botón para mostrar/ocultar contraseña
        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        Estilos.estiloBotones(botonVer);
        botonVer.addActionListener(e -> ControladorBotones.verContrasena(campoContrasena));
        loginMenu.add(botonVer, bag);

        // -------------------------
        // BOTONES PRINCIPALES
        // -------------------------

        // Iniciar sesión
        bag.gridwidth = 3; bag.gridy = 4; bag.gridx = 0;
        JButton botonInicio = new JButton("Iniciar Sesión");
        Estilos.estiloBotones(botonInicio);
        loginMenu.add(botonInicio, bag);

        // Panel de Registro
        bag.gridy = 5;
        JButton botonRegistro = new JButton("Registrarse");
        Estilos.estiloBotones(botonRegistro);
        loginMenu.add(botonRegistro, bag);

        // Panel de Cambio de Contraseña
        bag.gridy = 6;
        JButton botonCambioContrasena = new JButton("Cambio de contraseña");
        Estilos.estiloBotones(botonCambioContrasena);
        loginMenu.add(botonCambioContrasena, bag);

        // -------------------------
        // EVENTOS DE CAMPOS
        // -------------------------

        // Label de nombre / email
        campoNombre.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) { ControladorLogin.emailUsuarioRatonPresionado(e, campoNombre, campoContrasena); }
        });

        // Label de la contraseña

        campoContrasena.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) { ControladorLogin.contrasenaRatonPresionado(e, campoNombre, campoContrasena); }
        });

        // -------------------------
        // EVENTOS DE BOTONES
        // -------------------------

        // Inicio de sesión
        botonInicio.addActionListener((ActionEvent e) ->
        {
            String mensaje = ControladorLogin.inicioSesion(campoNombre.getText(), new String(campoContrasena.getPassword()));
            ControladorLogin.pulsarBotonInicio(mensaje, campoNombre, campoContrasena);
        });

        // Panel de Registro
        botonRegistro.addActionListener(e -> ControladorLogin.pulsarBotonRegistro(campoNombre, campoContrasena));

        // Panel de Cambio de Contraseña
        botonCambioContrasena.addActionListener(e -> ControladorLogin.pulsarBotonContrasena(campoNombre, campoContrasena));

        // Añade el formulario al panel principal
        panelCentral.add(loginMenu);

        return panelCentral;
    }
}
