package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.app.Controlador.*;
import main.app.util.*;

public class PanelCambioContrasena 
{
    protected static JPanel crearPanelContrasena()
    {
        JPanel panelCentral = new JPanel() 
        {
            private Image fondo;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondo != null) {
                    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }
            }

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
            ((JPanel) panelCentral).getClass()
                .getDeclaredMethod("setFondo", Image.class)
                .invoke(panelCentral, icono);
        } 
        catch (Exception e) 
        {
            panelCentral.setBackground(Color.DARK_GRAY);
        }

        JPanel cambioCMenu = new JPanel(new GridBagLayout());
        cambioCMenu.setBackground(new Color(0,0,0,100));
        cambioCMenu.setBorder(BorderFactory.createEmptyBorder(40, 40,40,40));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        JLabel titulo = new JLabel("Formulario de cambio de contraseña", SwingConstants.CENTER);
        Estilos.estilosTitulosLRC(titulo);
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 2;
        cambioCMenu.add(titulo, bag);
        
        JLabel labelNombre = new JLabel("Nombre de usuario:");
        Estilos.estiloLabelLRC(labelNombre);
        bag.gridwidth = 1; bag.gridy = 1; bag.gridx = 0;
        cambioCMenu.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        campoNombre.setText("Introduzca nombre o email");
        Estilos.estilosInputsDatos(campoNombre);
        cambioCMenu.add(campoNombre, bag);
        
        JLabel labelContrasena = new JLabel("Contraseña Actual:");
        Estilos.estiloLabelLRC(labelContrasena);
        bag.gridwidth = 1; bag.gridy = 2; bag.gridx = 0;
        cambioCMenu.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        campoContrasena.setText("********");
        Estilos.estilosInputsContrasenas(campoContrasena);
        cambioCMenu.add(campoContrasena, bag);
        
        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        Estilos.estiloBotones(botonVer);
        botonVer.addActionListener(e -> ControladorBotones.verContrasena(campoContrasena));
        cambioCMenu.add(botonVer, bag);

        bag.gridwidth = 1; bag.gridy = 3; bag.gridx = 0; 
        JLabel labelConfirmacion = new JLabel("Nueva Contraseña:");
        Estilos.estiloLabelLRC(labelConfirmacion);
        cambioCMenu.add(labelConfirmacion, bag);

        bag.gridx = 1;
        JPasswordField campoConfirmacion = new JPasswordField(20);
        campoConfirmacion.setText("********");
        Estilos.estilosInputsContrasenas(campoConfirmacion);
        cambioCMenu.add(campoConfirmacion, bag);

        bag.gridx = 2;
        JButton botonVerConfirmacion = new JButton("👁");
        Estilos.estiloBotones(botonVerConfirmacion);
        botonVerConfirmacion.addActionListener(e -> ControladorBotones.verContrasena(campoConfirmacion));
        cambioCMenu.add(botonVerConfirmacion, bag);

        bag.gridwidth = 2; bag.gridy = 4; bag.gridx = 0;
        JButton botonCambioContrasena = new JButton("Cambio de Contraseña");
        Estilos.estiloBotones(botonCambioContrasena);
        cambioCMenu.add(botonCambioContrasena, bag);

        bag.gridy = 5;
        JButton botonInicio = new JButton("Volver al Login");
        Estilos.estiloBotones(botonInicio);
        cambioCMenu.add(botonInicio, bag);

        campoNombre.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                ControladorContrasena.usuarioRatonPresionado(e, campoNombre, campoContrasena, campoConfirmacion);
            }
        });

        campoContrasena.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                ControladorContrasena.contrasenaRatonPresionado(e, campoNombre, campoContrasena, campoConfirmacion);
            }
        });

        campoConfirmacion.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                ControladorContrasena.confirmacionRatonPresionado(e, campoNombre, campoContrasena, campoConfirmacion);
            }
        });

        botonInicio.addActionListener(e -> ControladorContrasena.volverInicio(campoNombre, campoContrasena, campoConfirmacion));
        botonCambioContrasena.addActionListener((ActionEvent e) -> 
        {
            String mensaje = ControladorContrasena.cambiarContrasena(campoNombre.getText(), new String(campoContrasena.getPassword()), new String(campoConfirmacion.getPassword()));
            ControladorContrasena.ejecutarCambioContrasena(mensaje, campoNombre, campoContrasena, campoConfirmacion);

        });

        panelCentral.add(cambioCMenu);

        return panelCentral;
    }
}
