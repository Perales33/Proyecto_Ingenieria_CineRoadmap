package main.app.Vista;

import javax.swing.*;
import java.awt.*;

import main.app.Controlador.*;

public class PanelGenerador extends JFrame
{
    // Página principal
    static CardLayout colocacion = new CardLayout();
    static JPanel  mainPanel = new JPanel(colocacion); // Panel principal

    protected static JLabel tituloMain; // Título del la página principal
    
    // Getter de la página principal
    public static CardLayout getColocacion()
    {
        return colocacion;
    }
    
    // Paneles de la interfaz
    JPanel loginPanel; // Panel de Login
    JPanel registrarPanel; // Panel de Registro
    JPanel cambioContrasenaPanel; // Panel de Cambio de Contraseña
    JPanel peliculasPanel; // Subpanel de películas 
    JPanel perfilPanel; // Subpanel de perfil
    JPanel appPanel; // Panel del Main

    // Constructor de la Interfaz
    public PanelGenerador()
    {
        // Creación de los paneles
        loginPanel = PanelLogin.crearloginPanel();
        registrarPanel = PanelRegistro.crearPanelRegistro();
        cambioContrasenaPanel = PanelCambioContrasena.crearPanelContrasena();
        

        if(ControladorUsuario.getUsuarioActivo() != null)
        {
            appPanel = PanelApp.crearPanelInicio();
            perfilPanel = PanelPerfil.crearPanelPerfil();
            peliculasPanel = PanelPeliculas.crearPanelPeliculas();

            PanelGenerador.mainPanel.add(appPanel, "Inicio");
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
