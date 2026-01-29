package main.app.Controlador;

import javax.swing.JPasswordField;

public class ControladorBotones 
{
    // Botón de visión de contraseña en el Login, Registro y Cambio de Contraseña
    public static void verContrasena(JPasswordField boton)
    {
        if (boton.getEchoChar() == 0) 
        {
            // Botón presionado para ver la contraseña
            boton.setEchoChar('*');
        } 
        else 
        {
            // Botón vuelta al inicio
            boton.setEchoChar((char) 0);
        }
    }
}
