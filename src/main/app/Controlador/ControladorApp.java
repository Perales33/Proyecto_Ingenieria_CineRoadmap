package main.app.Controlador;

import main.app.Vista.PanelGenerador;

public class ControladorApp 
{
    public static void botonComunidadPresionado()
    {
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Comunidad");
    }
}
