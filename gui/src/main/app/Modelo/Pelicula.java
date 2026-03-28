package main.app.Modelo;

import java.util.Vector;
import java.util.ArrayList;

public class Pelicula
{
    // Atributos
    private String nombrePelicula;
    private int anio;
    private Vector<String> arrayGeneros;
    private Vector<String> arrayDirectores;
    private Vector<String> arrayActores;
    private String descripcion;
    private String fotoPelicula;
    private static ArrayList<Pelicula> catalogoPeliculas = new ArrayList<>();
    private static boolean peliculasCargadas = false;

    // Constructor
    private Pelicula(String nombre, int anio, Vector<String> generos, Vector<String> directores, Vector<String> actores, String descripcion, String foto)
    {
        this.nombrePelicula = nombre;
        this.anio = anio;
        this.arrayGeneros = generos;
        this.arrayDirectores = directores;
        this.arrayActores = actores;
        this.descripcion = descripcion;
        this.fotoPelicula = foto;
    }

    // Getters
    public String getnombrePelicula() { return this.nombrePelicula; }
    public int getAnio() { return this.anio; }
    public Vector<String> getGeneros() { return this.arrayGeneros; }
    public Vector<String> getDirectores() { return this.arrayDirectores; }
    public Vector<String> getActores() { return this.arrayActores; }

    public String getDescripcion() { return this.descripcion; }
    public String getFoto() { return this.fotoPelicula; }

