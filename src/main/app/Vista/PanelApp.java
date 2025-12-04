package main.app.Vista;

import javax.swing.*;
import java.awt.*;

import main.app.Controlador.*;
import main.app.util.*;

public class PanelApp 
{
    private static JLabel tituloMain;
    public static JLabel getTitutloMain()
    {
        return tituloMain;
    }
    
    public static JPanel crearPanelInicio()
    {
        JPanel panelCental = new JPanel(new BorderLayout());
        JPanel banner = PanelBanner.crearBanner();
        panelCental.add(banner, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        bag.gridwidth = 2; bag.gridy = 0; bag.gridx = 0;
        tituloMain = new JLabel();
        ControladorApp.actualizarTitulo(tituloMain);
        Estilos.estilosTitulosLRC(tituloMain);
        panelContenido.add(tituloMain, bag);

        panelCental.add(panelContenido, BorderLayout.CENTER);

        return panelCental;
    }
}
