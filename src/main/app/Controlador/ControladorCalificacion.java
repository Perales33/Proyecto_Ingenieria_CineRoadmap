package main.app.Controlador;

import main.app.Modelo.*;

public class ControladorCalificacion 
{
    // Guardar las calificaciones cuando se indiquen la película y la nota (1 al 5)
    public static String guardarCalificacion(Pelicula pelicula, int nota)
    {
        // Comprobar si hay un usuario activo
        if(Comunidad.getUsuarios() == null || Comunidad.getUsuarios().isEmpty())
        {
            return "No hay usuarios registrados";
        }
        // Comprobar que la nota sea 0
        if(nota == 0)
        {
            return "Valoré la película de 1 a 5";
        }
        // Comprueba que la película sea seleccionada
        if(pelicula == null)
        {
            return "Seleccione una película para valorar";
        }
        // Comprueba que la nota sea entre 1 y 5
        if (nota < 1 || nota > 5)
        {
            return "La valoración de la película debe estár entre 1 y 5";
        }

        // Ejecutar el guardado de las películas, actualización de los logros y marcar las películas como vistas
        try
        {
            Usuario usuario = ControladorUsuario.getUsuarioActivo();
            boolean calificacionExistente = false;

            for(Calificacion c : Calificacion.getListaCalificaciones())
            {
                // Comprueba que la película ha sido ya califica
                if(c.getUsuario().equals(usuario) && c.getPelicula().equals(pelicula))
                {
                    c.setCalificacion(nota);
                    calificacionExistente = true;
                    break;
                }
            }
            
            // Si la calificación no existe se crea una nueva
            if(!calificacionExistente)
            {
                Calificacion cNueva = new Calificacion(nota, pelicula, usuario);
                Calificacion.getListaCalificaciones().add(cNueva);
            }

            // Actualizar la lista de películas vistas por el usuario
            if(!usuario.getPeliculas().contains(pelicula))
            {
                usuario.setPeliculas(pelicula);
            }

            ControladorLogros.actualizarLogros(usuario); // Actualizar los logros
            
            return null;
        } 
        catch (Exception e) 
        {
            return e.getMessage();
        }
    }

    // Observar si el usuario tiene o no calificaciones de películas
    public static String crearValoraciones(Usuario u)
    {
        // Comprueba que el usurio tenga calificaciones
        if (u == null || Calificacion.getListaCalificaciones().isEmpty()) { return "Actualmente no hay calificaciones"; }

        StringBuilder texto = new StringBuilder();

        // Recorre cada película para poder ver hay alguna
        for(Calificacion c : Calificacion.getListaCalificaciones())
        {
            if(c.getUsuario().equals(u))
            {
                texto.append(c.getPelicula().getnombrePelicula()).append(" (Nota: ").append(c.getCalificacion()).append(" / 5)\n");
            }
            
        }

        // En caso de no haber alguna, pone el mensaje siguiente
        if(texto.length() == 0)
        {
            return "Actualmente no hay calificaciones";
        }
        return texto.toString();
    }
}
