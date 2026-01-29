package main.app.Controlador;

import main.app.Vista.PanelGenerador;

public class ControladorApp 
{
    // Botón "Únete"
    public static void botonComunidadPresionado()
    {
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Comunidad");
    }

    // Botón "Ir a sección logros" 
    public static void botonLogroPresionado()
    {
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "LogrosInsignias");
    }
}
