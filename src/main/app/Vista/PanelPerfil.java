package main.app.Vista;

import javax.swing.*;
import java.awt.*;

import main.app.Controlador.*;

public class PanelPerfil 
{
    // -------------------------
    // CREAR PANEL DE PERFIL
    // -------------------------
    public static JPanel crearPanelPerfil()
    {
        // -------------------------
        // PANEL PRINCIPAL
        // -------------------------
        JPanel panelCentral = new JPanel(new BorderLayout());

        // -------------------------
        // BANNER SUPERIOR
        // -------------------------
        JPanel banner = PanelBanner.crearBanner();
        panelCentral.add(banner, BorderLayout.NORTH);

        // -------------------------
        // PANEL DIVIDIDO HORIZONTAL
        // -------------------------
        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.5);

        // -------------------------
        // PANEL DATOS DE USUARIO
        // -------------------------
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.BLACK);
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de Usuario"));
        panelDatos.setMinimumSize(new Dimension(600, 600));
        panelDatos.setMaximumSize(new Dimension(600, 600));

        GridBagConstraints datosBag = new GridBagConstraints();
        datosBag.insets = new Insets(10, 10, 10, 10);
        datosBag.fill = GridBagConstraints.HORIZONTAL;
        datosBag.gridy = 0;

        // -------------------------
        // IMAGEN DE PERFIL
        // -------------------------
        ControladorPerfil.crearImagenPerfil(panelDatos, datosBag);

        // -------------------------
        // DATOS DEL USUARIO
        // -------------------------
        datosBag.gridy = 1;
        JPanel datosUsuario = new JPanel(new GridBagLayout());
        datosUsuario.setBackground(Color.WHITE);
        datosUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDatos.add(datosUsuario, datosBag);

        GridBagConstraints datosUsuarioBag = new GridBagConstraints();
        datosUsuarioBag.insets = new Insets(5,5,5,5);
        datosUsuarioBag.fill = GridBagConstraints.HORIZONTAL;

        // -------------------------
        // NOMBRE DE USUARIO
        // -------------------------
        datosUsuarioBag.gridx = 0; datosUsuarioBag.gridy = 0;
        JLabel labelNombreUsuario = new JLabel("Nombre: " + ControladorUsuario.getUsuarioActivo().getnombreUsuario());
        datosUsuario.add(labelNombreUsuario, datosUsuarioBag);

        // Botón para editar el nombre del usuario
        datosUsuarioBag.gridx = 1;
        JButton cambioNombre = new JButton("✏️");
        cambioNombre.setBackground(null);
        cambioNombre.setBorder(null);
        datosUsuario.add(cambioNombre, datosUsuarioBag);

        // -------------------------
        // EMAIL DEL USUARIO
        // -------------------------
        datosUsuarioBag.gridx = 0; datosUsuarioBag.gridy = 1;
        JLabel labelEmail = new JLabel("Email: " + ControladorUsuario.getUsuarioActivo().getEmail());
        datosUsuario.add(labelEmail, datosUsuarioBag);

        // Botón para editar email del usuario
        datosUsuarioBag.gridx = 1;
        JButton cambioEmail = new JButton("✏️");
        cambioEmail.setBackground(null);
        cambioEmail.setBorder(null);
        datosUsuario.add(cambioEmail, datosUsuarioBag);

        // -------------------------
        // PANEL DERECHO (PELÍCULAS + LOGROS)
        // -------------------------
        JSplitPane panelPIL = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelPIL.setResizeWeight(0.5);

        // -------------------------
        // PANEL PELÍCULAS VISTAS
        // -------------------------
        JPanel panelPeliculasVistas = new JPanel();
        panelPeliculasVistas.setLayout(new GridLayout(0, 4, 10, 10));
        panelPeliculasVistas.setBorder(BorderFactory.createTitledBorder("Películas Vistas"));

        // Cargar películas
        ControladorPerfil.crearPanelPeliculasVistas(panelPeliculasVistas);

        JScrollPane scrollPeliculas = new JScrollPane(panelPeliculasVistas);
        scrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPeliculas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPeliculas.getViewport().setOpaque(false);
        scrollPeliculas.setOpaque(false);

        // -------------------------
        // PANEL LOGROS
        // -------------------------
        JPanel panelLogros = new JPanel();
        panelLogros.setLayout(new GridLayout(0, 4, 10, 10));
        panelLogros.setBorder(BorderFactory.createTitledBorder("Insignias y Logros"));

        // Cargar logros del usuario completos
        ControladorPerfil.crearPanelLogros(panelLogros);

        JScrollPane scrollLogros = new JScrollPane(panelLogros);
        scrollLogros.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollLogros.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLogros.getViewport().setOpaque(false);
        scrollLogros.setOpaque(false);

        // -------------------------
        // DIVISIÓN VERTICAL DERECHA
        // -------------------------
        panelPIL.setLeftComponent(scrollPeliculas);
        panelPIL.setRightComponent(scrollLogros);
        panelPIL.setEnabled(false);
        panelPIL.setDividerSize(10);
        panelPIL.setMinimumSize(new Dimension(600, 600));
        panelPIL.setMaximumSize(new Dimension(600, 600));

        // -------------------------
        // UNIR PANEL IZQUIERDO Y DERECHO
        // -------------------------
        panelDividido.setLeftComponent(panelDatos);
        panelDividido.setRightComponent(panelPIL);
        panelDividido.setEnabled(false);
        panelDividido.setDividerSize(10);

        // -------------------------
        // ACCIONES
        // -------------------------

        // Botón de cambio de nombre del usuario
        cambioNombre.addActionListener(e -> 
        {
            String nuevoNombre = JOptionPane.showInputDialog(PanelGenerador.getMain(),"Ingresar nuevo nombre:");
            ControladorPerfil.cambiarNombre(nuevoNombre, labelNombreUsuario);
        });

        // Botón de cambio de email del usuario
        cambioEmail.addActionListener(e -> 
        {
            String nuevoEmail = JOptionPane.showInputDialog(PanelGenerador.getMain(),"Ingresar nuevo email:");
            ControladorPerfil.cambiarEmail(nuevoEmail, labelEmail);
        });

        panelCentral.add(panelDividido, BorderLayout.CENTER);

        return panelCentral;
    }
}

