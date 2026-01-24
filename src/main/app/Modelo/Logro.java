package main.app.Modelo;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ==========================================================
 * ARCHIVO CONJUNTO: LOGROS + INSIGNIAS
 * ==========================================================
 * Objetivo:
 * - Tener en un solo archivo todo lo necesario para gestionar logros.
 * - Se usa en un proyecto conjunto (repo) sin necesitar mil clases sueltas.
 *
 * Regla Java:
 * - Solo 1 clase puede ser public en este archivo.
 * - Como Logro es public, el archivo debe llamarse Logro.java
 */


/* ==========================================================
   1) CLASE BASE LOGRO
   ==========================================================
   Qué representa:
   - Un objetivo que el usuario puede completar (ej: evaluar pelis).
   - Tiene nombre, descripción, estado (completo o no) y una insignia asociada.

   Qué aporta además:
   - Progreso (actual / objetivo) para poder mostrar % en UI.
   - Método completar() que desbloquea la insignia.
   - Método procesarEvento() que las subclases sobrescriben.
   ========================================================== */
public class Logro {

    // Auto-id simple para que cada logro tenga un identificador único
    private static int AUTO_ID = 1;

    // Datos básicos del logro
    private final int id;
    private String nombreLogro;
    private String descripcionLogro;
    private boolean completo;
    private Insignia insignia;

    // Progreso genérico (sirve para logros cuantitativos)
    protected int progresoActual;
    protected int progresoObjetivo;

    // Timestamps útiles (creación y finalización)
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCompletado;

    public Logro(String nombre, String descripcion, Insignia insignia) {
        // Validación mínima (evita logros rotos)
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Nombre vacío");
        if (descripcion == null || descripcion.trim().isEmpty())
            throw new IllegalArgumentException("Descripción vacía");
        if (insignia == null)
            throw new IllegalArgumentException("Insignia nula");

        // Inicialización de campos
        this.id = AUTO_ID++;
        this.nombreLogro = nombre;
        this.descripcionLogro = descripcion;
        this.insignia = insignia;
        this.completo = false;

        // Progreso base
        this.progresoActual = 0;
        this.progresoObjetivo = 0;

        // Fecha creación
        this.fechaCreacion = LocalDateTime.now();
    }

    /**
     * Marca el logro como completado y desbloquea la insignia asociada.
     * Este es el núcleo del sistema.
     */
    public void completar() {
        if (!completo) {
            completo = true;
            fechaCompletado = LocalDateTime.now();
            insignia.desbloquear();
        }
    }

    /**
     * Resetear logro (por si hay botón reset / reinicio de perfil).
     */
    public void resetear() {
        completo = false;
        progresoActual = 0;
        fechaCompletado = null;
        insignia.bloquear();
    }

    /**
     * Entrada “genérica” por eventos:
     * - En Logro base no hace nada.
     * - Las subclases (Cantidad, Género, Película) sí lo implementan.
     */
    public boolean procesarEvento(EventoLogro evento) {
        return false;
    }

    // ---------- Getters básicos ----------
    public int getId() { return id; }
    public String getNombreLogro() { return nombreLogro; }
    public String getDescripcion() { return descripcionLogro; }
    public boolean getCompleto() { return completo; }
    public Insignia getInsignia() { return insignia; }
    public int getProgresoActual() { return progresoActual; }
    public int getProgresoObjetivo() { return progresoObjetivo; }

    /**
     * Porcentaje para mostrar progreso en UI (barra de progreso).
     */
    public double getPorcentaje() {
        if (progresoObjetivo == 0) return 0;
        return (progresoActual * 100.0) / progresoObjetivo;
    }
}


/* ==========================================================
   2) INSIGNIA
   ==========================================================
   Qué representa:
   - La recompensa visual asociada a un logro.
   - Se desbloquea cuando se completa el logro.

   Incluye:
   - Ruta de imagen
   - Rareza (extra para gamificación)
   - Estado desbloqueada sí/no
   - Fecha de desbloqueo (opcional)
   ========================================================== */
class Insignia {

    enum Rareza { COMUN, RARA, EPICA, LEGENDARIA }

    private String rutaImagen;
    private boolean desbloqueada;
    private Rareza rareza;
    private LocalDateTime fechaDesbloqueo;

    public Insignia(String rutaImagen) {
        this(rutaImagen, Rareza.COMUN);
    }

