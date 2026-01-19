package main.app.Controlador;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;

import main.app.Modelo.*;
import main.app.Vista.PanelGenerador;

public class ControladorRegistro 
{
    public static String registrarUsuario(String nombre, String email, String contrasena)
    {
        String extensiones[] = 
        {
            "@gmail.com", "@yahoo.es", "@yahoo.com",
            "@outlook.com", "@hotmail.com", "@live.com"
        };

        if(nombre.trim().isEmpty() || email.trim().isEmpty() || contrasena.trim().isEmpty())
        {
            return "Debes rellenar todos los campos para el registro";
        }
        else if (nombre.length() < 5)
        {
            return "No se puede crear un usuario con un nombre de menos de 5 letras";
        }
        else if (contrasena.length() < 6)
        {
            return "La longitud de la contraseña debe ser de al menos 6 dígitos";
        }
        else if(!contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).+$"))
        {
            return "La contraseña debe incluir al menos: una mayúscula, una minúscula, un número y un símbolo";
        }
        else
        {
            for(Usuario u : Comunidad.getUsuarios())
            {
                if(u.getnombreUsuario().equals(nombre))
                {
                    return "Ya existe un usuario con ese nombre";
                }
                else if(u.getEmail().equalsIgnoreCase(email))
                {
                    return "Ya se ha registrado un usuario con ese correo";
                }
            }

            boolean extensionValida = false;
            for(String ext : extensiones)
            {
                if(email.toLowerCase().endsWith(ext))
                {
                    extensionValida = true;
                    break;
                }
            }

            if(!extensionValida)
            {
                return "No se ha includo una extensión de correo válida";
            }

            Usuario nuevoUsuario = new Usuario(contrasena, email, nombre);
            Comunidad.setUsuarios(nuevoUsuario); 
            return null;
        }
    }

    public static void nombreRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JTextField campoEmail, JPasswordField campoContrasena)
    {
        if(campoNombre.getText().equals("Introduzca nombre"))
        {
            campoNombre.setText("");
            campoNombre.setForeground(Color.BLACK);
        }

        if(String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("********");
            campoContrasena.setForeground(Color.GRAY);
        }

        if(campoEmail.getText().isEmpty())
        {
            campoEmail.setText("Introduzca email");
            campoEmail.setForeground(Color.GRAY);
        }
    }

    public static void emailRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre,  JTextField campoEmail, JPasswordField campoContrasena)
    {
        if(campoEmail.getText().equals("Introduzca email"))
        {
            campoEmail.setText("");
            campoEmail.setForeground(Color.BLACK);
        }

        if(String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("********");
            campoContrasena.setForeground(Color.GRAY);
        }

        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre");
            campoNombre.setForeground(Color.GRAY);
        }
    }

    public static void contrasenaRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JTextField campoEmail, JPasswordField campoContrasena)
    {
        if(!String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("");
            campoContrasena.setForeground(Color.BLACK);
        }

        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre");
            campoNombre.setForeground(Color.GRAY);
        }

        if(campoEmail.getText().isEmpty())
        {
            campoEmail.setText("Introduzca email");
            campoEmail.setForeground(Color.GRAY);
        }
    }

    public static void botonInicioPresionado(JTextField campoNombre, JTextField campoEmail, JPasswordField campoContrasena)
    {
        campoNombre.setText("Introduzca nombre");
        campoEmail.setText("Introduzca email");
        campoContrasena.setText("********");
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
    }

    public static void botonRegistroPresionado(String mensaje, JTextField campoNombre, JTextField campoEmail, JPasswordField campoContrasena)
    {
        if(mensaje == null)
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), "Usuario registrado correctamente", "Usuario registrado", JOptionPane.INFORMATION_MESSAGE);
            campoNombre.setText("Introduzca nombre");
            campoEmail.setText("Introduzca email");
            campoContrasena.setText("********");
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
        }
        else
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), mensaje, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Registro");
        }
    }
}
