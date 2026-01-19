package main.app;

import main.app.util.PantallaCarga;
import main.app.Modelo.Comunidad;
public class index
{
    public static void main(String[] args) 
    {
        new PantallaCarga();
        Comunidad.cargarUsuarios();
    }
}