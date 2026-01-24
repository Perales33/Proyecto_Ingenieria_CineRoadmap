package main.app.Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.app.Modelo.Reto;
import main.app.Modelo.Usuario;

public class ControladorRetos {

    // Demo simple en memoria: retos aceptados por usuario (por email)
    private static final List<ItemMisRetos> misRetos = new ArrayList<>();

    private static class ItemMisRetos {
        String email;
        Reto reto;

        ItemMisRetos(String email, Reto reto) {
            this.email = email;
            this.reto = reto;
        }
    }

    // =========================
    // Retos disponibles (DEMO)
    // =========================
    public static List<Reto> obtenerRetosDemo() {
        LocalDate hoy = LocalDate.now();

        List<Reto> retos = new ArrayList<>();

        // Diario: válido hoy
        retos.add(new Reto(
                1,
                "Explora un género nuevo",
                "Ve una película de un género que no hayas visto esta semana.",
                Reto.TipoReto.DIARIO,
                1,
                hoy,
                hoy
        ));

        // Semanal: válido 7 días
        retos.add(new Reto(
                2,
                "Semana cinéfila",
                "Completa 3 películas esta semana.",
                Reto.TipoReto.SEMANAL,
                3,
                hoy,
                hoy.plusDays(7)
        ));

        // Temático: válido 30 días
        retos.add(new Reto(
                3,
                "Director desconocido",
                "Elige un director nuevo y mira una de sus películas.",
                Reto.TipoReto.TEMATICO,
                1,
                hoy,
                hoy.plusDays(30)
        ));

        return retos;
    }

    // =========================
    // MIS RETOS (usuario)
    // =========================
    public static boolean aceptarReto(Usuario usuario, Reto reto) {
        if (usuario == null || reto == null) return false;

        // Actualiza estado por fecha antes de aceptar
        reto.actualizarEstadoPorFecha(LocalDate.now());
        if (reto.getEstado() == Reto.Estado.EXPIRADO) return false;

        // Evitar duplicado activo del mismo reto
        for (ItemMisRetos it : misRetos) {
            if (it.email.equalsIgnoreCase(usuario.getEmail())
                    && it.reto.getIdReto() == reto.getIdReto()
                    && (it.reto.getEstado() == Reto.Estado.ACEPTADO || it.reto.getEstado() == Reto.Estado.DISPONIBLE)) {
                return false;
            }
        }

        // Marcar como aceptado y guardarlo
        reto.aceptar();
        misRetos.add(new ItemMisRetos(usuario.getEmail(), reto));
        return true;
    }

    public static List<Reto> getMisRetos(Usuario usuario) {
        List<Reto> lista = new ArrayList<>();
        if (usuario == null) return lista;

        LocalDate hoy = LocalDate.now();

        for (ItemMisRetos it : misRetos) {
            if (it.email.equalsIgnoreCase(usuario.getEmail())) {
                it.reto.actualizarEstadoPorFecha(hoy);
                // Solo mostramos aceptados/completados/expirados en “Mis Retos”
                if (it.reto.getEstado() != Reto.Estado.DISPONIBLE) {
                    lista.add(it.reto);
                }
            }
        }
        return lista;
    }

    public static void sumarProgreso(Usuario usuario, int idReto, int delta) {
        if (usuario == null) return;

        for (ItemMisRetos it : misRetos) {
            if (it.email.equalsIgnoreCase(usuario.getEmail()) && it.reto.getIdReto() == idReto) {
                it.reto.incrementarProgreso(Math.max(0, delta));
                break;
            }
        }
    }

    public static void completar(Usuario usuario, int idReto) {
        if (usuario == null) return;

        for (ItemMisRetos it : misRetos) {
            if (it.email.equalsIgnoreCase(usuario.getEmail()) && it.reto.getIdReto() == idReto) {
                // Forzamos a completado llevándolo al objetivo
                int falta = it.reto.getProgresoObjetivo() - it.reto.getProgresoActual();
                if (falta > 0) it.reto.incrementarProgreso(falta);
                break;
            }
        }
    }
}
