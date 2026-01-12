package main.app.Modelo;

import javax.swing.ImageIcon;

public class Insignia
{
    private ImageIcon imagen;
    private boolean desbloqueada;

    public Insignia(String rutaImagen)
    {
        this.imagen = new ImageIcon(rutaImagen);
        this.desbloqueada = false;
    }

    public void desbloquear() { desbloqueada = true; }
    public boolean getBloqueo() { return desbloqueada; }
    public ImageIcon getInsignia() { return imagen; }
}
