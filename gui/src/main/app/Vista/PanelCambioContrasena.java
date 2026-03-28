package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.app.Controlador.*;
import main.app.util.*;

public class PanelCambioContrasena 
{
    /// Crear el panel de la contraseña
    protected static JPanel crearPanelContrasena()
    {
        // ------------------------------
        // PANEL PRINCIPAL CON FONDO
        // ------------------------------
        JPanel panelCentral = new JPanel() 
        {
            private Image fondo; // Variable donde se va a cargar el fondo

            // Dibuja la imagen de fondo escalada al tamaño del panel
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                if (fondo != null) 
                {
                    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }
            }

            // Permite establecer dinámicamente la imagen de fondo
            public void setFondo(Image img) 
            {
                this.fondo = img;
                repaint();
            }
        };

        panelCentral.setLayout(new GridBagLayout()); 

        // Cargar la imagen de fondo
        try 
        {
            Image icono = new ImageIcon(PanelCambioContrasena.class.getResource("/main/resources/img/logoFondoPantalla.jpg")).getImage();
            ((JPanel) panelCentral).getClass().getDeclaredMethod("setFondo", Image.class).invoke(panelCentral, icono);
        } 
        catch (Exception e) 
        {
            panelCentral.setBackground(Color.DARK_GRAY);
        }

        // -----------------------
        // PANEL DEL FORMULARIO
        // -----------------------
        JPanel cambioCMenu = new JPanel(new GridBagLayout());
        cambioCMenu.setBackground(new Color(0,0,0,100));
        cambioCMenu.setBorder(BorderFactory.createEmptyBorder(40, 40,40,40));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        // -----------------------
        // TÍTULO
        // -----------------------
        JLabel titulo = new JLabel("Formulario de cambio de contraseña", SwingConstants.CENTER);
        Estilos.estilosTitulosLRC(titulo);
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 2;
        cambioCMenu.add(titulo, bag);

        // -----------------------
        // CAMPO NOMBRE
        // -----------------------
        JLabel labelNombre = new JLabel("Nombre de usuario:");
        Estilos.estiloLabelLRC(labelNombre);
        bag.gridy = 1; bag.gridx = 0; bag.gridwidth = 1;
        cambioCMenu.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        campoNombre.setText("Introduzca nombre o email");
        Estilos.estilosInputsDatos(campoNombre);
        cambioCMenu.add(campoNombre, bag);

        // -----------------------
        // CAMPO CONTRASEÑA ACTUAL
        // -----------------------
        JLabel labelContrasena = new JLabel("Contraseña Actual:");
        Estilos.estiloLabelLRC(labelContrasena);
        bag.gridy = 2; bag.gridx = 0;
        cambioCMenu.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        campoContrasena.setText("********");
        Estilos.estilosInputsContrasenas(campoContrasena);
        cambioCMenu.add(campoContrasena, bag);

        // Botón para ver/ocultar contraseña actual
        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        Estilos.estiloBotones(botonVer);
        botonVer.addActionListener(e -> ControladorBotones.verContrasena(campoContrasena));
        cambioCMenu.add(botonVer, bag);

        // -----------------------
        // CAMPO NUEVA CONTRASEÑA
        // -----------------------
        bag.gridy = 3; bag.gridx = 0; 
        JLabel labelConfirmacion = new JLabel("Nueva Contraseña:");
        Estilos.estiloLabelLRC(labelConfirmacion);
        cambioCMenu.add(labelConfirmacion, bag);

        bag.gridx = 1;
        JPasswordField campoConfirmacion = new JPasswordField(20);
        campoConfirmacion.setText("********");
        Estilos.estilosInputsContrasenas(campoConfirmacion);
        cambioCMenu.add(campoConfirmacion, bag);

        // Botón para ver/ocultar nueva contraseña
        bag.gridx = 2;
        JButton botonVerConfirmacion = new JButton("👁");
        Estilos.estiloBotones(botonVerConfirmacion);
        botonVerConfirmacion.addActionListener(e -> ControladorBotones.verContrasena(campoConfirmacion));
        cambioCMenu.add(botonVerConfirmacion, bag);

        // -----------------------
        // BOTÓN CAMBIAR CONTRASEÑA
        // -----------------------
        bag.gridwidth = 2; bag.gridy = 4; bag.gridx = 0;
        JButton botonCambioContrasena = new JButton("Cambio de Contraseña");
        Estilos.estiloBotones(botonCambioContrasena);
        cambioCMenu.add(botonCambioContrasena, bag);

        // -----------------------
        // BOTÓN VOLVER AL LOGIN
        // -----------------------
        bag.gridy = 5;
        JButton botonInicio = new JButton("Volver al Login");
        Estilos.estiloBotones(botonInicio);
        cambioCMenu.add(botonInicio, bag);

        // -----------------------
        // EVENTOS DE LOS CAMPOS
        // -----------------------

        // Campo del nombre
        campoNombre.addMouseListener(new MouseAdapter() 
        {
            public void mousePressed(MouseEvent e) 
            {
                ControladorContrasena.usuarioRatonPresionado(e, campoNombre, campoContrasena, campoConfirmacion);
            }
        });

        // Campo de contraseña
        campoContrasena.addMouseListener(new MouseAdapter() 
        {
            public void mousePressed(MouseEvent e) 
            {
                ControladorContrasena.contrasenaRatonPresionado(e, campoNombre, campoContrasena, campoConfirmacion);
            }
        });

        // Campo de nueva contraseña
        campoConfirmacion.addMouseListener(new MouseAdapter() 
        {
            public void mousePressed(MouseEvent e) 
            {
                ControladorContrasena.confirmacionRatonPresionado(e, campoNombre, campoContrasena, campoConfirmacion);
            }
        });

        // -----------------------
        // EVENTOS DE LOS BOTONES
        // -----------------------
        
        // Volver al login
        botonInicio.addActionListener(e -> ControladorContrasena.volverInicio(campoNombre, campoContrasena, campoConfirmacion));

        // Confirmar el cambio de contraseña
        botonCambioContrasena.addActionListener(e ->
        {
            String mensaje = ControladorContrasena.cambiarContrasena(campoNombre.getText(), new String(campoContrasena.getPassword()), new String(campoConfirmacion.getPassword()));
            ControladorContrasena.ejecutarCambioContrasena(mensaje, campoNombre, campoContrasena, campoConfirmacion);
        });

        panelCentral.add(cambioCMenu);
        return panelCentral;
    }
}

