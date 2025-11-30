package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PanelRegistro 
{
    protected static JPanel crearPanelRegistro()
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

            public void setFondo(Image img) {
                this.fondo = img;
                repaint();
            }
        };

        panelCentral.setLayout(new GridBagLayout());

        // Cargar la imagen de fondo
        try 
        {
            Image icono = new ImageIcon(PanelRegistro.class.getResource("../../static/imglogoFondoPantalla.jpg")).getImage();
            ((JPanel) panelCentral).getClass()
                .getDeclaredMethod("setFondo", Image.class)
                .invoke(panelCentral, icono); // asignamos usando reflexión o mejor con un panel custom
        } 
        catch (Exception e) 
        {
            panelCentral.setBackground(Color.DARK_GRAY);
        }

        JPanel RegistroMenu = new JPanel(new GridBagLayout());
        RegistroMenu.setBackground(new Color(0,0,0,100));
        RegistroMenu.setBorder(BorderFactory.createEmptyBorder(40, 40,40,40));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        JLabel titulo = new JLabel("Formulario de Registro", SwingConstants.CENTER);
        Estilos.estilosTitulosLRC(titulo);
        bag.gridx = 0; bag.gridy = 0; bag.gridwidth = 2;
        RegistroMenu.add(titulo, bag);
        
        JLabel labelNombre = new JLabel("Nombre de usuario:");
        Estilos.estiloLabelLRC(labelNombre);
        bag.gridwidth = 1; bag.gridy = 1; bag.gridx = 0;
        RegistroMenu.add(labelNombre, bag);

        bag.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        Estilos.estilosInputsDatos(campoNombre);
        RegistroMenu.add(campoNombre, bag);
        
        JLabel labelEmail = new JLabel("Email:");
        Estilos.estiloLabelLRC(labelEmail);
        bag.gridwidth = 1; bag.gridy = 2; bag.gridx = 0;
        RegistroMenu.add(labelEmail, bag);

        bag.gridx = 1;
        JTextField campoEmail = new JTextField(20);
        Estilos.estilosInputsDatos(campoEmail);
        RegistroMenu.add(campoEmail, bag);
        
        bag.gridwidth = 2; bag.gridy = 3; bag.gridx = 0; 
        JLabel labelContrasena = new JLabel("Contraseña:");
        Estilos.estiloLabelLRC(labelContrasena);
        RegistroMenu.add(labelContrasena, bag);

        bag.gridx = 1;
        JPasswordField campoContrasena = new JPasswordField(20);
        Estilos.estilosInputsContrasenas(campoContrasena);
        RegistroMenu.add(campoContrasena, bag);

        bag.gridx = 2;
        JButton botonVer = new JButton("👁");
        Estilos.estiloBotones(botonVer);
        botonVer.addActionListener(e -> Controlador.verContrasena(campoContrasena));
        RegistroMenu.add(botonVer, bag);

        bag.gridwidth = 3; bag.gridy = 4; bag.gridx = 0;
        JButton botonRegistro = new JButton("Registrarse");
        Estilos.estiloBotones(botonRegistro);
        botonRegistro.addActionListener((ActionEvent e) -> 
        {
            String mensaje = Controlador.registrarUsuario(campoNombre.getText(), campoEmail.getText(), new String(campoContrasena.getPassword()));

            if(mensaje == null)
            {
                JOptionPane.showMessageDialog(panelGenerador.mainPanel, "Usuario registrado correctamente", "Usuario registrado", JOptionPane.INFORMATION_MESSAGE);
                campoNombre.setText("");
                campoEmail.setText("");
                campoContrasena.setText("");
                panelGenerador.colocacion.show(panelGenerador.mainPanel, "Login");
            }
            else
            {
                JOptionPane.showMessageDialog(panelGenerador.mainPanel, mensaje, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
                panelGenerador.colocacion.show(panelGenerador.mainPanel, "Registro");
            }
        });
        RegistroMenu.add(botonRegistro, bag);

        bag.gridy = 5;
        JButton botonInicio = new JButton("Volver al Login");
        Estilos.estiloBotones(botonInicio);
        botonInicio.addActionListener(e -> 
            {
                campoNombre.setText("");
                campoEmail.setText("");
                campoContrasena.setText("");
                panelGenerador.colocacion.show(panelGenerador.mainPanel, "Login");
            }
        );
        RegistroMenu.add(botonInicio, bag);

        panelCentral.add(RegistroMenu);

        return panelCentral;
    }
}
