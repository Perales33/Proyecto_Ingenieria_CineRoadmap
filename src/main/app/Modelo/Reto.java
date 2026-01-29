package main.app.Modelo;

import java.time.LocalDate;

public class Reto 
{

    // Tipos de retos
    public enum TipoReto { DIARIO, SEMANAL, TEMATICO }
    
    // Estado de los retos
    public enum Estado { DISPONIBLE, ACEPTADO, COMPLETADO, EXPIRADO }

    // Atributos
    private int idReto;
    private String nombreReto;
    private String descripcion;
    private TipoReto tipo;
    private Estado estado;

    // Progreso: por ejemplo "ver 3 películas" => objetivo=3, actual=1
    private int progresoActual;
    private int progresoObjetivo;

    // Ventana de validez
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Reto(int idReto, String nombreReto, String descripcion, TipoReto tipo, int progresoObjetivo, LocalDate fechaInicio, LocalDate fechaFin) 
    {
        this.idReto = idReto;
        this.nombreReto = nombreReto;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.progresoObjetivo = progresoObjetivo;
        this.progresoActual = 0;
        this.estado = Estado.DISPONIBLE;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters
    public int getIdReto() { return idReto; }
    public String getNombreReto() { return nombreReto; }
    public String getDescripcion() { return descripcion; }
    public TipoReto getTipo() { return tipo; }
    public Estado getEstado() { return estado; }
    public int getProgresoActual() { return progresoActual; }
    public int getProgresoObjetivo() { return progresoObjetivo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }

    // Lógica simple 
    // (
    //      Aceptar = Ver reto como aceptado; 
    //      IncrementarProgreso = Avanzar el estado del reto; 
    //      estaVigente = Comprobar que no se ha completado la fecha del reto; 
    //      ActualzarEstadoPorFecha = Hacer que el reto pase a estar expirado
    // )
    
    public void aceptar() 
    {
        if (estado == Estado.DISPONIBLE) estado = Estado.ACEPTADO;
    }

    public void incrementarProgreso(int incremento) 
    {
        if (estado != Estado.ACEPTADO) return;
        progresoActual += incremento;
        if (progresoActual >= progresoObjetivo) 
        {
            progresoActual = progresoObjetivo;
            estado = Estado.COMPLETADO;
        }
    }

    public boolean estaVigente(LocalDate hoy) 
    {
        return (hoy.isEqual(fechaInicio) || hoy.isAfter(fechaInicio))
            && (hoy.isEqual(fechaFin) || hoy.isBefore(fechaFin));
    }

    public void actualizarEstadoPorFecha(LocalDate hoy) 
    {
        if (estado != Estado.COMPLETADO && !estaVigente(hoy)) 
        {
            estado = Estado.EXPIRADO;
        }
    }
}
