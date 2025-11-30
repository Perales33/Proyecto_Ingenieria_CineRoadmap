package app;

import javax.swing.*;
import java.awt.*;


public class PanelApp 
{
    protected static JPanel crearPanelInicio()
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
        PanelGenerador.tituloMain = new JLabel();
        actualizarTitulo();
        Estilos.estilosTitulosLRC(PanelGenerador.tituloMain);
        panelContenido.add(PanelGenerador.tituloMain, bag);

        panelCental.add(panelContenido, BorderLayout.CENTER);

        return panelCental;
    }
    
    protected static void actualizarTitulo()
    {
        if(Controlador.getUsuarioActivo() == null)
        {
            PanelGenerador.tituloMain.setText("Bienvenido");
        }
        else
        {
            PanelGenerador.tituloMain.setText("Bienvenido " + Controlador.getUsuarioActivo().getnombreUsuario());
        }
    }
}
