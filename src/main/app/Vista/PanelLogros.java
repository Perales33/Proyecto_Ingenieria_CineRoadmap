package main.app.Vista;

import main.app.Modelo.Logro;
import main.app.Modelo.Usuario;
import main.app.Controlador.ControladorUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelLogros {

    public static JPanel crearPanelLogros() {

        Usuario u = ControladorUsuario.getUsuarioActivo();
        ArrayList<Logro> logros = u.getLogros();

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
        hero.setBounds(0, 0, 1200, 200);

        JLabel heroImg = new JLabel();
        heroImg.setBounds(0, 0, 1200, 200);
        ImageIcon icon = new ImageIcon(PanelLogros.class.getResource("/main/resources/img/logros.jpg"));
        Image img = icon.getImage().getScaledInstance(1200, 200, Image.SCALE_SMOOTH);
        heroImg.setIcon(new ImageIcon(img));
        hero.add(heroImg, JLayeredPane.DEFAULT_LAYER);

        JPanel overlay = new JPanel(null);
        overlay.setBounds(0, 0, 1200, 200);
        overlay.setOpaque(false);
        hero.add(overlay, JLayeredPane.PALETTE_LAYER);

        JLabel heroTitle = new JLabel("Explora logros e insignias");
        heroTitle.setFont(new Font("Arial", Font.BOLD, 32));
        heroTitle.setForeground(Color.WHITE);
        heroTitle.setBounds(20, 30, 1160, 50);
        overlay.add(heroTitle);

        JLabel heroText = new JLabel("<html>Completa logros, desbloquea insignias y demuestra tus gustos<br>" +
                "en CineRoadmap. Cada logro es un paso hacia la maestría cinematográfica.</html>");
        heroText.setFont(new Font("Arial", Font.PLAIN, 16));
        heroText.setForeground(Color.WHITE);
        heroText.setBounds(20, 90, 1160, 60);
        overlay.add(heroText);

        panelContenido.add(hero);

        // =========================
        // TÍTULO SECCIÓN LOGROS
        // =========================
        JLabel tituloLogros = new JLabel("Logros Disponibles");
        tituloLogros.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLogros.setForeground(Color.WHITE);
        tituloLogros.setBounds(20, 220, 400, 30);
        panelContenido.add(tituloLogros);

        // =========================
        // PANEL DE CARDS DE LOGROS (grid 4 columnas)
        // =========================
        int columnas = 4;
        int hGap = 20; // espacio horizontal entre tarjetas
        int vGap = 20; // espacio vertical entre tarjetas

        JPanel logrosPanel = new JPanel();
        logrosPanel.setOpaque(false);

        // GridLayout con 0 filas y 4 columnas (filas dinámicas)
        logrosPanel.setLayout(new GridLayout(0, columnas, hGap, vGap));

        // Agregar los logros
        for (Logro logro : logros) {
            logrosPanel.add(crearCardLogro(logro));
        }

        // Crear JScrollPane para permitir scroll vertical
        JScrollPane scrollLogros = new JScrollPane(logrosPanel);
        scrollLogros.setBounds(20, 260, 1100, 440);

        // Quitar bordes
        scrollLogros.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        // Deshabilitar barra horizontal
        scrollLogros.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Mantener scroll vertical funcional pero invisible
        scrollLogros.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // barra invisible
        scrollLogros.getVerticalScrollBar().setUnitIncrement(16); // velocidad del scroll
        scrollLogros.setOpaque(false);
        scrollLogros.getViewport().setOpaque(false);

        // Opcional: hacer scroll con la rueda del ratón
        scrollLogros.getViewport().addMouseWheelListener(e -> {
            JScrollBar bar = scrollLogros.getVerticalScrollBar();
            int increment = e.getWheelRotation() * bar.getUnitIncrement();
            bar.setValue(bar.getValue() + increment);
        });


        // Agregar al panel principal
        panelContenido.add(scrollLogros);


        // =========================
        // FOOTER
        // =========================
        JPanel footer = PanelFooter.crearFooter("© CineRoadmap", 1200, 30);
        footer.setBounds(0, 770, 1200, 30); // sigue siendo null layout, posición manual
        panelContenido.add(footer);


        panelContenido.setPreferredSize(new Dimension(1200, 800));

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

    // ---------------- CARD LOGRO ----------------
    private static JPanel crearCardLogro(Logro logro) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Imagen de la insignia
        ImageIcon icon = logro.getInsignia() != null ? logro.getInsignia().getImagen() : null;
        JLabel imgLabel;
        if (icon != null) {
            Image img = icon.getImage();
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            int maxDim = 150;
            if (width > maxDim || height > maxDim) {
                float ratio = Math.min((float) maxDim / width, (float) maxDim / height);
                width = Math.round(width * ratio);
                height = Math.round(height * ratio);
                img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }
            imgLabel = new JLabel(new ImageIcon(img));
        } else {
            imgLabel = new JLabel();
            imgLabel.setPreferredSize(new Dimension(150, 150));
        }
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(imgLabel);
        card.add(Box.createVerticalStrut(10));

        // Al hacer click, mostrar progreso y detalles
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String msg = "<html><b>" + logro.getNombreReto() + "</b><br><br>" +
                        logro.getDescripcion() + "<br><br>" +
                        "Progreso: " + logro.getActual() + "/" + logro.getObjetivo() + "</html>";
                JOptionPane.showMessageDialog(card, msg, "Detalle del logro", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return card;
    }
}
