package main.app.Controlador;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;

import main.app.Modelo.Comunidad;
import main.app.Modelo.Usuario;
import main.app.Vista.*;

public class ControladorContrasena
{
    public static String cambiarContrasena(String nombre,String contrasena, String nuevaContrasena)
    {
        if(Comunidad.getUsuarios().isEmpty() || Comunidad.getUsuarios() == null)
        {
            return "No hay usuarios registrados";
        }
        else if(nombre.trim().isEmpty() ||  contrasena.trim().isEmpty() || nuevaContrasena.trim().isEmpty())
        {
            return "Debes rellenar todos los campos para el registro";
        }
        else if (nuevaContrasena.length() < 5)
        {
            return "La longitud de la contraseña debe ser de al menos 6 dígitos";
        }
        else if(!nuevaContrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).+$"))
        {
            return "La contraseña debe incluir al menos: una mayúscula, una minúscula, un número y un símbolo";
        }
        else
        {
            for(Usuario u : Comunidad.getUsuarios())
            {
                if((u.getnombreUsuario() != null && u.getnombreUsuario().equals(nombre)) || u.getEmail().equals(nombre))
                {
                    if(!u.getContrasena().trim().equals(contrasena.trim()))
                    {
                        return "La contraseña escrita no coincide con la contraseña actual del usuario";
                    }
                    
                    if(contrasena.trim().equals(nuevaContrasena.trim()))
                    {
                        return "La nueva contraseña no debe coincidir con la contraseña antigua";
                    }
                    
                    u.setContrasena(nuevaContrasena);
                    return null;
                }        
            }
            return "El usuario no existe";
        }
    }

    public static void usuarioRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
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

        if(String.valueOf(campoConfirmacion.getPassword()).isEmpty())
        {
            campoConfirmacion.setText("********");
            campoConfirmacion.setForeground(Color.GRAY);
        }
    }

    public static void contrasenaRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        if(!String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("");
            campoContrasena.setForeground(Color.BLACK);
        }

        if(String.valueOf(campoConfirmacion.getPassword()).isEmpty())
        {
            campoConfirmacion.setText("********");
            campoConfirmacion.setForeground(Color.GRAY);
        }

        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre o email");
            campoNombre.setForeground(Color.GRAY);
        }
    }

    public static void confirmacionRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        if(!String.valueOf(campoConfirmacion.getPassword()).isEmpty())
        {
            campoConfirmacion.setText("");
            campoConfirmacion.setForeground(Color.BLACK);
        }

        if(String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("********");
            campoContrasena.setForeground(Color.GRAY);
        }

        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre o email");
            campoNombre.setForeground(Color.GRAY);
        }
    }

    public static void volverInicio(JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        campoNombre.setText("Introduzca nombre o email");
        campoContrasena.setText("********");
        campoConfirmacion.setText("********");
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
    }

    public static void ejecutarCambioContrasena(String mensaje, JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        if(mensaje == null)
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), "Cambio de contraseña exitoso", "Cambio Exitoso", JOptionPane.INFORMATION_MESSAGE);
            campoNombre.setText("Introduzca nombre o email");
            campoContrasena.setText("********");
            campoConfirmacion.setText("********");
            PanelGenerador.getMain().add(PanelApp.crearPanelInicio(), "Inicio");
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
        }
        else
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), mensaje, "Cambio no efectuado", JOptionPane.ERROR_MESSAGE);
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "CambioContrasena");
        }
    }
}
