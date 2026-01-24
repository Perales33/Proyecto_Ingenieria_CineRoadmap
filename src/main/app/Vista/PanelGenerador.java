package main.app.Vista;

import javax.swing.*;
import java.awt.*;

import main.app.Controlador.*;

public class PanelGenerador extends JFrame
{
    // Página principal
    private static CardLayout colocacion = new CardLayout();
    private static JPanel mainPanel = new JPanel(colocacion); // Panel principal

    // Getter de la página principal
    public static CardLayout getColocacion()
    {
        return colocacion;
    }

    public static JPanel getMain()
    {
        return mainPanel;
    }

    // Paneles de la interfaz
    JPanel loginPanel;               // Panel de Login
    JPanel registrarPanel;           // Panel de Registro
    JPanel cambioContrasenaPanel;    // Panel de Cambio de Contraseña

    JPanel peliculasPanel;           // Subpanel de películas
    JPanel perfilPanel;              // Subpanel de perfil
    JPanel comunidadPanel;           // Panel de la comunidad
    JPanel retosPanel;               // Panel de Retos y Recomendaciones   ✅ NUEVO

    JPanel appPanel;                 // Panel del Main (Inicio)
    JPanel logrosPanel;              // Panel de logros e insignias

    // Constructor de la Interfaz
    public PanelGenerador()
    {
        // Creación de los paneles base (siempre disponibles)
        loginPanel = PanelLogin.crearloginPanel();
        registrarPanel = PanelRegistro.crearPanelRegistro();
        cambioContrasenaPanel = PanelCambioContrasena.crearPanelContrasena();

        // Asignación al main de los paneles base
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrarPanel, "Registro");
        mainPanel.add(cambioContrasenaPanel, "CambioContrasena");

        // Si ya hay usuario activo, cargamos la aplicación
        if (ControladorUsuario.getUsuarioActivo() != null)
        {
            appPanel = PanelApp.crearPanelInicio();
            perfilPanel = PanelPerfil.crearPanelPerfil();
            peliculasPanel = PanelPeliculas.crearPanelPeliculas();
            comunidadPanel = PanelComunidad.crearPanelComunidad();
            retosPanel = PanelRetosRecomendaciones.crearPanel();
            logrosPanel = PanelLogros.crearPanelLogros();

            PanelGenerador.getMain().add(appPanel, "Inicio");
        }
        
        // Asignación al main de los paneles
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrarPanel, "Registro");
        mainPanel.add(cambioContrasenaPanel, "CambioContrasena");

        // Características de la ventana
        add(mainPanel);
        setSize(1200, 730);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("CineRoadmap");

        ImageIcon logoVentana = new ImageIcon(getClass().getResource("/main/resources/img/LogoVentana.png"));
        setIconImage(logoVentana.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Establecer el Login como página principal
        colocacion.show(mainPanel, "Login");
    }
}