    // Método para cargar el catálogo de películas
    private static void cargarPeliculas(ArrayList<Pelicula> catalogo)
    {

        // Película 1: DUNE
        
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

        Pelicula p1 = new Pelicula("Dune: Parte Dos", 2024, generos1, directores1, actores1, "Paul Atreides se une a los Fremen para liberar Arrakis.", "Dune2.jpg");
        catalogo.add(p1);

        // Película 2: BARBIE
        
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
        
        Pelicula p2 = new Pelicula("Barbie", 2023, generos2, directores2, actores2, "Una muñeca viaja al mundo real para encontrar la felicidad.", "Barbie.jpg");
        catalogo.add(p2);
        
        // Película 3: OPPENHEIMER
        
        // Generos
        Vector<String> generos3 = new Vector<>();
        generos3.add("Drama");
        generos3.add("Histórica");
        
        // Directores
        Vector<String> directores3 = new Vector<>();
        directores3.add("Christopher Nolan");
        
        Vector<String> actores3 = new Vector<>();
        actores3.add("Cillian Murphy");
        actores3.add("Emily Blunt");
        
        Pelicula p3 = new Pelicula("Oppenheimer", 2023, generos3, directores3, actores3, "La historia del padre de la bomba atómica.", "Openhaimmer.jpg");
        catalogo.add(p3);

        // Película 4: Avatar: The Way of Water
        Vector<String> generos4 = new Vector<>();
        generos4.add("Aventura"); 
        generos4.add("Ciencia Ficción");
        
        Vector<String> directores4 = new Vector<>();
        directores4.add("James Cameron");
        
        Vector<String> actores4 = new Vector<>();
        actores4.add("Sam Worthington"); 
        actores4.add("Zoe Saldana");
        
        Pelicula p4 = new Pelicula("Avatar: The Way of Water", 2022, generos4, directores4, actores4, "La continuación del viaje de Jake y Neytiri en Pandora.", "Avatar2.jpg");
        catalogo.add(p4);

        // Película 5: Black Panther: Wakanda Forever
        Vector<String> generos5 = new Vector<>();
        generos5.add("Acción"); 
        generos5.add("Aventura");
        
        Vector<String> directores5 = new Vector<>();
        directores5.add("Ryan Coogler");
        
        Vector<String> actores5 = new Vector<>();
        actores5.add("Letitia Wright"); 
        actores5.add("Lupita Nyong'o");
        
        Pelicula p5 = new Pelicula("Black Panther: Wakanda Forever", 2022, generos5, directores5, actores5, "El legado de Black Panther y la defensa de Wakanda.", "BlackPanther2.jpg");
        catalogo.add(p5);

        // Película 6: Spider-Man: No Way Home
        Vector<String> generos6 = new Vector<>();
        generos6.add("Acción"); 
        generos6.add("Aventura");
        
        Vector<String> directores6 = new Vector<>();
        directores6.add("Jon Watts");
        
        Vector<String> actores6 = new Vector<>();
        actores6.add("Tom Holland"); 
        actores6.add("Zendaya");
        
        Pelicula p6 = new Pelicula("Spider-Man: No Way Home", 2021, generos6, directores6, actores6, "Spider-Man enfrenta el multiverso y sus enemigos más temibles.", "SpiderMan3.jpg");
        catalogo.add(p6);

        // Película 7: Dune
        Vector<String> generos7 = new Vector<>();
        generos7.add("Ciencia Ficción"); 
        generos7.add("Aventura");
        
        Vector<String> directores7 = new Vector<>();
        directores7.add("Denis Villeneuve");
        
        Vector<String> actores7 = new Vector<>();
        actores7.add("Timothée Chalamet"); 
        actores7.add("Zendaya");
        
        Pelicula p7 = new Pelicula("Dune", 2021, generos7, directores7, actores7, "Paul Atreides lidera la batalla por el control de Arrakis.", "Dune1.jpg");
        catalogo.add(p7);

        // Película 8: Thor: Love and Thunder
        Vector<String> generos8 = new Vector<>();
        generos8.add("Acción"); 
        generos8.add("Comedia");

        Vector<String> directores8 = new Vector<>();
        directores8.add("Taika Waititi");
        
        Vector<String> actores8 = new Vector<>();
        actores8.add("Chris Hemsworth"); 
        actores8.add("Natalie Portman");
        
        Pelicula p8 = new Pelicula("Thor: Love and Thunder", 2022, generos8, directores8, actores8, "Thor busca recuperar su propósito mientras enfrenta un nuevo villano.", "Thor4.jpg");
        catalogo.add(p8);

        // Película 9: Everything Everywhere All at Once
        Vector<String> generos9 = new Vector<>();
        generos9.add("Comedia"); 
        generos9.add("Ciencia Ficción");
        
        Vector<String> directores9 = new Vector<>();
        directores9.add("Daniel Kwan"); 
        directores9.add("Daniel Scheinert");
        
        Vector<String> actores9 = new Vector<>();
        actores9.add("Michelle Yeoh"); 
        actores9.add("Ke Huy Quan");
        
        Pelicula p9 = new Pelicula("Everything Everywhere All at Once", 2022, generos9, directores9, actores9, "Una mujer descubre que es clave en el multiverso.", "EverythingEveryWhere.jpg");
        catalogo.add(p9);

        // --- Película 10: The Fabelmans ---
        Vector<String> generos10 = new Vector<>();
        generos10.add("Drama"); 
        generos10.add("Biográfico");
        
        Vector<String> directores10 = new Vector<>();
        directores10.add("Steven Spielberg");
        
        Vector<String> actores10 = new Vector<>();
        actores10.add("Gabriel LaBelle"); 
        actores10.add("Michelle Williams");
        
        Pelicula p10 = new Pelicula("The Fabelmans", 2022, generos10, directores10, actores10, "Historia semi-autobiográfica de la infancia de Spielberg.", "Fabelmans.jpg");
        catalogo.add(p10);

        // Película 11: Killers of the Flower Moon 
        Vector<String> generos11 = new Vector<>();
        generos11.add("Crimen"); 
        generos11.add("Drama");
        
        Vector<String> directores11 = new Vector<>();
        directores11.add("Martin Scorsese");
        
        Vector<String> actores11 = new Vector<>();
        actores11.add("Leonardo DiCaprio"); 
        actores11.add("Robert De Niro");
        
        Pelicula p11 = new Pelicula("Killers of the Flower Moon", 2023, generos11, directores11, actores11, "Investigación de asesinatos en la Nación Osage en los años 20.", "KillerFlowerMoon.jpg");
        catalogo.add(p11);

        // Película 12: Wonka
        Vector<String> generos12 = new Vector<>();
        generos12.add("Aventura"); 
        generos12.add("Familia");
        
        Vector<String> directores12 = new Vector<>();
        directores12.add("Paul King");
        
        Vector<String> actores12 = new Vector<>();
        actores12.add("Timothée Chalamet"); 
        actores12.add("Olivia Colman");
        
        Pelicula p12 = new Pelicula("Wonka", 2023, generos12, directores12, actores12, "Historia de cómo Willy Wonka conoció el mundo de los chocolates.", "Wonka.jpg");
        catalogo.add(p12);

        // Película 13: Pulp Fiction
        Vector<String> generos13 = new Vector<>();
        generos13.add("Crimen"); 
        generos13.add("Drama");
        
        Vector<String> directores13 = new Vector<>();
        directores13.add("Quentin Tarantino");
        
        Vector<String> actores13 = new Vector<>();
        actores13.add("John Travolta"); 
        actores13.add("Samuel L. Jackson");
        
        Pelicula p13 = new Pelicula("Pulp Fiction", 1994, generos13, directores13, actores13, "Jules y Vincent, dos asesinos a sueldo con no demasiadas luces, trabajan para el gángster Marsellus Wallace.", "pulpfiction.jpg");
        catalogo.add(p13);

        // Película 14: 12 Angry Men
        Vector<String> generos14 = new Vector<>();
        generos14.add("Drama");
        
        Vector<String> directores14 = new Vector<>();
        directores14.add("Sidney Lumet");
        
        Vector<String> actores14 = new Vector<>();
        actores14.add("Henry Fonda"); 
        actores14.add("Lee J. Cobb");
        
        Pelicula p14 = new Pelicula("12 Angry Men", 1957, generos14, directores14, actores14,"Los doce miembros de un jurado deben juzgar a un adolescente acusado de haber matado a su padre. Todos menos uno están convencidos de la culpabilidad.", "12angrymen.jpg");
        catalogo.add(p14);
    }
    
    // Getter para obtener el catálogo de todas la películas cargadas
    public static ArrayList<Pelicula> getCatalogo() 
    {
        if(!peliculasCargadas)
        {
            cargarPeliculas(catalogoPeliculas);
            peliculasCargadas = true;
        }

        return catalogoPeliculas;
    }

    @Override
    public String toString() 
    {
        return this.nombrePelicula;
    }
}
