package main.app.Modelo;

import java.util.ArrayList;

public class Logro
{
    private String nombreLogro;
    private String descripcionLogro;
    private boolean isComplete;
    private Insignia insignia;

    public Logro(String nombre, String descripcion, Insignia insignia)
    {
        this.nombreLogro = nombre;
        this.descripcionLogro = descripcion;
        this.isComplete = false;
        this.insignia = insignia;
    }

    public void completar()
    {
        isComplete = true;
        insignia.desbloquear();
    }

    public String getnombreReto() { return this.nombreLogro; }
    public String getDescripcion() { return this.descripcionLogro; }
    public boolean getCompleto() { return this.isComplete; }
    public Insignia getInsignia() { return this.insignia; }
}


class LogroCantidad extends Logro
{
    private int objetivo;

    public LogroCantidad(String nombre, String descripcion, int objetivo, Insignia insignia)
    {
        super(nombre, descripcion, insignia);
        this.objetivo = objetivo;
    }
}

class LogroPelicula extends Logro {

    private String nombrePelicula;

    public LogroPelicula(String nombre, String descripcion, String nombrePelicula, Insignia insignia) {
        super(nombre, descripcion, insignia);
        this.nombrePelicula = nombrePelicula;
    }
}

class LogroGenero extends Logro {

    private String genero;
    private int objetivo;


    public LogroGenero(String nombre, String descripcion, String genero, int objetivo,Insignia insignia) {
        super(nombre, descripcion, insignia);
        this.genero = genero;
        this.objetivo = objetivo;
    }
}

class CatalogoLogros
{
    private static ArrayList<Logro> logros = new ArrayList<>();
    private static void cargarLogros()
    {
        logros.add(new LogroCantidad("Primeros pasos", "Evalúa 1 película", 1, new Insignia("/main/resources/img/primer.jpg")));
        logros.add(new LogroCantidad("Cinéfilo experto", "Evalúa 10 películas", 10, new Insignia("/main/resources/img/cinefiloexperto.jpg")));
        logros.add(new LogroGenero("Amante de la Ciencia Ficción", "Evalúa 3 películas de ciencia ficción", "Ciencia Ficción", 3,new Insignia("/main/resources/img/primer.jpg")));
        logros.add(new LogroPelicula("Clásico de acción", "Evalúa la película 12 Angry Men", "12 Angry Men", new Insignia("/main/resources/img/clasic.jpg")));
    }

    public static ArrayList<Logro> getLogros() { return logros; }
}