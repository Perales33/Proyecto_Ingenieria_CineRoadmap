package main.app.Controlador;

import main.app.Modelo.*;

public class ControladorCalificacion 
{
    public static String guardarCalificacion(Pelicula pelicula, int nota)
    {
        if(Usuario.getUsuarios() == null || Usuario.getUsuarios().isEmpty())
        {
            return "No hay usuarios registrados";
        }
        
        if(nota == 0)
        {
            return "Valoré la película de 1 a 5";
        }

        if(pelicula == null)
        {
            return "Seleccione una película para valorar";
        }
        
        if (nota < 1 || nota > 5)
        {
            return "La valoración de la película debe estár entre 1 y 5";
        }

        try 
        {
            Usuario usuario = ControladorUsuario.getUsuarioActivo();
            boolean calificacionExistente = false;

            for(Calificacion c : Calificacion.getListaCalificaciones())
            {
                if(c.getUsuario().equals(usuario) && c.getPelicula().equals(pelicula))
                {
                    c.setCalificacion(nota);
                    calificacionExistente = true;
                    break;
                }
            }
            
            if(!calificacionExistente)
            {
                Calificacion cNueva = new Calificacion(nota, pelicula, usuario);
                Calificacion.getListaCalificaciones().add(cNueva);
            }

            if(!usuario.getPeliculas().contains(pelicula))
            {
                usuario.setPeliculas(pelicula);
            }
            return null;
        } 
        catch (Exception e) 
        {
            return e.getMessage();
        }
    }

    public static String crearValoraciones(Usuario u)
    {
        if (u == null || Calificacion.getListaCalificaciones().isEmpty()) { return "Actualmente no hay calificaciones"; }

        StringBuilder texto = new StringBuilder();

        for(Calificacion c : Calificacion.getListaCalificaciones())
        {
            if(c.getUsuario().equals(u))
            {
                texto.append(c.getPelicula().getnombrePelicula()).append(" (Nota: ").append(c.getCalificacion()).append(" / 5)\n");
            }
            
        }

        if(texto.length() == 0)
        {
            return "Actualmente no hay calificaciones";
        }
        return texto.toString();
    }
}
