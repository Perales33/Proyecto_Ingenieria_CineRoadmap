package main.app.util;

import javax.swing.*;
import java.awt.*;
import main.app.Vista.PanelGenerador;

public class PantallaCarga extends JWindow 
{

    private JLabel cargandoLabel;
    private JLabel porcentajeLabel;
    private JProgressBar barraProgreso;
    private JLabel logoLabel;
    private JLabel tituloLabel;
    private JLabel fondoLabel;

    public PantallaCarga() 
    {
        setSize(889, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel panelFondo = new JPanel();
        panelFondo.setBackground(Color.BLACK);
        panelFondo.setLayout(null);
        panelFondo.setBounds(0, 0, 900, 500);

        cargandoLabel = new JLabel("Cargando...");
        cargandoLabel.setForeground(Color.WHITE);
        cargandoLabel.setBounds(145, 470, 300, 20);
        panelFondo.add(cargandoLabel);

        porcentajeLabel = new JLabel("0%");
        porcentajeLabel.setForeground(Color.WHITE);
        porcentajeLabel.setBounds(720, 470, 50, 20);
        panelFondo.add(porcentajeLabel);

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("../../resources/img/LogoVentana.png"));
        Image imagen = originalIcon.getImage().getScaledInstance(280, 260, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(imagen));
        logoLabel.setBounds(325, 140, 250, 260);
        panelFondo.add(logoLabel);

        tituloLabel = new JLabel("CineRoadmap");
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 30));
        tituloLabel.setBounds(345, 370, 300, 50);
        panelFondo.add(tituloLabel);

        fondoLabel = new JLabel(new ImageIcon(getClass().getResource("../../resources/img/curtain-openning-show-isolated-alpha-overlay-transparent-background-png.png")));
        fondoLabel.setBounds(0, 0, 889, 500);
        panelFondo.add(fondoLabel);

        barraProgreso = new JProgressBar();
        barraProgreso.setBounds(145, 490, 600, 20);
        barraProgreso.setBackground(Color.BLACK);
        barraProgreso.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        barraProgreso.setForeground(new Color(200, 0, 0));
        panelFondo.add(barraProgreso);

        setVisible(true);
        iniciarCarga();

        add(panelFondo);
    }

    public void iniciarCarga() 
    {
        new Thread(() -> 
        {
            try 
            {
                for (int i = 0; i <= 100; i++) 
                {
                    Thread.sleep(100);
                    final int porcentaje = i;

                    SwingUtilities.invokeLater(() -> 
                    {
                        porcentajeLabel.setText(porcentaje + "%");
                        barraProgreso.setValue(porcentaje);

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
                                dispose(); // Cerrar la ventana de carga
                                new PanelGenerador(); // Abrir PanelGenerador
                                break;
                            default:
                                break;
                        }
                    });
                }
            } catch (InterruptedException e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }).start();
    }
}
