package main.app.Modelo;

import javax.swing.*;

import main.app.Vista.PanelLogros;

public class Insignia {
    private String nombre;
    private ImageIcon imagen;

    public Insignia(String nombre, ImageIcon imagen){
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() { return nombre; }
    public ImageIcon getImagen() { return imagen; }

    // Catálogo de insignias
    public static Insignia[] getCatalogo() {
        return new Insignia[]{
            new Insignia("Cinéfilo Inicial", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia.jpg"))),
            new Insignia("Cinéfilo Intermedio", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia.jpg"))),
            new Insignia("Desafiante", new ImageIcon(PanelLogros.class.getResource("/main/resources/img/insignia.jpg")))
        };
    }
}