    public Insignia(String rutaImagen, Rareza rareza) {
        this.rutaImagen = rutaImagen;
        this.rareza = rareza;
        this.desbloqueada = false;
    }

    public void desbloquear() {
        desbloqueada = true;
        fechaDesbloqueo = LocalDateTime.now();
    }

    public void bloquear() {
        desbloqueada = false;
        fechaDesbloqueo = null;
    }

    public boolean isDesbloqueada() { return desbloqueada; }
    public String getRutaImagen() { return rutaImagen; }
    public Rareza getRareza() { return rareza; }
}


/* ==========================================================
   3) EVENTOS
   ==========================================================
   Qué son:
   - “Acciones del usuario” transformadas en eventos.
   - El Gestor recibe eventos y los pasa a los logros para actualizar progreso.

   Tipos:
   - PELICULA_EVALUADA
   - GENERO_EVALUADO
   - LOGIN_DIARIO (por si hay logros de racha)
   ========================================================== */
enum TipoEventoLogro {
    PELICULA_EVALUADA,
    GENERO_EVALUADO,
    LOGIN_DIARIO
}

/**
 * EventoLogro:
 * - tipo: qué ha ocurrido
 * - datos: información extra (ej: nombrePelicula, genero)
 */
class EventoLogro {
    private TipoEventoLogro tipo;
    private Map<String, Object> datos = new HashMap<>();

    public EventoLogro(TipoEventoLogro tipo) {
        this.tipo = tipo;
    }

    public EventoLogro(TipoEventoLogro tipo, Map<String, Object> datos) {
        this.tipo = tipo;
        if (datos != null) this.datos.putAll(datos);
    }

    public TipoEventoLogro getTipo() { return tipo; }

    // Helper para leer strings de los datos (pelicula, genero, etc.)
    public String getString(String key) {
        Object v = datos.get(key);
        return (v instanceof String) ? (String) v : null;
    }
}


/* ==========================================================
   4) TIPOS DE LOGRO (SUBCLASES)
   ==========================================================
   - Cada subclase implementa procesarEvento(evento)
   - Si se cumple condición -> completar()
   ========================================================== */

/**
 * LogroCantidad:
 * - Se completa cuando ocurre el evento PELICULA_EVALUADA X veces.
 */
class LogroCantidad extends Logro {

    private int objetivo;

    public LogroCantidad(String nombre, String descripcion, int objetivo, Insignia insignia) {
        super(nombre, descripcion, insignia);
        this.objetivo = objetivo;
        this.progresoObjetivo = objetivo;
    }

    @Override
    public boolean procesarEvento(EventoLogro evento) {
        if (getCompleto()) return false;

        if (evento.getTipo() == TipoEventoLogro.PELICULA_EVALUADA) {
            progresoActual++;
            if (progresoActual >= objetivo) completar();
            return true;
        }
        return false;
    }
}

/**
 * LogroGenero:
 * - Se completa cuando ocurre GENERO_EVALUADO con un género concreto X veces.
 * - El género se lee del evento: evento.getString("genero")
 */
class LogroGenero extends Logro {

    private String genero;
    private int objetivo;

    public LogroGenero(String nombre, String descripcion, String genero, int objetivo, Insignia insignia) {
        super(nombre, descripcion, insignia);
        this.genero = genero;
        this.objetivo = objetivo;
        this.progresoObjetivo = objetivo;
    }

    @Override
    public boolean procesarEvento(EventoLogro evento) {
        if (getCompleto()) return false;

        if (evento.getTipo() == TipoEventoLogro.GENERO_EVALUADO) {
            String g = evento.getString("genero");
            if (g != null && g.equalsIgnoreCase(genero)) {
                progresoActual++;
                if (progresoActual >= objetivo) completar();
                return true;
            }
        }
        return false;
    }
}

/**
 * LogroPelicula:
 * - Se completa cuando el evento PELICULA_EVALUADA coincide con una película concreta.
 * - Se lee del evento: evento.getString("pelicula")
 */
class LogroPelicula extends Logro {

    private String pelicula;

    public LogroPelicula(String nombre, String descripcion, String pelicula, Insignia insignia) {
        super(nombre, descripcion, insignia);
        this.pelicula = pelicula;
        this.progresoObjetivo = 1;
    }

    @Override
    public boolean procesarEvento(EventoLogro evento) {
        if (getCompleto()) return false;

        if (evento.getTipo() == TipoEventoLogro.PELICULA_EVALUADA) {
            String p = evento.getString("pelicula");
            if (p != null && p.equalsIgnoreCase(pelicula)) {
                progresoActual = 1;
                completar();
                return true;
            }
        }
        return false;
    }
}


