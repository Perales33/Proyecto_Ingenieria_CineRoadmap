package main.app.Modelo;

public class Reto
{
    private int idReto;
    private String nombreReto;
    private boolean estadoReto;

    void setEstado(boolean estado) { this.estadoReto = estado; }

    public String getnombreReto() { return this.nombreReto; }
    public int getID() { return this.idReto; }
    public boolean getEstado() { return this.estadoReto; }
}