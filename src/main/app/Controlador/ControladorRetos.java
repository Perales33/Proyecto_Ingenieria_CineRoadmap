package main.app.Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.app.Modelo.Reto;
import main.app.Modelo.Usuario;

public class ControladorRetos {

    // =========================
    // Catálogo fijo de retos (DEMO)
    // =========================
    private static final List<Reto> catalogo = new ArrayList<>();
    private static boolean catalogoInicializado = false;

    private static void initCatalogoSiHaceFalta() {
        if (catalogoInicializado) return;

        LocalDate hoy = LocalDate.now();

        catalogo.add(new Reto(
                1,
                "Explora un género nuevo",
                "Ve una película de un género que no hayas visto esta semana.",
                Reto.TipoReto.DIARIO,
                1,
                hoy,
                hoy.plusDays(1)
        ));

        catalogo.add(new Reto(
                2,
                "Semana cinéfila",
                "Completa 3 películas esta semana.",
                Reto.TipoReto.SEMANAL,
                3,
                hoy,
                hoy.plusDays(7)
        ));

        catalogo.add(new Reto(
                3,
                "Director desconocido",
                "Elige un director nuevo y mira una de sus películas.",
                Reto.TipoReto.TEMATICO,
                1,
                hoy,
                hoy.plusDays(30)
        ));

        catalogoInicializado = true;
    }

    // Clona un reto del catálogo para crear la instancia del usuario
    private static Reto clonarParaUsuario(Reto base) {
        return new Reto(
                base.getIdReto(),
                base.getNombreReto(),
                base.getDescripcion(),
                base.getTipo(),
                base.getProgresoObjetivo(),
                base.getFechaInicio(),
                base.getFechaFin()
        );
    }

    // =========================
    // Retos para pintar (cruzados con los del usuario)
    // =========================
    public static List<Reto> obtenerRetosParaUsuario(Usuario usuario) {
        initCatalogoSiHaceFalta();
        LocalDate hoy = LocalDate.now();

        List<Reto> resultado = new ArrayList<>();

        for (Reto base : catalogo) {
            Reto retoUsuario = null;

            // ¿El usuario ya tiene este reto?
            if (usuario != null) {
                for (Reto r : usuario.getRetos()) {
                    r.actualizarEstadoPorFecha(hoy);
                    if (r.getIdReto() == base.getIdReto()) {
                        retoUsuario = r;
                        break;
                    }
                }
            }

            // Si lo tiene, devolvemos su instancia (con progreso/estado real)
            if (retoUsuario != null) {
                resultado.add(retoUsuario);
            } else {
                // Si no lo tiene, devolvemos una copia "limpia"
                Reto copia = clonarParaUsuario(base);
                copia.actualizarEstadoPorFecha(hoy);
                resultado.add(copia);
            }
        }

        return resultado;
    }

    // Mantengo este método por compatibilidad, pero use obtenerRetosParaUsuario(usuario)
    public static List<Reto> obtenerRetosDemo() {
        initCatalogoSiHaceFalta();
        return new ArrayList<>(catalogo);
    }

    // =========================
    // Aceptar reto (lo añade al usuario)
    // =========================
    public static boolean aceptarReto(Usuario usuario, Reto reto) {
        if (usuario == null || reto == null) return false;

        initCatalogoSiHaceFalta();
        LocalDate hoy = LocalDate.now();

        // No duplicar
        if (usuario.tieneReto(reto.getIdReto())) return false;

        // Buscar base en catálogo
        Reto base = null;
        for (Reto r : catalogo) {
            if (r.getIdReto() == reto.getIdReto()) { base = r; break; }
        }
        if (base == null) return false;

        // Crear instancia del usuario
        Reto retoUsuario = clonarParaUsuario(base);
        retoUsuario.actualizarEstadoPorFecha(hoy);
        if (retoUsuario.getEstado() == Reto.Estado.EXPIRADO) return false;

        retoUsuario.aceptar();

        // ✅ Esto es lo clave: queda guardado en el usuario
        return usuario.addReto(retoUsuario);
    }

    // =========================
    // Mis retos = lo que tiene el usuario
    // =========================
    public static List<Reto> getMisRetos(Usuario usuario) {
        List<Reto> lista = new ArrayList<>();
        if (usuario == null) return lista;

        LocalDate hoy = LocalDate.now();

        for (Reto r : usuario.getRetos()) {
            r.actualizarEstadoPorFecha(hoy);

            // Mostramos aceptados/completados/expirados (no DISPONIBLE)
            if (r.getEstado() != Reto.Estado.DISPONIBLE) {
                lista.add(r);
            }
        }

        return lista;
    }

    public static void sumarProgreso(Usuario usuario, int idReto, int delta) {
        if (usuario == null) return;

        for (Reto r : usuario.getRetos()) {
            if (r.getIdReto() == idReto) {
                r.incrementarProgreso(Math.max(0, delta));
                break;
            }
        }
    }

    public static void completar(Usuario usuario, int idReto) {
        if (usuario == null) return;

        for (Reto r : usuario.getRetos()) {
            if (r.getIdReto() == idReto) {
                int falta = r.getProgresoObjetivo() - r.getProgresoActual();
                if (falta > 0) r.incrementarProgreso(falta);
                break;
            }
        }
    }
}
