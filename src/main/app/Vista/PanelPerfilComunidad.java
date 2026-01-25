package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import main.app.Controlador.*;
import main.app.Modelo.Usuario;
import main.app.util.WrapLayout;

public class PanelPerfilComunidad
{
    public static JPanel crearPerfilComunidad(Usuario usuario)
    {
        JPanel panelCentral = new JPanel(new BorderLayout());

        // ------------------ BANNER ------------------
        JPanel banner = PanelBanner.crearBanner();
        panelCentral.add(banner, BorderLayout.NORTH);

        // ------------------ PANEL DIVIDIDO HORIZONTAL ------------------
        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.5);

        // ------------------ PANEL DATOS ------------------
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.BLACK);
        TitledBorder border = BorderFactory.createTitledBorder("Datos de Usuario");
        border.setTitleColor(Color.WHITE);
        panelDatos.setBorder(border);
        panelDatos.setMinimumSize(new Dimension(600, 600));
        panelDatos.setMaximumSize(new Dimension(600, 600));

        GridBagConstraints datosBag = new GridBagConstraints();
        datosBag.insets = new Insets(10, 10, 10, 10);
        datosBag.fill = GridBagConstraints.HORIZONTAL;
        datosBag.gridy = 0;

        // Imagen de perfil
        ControladorPerfilComunidad.crearImagenPerfil(panelDatos, datosBag, usuario);

        // Datos del usuario
        datosBag.gridy = 1;
        JPanel datosUsuario = new JPanel(new GridBagLayout());
        datosUsuario.setBackground(Color.WHITE);
        datosUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDatos.add(datosUsuario, datosBag);

        GridBagConstraints datosUsuarioBag = new GridBagConstraints();
        datosUsuarioBag.insets = new Insets(5, 5, 5, 5);
        datosUsuarioBag.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        datosUsuarioBag.gridx = 0;
        datosUsuarioBag.gridy = 0;
        JLabel labelNombreUsuario = new JLabel("Nombre: " + usuario.getnombreUsuario());
        datosUsuario.add(labelNombreUsuario, datosUsuarioBag);

        // ------------------ PANEL PELÍCULAS Y LOGROS (DIVIDIDO VERTICAL) ------------------
        JSplitPane panelPIL = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelPIL.setResizeWeight(0.5);

        // ------------------ PANEL PELÍCULAS ------------------
        JPanel panelPeliculasVistas = new JPanel();
        panelPeliculasVistas.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
        panelPeliculasVistas.setBorder(BorderFactory.createTitledBorder("Películas Vistas"));

        // Cargar películas del usuario
        ControladorPerfilComunidad.crearPanelPeliculasVistas(panelPeliculasVistas, usuario);

        JScrollPane scrollPeliculas = new JScrollPane(panelPeliculasVistas);
        scrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPeliculas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPeliculas.getViewport().setOpaque(false);
        scrollPeliculas.setOpaque(false);

        // ------------------ PANEL LOGROS ------------------
        JPanel panelLogros = new JPanel();
        panelLogros.setBorder(BorderFactory.createTitledBorder("Insignias y Logros"));

        // Cargar logros del usuario
        ControladorPerfilComunidad.crearPanelLogros(panelLogros, usuario);

        JScrollPane scrollLogros = new JScrollPane(panelLogros);
        scrollLogros.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollLogros.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLogros.getViewport().setOpaque(false);
        scrollLogros.setOpaque(false);

        // Dividir verticalmente entre películas y logros
        panelPIL.setLeftComponent(scrollPeliculas);
        panelPIL.setRightComponent(scrollLogros);
        panelPIL.setEnabled(false);
        panelPIL.setDividerSize(10);
        panelPIL.setMinimumSize(new Dimension(600, 600));
        panelPIL.setMaximumSize(new Dimension(600, 600));

        // ------------------ PANEL DIVIDIDO HORIZONTAL ------------------
        panelDividido.setLeftComponent(panelDatos);
        panelDividido.setRightComponent(panelPIL);
        panelDividido.setEnabled(false);
        panelDividido.setDividerSize(10);

        panelCentral.add(panelDividido);
        return panelCentral;
    }
}
