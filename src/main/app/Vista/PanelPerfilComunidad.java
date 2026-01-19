package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import main.app.util.*;
import main.app.Controlador.*;
import main.app.Modelo.Usuario;

public class PanelPerfilComunidad
{
    // Método para crear el perfil de un usuario de la comunidad (solo visualización)
    public static JPanel crearPerfilComunidad(Usuario usuario)
    {
        JPanel panelCentral = new JPanel(new BorderLayout());

        // Banner superior
        JPanel banner = PanelBanner.crearBanner();
        panelCentral.add(banner, BorderLayout.NORTH);

        // Panel contenido
        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(new Color(200, 200, 200));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL;

        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.5);

        // Panel de datos del usuario
        JPanel panelDatos = new JPanel(new GridBagLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Datos de Usuario");
        border.setTitleColor(Color.WHITE);
        panelDatos.setBorder(border);
        panelDatos.setBackground(Color.BLACK);

        panelDatos.setMinimumSize(new Dimension(600, 600));  
        panelDatos.setMaximumSize(new Dimension(600, 600));

        GridBagConstraints datosBag = new GridBagConstraints();
        datosBag.insets = new Insets(10,10, 10, 10);
        datosBag.fill = GridBagConstraints.HORIZONTAL;
        datosBag.gridy = 0;

        // Imagen del perfil
        ControladorPerfilComunidad.crearImagenPerfil(panelDatos, datosBag, usuario);

        // Panel con datos de texto
        datosBag.gridy = 1;
        JPanel datosUsuario = new JPanel(new GridBagLayout());
        datosUsuario.setBackground(Color.WHITE);
        datosUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints datosUsuarioBag = new GridBagConstraints();
        datosUsuarioBag.insets = new Insets(5, 5, 5, 5);
        datosUsuarioBag.fill = GridBagConstraints.HORIZONTAL;
        datosUsuarioBag.anchor = GridBagConstraints.CENTER;

        panelDatos.add(datosUsuario, datosBag);

        // Mostrar solo el nombre
        datosUsuarioBag.gridy = 0; 
        datosUsuarioBag.gridx = 0;
        JLabel labelNombreUsuario = new JLabel("Nombre: " + usuario.getnombreUsuario());
        labelNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        datosUsuario.add(labelNombreUsuario, datosUsuarioBag);

        // NO mostrar email y NO botones de edición

        // Panel de películas vistas e insignias/logros
        JSplitPane panelPIL = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelPIL.setResizeWeight(0.5);

        panelDividido.setEnabled(false);
        panelDividido.setDividerSize(10);
        panelDividido.setLeftComponent(panelDatos);
        panelDividido.setRightComponent(panelPIL);

        // Películas vistas
        JPanel panelPeliculasVistas = new JPanel();
        panelPeliculasVistas.setBorder(BorderFactory.createTitledBorder("Películas Vistas"));
        panelPeliculasVistas.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
        ControladorPerfilComunidad.crearPanelPeliculasVistas(panelPeliculasVistas, usuario);

        JScrollPane panelScrollPeliculas = new JScrollPane(panelPeliculasVistas);
        panelScrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 

        // Insignias y logros
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

        panelCentral.add(panelDividido);

        return panelCentral;
    }
}
