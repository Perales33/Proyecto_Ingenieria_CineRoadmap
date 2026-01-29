package main.app.Vista;

import javax.swing.*;
import java.awt.*;

import main.app.Controlador.*;

public class PanelPerfil 
{
    public static JPanel crearPanelPerfil()
    {
        JPanel panelCental = new JPanel(new BorderLayout());

        // ------------------ BANNER ------------------
        JPanel banner = PanelBanner.crearBanner();
        panelCental.add(banner, BorderLayout.NORTH);

        // ------------------ PANEL DIVIDIDO HORIZONTAL ------------------
        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelDividido.setResizeWeight(0.5);

        // ------------------ PANEL DATOS ------------------
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.BLACK);
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de Usuario"));
        panelDatos.setMinimumSize(new Dimension(600, 600));
        panelDatos.setMaximumSize(new Dimension(600, 600));

        GridBagConstraints datosBag = new GridBagConstraints();
        datosBag.insets = new Insets(10, 10, 10, 10);
        datosBag.fill = GridBagConstraints.HORIZONTAL;
        datosBag.gridy = 0;

        // Imagen de perfil
        ControladorPerfil.crearImagenPerfil(panelDatos, datosBag);

        // Datos del usuario
        datosBag.gridy = 1;
        JPanel datosUsuario = new JPanel(new GridBagLayout());
        datosUsuario.setBackground(Color.WHITE);
        datosUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDatos.add(datosUsuario, datosBag);

        GridBagConstraints datosUsuarioBag = new GridBagConstraints();
        datosUsuarioBag.insets = new Insets(5,5,5,5);
        datosUsuarioBag.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        datosUsuarioBag.gridx = 0; datosUsuarioBag.gridy = 0;
        JLabel labelNombreUsuario = new JLabel("Nombre: " + ControladorUsuario.getUsuarioActivo().getnombreUsuario());
        datosUsuario.add(labelNombreUsuario, datosUsuarioBag);

        datosUsuarioBag.gridx = 1;
        JButton cambioNombre = new JButton("✏️");
        cambioNombre.setBackground(null); cambioNombre.setBorder(null);
        datosUsuario.add(cambioNombre, datosUsuarioBag);

        // Email
        datosUsuarioBag.gridx = 0; datosUsuarioBag.gridy = 1;
        JLabel labelEmail = new JLabel("Email: " + ControladorUsuario.getUsuarioActivo().getEmail());
        datosUsuario.add(labelEmail, datosUsuarioBag);

        datosUsuarioBag.gridx = 1;
        JButton cambioEmail = new JButton("✏️");
        cambioEmail.setBackground(null); cambioEmail.setBorder(null);
        datosUsuario.add(cambioEmail, datosUsuarioBag);

        // ------------------ PANEL PELÍCULAS Y LOGROS (DIVIDIDO VERTICAL) ------------------
        JSplitPane panelPIL = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelPIL.setResizeWeight(0.5);

        // ------------------ PANEL PELÍCULAS ------------------
        JPanel panelPeliculasVistas = new JPanel();
        panelPeliculasVistas.setLayout(new GridLayout(0, 4, 10, 10)); // 4 columnas, filas dinámicas
        panelPeliculasVistas.setBorder(BorderFactory.createTitledBorder("Películas Vistas"));

        // Cargar las películas (solo imágenes)
        ControladorPerfil.crearPanelPeliculasVistas(panelPeliculasVistas);

        JScrollPane scrollPeliculas = new JScrollPane(panelPeliculasVistas);
        scrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPeliculas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPeliculas.getViewport().setOpaque(false);
        scrollPeliculas.setOpaque(false);

        // ------------------ PANEL LOGROS ------------------
        JPanel panelLogros = new JPanel();
        panelLogros.setLayout(new GridLayout(0, 4, 10, 10)); // 4 columnas, filas dinámicas
        panelLogros.setBorder(BorderFactory.createTitledBorder("Insignias y Logros"));

        // Cargar logros (solo imágenes)
        ControladorPerfil.crearPanelLogros(panelLogros);

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

        // ------------------ ACCIONES ------------------
        cambioNombre.addActionListener(e -> {
            String nuevoNombre = JOptionPane.showInputDialog(PanelGenerador.getMain(), "Ingresar nuevo nombre:");
            ControladorPerfil.cambiarNombre(nuevoNombre, labelNombreUsuario);
        });

        cambioEmail.addActionListener(e -> {
            String nuevoEmail = JOptionPane.showInputDialog(PanelGenerador.getMain(), "Ingresar nuevo email:");
            ControladorPerfil.cambiarEmail(nuevoEmail, labelEmail);
        });

        panelCental.add(panelDividido);
        return panelCental;
    }
}
