package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.app.Controlador.*;
import main.app.util.*;

public class PanelRegistro 
{
    // -------------------------
    // CREAR PANEL DE REGISTRO
    // -------------------------
    protected static JPanel crearPanelRegistro()
    {
        // -------------------------
        // PANEL PRINCIPAL CON FONDO
        // -------------------------
        JPanel panelCentral = new JPanel() 
        {
            private Image fondo; // Imagen de fondo

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

            // Permite establecer la imagen de fondo
            public void setFondo(Image img) 
            {
                this.fondo = img;
                repaint();
            }
        };

        panelCentral.setLayout(new GridBagLayout());

        // Carga la imagen de fondo
        try 
        {
            Image icono = new ImageIcon(PanelRegistro.class.getResource("/main/resources/img/logoFondoPantalla.jpg")).getImage();

            ((JPanel) panelCentral).getClass().getDeclaredMethod("setFondo", Image.class).invoke(panelCentral, icono);
        } 
        catch (Exception e) 
        {
            // Si falla la imagen, se usa un fondo oscuro
            panelCentral.setBackground(Color.DARK_GRAY);
        }

        // -------------------------
        // PANEL DEL FORMULARIO
        // -------------------------
        JPanel registroMenu = new JPanel(new GridBagLayout());
        registroMenu.setBackground(new Color(0,0,0,100));
        registroMenu.setBorder(BorderFactory.createEmptyBorder(40, 40,40,40));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        // -------------------------
        // TÍTULO
        // -------------------------
        JLabel titulo = new JLabel("Formulario de Registro", SwingConstants.CENTER);
        Estilos.estilosTitulosLRC(titulo);
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 2;
        registroMenu.add(titulo, bag);

        // -------------------------
        // CAMPO NOMBRE
        // -------------------------
        JLabel labelNombre = new JLabel("Nombre de usuario:");
        Estilos.estiloLabelLRC(labelNombre);
        bag.gridy = 1; bag.gridx = 0; bag.gridwidth = 1;
        registroMenu.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        campoNombre.setText("Introduzca nombre");
        Estilos.estilosInputsDatos(campoNombre);
        registroMenu.add(campoNombre, bag);

        // -------------------------
        // CAMPO EMAIL
        // -------------------------
        JLabel labelEmail = new JLabel("Email:");
        Estilos.estiloLabelLRC(labelEmail);
        bag.gridy = 2; bag.gridx = 0;
        registroMenu.add(labelEmail, bag);

        bag.gridx = 1;
        JTextField campoEmail = new JTextField(20);
        campoEmail.setText("Introduzca email");
        Estilos.estilosInputsDatos(campoEmail);
        registroMenu.add(campoEmail, bag);

        // -------------------------
        // CAMPO CONTRASEÑA
        // -------------------------
        bag.gridy = 3; bag.gridx = 0; bag.gridwidth = 1;
        JLabel labelContrasena = new JLabel("Contraseña:");
        Estilos.estiloLabelLRC(labelContrasena);
        registroMenu.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        campoContrasena.setText("********");
        Estilos.estilosInputsContrasenas(campoContrasena);
        registroMenu.add(campoContrasena, bag);

        // Botón para mostrar/ocultar contraseña
        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        Estilos.estiloBotones(botonVer);
        botonVer.addActionListener(e -> ControladorBotones.verContrasena(campoContrasena));
        registroMenu.add(botonVer, bag);

        // -------------------------
        // BOTONES PRINCIPALES
        // -------------------------
        bag.gridwidth = 3; bag.gridy = 4; bag.gridx = 0;
        JButton botonRegistro = new JButton("Registrarse");
        Estilos.estiloBotones(botonRegistro);
        registroMenu.add(botonRegistro, bag);

        bag.gridy = 5;
        JButton botonInicio = new JButton("Volver al Login");
        Estilos.estiloBotones(botonInicio);
        registroMenu.add(botonInicio, bag);

        // -------------------------
        // EVENTOS DE CAMPOS
        // -------------------------

        // Label nombre
        campoNombre.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) { ControladorRegistro.nombreRatonPresionado(e, campoNombre, campoEmail ,campoContrasena); }
        });

        // Label email
        campoEmail.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) { ControladorRegistro.emailRatonPresionado( e, campoNombre, campoEmail, campoContrasena); }
        });

        // Label contraseña
        campoContrasena.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) { ControladorRegistro.contrasenaRatonPresionado(e, campoNombre, campoEmail, campoContrasena); }
        });

        // -------------------------
        // EVENTOS DE BOTONES
        // -------------------------

        // Ejecutar el registro del usuario
        botonRegistro.addActionListener((ActionEvent e) -> 
        {
            String mensaje = ControladorRegistro.registrarUsuario(campoNombre.getText(), campoEmail.getText(), new String(campoContrasena.getPassword()));
            ControladorRegistro.botonRegistroPresionado(mensaje, campoNombre, campoEmail, campoContrasena);
        });

        // Volver al login
        botonInicio.addActionListener(e -> ControladorRegistro.botonInicioPresionado(campoNombre, campoEmail, campoContrasena));

        // Añade el formulario al panel principal
        panelCentral.add(registroMenu);

        return panelCentral;
    }
}

