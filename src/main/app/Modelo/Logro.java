package main.app.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Logro {
    private String nombreReto;
    private String descripcion;
    private boolean completo;
    private int actual;
    private int objetivo;
    private Insignia insignia;

    public Logro(String nombre, String desc, int objetivo, Insignia ins){
        this.nombreReto = nombre;
        this.descripcion = desc;
        this.objetivo = objetivo;
        this.insignia = ins;
        this.actual = 0;
        this.completo = false;
    }

    // GETTERS Y SETTERS
    public String getNombreReto() { return nombreReto; }
    public String getDescripcion() { return descripcion; }
    public boolean isCompleto() { return completo; }
    public int getActual() { return actual; }
    public int getObjetivo() { return objetivo; }
    public Insignia getInsignia() { return insignia; }

    public void setActual(int valor){
        this.actual = valor;
        this.completo = actual >= objetivo;
    }

    public void incrementar(int valor){
        setActual(actual + valor);
    }

    // Catálogo de logros con insignias asignadas
    public static List<Logro> getCatalogo() {
        Insignia[] insignias = Insignia.getCatalogo();
        List<Logro> logros = new ArrayList<>();
        logros.add(new Logro("Ver 5 películas", "Completa viendo 5 películas", 5, insignias[0]));
        logros.add(new Logro("Ver 10 películas", "Completa viendo 10 películas", 10, insignias[1]));
        logros.add(new Logro("Completa 3 retos", "Completa 3 retos en la plataforma", 3, insignias[2]));
        return logros;
    }
}
