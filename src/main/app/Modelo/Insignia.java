package main.app.Modelo;

import javax.swing.*;

public class Insignia {

    private ImageIcon insignia;
    private boolean desbloqueada;

    public Insignia(ImageIcon insignia, boolean desbloqueada) {
        this.insignia = insignia;
        this.desbloqueada = desbloqueada;
    }

    // GETTERS
    public ImageIcon getInsignia() {
        return insignia;
    }

    public boolean isDesbloqueada() {
        return desbloqueada;
    }

    // SETTERS
    public void setInsignia(ImageIcon insignia) {
        this.insignia = insignia;
    }

    public void setDesbloqueada(boolean desbloqueada) {
        this.desbloqueada = desbloqueada;
    }
}
