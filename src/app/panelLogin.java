package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelLogin 
{
    static protected JPanel crearloginPanel()
    {
        JPanel panelCentral = new JPanel() 
        {
            private static Image fondo;

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
            Image icono = new ImageIcon(PanelLogin.class.getResource("../../static/img/logoFondoPantalla.jpg")).getImage();
            ((JPanel) panelCentral).getClass()
                .getDeclaredMethod("setFondo", Image.class)
                .invoke(panelCentral, icono); // asignamos usando reflexión o mejor con un panel custom
        } 
        catch (Exception e) 
        {
            panelCentral.setBackground(Color.DARK_GRAY);
        }

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
        Estilos.estilosInputsDatos(campoNombre);
        loginMenu.add(campoNombre, bag);
        
        bag.gridwidth = 2; bag.gridy = 3; bag.gridx = 0; 
        JLabel labelContrasena = new JLabel("Contraseña:");
        Estilos.estiloLabelLRC(labelContrasena);
        loginMenu.add(labelContrasena, bag);
        
        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        Estilos.estilosInputsContrasenas(campoContrasena);
        loginMenu.add(campoContrasena, bag);

        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        Estilos.estiloBotones(botonVer);
        botonVer.addActionListener(e -> Controlador.verContrasena(campoContrasena));
        loginMenu.add(botonVer, bag);
        
        bag.gridwidth = 3; bag.gridy = 4; bag.gridx = 0;
        JButton botonInicio = new JButton("Iniciar Sesión");
        Estilos.estiloBotones(botonInicio);
        botonInicio.addActionListener((ActionEvent e) -> 
        {
            String mensaje = Controlador.inicioSesion(campoNombre.getText(), new String(campoContrasena.getPassword()));

            if(mensaje == null)
            {
                JOptionPane.showMessageDialog(panelGenerador.mainPanel, ("Bienvenido " + Controlador.getUsuarioActivo().getnombreUsuario()), "Usuario registrado", JOptionPane.INFORMATION_MESSAGE);
                campoNombre.setText("");
                campoContrasena.setText("");
                panelGenerador.mainPanel.add(PanelApp.crearPanelInicio(), "Inicio");
                panelGenerador.colocacion.show(panelGenerador.mainPanel, "Inicio");
            }
            else
            {
                JOptionPane.showMessageDialog(panelGenerador.mainPanel, mensaje, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
                campoNombre.setText("");
                campoContrasena.setText("");
                panelGenerador.colocacion.show(panelGenerador.mainPanel, "Login");
            }
        });
        loginMenu.add(botonInicio, bag);

        bag.gridy = 5;
        JButton botonRegistro = new JButton("Registrarse");
        Estilos.estiloBotones(botonRegistro);
        botonRegistro.addActionListener(e -> 
            {
                campoNombre.setText("");
                campoContrasena.setText("");
                panelGenerador.colocacion.show(panelGenerador.mainPanel, "Registro");
            }
            );
        loginMenu.add(botonRegistro, bag);
        
        bag.gridy = 6;
        JButton botonCambioContrasena = new JButton("Cambio de contraseña");
        Estilos.estiloBotones(botonCambioContrasena);
        botonCambioContrasena.addActionListener(e -> panelGenerador.colocacion.show(panelGenerador.mainPanel, "CambioContrasena"));
        loginMenu.add(botonCambioContrasena, bag);

        panelCentral.add(loginMenu);

        return panelCentral;
    }
}
