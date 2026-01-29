package main.app.Vista;

import javax.swing.*;

import main.app.Controlador.ControladorApp;
import main.app.Modelo.Pelicula;
import main.app.util.Estilos;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PanelApp 
{
    public static JPanel crearPanelInicio() 
    {
        // Panel principal tipo BorderLayout
        JPanel panelCentral = new JPanel(new BorderLayout());

        // Banner
        JPanel banner = PanelBanner.crearBanner();
        panelCentral.add(banner, BorderLayout.NORTH);

        // Panel de contenido con scroll
        JPanel panelContenido = new JPanel(null);
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // =========================
        // Cabecera
        // =========================
        JLayeredPane cabecera = new JLayeredPane();
        cabecera.setBounds(0, 0, 1200, 250);

        JLabel cabeceraImg = new JLabel();
        cabeceraImg.setBounds(0, 0, 1200, 250);
        ImageIcon icon = new ImageIcon(PanelApp.class.getResource("/main/resources/img/fondo.jpg"));
        Image img = icon.getImage().getScaledInstance(1200, 250, Image.SCALE_SMOOTH);
        cabeceraImg.setIcon(new ImageIcon(img));
        cabecera.add(cabeceraImg, JLayeredPane.DEFAULT_LAYER);

        JPanel overlay = new JPanel(null);
        overlay.setBounds(0, 0, 1200, 250);
        overlay.setOpaque(false);
        cabecera.add(overlay, JLayeredPane.PALETTE_LAYER);

        JLabel cabeceraTitle = new JLabel("Bienvenido a CineRoadmap");
        cabeceraTitle.setFont(new Font("Arial", Font.BOLD, 36));
        cabeceraTitle.setForeground(Color.WHITE);
        cabeceraTitle.setBounds(20, 30, 1160, 50);
        overlay.add(cabeceraTitle);

        JLabel cabeceraText = new JLabel("<html>El cine no solo se mira, se recorre.<br>" +
                "Explora clásicos eternos y nuevas propuestas, descubre recomendaciones y deja tu huella <br>" +
                "en una comunidad que entiende el cine como arte, memoria y emoción.</html>");
        cabeceraText.setFont(new Font("Arial", Font.PLAIN, 16));
        cabeceraText.setForeground(Color.WHITE);
        cabeceraText.setBounds(20, 90, 1160, 80);
        overlay.add(cabeceraText);

        JButton joinButton = new JButton("Únete");
        joinButton.setBounds(20, 180, 120, 40);
        joinButton.setBackground(new Color(255, 87, 34));
        joinButton.setForeground(Color.WHITE);
        joinButton.setFocusPainted(false);
        joinButton.addActionListener(e -> ControladorApp.botonComunidadPresionado());
        overlay.add(joinButton);

        panelContenido.add(cabecera);

        // =========================
        // SECCIÓN PELÍCULAS
        // =========================

        // Catálogo original
        ArrayList<Pelicula> catalogoOriginal = Pelicula.getCatalogo();

        // Copia del catálogo
        ArrayList<Pelicula> catalogo = new ArrayList<>(catalogoOriginal);
        Collections.shuffle(catalogo); // Mezclar las películas

        JLabel tituloPeliculas = new JLabel("Califica películas");
        tituloPeliculas.setFont(new Font("Arial", Font.BOLD, 24));
        tituloPeliculas.setForeground(Color.WHITE);
        tituloPeliculas.setBounds(20, 260, 300, 30);
        panelContenido.add(tituloPeliculas);

        JPanel peliculasPanel = new JPanel();
        peliculasPanel.setBounds(20, 300, 1160, 250);
        peliculasPanel.setOpaque(false);
        peliculasPanel.setLayout(new GridLayout(1, 3, 20, 0));

        for (int i = 0; i < 3 && i < catalogo.size(); i++) {
            Pelicula p = catalogo.get(i);
            peliculasPanel.add(crearCard(p.getnombrePelicula(), p.getFoto()));
        }

        panelContenido.add(peliculasPanel);

        // =========================
        // SECCIÓN INSIGNIAS Y LOGROS
        // =========================

        // Título de la sección
        JLabel tituloInsignias = new JLabel("Obtén insignias");
        tituloInsignias.setFont(new Font("Arial", Font.BOLD, 24));
        tituloInsignias.setBounds(20, 570, 400, 30);
        tituloInsignias.setForeground(Color.WHITE);
        panelContenido.add(tituloInsignias);

        // Panel principal de insignias
        JPanel insigniasPanel = new JPanel(new BorderLayout());
        insigniasPanel.setBounds(20, 600, 1100, 250);
        insigniasPanel.setBackground(null);

        // Imagen a la derecha
        JLabel imgLabel = new JLabel();
        ImageIcon imgIcon = new ImageIcon(PanelApp.class.getResource("/main/resources/img/insigniaCamino.png"));
        Image imgInsignias = imgIcon.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(imgInsignias));
        insigniasPanel.add(imgLabel, BorderLayout.EAST);

        // Panel de logros de ejemplo
        JPanel logrosPanel = new JPanel();
        logrosPanel.setLayout(new BoxLayout(logrosPanel, BoxLayout.Y_AXIS));
        logrosPanel.setOpaque(false);
        logrosPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5)); 

        // Matriz de logros para completar la pantalla principal
        String[][] logros = {
            {"Top 100 películas", "Ver las 100 mejores películas según IMDb"},
            {"Fan de Scorsese", "Ver al menos 10 películas de Martin Scorsese"},
            {"Rey del VHS (80s)", "Ver 100 películas de los años 80"},
            {"1 Day 1 Saga", "Ver toda una saga en un solo día (ejemplo: Star Wars, Harry Potter, El Señor de los Anillos)"}
        };

        // Estilo genéricos para títulos de logros y descripción
        Font fontTitulo = new Font("Arial", Font.BOLD, 12);
        Font fontDesc = new Font("Arial", Font.PLAIN, 12);
        Color colorTexto = Color.WHITE;

        for (String[] logro : logros) 
        {
            // Panel por cada logro
            JPanel logroPanel = new JPanel();
            logroPanel.setLayout(new BoxLayout(logroPanel, BoxLayout.Y_AXIS));
            logroPanel.setOpaque(false);
            logroPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Título de cada logro
            JLabel tituloLogro = new JLabel(logro[0] + ":");
            tituloLogro.setFont(fontTitulo);
            tituloLogro.setForeground(colorTexto);
            tituloLogro.setBackground(null);
            tituloLogro.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Descripción del logro
            JLabel descLogro = new JLabel("<html><div style='width:400px;'>" + logro[1] + "</div></html>");
            descLogro.setFont(fontDesc);
            descLogro.setForeground(colorTexto);
            descLogro.setAlignmentX(Component.LEFT_ALIGNMENT);

            logroPanel.add(tituloLogro);
            logroPanel.add(Box.createVerticalStrut(2));
            logroPanel.add(descLogro);

            logrosPanel.add(logroPanel);
            logrosPanel.add(Box.createVerticalStrut(10));
        }

        // Botón al final
        JButton verInsigniasButton = new JButton("Ir a logros e insignias");
        verInsigniasButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        Estilos.estiloBotones(verInsigniasButton);
        verInsigniasButton.addActionListener(e -> ControladorApp.botonLogroPresionado());
        logrosPanel.add(verInsigniasButton);

        insigniasPanel.add(logrosPanel, BorderLayout.CENTER);

        panelContenido.add(insigniasPanel);

        // =========================
        // FOOTER
        // =========================
        JPanel footer = PanelFooter.crearFooter("© CineRoadmap", 1200, 30);
        footer.setBounds(0, 870, 1200, 30); // sigue siendo null layout, posición manual
        panelContenido.add(footer);

        panelContenido.setPreferredSize(new Dimension(1200, 900));

        // Panel con scroll general de la pantalla
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panelCentral.add(scrollPane, BorderLayout.CENTER);

        return panelCentral;
    }

    // =========================
    // MÉTODO PARA CREAR CARDS CENTRADAS
    // =========================
    private static JPanel crearCard(String titulo, String imgPelicula) 
    {
        // Panel general por cada película aleatoria
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 250));
        card.setOpaque(false);

        // Imagen de la película
        ImageIcon icon = new ImageIcon(PanelApp.class.getResource("/main/resources/img/" + imgPelicula));
        Image img = icon.getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);
        card.add(imgLabel, BorderLayout.NORTH);

        // Panel para el título
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        // Estilos para el título de la película
        Font fontTitulo = new Font("Arial", Font.BOLD, 12);
        Color colorTexto = Color.WHITE;

        // Título de la película
        JLabel titleLabel = new JLabel(titulo, SwingConstants.CENTER);
        titleLabel.setFont(fontTitulo);
        titleLabel.setForeground(colorTexto);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(titleLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }
}
