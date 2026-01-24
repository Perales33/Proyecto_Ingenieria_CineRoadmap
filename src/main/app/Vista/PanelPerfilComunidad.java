package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import main.app.util.*;
import main.app.Controlador.*;
import main.app.Modelo.Usuario;

public class PanelPerfilComunidad
{
    public static JPanel crearPerfilComunidad(Usuario usuario)
    {
        JPanel panelCentral = new JPanel(new BorderLayout());

        JPanel banner = PanelBanner.crearBanner();
        panelCentral.add(banner, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(new Color(200, 200, 200));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.5);

        JPanel panelDatos = new JPanel(new GridBagLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Datos de Usuario");
        border.setTitleColor(Color.WHITE);
        panelDatos.setBorder(border);
        panelDatos.setBackground(Color.BLACK);
        panelDatos.setMinimumSize(new Dimension(600, 600));

        GridBagConstraints datosBag = new GridBagConstraints();
        datosBag.insets = new Insets(10,10, 10, 10);
        datosBag.fill = GridBagConstraints.HORIZONTAL;
        datosBag.gridy = 0;

        ControladorPerfilComunidad.crearImagenPerfil(panelDatos, datosBag, usuario);

        datosBag.gridy = 1;
        JPanel datosUsuario = new JPanel(new GridBagLayout());
        datosUsuario.setBackground(Color.WHITE);
        panelDatos.add(datosUsuario, datosBag);

        JLabel labelNombreUsuario = new JLabel("Nombre: " + usuario.getnombreUsuario());
        datosUsuario.add(labelNombreUsuario);

        JSplitPane panelPIL = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelPIL.setResizeWeight(0.5);

        panelDividido.setLeftComponent(panelDatos);
        panelDividido.setRightComponent(panelPIL);

        JPanel panelPeliculasVistas = new JPanel();
        panelPeliculasVistas.setBorder(BorderFactory.createTitledBorder("Películas Vistas"));
        panelPeliculasVistas.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
        ControladorPerfilComunidad.crearPanelPeliculasVistas(panelPeliculasVistas, usuario);

        JScrollPane panelScrollPeliculas = new JScrollPane(panelPeliculasVistas);

        JPanel panelIL = new JPanel();
        panelIL.setBorder(BorderFactory.createTitledBorder("Insignias y Logros"));

        panelPIL.setLeftComponent(panelScrollPeliculas);
        panelPIL.setRightComponent(panelIL);

        panelCentral.add(panelDividido);

        return panelCentral;
    }
}