/* ==========================================================
   5) GESTOR DE LOGROS
   ==========================================================
   Qué hace:
   - Tiene una lista de logros
   - Recibe un evento y se lo manda a todos los logros
   - Devuelve completados para mostrar en UI

   Nota:
   - Este gestor es simple (suficiente para práctica y proyecto conjunto).
   ========================================================== */
class GestorLogros {

    private List<Logro> logros = new ArrayList<>();

    public GestorLogros(List<Logro> logros) {
        if (logros != null) this.logros.addAll(logros);
    }

    public void emitirEvento(EventoLogro evento) {
        for (Logro l : logros) {
            l.procesarEvento(evento);
        }
    }

    public List<Logro> getCompletados() {
        List<Logro> out = new ArrayList<>();
        for (Logro l : logros) if (l.getCompleto()) out.add(l);
        return out;
    }
}


/* ==========================================================
   6) CATÁLOGO DE LOGROS
   ==========================================================
   Qué hace:
   - Crea todos los logros “predefinidos” del juego/app.
   - Se carga una sola vez (flag cargado).
   ========================================================== */
class CatalogoLogros {

    private static ArrayList<Logro> logros = new ArrayList<>();
    private static boolean cargado = false;

    public static ArrayList<Logro> getLogros() {
        if (!cargado) cargar();
        return logros;
    }

    private static void cargar() {

        // Logro 1: Cantidad 1 película
        logros.add(new LogroCantidad(
                "Primeros pasos",
                "Evalúa 1 película",
                1,
                new Insignia("/img/primer.jpg", Insignia.Rareza.COMUN)));

        // Logro 2: Cantidad 10 películas
        logros.add(new LogroCantidad(
                "Cinéfilo experto",
                "Evalúa 10 películas",
                10,
                new Insignia("/img/cinefilo.jpg", Insignia.Rareza.RARA)));

        // Logro 3: Género (Ciencia Ficción) 3 veces
        logros.add(new LogroGenero(
                "Amante de la Ciencia Ficción",
                "Evalúa 3 pelis de ciencia ficción",
                "Ciencia Ficción",
                3,
                new Insignia("/img/scifi.jpg", Insignia.Rareza.COMUN)));

        // Logro 4: Película concreta
        logros.add(new LogroPelicula(
                "Clásico de acción",
                "Evalúa 12 Angry Men",
                "12 Angry Men",
                new Insignia("/img/clasic.jpg", Insignia.Rareza.EPICA)));

        cargado = true;
    }
}


/* ==========================================================
   7) DEMO / PRUEBA RÁPIDA
   ==========================================================
   Para comprobar que:
   - Compila
   - Se desbloquean logros
   - Se pueden emitir eventos

   Si en el repo no quieren main, se borra esta clase.
   ========================================================== */
class DemoLogros {
    public static void main(String[] args) {

        // Se crea el gestor usando el catálogo
        GestorLogros gestor = new GestorLogros(CatalogoLogros.getLogros());

        // Evento 1: evaluar película genérica (suma para logros por cantidad)
        gestor.emitirEvento(new EventoLogro(TipoEventoLogro.PELICULA_EVALUADA));

        // Evento 2: evaluar una peli concreta (para LogroPelicula)
        Map<String, Object> data = new HashMap<>();
        data.put("pelicula", "12 Angry Men");
        gestor.emitirEvento(new EventoLogro(TipoEventoLogro.PELICULA_EVALUADA, data));

        // Evento 3: evaluar género (para LogroGenero)
        Map<String, Object> data2 = new HashMap<>();
        data2.put("genero", "Ciencia Ficción");
        gestor.emitirEvento(new EventoLogro(TipoEventoLogro.GENERO_EVALUADO, data2));
        gestor.emitirEvento(new EventoLogro(TipoEventoLogro.GENERO_EVALUADO, data2));
        gestor.emitirEvento(new EventoLogro(TipoEventoLogro.GENERO_EVALUADO, data2));

        // Mostrar cuántos completados hay
        System.out.println("Completados: " + gestor.getCompletados().size());
        for (Logro l : gestor.getCompletados()) {
            System.out.println(" - " + l.getNombreLogro() + " | Insignia: " + l.getInsignia().getRutaImagen());
        }
    }
}
