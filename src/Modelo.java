import java.util.Vector;
import java.util.ArrayList;

class Usuario
{
    private String contrasena;
    private String email;
    private String nombreUsuario;
    private String fotoUsuario;
    private ArrayList<Pelicula> listaPeliculas;

    public Usuario(String contrasena, String email, String nombreUsuario, String fotoUsuario)
    {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.fotoUsuario = "default.png";
        this.listaPeliculas = new ArrayList<>();
    }

    public Usuario(String contrasena, String email, String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.fotoUsuario = "default.png";
        this.listaPeliculas = new ArrayList<>();
    }

    void setnombreUsuario(String usuario) { this.nombreUsuario = usuario; }
    void setEmail(String correo) { this.email = correo; }
    void setContrasena(String contrasena) { this.contrasena = contrasena; }
    void setFoto(String foto) { this.fotoUsuario = foto; }
    void setPelicula(Pelicula pel) 
    { 
        if(this.listaPeliculas.contains(pel))
        {
            System.out.println("\n+----------------------------------------+");
            System.out.println("| La película ya estaba marcada como vista |");
            System.out.println("+------------------------------------------+");
        }
        else
        {
            listaPeliculas.add(pel);
            System.out.println("\n+-----------------------------+");
            System.out.println("| Película marcada como vista |");
            System.out.println("+-----------------------------+");
        }
    };

    String getnombreUsuario() { return this.nombreUsuario; }
    String getEmail() { return this.email; }
    String getContrasena() { return this.contrasena; }
    String getFoto() { return this.fotoUsuario; }
    ArrayList<Pelicula> getPeliculas() { return this.listaPeliculas; }
}

class Pelicula
{
    private String nombrePelicula;
    private int anio;
    private Vector<String> arrayGeneros;
    private Vector<String> arrayDirectores;
    private Vector<String> arrayActores;
    private String descripcion;
    private String fotoPelicula;


    public Pelicula(String nombre, int anio, Vector<String> generos, Vector<String> directores, Vector<String> actores, String descripcion, String foto)
    {
        this.nombrePelicula = nombre;
        this.anio = anio;
        this.arrayGeneros = generos;
        this.arrayDirectores = directores;
        this.arrayActores = actores;
        this.descripcion = descripcion;
        this.fotoPelicula = foto;
    }

    String getnombrePelicula() { return this.nombrePelicula; }
    int getAnio() { return this.anio; }
    Vector<String> getGeneros() { return this.arrayGeneros; }
    Vector<String> getDirectores() { return this.arrayDirectores; }
    Vector<String> getActores() { return this.arrayActores; }

    String getDescripcion() { return this.descripcion; }
    String getFoto() { return this.fotoPelicula; }

    public static void cargarPeliculas(ArrayList<Pelicula> catalogo)
    {
        // --- Película 1: DUNE ---
        
        // Generos
        Vector<String> generos1 = new Vector<>();
        generos1.add("Acción");
        generos1.add("Ciencia Ficción");
        
        // Directores
        Vector<String> directores1 = new Vector<>();
        directores1.add("Denis Villeneuve");
        
        // Actores
        Vector<String> actores1 = new Vector<>();
        actores1.add("Timothée Chalamet");
        actores1.add("Zendaya");

        Pelicula p1 = new Pelicula("Dune: Parte Dos", 2024, generos1, directores1, actores1, "Paul Atreides se une a los Fremen para liberar Arrakis.", "dune2.jpg");
        catalogo.add(p1);

        // --- Película 2: BARBIE ---
        
        // Generos
        Vector<String> generos2 = new Vector<>();
        generos2.add("Comedia");
        generos2.add("Aventura");
        
        // Directores
        Vector<String> directores2 = new Vector<>();
        directores2.add("Greta Gerwig");
        
        // Actores
        Vector<String> actores2 = new Vector<>();
        actores2.add("Margot Robbie");
        actores2.add("Ryan Gosling");
        
        Pelicula p2 = new Pelicula("Barbie", 2023, generos2, directores2, actores2, "Una muñeca viaja al mundo real para encontrar la felicidad.", "barbie.jpg");
        catalogo.add(p2);
        
        // --- Película 3: OPPENHEIMER ---
        
        // Generos
        Vector<String> generos3 = new Vector<>();
        generos3.add("Drama");
        generos3.add("Histórica");
        
        // Directores
        Vector<String> directores3 = new Vector<>();
        directores3.add("Christopher Nolan");
        
        // Actores
        Vector<String> actores3 = new Vector<>();
        actores3.add("Cillian Murphy");
        actores3.add("Emily Blunt");
        
        Pelicula p3 = new Pelicula("Oppenheimer", 2023, generos3, directores3, actores3, "La historia del padre de la bomba atómica.", "oppenheimer.jpg");
        catalogo.add(p3);
    }
}

class Retos
{
    private int idReto;
    private String nombreReto;
    private boolean estadoReto;

    void setEstado(boolean estado) { this.estadoReto = estado; }

    String getnombreReto() { return this.nombreReto; }
    int getID() { return this.idReto; }
    boolean getEstado() { return this.estadoReto; }
}

class Insignias
{
    private int idInsignia;
    private String nombreInsignia;
    private String descripcionInsignia;

    String getnombreReto() { return this.nombreInsignia; }
    int getID() { return this.idInsignia; }
    String getDescripcion() { return this.descripcionInsignia; }
}

class Logro
{
    private int idLogro;
    private String nombreLogro;
    private String descripcionLogro;

    String getnombreReto() { return this.nombreLogro; }
    int getID() { return this.idLogro; }
    String getDescripcion() { return this.descripcionLogro; }
}

class Calificaciones
{
    private int calificacion;
    private Pelicula pelicula;
    private Usuario usuario;

    void setCalificacion(int calificacion) { this.calificacion = calificacion; }
    int getCalificacion() { return calificacion; }
    
    Pelicula getPelicula() { return pelicula; }
    void setPelicula(Pelicula pel) { this.pelicula = pel; }

    Usuario getUsuario() { return usuario; }
    void setUsuario(Usuario us) { this.usuario = us;}
}