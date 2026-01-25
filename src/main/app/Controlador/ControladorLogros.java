package main.app.Controlador;

import main.app.Modelo.Usuario;
import main.app.Modelo.Logro;
import main.app.Modelo.Pelicula;

public class ControladorLogros {

    public static void actualizarLogros(Usuario u) {
        int totalPeliculas = u.getPeliculas().size();
        int totalLogrosCompletados = 0;

        for(Logro l : u.getLogros()) {

            // --- Logros generales ---
            if(l.getNombreReto().contains("películas")) {
                l.setActual(totalPeliculas);
            }

            // --- Logros por género ---
            if(l.getNombreReto().contains("Ciencia Ficción")) {
                l.setActual(countPorGenero(u, "Ciencia Ficción"));
            }
            if(l.getNombreReto().contains("Comedia")) {
                l.setActual(countPorGenero(u, "Comedia"));
            }
            if(l.getNombreReto().contains("Drama")) {
                l.setActual(countPorGenero(u, "Drama"));
            }
            if(l.getNombreReto().contains("Aventura")) {
                l.setActual(countPorGenero(u, "Aventura"));
            }

            // --- Logros por director ---
            if(l.getNombreReto().contains("Denis Villeneuve")) {
                l.setActual(countPorDirector(u, "Denis Villeneuve"));
            }
            if(l.getNombreReto().contains("Christopher Nolan")) {
                l.setActual(countPorDirector(u, "Christopher Nolan"));
            }
            if(l.getNombreReto().contains("Greta Gerwig")) {
                l.setActual(countPorDirector(u, "Greta Gerwig"));
            }

            // --- Logros por actor ---
            if(l.getNombreReto().contains("Timothée Chalamet")) {
                l.setActual(countPorActor(u, "Timothée Chalamet"));
            }
            if(l.getNombreReto().contains("Zendaya")) {
                l.setActual(countPorActor(u, "Zendaya"));
            }
            if(l.getNombreReto().contains("Margot Robbie")) {
                l.setActual(countPorActor(u, "Margot Robbie"));
            }

            // --- Logros por año ---
            if(l.getNombreReto().contains("2023")) {
                l.setActual(countPorAnio(u, 2023));
            }
            if(l.getNombreReto().contains("2022")) {
                l.setActual(countPorAnio(u, 2022));
            }
            if(l.getNombreReto().contains("2021")) {
                l.setActual(countPorAnio(u, 2021));
            }

            if(l.isCompleto()) totalLogrosCompletados++;
        }

        // Logro "Completa 3 retos"
        for(Logro l : u.getLogros()) {
            if(l.getNombreReto().contains("3 retos")) {
                l.setActual(totalLogrosCompletados);
            }
        }
    }

    // Funciones auxiliares para contar
    private static int countPorGenero(Usuario u, String genero) {
        int count = 0;
        for(Pelicula p : u.getPeliculas()) {
            if(p.getGeneros().contains(genero)) count++;
        }
        return count;
    }

    private static int countPorDirector(Usuario u, String director) {
        int count = 0;
        for(Pelicula p : u.getPeliculas()) {
            if(p.getDirectores().contains(director)) count++;
        }
        return count;
    }

    private static int countPorActor(Usuario u, String actor) {
        int count = 0;
        for(Pelicula p : u.getPeliculas()) {
            if(p.getActores().contains(actor)) count++;
        }
        return count;
    }

    private static int countPorAnio(Usuario u, int anio) {
        int count = 0;
        for(Pelicula p : u.getPeliculas()) {
            if(p.getAnio() == anio) count++;
        }
        return count;
    }

}
