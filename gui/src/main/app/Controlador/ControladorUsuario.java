package main.app.Controlador;

import main.app.Modelo.*;

public class ControladorUsuario 
{
    // Variable para obtener el usuario que ha iniciado sesión
    private static Usuario usuarioActivo;

    // Getters y setters
    public static Usuario getUsuarioActivo() { return usuarioActivo; }
    protected static void setUsuarioActivo(Usuario u) {usuarioActivo = u; }
    public static void setUsuarioActivo() {usuarioActivo = null; }
}
