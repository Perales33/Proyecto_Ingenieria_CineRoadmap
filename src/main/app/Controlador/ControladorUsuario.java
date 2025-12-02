package main.app.Controlador;

import main.app.Modelo.*;

public class ControladorUsuario 
{
    private static Usuario usuarioActivo;

    public static Usuario getUsuarioActivo() { return usuarioActivo; }
    protected static void setUsuarioActivo(Usuario u) {usuarioActivo = u; }
    public static void setUsuarioActivo() {usuarioActivo = null; }
}
