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
        fotoUsuario = foto;

        for(Logro l : Logro.getCatalogo()){
            listaLogros.add(new Logro(l));
        }
    }

    public Usuario(String contrasena, String email, String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        fotoUsuario = "logoCineRoadmap.jpg";

        for(Logro l : Logro.getCatalogo()){
            listaLogros.add(new Logro(l));
        }
    }

    public void setnombreUsuario(String usuario) { this.nombreUsuario = usuario; }
    public void setEmail(String correo) { this.email = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setFoto(String foto) { fotoUsuario = foto; }
    
    public void setPeliculas (Pelicula pelicula) { this.listaPeliculas.add(pelicula); }
    public void setLogros(Logro logro) { listaLogros.add(logro); }

    public String getnombreUsuario() { return this.nombreUsuario; }
    public String getEmail() { return this.email; }
    public String getContrasena() { return this.contrasena; }
    public String getFoto() { return fotoUsuario; }
    public ArrayList<Pelicula> getPeliculas() { return listaPeliculas; }
    public ArrayList<Logro> getLogros() { return listaLogros; }
}