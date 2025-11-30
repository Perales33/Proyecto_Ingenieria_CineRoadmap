package app;

import javax.swing.*;
import java.awt.*;


public class panelApp 
{
    protected static JPanel crearPanelInicio()
    {
        JPanel panelCental = new JPanel(new BorderLayout());

        JPanel banner = panelBanner.crearBanner();
        panelCental.add(banner, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints bag = new GridBagConstraints();
        bag.insets = new Insets(10,10, 10, 10);
        bag.fill = GridBagConstraints.HORIZONTAL; 

        bag.gridwidth = 2; bag.gridy = 0; bag.gridx = 0;
        panelGenerador.tituloMain = new JLabel();
        actualizarTitulo();
        Estilos.estilosTitulosLRC(panelGenerador.tituloMain);
        panelContenido.add(panelGenerador.tituloMain, bag);

        panelCental.add(panelContenido, BorderLayout.CENTER);

        return panelCental;
    }
    
    protected static void actualizarTitulo()
    {
        if(Controlador.getUsuarioActivo() == null)
        {
            panelGenerador.tituloMain.setText("Bienvenido");
        }
        else
        {
            panelGenerador.tituloMain.setText("Bienvenido " + Controlador.getUsuarioActivo().getnombreUsuario());
        }
    }
}
