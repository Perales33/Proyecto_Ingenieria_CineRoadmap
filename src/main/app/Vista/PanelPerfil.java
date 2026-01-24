package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import main.app.util.*;
import main.app.Controlador.*;


public class PanelPerfil 
{
    public static JPanel crearPanelPerfil()
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
        
        ControladorPerfil.crearImagenPerfil(panelDatos, datosBag);
        

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
        ControladorPerfil.crearPanelPeliculasVistas(panelPeliculasVistas);

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

        cambioNombre.addActionListener(e -> 
            {
                String nuevoNombre = JOptionPane.showInputDialog(PanelGenerador.getMain(), "Ingresar nuevo nombre:", "Cambio nombre", JOptionPane.DEFAULT_OPTION);
                ControladorPerfil.cambiarNombre(nuevoNombre, labelNombreUsuario);
            }
        );

        cambioEmail.addActionListener(e -> 
            {
                String nuevoEmail = JOptionPane.showInputDialog(PanelGenerador.getMain(), "Ingresar nuevo email:","Cambio email" ,JOptionPane.DEFAULT_OPTION);
                ControladorPerfil.cambiarEmail(nuevoEmail, labelEmail);
            }
        );

        panelCental.add(panelDividido);

        return panelCental;
    }
}
