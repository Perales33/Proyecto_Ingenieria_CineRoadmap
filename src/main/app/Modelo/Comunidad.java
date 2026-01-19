package main.app.Modelo;

import java.util.ArrayList;

public class Comunidad 
{
    private static ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    public static void setUsuarios (Usuario usuario) { listaUsuarios.add(usuario); }
    public static ArrayList<Usuario>  getUsuarios() { return listaUsuarios; }


        public static void cargarUsuarios() 
        {
            listaUsuarios.add(new Usuario("Pass!123", "juan@gmail.com", "Juan01", "usuario1.jpeg"));
            listaUsuarios.add(new Usuario("Clave@456", "ana@gmail.com", "AnaUser", "usuario2.jpeg"));
            listaUsuarios.add(new Usuario("Segura#789", "carlos@gmail.com", "Carlos", "usuario3.jpeg"));
            listaUsuarios.add(new Usuario("Admin$000", "lucia@gmail.com", "LuciaX", "usuario4.jpeg"));
            listaUsuarios.add(new Usuario("Qwerty%999", "mario@gmail.com", "Mario1", "usuario5.jpeg"));
            listaUsuarios.add(new Usuario("Cine&2024", "sofia@gmail.com", "SofiaM", "usuario6.jpeg"));
            listaUsuarios.add(new Usuario("Roadmap*88", "david@gmail.com", "DavidR", "usuario7.jpeg"));
            listaUsuarios.add(new Usuario("Pelis!321", "laura@gmail.com", "LauraG", "usuario8.jpeg"));
            listaUsuarios.add(new Usuario("Usuario@999", "pedro@gmail.com", "PedroS", "usuario9.jpeg"));
        }
}
