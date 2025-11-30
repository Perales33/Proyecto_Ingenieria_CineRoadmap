package main.app.Modelo;

import java.util.ArrayList;

public class Calificacion
{
    private int calificacion;
    private Pelicula pelicula;
    private Usuario usuario;
    private static ArrayList<Calificacion> listaCalificaciones = new ArrayList<>();

    public Calificacion(int calif, Pelicula pel, Usuario us)
    {
        calificacion = calif;
        pelicula = pel;
        usuario = us;
    }

    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }
    public Pelicula getPelicula() { return pelicula; }
    public Usuario getUsuario() { return usuario; }

    public static ArrayList<Calificacion> getListaCalificaciones() { return listaCalificaciones; }
}
