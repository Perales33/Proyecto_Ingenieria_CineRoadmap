package main.app.Vista;

import javax.swing.*;
import java.awt.*;

public class Ejemplo extends JFrame {

    public Ejemplo() {
        setTitle("Banner CineRoadmap");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 1050);
        setLocationRelativeTo(null);
        setLayout(null); // layout absoluto para controlar posición exacta

        // Panel negro para el logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.BLACK);
        logoPanel.setBounds(0, 0, 100, 150); // ancho fijo del logo
        logoPanel.setLayout(null);
        add(logoPanel);

        // Logo
// Carga la imagen original
ImageIcon logoIcon = new ImageIcon(getClass().getResource("../../resources/img/LogoVentana.png"));

// Escala la imagen al tamaño que quieras que ocupe dentro del logoPanel
Image logoImg = logoIcon.getImage().getScaledInstance(90, 86, Image.SCALE_SMOOTH); // ancho=100, alto=150
logoIcon = new ImageIcon(logoImg);

// Label con la imagen escalada
JLabel logoLabel = new JLabel(logoIcon);
logoLabel.setBounds(0, 0, 100, 150); // ahora ocupa todo el logoPanel
logoPanel.add(logoLabel);


        // Panel rojo del menú
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(200, 0, 0));
        menuPanel.setBounds(100, 0, 1100, 150); // el resto del ancho
        menuPanel.setLayout(null);
        add(menuPanel);

        // Labels del menú con posiciones absolutas
        JLabel inicioLabel = new JLabel("Inicio");
        JLabel peliculasLabel = new JLabel("Películas");
        JLabel insigniasLabel = new JLabel("Insignias y Logros");
        JLabel retosLabel = new JLabel("Retos");
        JLabel cerrarSesionLabel = new JLabel("Cerrar Sesión");

        Font menuFont = new Font("Arial", Font.PLAIN, 20);
        inicioLabel.setFont(menuFont);
        peliculasLabel.setFont(menuFont);
        insigniasLabel.setFont(menuFont);
        retosLabel.setFont(menuFont);
        cerrarSesionLabel.setFont(menuFont);

        // Posicionamiento horizontal (ajusta x según necesites)
        inicioLabel.setBounds(50, 60, 80, 30);
        peliculasLabel.setBounds(150, 60, 100, 30);
        insigniasLabel.setBounds(300, 60, 200, 30);
        retosLabel.setBounds(550, 60, 80, 30);
        cerrarSesionLabel.setBounds(925, 60, 150, 30);

        menuPanel.add(inicioLabel);
        menuPanel.add(peliculasLabel);
        menuPanel.add(insigniasLabel);
        menuPanel.add(retosLabel);
        menuPanel.add(cerrarSesionLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ejemplo ventana = new Ejemplo();
            ventana.setVisible(true);
        });
    }
}
