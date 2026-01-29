package main.app.Vista;

import main.app.Modelo.Logro;
import main.app.Modelo.Usuario;
import main.app.Controlador.ControladorUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelLogros 
{
    // -------------------------
    // CREAR PANEL LOGROS
    // -------------------------
    public static JPanel crearPanelLogros() 
    {
        Usuario u = ControladorUsuario.getUsuarioActivo();
        ArrayList<Logro> logros = u.getLogros();

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
        // PANEL CONTENIDO
        // -------------------------
        JPanel panelContenido = new JPanel(null);
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // -------------------------
        // CABECERA
        // -------------------------
        JLayeredPane cabecera = new JLayeredPane();
        cabecera.setBounds(0, 0, 1200, 200);

        // Imagen de la cabecera
        JLabel cabeceraImg = new JLabel();
        cabeceraImg.setBounds(0, 0, 1200, 200);
        ImageIcon icon = new ImageIcon(PanelLogros.class.getResource("/main/resources/img/logros.jpg"));
        Image img = icon.getImage().getScaledInstance(1200, 200, Image.SCALE_SMOOTH);
        cabeceraImg.setIcon(new ImageIcon(img));
        cabecera.add(cabeceraImg, JLayeredPane.DEFAULT_LAYER);

        // Panel por encima de la imagen
        JPanel overlay = new JPanel(null);
        overlay.setBounds(0, 0, 1200, 200);
        overlay.setOpaque(false);
        cabecera.add(overlay, JLayeredPane.PALETTE_LAYER);

        // Título de la cabecera
        JLabel cabeceraTitle = new JLabel("Explora logros e insignias");
        cabeceraTitle.setFont(new Font("Arial", Font.BOLD, 32));
        cabeceraTitle.setForeground(Color.WHITE);
        cabeceraTitle.setBounds(20, 30, 1160, 50);
        overlay.add(cabeceraTitle);

        // Descripción de sección
        JLabel cabeceraText = new JLabel("<html>Completa logros, desbloquea insignias y demuestra tus gustos<br>" +
                                        "en CineRoadmap. Cada logro es un paso hacia la maestría cinematográfica.</html>");
        cabeceraText.setFont(new Font("Arial", Font.PLAIN, 16));
        cabeceraText.setForeground(Color.WHITE);
        cabeceraText.setBounds(20, 90, 1160, 60);
        overlay.add(cabeceraText);

        panelContenido.add(cabecera);

        // -------------------------
        // TÍTULO SECCIÓN LOGROS
        // -------------------------
        JLabel tituloLogros = new JLabel("Logros Disponibles");
        tituloLogros.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLogros.setForeground(Color.WHITE);
        tituloLogros.setBounds(20, 220, 400, 30);
        panelContenido.add(tituloLogros);

        // -------------------------
        // PANEL DE LOGROS
        // -------------------------
        int columnas = 4;
        int hGap = 20;
        int vGap = 20;

        JPanel logrosPanel = new JPanel();
        logrosPanel.setOpaque(false);
        logrosPanel.setLayout(new GridLayout(0, columnas, hGap, vGap));

        // Agregar los logros
        for (Logro logro : logros) 
        {
            logrosPanel.add(crearCardLogro(logro));
        }

        // -------------------------
        // SCROLL DE LOGROS
        // -------------------------
        JScrollPane scrollLogros = new JScrollPane(logrosPanel);
        scrollLogros.setBounds(20, 260, 1100, 440);
        scrollLogros.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        scrollLogros.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLogros.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollLogros.getVerticalScrollBar().setUnitIncrement(16);
        scrollLogros.setOpaque(false);
        scrollLogros.getViewport().setOpaque(false);

        scrollLogros.getViewport().addMouseWheelListener(e -> 
        {
            JScrollBar bar = scrollLogros.getVerticalScrollBar();
            int increment = e.getWheelRotation() * bar.getUnitIncrement();
            bar.setValue(bar.getValue() + increment);
        });

        panelContenido.add(scrollLogros);

        // -------------------------
        // FOOTER
        // -------------------------
        JPanel footer = PanelFooter.crearFooter("© CineRoadmap", 1200, 30);
        footer.setBounds(0, 770, 1200, 30);
        panelContenido.add(footer);

        panelContenido.setPreferredSize(new Dimension(1200, 800));

        // -------------------------
        // SCROLL GENERAL
        // -------------------------
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panelCentral.add(scrollPane, BorderLayout.CENTER);

        return panelCentral;
    }

    // -------------------------
    // PANEL DE CADA LOGRO
    // -------------------------
    private static JPanel crearCardLogro(Logro logro) 
    {
        JPanel panelLogro = new JPanel();
        panelLogro.setLayout(new BoxLayout(panelLogro, BoxLayout.Y_AXIS));
        panelLogro.setOpaque(false);
        panelLogro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelLogro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Imagen de la insignia
        ImageIcon icon = logro.getInsignia() != null ? logro.getInsignia().getImagen() : null;
        JLabel imgLabel;

        // Dimensiones de cada insignia
        if (icon != null) 
        {
            Image img = icon.getImage();
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            int maxDim = 150;

            if (width > maxDim || height > maxDim) 
            {
                float ratio = Math.min((float) maxDim / width, (float) maxDim / height);
                width = Math.round(width * ratio);
                height = Math.round(height * ratio);
                img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }

            imgLabel = new JLabel(new ImageIcon(img));
        } 
        else 
        {
            imgLabel = new JLabel();
            imgLabel.setPreferredSize(new Dimension(150, 150));
        }

        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLogro.add(imgLabel);
        panelLogro.add(Box.createVerticalStrut(10));

        // Detalles de cada logro
        panelLogro.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) 
            {
                String msg= "<html><b>" + logro.getNombreReto() + "</b><br><br>" + logro.getDescripcion() + "<br><br>" + "Progreso: " + logro.getActual() + "/" + logro.getObjetivo() + "</html>";
                JOptionPane.showMessageDialog(panelLogro, msg, "Detalle del logro", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panelLogro;
    }
}

