package main.app.Controlador;

import java.awt.Color;
import javax.swing.*;

import main.app.Vista.*;
import main.app.Modelo.*;

public class ControladorLogin 
{
    public static String inicioSesion(String nombreEmail, String contrasena)
    {
        if(Comunidad.getUsuarios().isEmpty())
        {
            return "No hay usuarios registrados";
        }
        else if(nombreEmail.trim().isEmpty() || contrasena.trim().isEmpty())
        {
            return "Debes rellenar todos los campos para el registro";
        }
        else
        {
            for(Usuario u : Comunidad.getUsuarios())
            {
                if(u.getnombreUsuario().equalsIgnoreCase(nombreEmail) || u.getEmail().equals(nombreEmail))
                {
                    if(!u.getContrasena().equals(contrasena))
                    {
                        return "Contraseña incorrecta";
                    }
                    else
                    {
                        ControladorUsuario.setUsuarioActivo(u);
                        return null;
                    }        
                } 
            } 
            return "El usuario no existe";
        }
    }

    public static void emailUsuarioRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena)
    {
        if(campoNombre.getText().equals("Introduzca nombre o email"))
        {
            campoNombre.setText("");
            campoNombre.setForeground(Color.BLACK);
        }

        if(String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("********");
            campoContrasena.setForeground(Color.GRAY);
        }
        
    }

    public static void contrasenaRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena)
    {
        if(!String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("");
            campoContrasena.setForeground(Color.BLACK);
        }

        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre o email");
            campoNombre.setForeground(Color.GRAY);
        }
    }

    public static void pulsarBotonInicio(String mensaje, JTextField campoNombre, JPasswordField campoContrasena)
    {
        if(mensaje == null)
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), ("Bienvenido " + ControladorUsuario.getUsuarioActivo().getnombreUsuario()), "Usuario registrado", JOptionPane.INFORMATION_MESSAGE);
            campoNombre.setText("Introduzca nombre o email");
            campoContrasena.setText("");
            PanelGenerador.getMain().add(PanelApp.crearPanelInicio(), "Inicio");
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Inicio");
        }
        else
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), mensaje, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
            campoNombre.setText("Introduzca nombre o email");
            campoContrasena.setText("********");
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
        }
    }

    public static void pulsarBotonRegistro(JTextField campoNombre, JPasswordField campoContrasena)
    {
        campoNombre.setText("Introduzca nombre o email");
        campoContrasena.setText("********");
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Registro");
    }

    public static void pulsarBotonContrasena(JTextField campoNombre, JPasswordField campoContrasena)
    {
        campoNombre.setText("Introduzca nombre o email");
        campoContrasena.setText("********");
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "CambioContrasena");
    }
}
