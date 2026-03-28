package main.app.Modelo;

import javax.swing.*;

import main.app.Vista.PanelLogros;

public class Insignia 
{
    // Atributos
    private String nombre;
    private ImageIcon imagen;

    // Constructores
    public Insignia(String nombre, ImageIcon imagen){
        this.nombre = nombre;
        this.imagen = imagen;
    }

    // Getters
    public String getNombre() { return nombre; }
    public ImageIcon getImagen() { return imagen; }

    // Catálogo de insignias
    public static Insignia[] getCatalogo() 
    {
        return new Insignia[]
        {
            // Logros generales
            new Insignia("Cinéfilo Inicial", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia1.png"))),
            new Insignia("Cinéfilo Intermedio", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia2.png"))),
            new Insignia("Desafiante", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia3.png"))),

            // Logros por género
            new Insignia("Explorador de Ciencia Ficción", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia4.png"))),
            new Insignia("Fan de la Comedia", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia5.png"))),
            new Insignia("Amante del Drama", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia6.png"))),
            new Insignia("Aventura sin límites", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia7.png"))),

            // Logros por director
            new Insignia("Seguidor de Denis Villeneuve", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia8.png"))),
            new Insignia("Seguidor de Christopher Nolan", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia9.png"))),
            new Insignia("Seguidor de Greta Gerwig", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia10.png"))),

            // Logros por actor
            new Insignia("Fan de Timothée Chalamet", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia11.png"))),
            new Insignia("Fan de Zendaya", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia12.png"))),
            new Insignia("Fan de Margot Robbie", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia13.png"))),

            // Logros por año
            new Insignia("Cinéfilo del 2023", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia14.png"))),
            new Insignia("Cinéfilo del 2022", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia15.png"))),
            new Insignia("Cinéfilo del 2021", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia16.png")))
        };
    }

}
