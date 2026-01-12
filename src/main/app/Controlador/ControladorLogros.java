package main.app.Controlador;

import main.app.Modelo.*;

import java.util.ArrayList;

public class ControladorLogros 
{
    public static void evaluar(Usuario usuario)
    {
        ArrayList<Logro> catalogo = CatalogoLogros.getLogros();
        int totalPeliculasEvaluadas = usuario.getPeliculas().size();

        for(Logro l : catalogo)
        {
            // Logros por cantidad de películas
            if(l instanceof LogroCantidad)
            {
                LogroCantidad lc = (LogroCantidad) l;
                if(!lc.getCompleto() && totalPeliculasEvaluadas >= lc.objetivo)
                {
                    lc.completar();
                }
            }

            // Logros por género
            if(l instanceof LogroGenero)
            {
                LogroGenero lg = (LogroGenero) l;
                long count = usuario.getPeliculas().stream()
                                .filter(p -> p.getGeneros().contains(lg.genero))
                                .count();
                if(!lg.getCompleto() && count >= lg.objetivo)
                {
                    lg.completar();
                }
            }

            // Logros por película específica
            if(l instanceof LogroPelicula)
            {
                LogroPelicula lp = (LogroPelicula) l;
                boolean evaluada = usuario.getPeliculas().stream()
                                    .anyMatch(p -> p.getnombrePelicula().equals(lp.nombrePelicula));
                if(!lp.getCompleto() && evaluada)
                {
                    lp.completar();
                }
            }
        }
    }
}
