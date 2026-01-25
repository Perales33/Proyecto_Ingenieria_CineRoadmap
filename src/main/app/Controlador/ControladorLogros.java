package main.app.Controlador;

import main.app.Modelo.Usuario;
import main.app.Modelo.Logro;

public class ControladorLogros {

    public static void actualizarLogros(Usuario u) {
        int totalPeliculas = u.getPeliculas().size();
        int totalLogrosCompletados = 0;

        // Actualizar logros de películas
        for(Logro l : u.getLogros()) {

            if(l.getNombreReto().contains("películas")) {
                l.setActual(totalPeliculas);
            }

            if(l.isCompleto()) {
                totalLogrosCompletados++;
            }
        }

        // Actualizar logros tipo "Completa 3 retos"
        for(Logro l : u.getLogros()) {
            if(l.getNombreReto().contains("3 retos")) {
                l.setActual(totalLogrosCompletados);
            }
        }
    }
}
