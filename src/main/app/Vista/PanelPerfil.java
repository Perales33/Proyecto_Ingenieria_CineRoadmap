package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import main.app.util.*;
import main.app.Controlador.*;
import main.app.Modelo.*;

import java.util.ArrayList;

public class PanelPerfil 
{
    protected static JPanel crearPanelPerfil()
    {
        JPanel panelCental = new JPanel(new BorderLayout());

        JPanel banner = PanelBanner.crearBanner();
        panelCental.add(banner, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(new Color(200, 200, 200));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL;

        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.5);

        JPanel panelDatos = new JPanel(new GridBagLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Datos de Usuario");
        border.setTitleColor(Color.WHITE);
        panelDatos.setBorder(border);
        panelDatos.setBackground(Color.BLACK);

        panelDatos.setMinimumSize(new Dimension(600, 600));  // ajusta según tu preferencia
        panelDatos.setMaximumSize(new Dimension(600, 600));

        GridBagConstraints datosBag = new GridBagConstraints();
        datosBag.insets = new Insets(10,10, 10, 10);
        datosBag.fill = GridBagConstraints.HORIZONTAL;
        
        datosBag.gridy = 0;
        ImageIcon icono;

        try
        {
            icono = new ImageIcon(PanelPerfil.class.getResource("/main/resources/img/" + ControladorUsuario.getUsuarioActivo().getFoto()));
        }
        catch(Exception e)
        {
            icono = new ImageIcon();
        }

        JLabel imagenLabel;
        Image imagen;

        if(icono == null || icono.getImage() == null)
        {
            imagenLabel = new JLabel("No fotos");
        }
        else
        {
            imagen = icono.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
        }
        imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panelDatos.add(imagenLabel, datosBag);

        datosBag.gridy = 1;
        JPanel datosUsuario = new JPanel(new GridBagLayout());
        datosUsuario.setBackground(Color.WHITE);
        datosUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints datosUsuarioBag = new GridBagConstraints();
        datosUsuarioBag.insets = new Insets(5, 5, 5, 5);
        datosUsuarioBag.fill = GridBagConstraints.HORIZONTAL;
        datosUsuarioBag.anchor = GridBagConstraints.CENTER;
        panelDatos.add(datosUsuario, datosBag);

        String texto;

        if(ControladorUsuario.getUsuarioActivo() == null) 
        { 
            texto = "Nombre: No hay datos del usuario"; } 
        else 
        { 
            texto = "Nombre: " + ControladorUsuario.getUsuarioActivo().getnombreUsuario();
        }
        datosUsuarioBag.gridy = 0; datosUsuarioBag.gridx = 0;
        JLabel labelNombreUsuario = new JLabel();
        labelNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        labelNombreUsuario.setText(texto);
        datosUsuario.add(labelNombreUsuario, datosUsuarioBag);

        datosUsuarioBag.gridx = 1;
        JButton cambioNombre = new JButton("✏️");
        cambioNombre.setToolTipText("Cambiar nombre");
        cambioNombre.setBackground(null);
        cambioNombre.setBorder(null);
        cambioNombre.addActionListener(e -> 
            {
                String nuevoNombre = JOptionPane.showInputDialog(PanelGenerador.mainPanel, "Ingresar nuevo nombre:", "Cambio nombre", JOptionPane.DEFAULT_OPTION);
                
                if(nuevoNombre != null && !nuevoNombre.trim().isEmpty())
                {
                    boolean actualizarNombre = ControladorPerfil.actualizarNombre(nuevoNombre); 

                    if(actualizarNombre)
                    {
                        ControladorUsuario.getUsuarioActivo().setnombreUsuario(nuevoNombre);
                        labelNombreUsuario.setText("Nombre: " + nuevoNombre);
                        PanelApp.actualizarTitulo();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(PanelGenerador.mainPanel, "El nuevo nombre debe tener al menos 5 dígitos", "Nombre invalido", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        );
        datosUsuario.add(cambioNombre, datosUsuarioBag);
        

        datosUsuarioBag.gridy = 1; datosUsuarioBag.gridx = 0;
        JLabel labelEmail = new JLabel();
        labelEmail.setHorizontalAlignment(SwingConstants.CENTER);
        
        if(ControladorUsuario.getUsuarioActivo() == null) 
        { 
            texto = "Email: No hay datos del usuario"; } 
        else 
        { 
            texto = "Email: " + ControladorUsuario.getUsuarioActivo().getEmail();
        }
        
        labelEmail.setText(texto);
        datosUsuario.add(labelEmail, datosUsuarioBag);

        datosUsuarioBag.gridx = 1;
        JButton cambioEmail = new JButton("✏️");
        cambioEmail.setToolTipText("Cambiar nombre");
        cambioEmail.setBackground(null);
        cambioEmail.setBorder(null);
        cambioEmail.addActionListener(e -> 
            {
                String nuevoEmail = JOptionPane.showInputDialog(PanelGenerador.mainPanel, "Ingresar nuevo email:","Cambio email" ,JOptionPane.DEFAULT_OPTION);
                if(nuevoEmail != null && !nuevoEmail.trim().isEmpty())
                {
                    boolean extensionValida = ControladorPerfil.actualizarEmail(nuevoEmail);

                    if(extensionValida)
                    {
                        ControladorUsuario.getUsuarioActivo().setEmail(nuevoEmail);
                        labelEmail.setText("Email: " + nuevoEmail);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(PanelGenerador.mainPanel, "El correo ingresado no tiene una extensión válida \n" + 
                        "Extensiones permitidas: @gmail.com, @yahoo.es, @yahoo.com, @outlook.com, @hotmail.com, @live.com",
                        "Email erroneo", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
            }
        );
        datosUsuario.add(cambioEmail, datosUsuarioBag);


        JSplitPane panelPIL = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelPIL.setResizeWeight(0.5);

        panelDividido.setEnabled(false);
        panelDividido.setDividerSize(10);
        panelDividido.setLeftComponent(panelDatos);
        panelDividido.setRightComponent(panelPIL);

        JPanel panelPeliculasVistas = new JPanel();
        panelPeliculasVistas.setBorder(BorderFactory.createTitledBorder("Películas Vistas"));
        panelPeliculasVistas.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

        ArrayList<Pelicula> peliculasVistas = ControladorUsuario.getUsuarioActivo().getPeliculas();

        if (panelPeliculasVistas == null || peliculasVistas.isEmpty()) 
        {
            JLabel textoInicial = new JLabel("Actualmente no tienes películas vistas");
            panelPeliculasVistas.add(textoInicial);
        }

        panelPeliculasVistas.removeAll();
        if (peliculasVistas.isEmpty()) 
        {
            JLabel textoInicial = new JLabel("Actualmente no tienes películas vistas");
            panelPeliculasVistas.add(textoInicial);
        } 
        else 
        {
            for(Pelicula p : peliculasVistas)
            {
                panelPeliculasVistas.add(crearPeliculasVistas(p));
            }
        }
        panelPeliculasVistas.revalidate();
        panelPeliculasVistas.repaint();

        JScrollPane panelScrollPeliculas = new JScrollPane(panelPeliculasVistas);
        panelScrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 


        JPanel panelIL = new JPanel();
        panelIL.setBorder(BorderFactory.createTitledBorder("Insignias y Logros"));
        
        panelScrollPeliculas.setMinimumSize(new Dimension(600, 300));
        panelScrollPeliculas.setMaximumSize(new Dimension(600, 300));

        panelIL.setMinimumSize(new Dimension(600, 300));
        panelIL.setMaximumSize(new Dimension(600, 300));

        panelPIL.setEnabled(false);
        panelPIL.setLeftComponent(panelScrollPeliculas);
        panelPIL.setRightComponent(panelIL);
    
        panelPIL.setMinimumSize(new Dimension(600, 600));
        panelPIL.setMaximumSize(new Dimension(600, 600));

        panelCental.add(panelDividido);

        return panelCental;
    }

    private static JPanel crearPeliculasVistas(Pelicula p)
    {
        JPanel panelPeliculaV = new JPanel();
        panelPeliculaV.setLayout(new BoxLayout(panelPeliculaV, BoxLayout.Y_AXIS));
        panelPeliculaV.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        ImageIcon icono;
        JLabel imagenLabel;
        try
        {
            icono = new ImageIcon(PanelPeliculas.class.getResource("/main/resources/img/" + p.getFoto()));
            Image imagen = icono.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
            imagenLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            panelPeliculaV.add(imagenLabel);
        }
        catch(Exception e)
        {
            icono = new ImageIcon();
        }
        
        return panelPeliculaV;
    }
}
