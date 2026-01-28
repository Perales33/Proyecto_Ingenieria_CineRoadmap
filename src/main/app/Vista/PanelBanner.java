package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.app.Controlador.*;
import main.app.Modelo.Usuario;

public class PanelBanner {

    public static JPanel crearBanner()
    {
        JPanel bannerPanel = new JPanel(null);
        bannerPanel.setPreferredSize(new Dimension(1200, 86));

        // ----------------------
        // Panel negro para el logo
        // ----------------------
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.BLACK);
        logoPanel.setBounds(0, 0, 100, 86);
        logoPanel.setLayout(null);
        bannerPanel.add(logoPanel);

        Usuario usuario = ControladorUsuario.getUsuarioActivo();
        String fotoUsuario = (usuario != null && usuario.getFoto() != null) ? usuario.getFoto() : "logoCineRoadmap.jpg";

        ImageIcon logoIcon = new ImageIcon(PanelPerfil.class.getResource("/main/resources/img/" + fotoUsuario));
        Image logoImg = logoIcon.getImage().getScaledInstance(90, 86, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImg);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(5, 0, 90, 86);
        logoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoPanel.add(logoLabel);

        // Click logo -> Perfil
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                refrescarPantalla("Perfil", PanelPerfil.crearPanelPerfil());
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Perfil");
            }
        });

        // ----------------------
        // Panel rojo del menú
        // ----------------------
        JPanel menuPanel = new JPanel(null);
        menuPanel.setBackground(new Color(200, 0, 0));
        menuPanel.setBounds(100, 0, 1100, 86);
        bannerPanel.add(menuPanel);

        // Labels
        JLabel inicioLabel = new JLabel("Inicio");
        JLabel peliculasLabel = new JLabel("Películas");
        JLabel insigniasLabel = new JLabel("Logros");
        JLabel retosLabel = new JLabel("Retos");
        JLabel misRetosLabel = new JLabel("Mis Retos"); // ✅ NUEVO
        JLabel comunidadLabel = new JLabel("Comunidad");
        JLabel cerrarSesionLabel = new JLabel("Cerrar Sesión");

        JLabel[] labels = {
                inicioLabel, peliculasLabel, insigniasLabel,
                retosLabel, misRetosLabel, comunidadLabel,
                cerrarSesionLabel
        };

        Font menuFont = new Font("Arial", Font.BOLD, 16);
        for (JLabel label : labels)
        {
            label.setFont(menuFont);
            label.setForeground(Color.WHITE);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            menuPanel.add(label);
        }

        // Posiciones (ajústalas si quieres más aire)
        inicioLabel.setBounds(40, 30, 70, 30);
        peliculasLabel.setBounds(140, 30, 100, 30);
        insigniasLabel.setBounds(260, 30, 80, 30);
        retosLabel.setBounds(360, 30, 60, 30);
        misRetosLabel.setBounds(440, 30, 90, 30);       // ✅ NUEVO
        comunidadLabel.setBounds(560, 30, 110, 30);
        cerrarSesionLabel.setBounds(900, 30, 180, 30);

        // ----------------------
        // Navegación (sin duplicar tarjetas)
        // ----------------------
        inicioLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                refrescarPantalla("Inicio", PanelApp.crearPanelInicio());
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Inicio");
            }
        });

        peliculasLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                refrescarPantalla("Peliculas", PanelPeliculas.crearPanelPeliculas());
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Peliculas");
            }
        });

        comunidadLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                refrescarPantalla("Comunidad", PanelComunidad.crearPanelComunidad());
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Comunidad");
            }
        });

        retosLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                refrescarPantalla("Retos", PanelRetosRecomendaciones.crearPanel());
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Retos");
            }
        });

        // ✅ NUEVO: Mis Retos
        misRetosLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {

                if (ControladorUsuario.getUsuarioActivo() == null) {
                    JOptionPane.showMessageDialog(PanelGenerador.getMain(),
                            "Debes iniciar sesión para ver tus retos.",
                            "Sin sesión",
                            JOptionPane.WARNING_MESSAGE);
                    PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
                    return;
                }

                refrescarPantalla("MisRetos", PanelMisRetos.crearPanel());
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "MisRetos");
            }
        });

        insigniasLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                refrescarPantalla("LogrosInsignias", PanelLogros.crearPanelLogros());
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "LogrosInsignias");
            }
        });

        cerrarSesionLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                Usuario u = ControladorUsuario.getUsuarioActivo();

                JOptionPane.showMessageDialog(PanelGenerador.getMain(),
                        "Cerrando Sesión " + (u != null ? u.getnombreUsuario() : ""),
                        "Cerrar Sesión",
                        JOptionPane.INFORMATION_MESSAGE);

                ControladorUsuario.setUsuarioActivo();
                PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
            }
        });

        return bannerPanel;
    }

    // =========================
    // Refresco seguro de tarjetas
    // =========================
    private static void refrescarPantalla(String nombre, JPanel nuevoPanel)
    {
        JPanel main = PanelGenerador.getMain();

        Component[] comps = main.getComponents();
        for (Component c : comps) {
            if (nombre.equals(c.getName())) {
                main.remove(c);
                break;
            }
        }

        nuevoPanel.setName(nombre);
        main.add(nuevoPanel, nombre);

        main.revalidate();
        main.repaint();
    }
}
