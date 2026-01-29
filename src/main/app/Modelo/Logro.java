package main.app.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Logro 
{
    // Atributos
    private String nombreReto;
    private String descripcion;
    private boolean completo;
    private int actual;
    private int objetivo;
    private Insignia insignia;

    // Constructor
    public Logro(String nombre, String desc, int objetivo, Insignia ins){
        this.nombreReto = nombre;
        this.descripcion = desc;
        this.objetivo = objetivo;
        this.insignia = ins;
        this.actual = 0;
        this.completo = false;
    }

    // Constructor sobrecargado para copiar todos los retos a todos los usuarios
    public Logro(Logro l){
        this.nombreReto = l.nombreReto;
        this.descripcion = l.descripcion;
        this.objetivo = l.objetivo;
        this.insignia = l.insignia;
        this.actual = 0;
        this.completo = false;
    }

    // Getters
    public String getNombreReto() { return nombreReto; }
    public String getDescripcion() { return descripcion; }
    public boolean isCompleto() { return completo; }
    public int getActual() { return actual; }
    public int getObjetivo() { return objetivo; }
    public Insignia getInsignia() { return insignia; }

    // Setters
    public void setActual(int valor){
        this.actual = valor;
        this.completo = actual >= objetivo;
    }

    // Actualizar el estado del logro
    public void incrementar(int valor){
        setActual(actual + valor);
    }

    // Catálogo de logros
    public static List<Logro> getCatalogo() 
    {
        // Obtener todas las insignias
        Insignia[] insignias = Insignia.getCatalogo();
        
        // Catálogo de logros
        List<Logro> logros = new ArrayList<>();

        logros.add(new Logro("Ver 5 películas", "Completa viendo 5 películas", 5, insignias[0]));
        logros.add(new Logro("Ver 10 películas", "Completa viendo 10 películas", 10, insignias[1]));
        logros.add(new Logro("Completa 3 retos", "Completa 3 logros en la plataforma", 3, insignias[2]));
        logros.add(new Logro("Explorador de Ciencia Ficción", "Ve 3 películas de Ciencia Ficción", 3, insignias[3]));
        logros.add(new Logro("Fan de la Comedia", "Ve 3 películas de Comedia", 3, insignias[4]));
        logros.add(new Logro("Amante del Drama", "Ve 3 películas de Drama", 3, insignias[5]));
        logros.add(new Logro("Aventura sin límites", "Ve 3 películas de Aventura", 3, insignias[6]));
        logros.add(new Logro("Seguidor de Denis Villeneuve", "Ve 2 películas dirigidas por Denis Villeneuve", 2, insignias[7]));
        logros.add(new Logro("Seguidor de Christopher Nolan", "Ve 2 películas dirigidas por Christopher Nolan", 2, insignias[8]));
        logros.add(new Logro("Seguidor de Greta Gerwig", "Ve 2 películas dirigidas por Greta Gerwig", 2, insignias[9]));
        logros.add(new Logro("Fan de Timothée Chalamet", "Ve 2 películas con Timothée Chalamet", 2, insignias[10]));
        logros.add(new Logro("Fan de Zendaya", "Ve 2 películas con Zendaya", 2, insignias[11]));
        logros.add(new Logro("Fan de Margot Robbie", "Ve 2 películas con Margot Robbie", 2, insignias[12]));
        logros.add(new Logro("Cinéfilo del 2023", "Ve 3 películas estrenadas en 2023", 3, insignias[13]));
        logros.add(new Logro("Cinéfilo del 2022", "Ve 3 películas estrenadas en 2022", 3, insignias[14]));
        logros.add(new Logro("Cinéfilo del 2021", "Ve 3 películas estrenadas en 2021", 3, insignias[15]));

        return logros; // Devolver los logros cargados
    }
}
