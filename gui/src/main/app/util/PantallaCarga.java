package main.app.util;

import javax.swing.*;
import java.awt.*;
import main.app.Vista.PanelGenerador;

public class PantallaCarga extends JWindow 
{
    // -------------------------
    // COMPONENTES PRINCIPALES
    // -------------------------
    private JLabel cargandoLabel;   // texto principal de estado
    private JLabel porcentajeLabel;  // porcentaje de carga
    private JProgressBar barraProgreso; // barra de progreso visual
    private JLabel logoLabel;        // logo de CineRoadmap
    private JLabel tituloLabel;      // título de la app
    private JLabel fondoLabel;       // imagen de fondo decorativa

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public PantallaCarga() 
    {
        // -------------------------
        // CONFIGURACIÓN DE VENTANA
        // -------------------------
        setSize(889, 500);              // tamaño fijo
        setLocationRelativeTo(null);    // centrado en pantalla
        setLayout(null);                // layout absoluto

        // -------------------------
        // PANEL PRINCIPAL DE FONDO
        // -------------------------
        JPanel panelFondo = new JPanel();
        panelFondo.setBackground(Color.BLACK);
        panelFondo.setLayout(null);
        panelFondo.setBounds(0, 0, 900, 500);

        // -------------------------
        // LABEL TEXTO DE ESTADO ("Cargando...")
        // -------------------------
        cargandoLabel = new JLabel("Cargando...");
        cargandoLabel.setForeground(Color.WHITE);
        cargandoLabel.setBounds(145, 470, 300, 20);
        panelFondo.add(cargandoLabel);

        // -------------------------
        // LABEL PORCENTAJE
        // -------------------------
        porcentajeLabel = new JLabel("0%");
        porcentajeLabel.setForeground(Color.WHITE);
        porcentajeLabel.setBounds(720, 470, 50, 20);
        panelFondo.add(porcentajeLabel);

        // -------------------------
        // LOGO DE CINE ROADMAP
        // -------------------------
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("../../resources/img/LogoVentana.png"));
        Image imagen = originalIcon.getImage().getScaledInstance(280, 260, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(imagen));
        logoLabel.setBounds(325, 140, 250, 260);
        panelFondo.add(logoLabel);

        // -------------------------
        // TITULO DE LA APP
        // -------------------------
        tituloLabel = new JLabel("CineRoadmap");
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 30));
        tituloLabel.setBounds(345, 370, 300, 50);
        panelFondo.add(tituloLabel);

        // -------------------------
        // IMAGEN DE FONDO DECORATIVA
        // -------------------------
        fondoLabel = new JLabel(new ImageIcon(getClass().getResource(
            "../../resources/img/curtain-openning-show-isolated-alpha-overlay-transparent-background-png.png")));
        fondoLabel.setBounds(0, 0, 889, 500);
        panelFondo.add(fondoLabel);

        // -------------------------
        // BARRA DE PROGRESO
        // -------------------------
        barraProgreso = new JProgressBar();
        barraProgreso.setBounds(145, 490, 600, 20);
        barraProgreso.setBackground(Color.BLACK);
        barraProgreso.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        barraProgreso.setForeground(new Color(200, 0, 0));
        panelFondo.add(barraProgreso);

        // -------------------------
        // MOSTRAR VENTANA Y EMPEZAR CARGA
        // -------------------------
        setVisible(true);
        iniciarCarga();  // ejecuta hilo de carga animada

        // Añadir el panel de fondo a la ventana
        add(panelFondo);
    }

    // -------------------------
    // MÉTODO DE CARGA CON HILO
    // -------------------------
    public void iniciarCarga() 
    {
        new Thread(() -> 
        {
            try 
            {
                for (int i = 0; i <= 100; i++) 
                {
                    Thread.sleep(100); // simula carga
                    final int porcentaje = i;

                    // -------------------------
                    // ACTUALIZAR UI EN EL HILO DE EVENTOS
                    // -------------------------
                    SwingUtilities.invokeLater(() -> 
                    {
                        porcentajeLabel.setText(porcentaje + "%");  // actualizar porcentaje
                        barraProgreso.setValue(porcentaje);        // actualizar barra

                        // -------------------------
                        // MENSAJES SEGÚN PORCENTAJE
                        // -------------------------
                        switch (porcentaje) 
                        {
                            case 10:
                                cargandoLabel.setText("Recuperando archivos de configuración...");
                                break;
                            case 20:
                                cargandoLabel.setText("Cargando módulos...");
                                break;
                            case 50:
                                cargandoLabel.setText("Conectando con la base de datos...");
                                break;
                            case 80:
                                cargandoLabel.setText("Conexión establecida...");
                                break;
                            case 90:
                                cargandoLabel.setText("Lanzando aplicación...");
                                break;
                            case 100:
                                cargandoLabel.setText("Lanzando aplicación...");
                                dispose();               // cerrar ventana de carga
                                new PanelGenerador();    // abrir panel principal
                                break;
                            default:
                                break;
                        }
                    });
                }
            } 
            catch (InterruptedException e) 
            {
                JOptionPane.showMessageDialog(null, e); // mostrar error si el hilo falla
            }
        }).start(); // ejecutar en segundo plano
    }
}
