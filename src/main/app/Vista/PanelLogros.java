package main.app.Vista;

import main.app.Modelo.Logro;
import main.app.Modelo.Insignia;
import main.app.Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PanelLogros {

   
    public static JPanel crearPanelLogros(Usuario usuario) {

    ArrayList<Logro> logros = new ArrayList<>(usuario.getLogros());
    Collections.shuffle(logros);

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
        ImageIcon icon = new ImageIcon(PanelLogros.class.getResource("/main/resources/img/logoFondoPantalla.jpg"));
        Image img = icon.getImage().getScaledInstance(1200, 200, Image.SCALE_SMOOTH);
        heroImg.setIcon(new ImageIcon(img));
        hero.add(heroImg, JLayeredPane.DEFAULT_LAYER);

        JPanel overlay = new JPanel(null);
        overlay.setBounds(0, 0, 1200, 200);
        overlay.setOpaque(false);
        hero.add(overlay, JLayeredPane.PALETTE_LAYER);

        JLabel heroTitle = new JLabel("Explora tus logros e insignias");
        heroTitle.setFont(new Font("Arial", Font.BOLD, 32));
        heroTitle.setForeground(Color.WHITE);
        heroTitle.setBounds(20, 30, 1160, 50);
        overlay.add(heroTitle);

        JLabel heroText = new JLabel("<html>Completa retos, desbloquea insignias y demuestra tus logros<br>" +
                "en CineRoadmap. Cada logro es un paso hacia la maestría cinematográfica.</html>");
        heroText.setFont(new Font("Arial", Font.PLAIN, 16));
        heroText.setForeground(Color.WHITE);
        heroText.setBounds(20, 90, 1160, 60);
        overlay.add(heroText);

        panelContenido.add(hero);

        // =========================
        // TÍTULO SECCIÓN LOGROS
        // =========================
        JLabel tituloLogros = new JLabel("Mis Logros");
        tituloLogros.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLogros.setForeground(Color.WHITE);
        tituloLogros.setBounds(20, 220, 400, 30);
        panelContenido.add(tituloLogros);

        // =========================
        // PANEL DE CARDS DE LOGROS
        // =========================
        JPanel logrosPanel = new JPanel();
        logrosPanel.setBounds(20, 260, 1160, 440);
        logrosPanel.setOpaque(false);
        logrosPanel.setLayout(new GridLayout(2, 3, 40, 20));

        int maxLogros = Math.min(6, logros.size());
        for(int i = 0; i < maxLogros; i++){
            logrosPanel.add(crearCardLogro(logros.get(i)));
        }

        panelContenido.add(logrosPanel);

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
    private static JPanel crearCardLogro(Logro logro){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon icon = logro.getInsignia() != null ? logro.getInsignia().getImagen() : null;
        Image img = (icon != null) ? icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH) : null;
        JLabel imgLabel = new JLabel((img != null) ? new ImageIcon(img) : new JLabel().getIcon());
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nombreLabel = new JLabel(logro.getNombreReto());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nombreLabel.setForeground(Color.WHITE);
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel estadoLabel = new JLabel(logro.isCompleto() ? "Completado ✅" : "Pendiente");
        estadoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        estadoLabel.setForeground(logro.isCompleto() ? Color.GREEN : Color.LIGHT_GRAY);
        estadoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(imgLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(nombreLabel);
        card.add(estadoLabel);

        card.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                String msg = "<html><b>"+logro.getNombreReto()+"</b><br><br>"+
                        logro.getDescripcion()+"<br><br>"+
                        "Progreso: "+logro.getActual()+"/"+logro.getObjetivo()+"</html>";
                JOptionPane.showMessageDialog(card, msg, "Detalle del logro", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return card;
    }
}
