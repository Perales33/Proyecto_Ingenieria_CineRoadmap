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
        Estilos.estilosInputsDatos(campoNombre);
        cambioCMenu.add(campoNombre, bag);
        
        JLabel labelContrasena = new JLabel("Contraseña Actual:");
        Estilos.estiloLabelLRC(labelContrasena);
        bag.gridwidth = 1; bag.gridy = 2; bag.gridx = 0;
        cambioCMenu.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
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
        botonCambioContrasena.addActionListener((ActionEvent e) -> 
        {
            String mensaje = ControladorContrasena.cambiarContrasena(campoNombre.getText(), new String(campoContrasena.getPassword()), new String(campoConfirmacion.getPassword()));

            if(mensaje == null)
            {
                JOptionPane.showMessageDialog(PanelGenerador.mainPanel, "Cambio de contraseña exitoso", "Cambio Exitoso", JOptionPane.INFORMATION_MESSAGE);
                campoNombre.setText("");
                campoContrasena.setText("");
                campoConfirmacion.setText("");
                PanelGenerador.mainPanel.add(PanelApp.crearPanelInicio(), "Inicio");
                PanelGenerador.colocacion.show(PanelGenerador.mainPanel, "Login");
            }
            else
            {
                JOptionPane.showMessageDialog(PanelGenerador.mainPanel, mensaje, "Cambio no efectuado", JOptionPane.ERROR_MESSAGE);
                PanelGenerador.colocacion.show(PanelGenerador.mainPanel, "CambioContrasena");
            }
        });
        cambioCMenu.add(botonCambioContrasena, bag);

        bag.gridy = 5;
        JButton botonInicio = new JButton("Volver al Login");
        Estilos.estiloBotones(botonInicio);
        botonInicio.addActionListener(e -> 
            {
                campoNombre.setText("");
                campoContrasena.setText("");
                campoConfirmacion.setText("");
                PanelGenerador.colocacion.show(PanelGenerador.mainPanel, "Login");
            }
        );    
        cambioCMenu.add(botonInicio, bag);

        panelCentral.add(cambioCMenu);

        return panelCentral;
    }
}
