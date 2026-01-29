package main.app.Vista;

import javax.swing.*;
import java.awt.*;

//Panel Swing con imagen de fondo escalada automáticamente.
// Permite superponer componentes encima (labels, botones, etc.).
public class PanelConFondo extends JPanel {

    private Image fondo;

    public PanelConFondo(LayoutManager layout) 
    {
        super(layout);
        setOpaque(false); // Permite que se vea correctamente el fondo
    }

    // Asigna la imagen de fondo y fuerza repintado.
    public void setFondo(Image imagen) 
    {
        this.fondo = imagen;
        repaint();
    }

    // Permite recuperar la imagen actual (si fuese necesario).
    public Image getFondo() 
    {
        return fondo;
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        if (fondo != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            // Activar antialias para mejor calidad visual
            g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

            // Escala automática al tamaño del panel
            g2.drawImage(
                fondo,
                0, 0,
                getWidth(),
                getHeight(),
                this
            );

            g2.dispose();
        }
    }
}
