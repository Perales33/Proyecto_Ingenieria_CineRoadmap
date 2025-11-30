package main.app.Controlador;

import javax.swing.JPasswordField;

public class ControladorBotones 
{
    public static void verContrasena(JPasswordField boton)
    {
        if (boton.getEchoChar() == 0) 
        {
            boton.setEchoChar('*');
        } 
        else 
        {
            boton.setEchoChar((char) 0);
        }
    }
}
