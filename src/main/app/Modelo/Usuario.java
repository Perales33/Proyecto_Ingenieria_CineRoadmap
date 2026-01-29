package main.app.Modelo;

import java.util.ArrayList;

public class Usuario
{
    // Atributos
    private String contrasena;
    private String email;
    private String nombreUsuario;
    private String fotoUsuario;

    private ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
    private ArrayList<Reto> listaRetos = new ArrayList<>();
    private ArrayList<Logro> listaLogros = new ArrayList<>();

    // Constructor con la imagen puesta desde el usuario (Solo sirve para los ejemplos)
    public Usuario(String contrasena, String email, String nombreUsuario, String foto)
    {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.fotoUsuario = foto;

        // Catálogo de logros (precargado a cada usuario)
        for (Logro l : Logro.getCatalogo()) 
        {
            listaLogros.add(new Logro(l));
        }
    }

    // Constructor con la imagen (Usuarios recien registrados)
    public Usuario(String contrasena, String email, String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.fotoUsuario = "logoCineRoadmap.jpg";

        // Catálogo de logros (precargado a cada usuario)
        for (Logro l : Logro.getCatalogo()) 
        {
            listaLogros.add(new Logro(l));
        }
    }

    // Setters
    public void setnombreUsuario(String usuario) { this.nombreUsuario = usuario; }
    public void setEmail(String correo) { this.email = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setFoto(String foto) { this.fotoUsuario = foto; }
    public void setPeliculas(Pelicula pelicula) { this.listaPeliculas.add(pelicula); }
    public void setLogros(Logro logro) { this.listaLogros.add(logro); }
    public ArrayList<Reto> getRetos() { return listaRetos; }

    // Añade los retos al usuario cuando los acepta
    public boolean addReto(Reto reto) 
    {
        if (reto == null) return false;

        for (Reto r : listaRetos) {
            if (r.getIdReto() == reto.getIdReto()) 
            {
                return false; // ya existe
            }
        }
        listaRetos.add(reto);
        return true;
    }

    // Sobreescribe la lista de retos por si existiera una base de datos
    public void setRetos(ArrayList<Reto> retos) { this.listaRetos = (retos != null) ? retos : new ArrayList<>(); }

    // Comprobar si tiene reto o no el usuario para asignarselo en base a su ID y no repetir un reto
    public boolean tieneReto(int idReto) 
    {
        for (Reto r : listaRetos) 
        {
            if (r.getIdReto() == idReto) return true;
        }
        return false;
    }

    // Getters
    public String getnombreUsuario() { return this.nombreUsuario; }
    public String getEmail() { return this.email; }
    public String getContrasena() { return this.contrasena; }
    public String getFoto() { return this.fotoUsuario; }
    public ArrayList<Pelicula> getPeliculas() { return listaPeliculas; }
    public ArrayList<Logro> getLogros() { return listaLogros; }
}
