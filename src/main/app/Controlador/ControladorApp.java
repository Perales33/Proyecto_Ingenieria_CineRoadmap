package main.app.Controlador;

import javax.swing.JLabel;

public class ControladorApp 
{
    public static void actualizarTitulo(JLabel titulo)
    {
        if(ControladorUsuario.getUsuarioActivo() == null)
        {
            titulo.setText("Bienvenido");
        }
        else
        {
            titulo.setText("Bienvenido " + ControladorUsuario.getUsuarioActivo().getnombreUsuario());
        }
    }
}
