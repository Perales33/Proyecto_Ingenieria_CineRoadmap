package main.app.Vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import main.app.Modelo.Usuario;
import main.app.Controlador.ControladorUsuario;
import main.app.Modelo.Comunidad;

public class PanelComunidad {

    public static JPanel crearPanelComunidad() {

        // Panel principal
        JPanel panelCentral = new JPanel(new BorderLayout());

        // =========================
        // BANNER SUPERIOR
        // =========================
        JPanel banner = PanelBanner.crearBanner();
        panelCentral.add(banner, BorderLayout.NORTH);

        // =========================
        // PANEL CONTENIDO (SCROLL)
        // =========================
        JPanel panelContenido = new JPanel(null);
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // =========================
        // HERO
        // =========================
        JLayeredPane hero = new JLayeredPane();
        hero.setBounds(0, 0, 1200, 250);

        JLabel heroImg = new JLabel();
        heroImg.setBounds(0, 0, 1200, 250);
        ImageIcon icon = new ImageIcon(
                PanelApp.class.getResource("/main/resources/img/comunidad.jpeg")
        );
        Image img = icon.getImage().getScaledInstance(1200, 250, Image.SCALE_SMOOTH);
        heroImg.setIcon(new ImageIcon(img));
        hero.add(heroImg, JLayeredPane.DEFAULT_LAYER);

        JPanel overlay = new JPanel(null);
        overlay.setBounds(0, 0, 1200, 250);
        overlay.setOpaque(false);
        hero.add(overlay, JLayeredPane.PALETTE_LAYER);

        JLabel heroTitle = new JLabel("Bienvenido a nuestra comunidad");
        heroTitle.setFont(new Font("Arial", Font.BOLD, 36));
        heroTitle.setForeground(Color.WHITE);
        heroTitle.setBounds(20, 30, 1160, 50);
        overlay.add(heroTitle);

        JLabel heroText = new JLabel("<html>Nuestro objetivo es inspirar a los usuarios a explorar nuevos géneros,<br>" +
                "épocas y directores mientras se divierten completando retos y logros.<br>" +
                "CineRoadmap convierte cada película en una aventura.</html>");
        heroText.setFont(new Font("Arial", Font.PLAIN, 16));
        heroText.setForeground(Color.WHITE);
        heroText.setBounds(20, 90, 1160, 80);
        overlay.add(heroText);

        panelContenido.add(hero);

        // =========================
        // USUARIOS DE LA COMUNIDAD
        // =========================
        JLabel tituloUsuarios = new JLabel("Usuarios de la comunidad");
        tituloUsuarios.setFont(new Font("Arial", Font.BOLD, 24));
        tituloUsuarios.setForeground(Color.WHITE);
        tituloUsuarios.setBounds(20, 260, 400, 30);
        panelContenido.add(tituloUsuarios);

        // Panel para los usuarios
        JPanel usuariosPanel = new JPanel();
        usuariosPanel.setBounds(20, 300, 1160, 440); // Doble altura para 2 filas
        usuariosPanel.setOpaque(false);
        usuariosPanel.setLayout(new GridLayout(2, 3, 40, 20)); // 2 filas x 3 columnas

        // =========================
        // FILTRAR USUARIOS (EXCLUIR USUARIO ACTIVO)
        // =========================
        Usuario usuarioActivo = ControladorUsuario.getUsuarioActivo();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        for (Usuario u : Comunidad.getUsuarios()) {
            if (!u.equals(usuarioActivo)) {
                usuarios.add(u);
            }
        }

        Collections.shuffle(usuarios);

        // Tomar hasta 6 usuarios únicos (2 filas x 3 columnas)
        int maxUsuarios = Math.min(6, usuarios.size());
        for (int i = 0; i < maxUsuarios; i++) {
            usuariosPanel.add(crearCardUsuario(usuarios.get(i)));
        }

        panelContenido.add(usuariosPanel);

        // =========================
        // FOOTER
        // =========================
        JPanel footer = new JPanel();
        footer.setBounds(0, 750, 1200, 30);
        footer.setBackground(Color.DARK_GRAY);

        JLabel footerText = new JLabel("© CineRoadmap");
        footerText.setForeground(Color.WHITE);
        footer.add(footerText);

        panelContenido.add(footer);

        panelContenido.setPreferredSize(new Dimension(1200, 800)); // Ajustar altura total

        // =========================
        // SCROLL
        // =========================
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panelCentral.add(scrollPane, BorderLayout.CENTER);

        return panelCentral;
    }

    // =========================
    // CARD USUARIO
    // =========================
    private static JPanel crearCardUsuario(Usuario usuario) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Imagen del usuario
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(
                PanelApp.class.getResource("/main/resources/img/" + usuario.getFoto())
            );
        } catch (Exception e) {
            icon = new ImageIcon(); // fallback
        }

        Image img = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        JLabel fotoLabel = new JLabel(new ImageIcon(img));
        fotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nombreLabel = new JLabel(usuario.getnombreUsuario());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nombreLabel.setForeground(Color.WHITE);
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(fotoLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(nombreLabel);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Crear el panel del perfil del usuario seleccionado
                JPanel panelPerfil = PanelPerfilComunidad.crearPerfilComunidad(usuario);

                // Obtener panel principal con CardLayout
                JPanel mainPanel = PanelGenerador.getMain();
                CardLayout cl = (CardLayout) mainPanel.getLayout();

                // Eliminar panel previo de perfiles de comunidad
                for (Component comp : mainPanel.getComponents()) {
                    if (comp instanceof JPanel && comp != mainPanel.getComponent(0)) {
                        mainPanel.remove(comp);
                    }
                }

                // Agregar panel del usuario con nombre único
                String cardName = "PanelPerfilComunidad_" + usuario.getnombreUsuario();
                mainPanel.add(panelPerfil, cardName);

                // Mostrarlo
                cl.show(mainPanel, cardName);

                // Actualizar interfaz del panel recién agregado
                panelPerfil.revalidate();
                panelPerfil.repaint();
            }
        });

        return card;
    }
}
