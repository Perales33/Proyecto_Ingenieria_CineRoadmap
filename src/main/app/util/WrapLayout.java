package main.app.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Component;

/**
 * WrapLayout extiende FlowLayout para permitir que los componentes se envuelvan
 * a una nueva línea cuando exceden el ancho disponible del contenedor.
 * Útil para paneles de tarjetas, imágenes o botones dinámicos.
 */
public class WrapLayout extends FlowLayout 
{
    // -------------------------
    // CONSTRUCTORES
    // -------------------------
    public WrapLayout() { super(); }                       // alineación por defecto (CENTER)
    public WrapLayout(int align) { super(align); }         // alineación personalizada
    public WrapLayout(int align, int hgap, int vgap)       // alineación + separaciones
    {
        super(align, hgap, vgap);
    }

    // -------------------------
    // PREFERRED SIZE
    // -------------------------
    @Override
    public Dimension preferredLayoutSize(Container target) 
    {
        return layoutSize(target, true);  // calcula usando tamaño preferido de los componentes
    }

    // -------------------------
    // MINIMUM SIZE
    // -------------------------
    @Override
    public Dimension minimumLayoutSize(Container target) 
    {
        return layoutSize(target, false); // calcula usando tamaño mínimo de los componentes
    }

    // -------------------------
    // CÁLCULO DEL LAYOUT
    // -------------------------
    private Dimension layoutSize(Container target, boolean preferred) 
    {
        synchronized (target.getTreeLock()) // sincroniza con el árbol de componentes 
        { 
            int targetWidth = target.getWidth();
            if (targetWidth == 0) targetWidth = Integer.MAX_VALUE; // ancho infinito si aún no se ha renderizado

            int hgap = getHgap();                 // espacio horizontal entre componentes
            int vgap = getVgap();                 // espacio vertical entre filas
            Insets insets = target.getInsets();   // márgenes del contenedor
            int maxWidth = targetWidth - (insets.left + insets.right + hgap*2);

            Dimension dim = new Dimension(0, 0);  // dimensión final calculada
            int rowWidth = 0, rowHeight = 0;      // ancho y alto de la fila actual

            // -------------------------
            // ITERAR COMPONENTES
            // -------------------------
            for (Component c : target.getComponents()) 
            {
                if (!c.isVisible()) continue;                // ignorar componentes invisibles
                Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();

                // Si el componente no cabe en la fila actual, iniciar nueva fila
                if (rowWidth + d.width > maxWidth) 
                {
                    dim.width = Math.max(dim.width, rowWidth);  // ancho máximo hasta ahora
                    dim.height += rowHeight + vgap;            // sumar altura de fila + separación
                    rowWidth = 0;
                    rowHeight = 0;
                }

                // Sumar componente actual a la fila
                rowWidth += d.width + hgap;
                rowHeight = Math.max(rowHeight, d.height); // altura máxima de la fila
            }

            // -------------------------
            // AJUSTE FINAL
            // -------------------------
            dim.width = Math.max(dim.width, rowWidth);         // asegurar ancho máximo
            dim.height += rowHeight + insets.top + insets.bottom; // sumar la última fila + márgenes

            return dim; // retornar dimensión total requerida
        }
    }
}
