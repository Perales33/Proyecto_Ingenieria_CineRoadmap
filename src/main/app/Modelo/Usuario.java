package main.app.Modelo;

import java.util.ArrayList;

public class Usuario
{
    private String contrasena;
    private String email;
    private String nombreUsuario;
    private String fotoUsuario;

    private ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
    private ArrayList<Reto> listaRetos = new ArrayList<>();
    private ArrayList<Logro> listaLogros = new ArrayList<>();

    public Usuario(String contrasena, String email, String nombreUsuario, String foto)
    {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.fotoUsuario = foto;

        // Catálogo de logros (copia por usuario)
        for (Logro l : Logro.getCatalogo()) {
            listaLogros.add(new Logro(l));
        }
    }

    public Usuario(String contrasena, String email, String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.fotoUsuario = "logoCineRoadmap.jpg";

        for (Logro l : Logro.getCatalogo()) {
            listaLogros.add(new Logro(l));
        }
    }

    // =========================
    // SETTERS básicos
    // =========================
    public void setnombreUsuario(String usuario) { this.nombreUsuario = usuario; }
    public void setEmail(String correo) { this.email = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setFoto(String foto) { this.fotoUsuario = foto; }

    // Películas / Logros
    public void setPeliculas(Pelicula pelicula) { this.listaPeliculas.add(pelicula); }
    public void setLogros(Logro logro) { this.listaLogros.add(logro); }

    // =========================
    // RETOS (nuevo y necesario)
    // =========================
    public ArrayList<Reto> getRetos() {
        return listaRetos;
    }

    /** Añade un reto evitando duplicados por id. Devuelve true si lo añadió. */
    public boolean addReto(Reto reto) {
        if (reto == null) return false;

        for (Reto r : listaRetos) {
            if (r.getIdReto() == reto.getIdReto()) {
                return false; // ya existe
            }
        }
        listaRetos.add(reto);
        return true;
    }

    /** Sobrescribe la lista completa (por si carga desde BD en el futuro). */
    public void setRetos(ArrayList<Reto> retos) {
        this.listaRetos = (retos != null) ? retos : new ArrayList<>();
    }

    public boolean tieneReto(int idReto) {
        for (Reto r : listaRetos) {
            if (r.getIdReto() == idReto) return true;
        }
        return false;
    }

    // =========================
    // GETTERS básicos
    // =========================
    public String getnombreUsuario() { return this.nombreUsuario; }
    public String getEmail() { return this.email; }
    public String getContrasena() { return this.contrasena; }
    public String getFoto() { return this.fotoUsuario; }

    public ArrayList<Pelicula> getPeliculas() { return listaPeliculas; }
    public ArrayList<Logro> getLogros() { return listaLogros; }
}
