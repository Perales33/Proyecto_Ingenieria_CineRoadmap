package main.app;

import main.app.Modelo.Comunidad;
import main.app.util.PantallaCarga;
public class index
{
    public static void main(String[] args) 
    {
        new PantallaCarga();
        Comunidad.cargarUsuarios();
    }
}