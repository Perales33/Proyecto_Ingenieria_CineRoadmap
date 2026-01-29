package main.app.Modelo;

import java.util.ArrayList;
import java.util.Date;

public class Calificacion
{
    // Atributos
    private int calificacion;
    private Pelicula pelicula;
    private Usuario usuario;
    private Date fechaCalificacion;
    private static ArrayList<Calificacion> listaCalificaciones = new ArrayList<>();

    // Constructor
    public Calificacion(int calif, Pelicula pel, Usuario us)
    {
        calificacion = calif;
        pelicula = pel;
        usuario = us;
        fechaCalificacion = new Date();
    }

    // Getters y Setters
    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }
    public Pelicula getPelicula() { return pelicula; }
    public Usuario getUsuario() { return usuario; }
    public Date getFechaCalificacion() { return fechaCalificacion; }
    public static ArrayList<Calificacion> getListaCalificaciones() { return listaCalificaciones; }
}
