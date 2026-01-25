package main.app.Controlador;

import main.app.Modelo.*;
import java.time.LocalDate;

public class ControladorLogros {

    public void peliculaVista(Usuario usuario, Pelicula pelicula) {
        if (usuario == null || pelicula == null) return;

        usuario.setPeliculas(pelicula);

        LocalDate hoy = LocalDate.now();
        for (Reto r : usuario.getRetos()) {
            r.actualizarEstadoPorFecha(hoy);

            if (r.getEstado() == Reto.Estado.ACEPTADO && r.estaVigente(hoy)) {
                if (r.getIdReto() == 1) {
                    r.incrementarProgreso(1);
                }
            }
        }

        for (Logro l : usuario.getLogros()) {
            l.syncConReto();
        }
    }
}
}

