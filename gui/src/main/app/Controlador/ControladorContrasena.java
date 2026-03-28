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
        // Comprueba si no hay usuarios registrados
        if(Comunidad.getUsuarios().isEmpty() || Comunidad.getUsuarios() == null)
        {
            return "No hay usuarios registrados";
        }
        // Comprueba que ningún campo esté vacío
        else if(nombre.trim().isEmpty() ||  contrasena.trim().isEmpty() || nuevaContrasena.trim().isEmpty())
        {
            return "Debes rellenar todos los campos para el registro";
        }
        // Comprueba longitud mínima de la nueva contraseña
        else if (nuevaContrasena.length() < 5)
        {
            return "La longitud de la contraseña debe ser de al menos 6 dígitos";
        }
        // Comprueba que la nueva contraseña tenga mayúscula, minúscula, número y símbolo
        else if(!nuevaContrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).+$"))
        {
            return "La contraseña debe incluir al menos: una mayúscula, una minúscula, un número y un símbolo";
        }
        else
        {
            // Recorre la lista de usuarios de la comunidad
            for(Usuario u : Comunidad.getUsuarios())
            {
                // Comprueba si el nombre o email coinciden
                if((u.getnombreUsuario() != null && u.getnombreUsuario().equals(nombre)) || u.getEmail().equals(nombre))
                {
                    // Verifica que la contraseña actual sea correcta
                    if(!u.getContrasena().trim().equals(contrasena.trim()))
                    {
                        return "La contraseña escrita no coincide con la contraseña actual del usuario";
                    }

                    // Evita que la nueva contraseña sea igual a la anterior
                    if(contrasena.trim().equals(nuevaContrasena.trim()))
                    {
                        return "La nueva contraseña no debe coincidir con la contraseña antigua";
                    }

                    // Cambia la contraseña
                    u.setContrasena(nuevaContrasena);
                    return null; // Éxito
                }        
            }
            return "El usuario no existe"; // Error: EL usuario no existe
        }
    }

    // Evento que permite restablecer los datos del campo nombre cuando se cambia de campo
    public static void usuarioRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        // Borra el texto por defecto del campo nombre
        if(campoNombre.getText().equals("Introduzca nombre o email"))
        {
            campoNombre.setText("");
            campoNombre.setForeground(Color.BLACK);
        }

        // Coloca el texto por defecto en contraseña si está vacío
        if(String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("********");
            campoContrasena.setForeground(Color.GRAY);
        }

        // Coloca el texto por defecto en confirmación si está vacío
        if(String.valueOf(campoConfirmacion.getPassword()).isEmpty())
        {
            campoConfirmacion.setText("********");
            campoConfirmacion.setForeground(Color.GRAY);
        }
    }

    // Evento que permite restablecer los datos de la contraseña cuando se cambia de campo
    public static void contrasenaRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        // Borra el texto por defecto de contraseña
        if(!String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("");
            campoContrasena.setForeground(Color.BLACK);
        }

        // Restaura el placeholder de confirmación
        if(String.valueOf(campoConfirmacion.getPassword()).isEmpty())
        {
            campoConfirmacion.setText("********");
            campoConfirmacion.setForeground(Color.GRAY);
        }

        // Restaura el placeholder del nombre
        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre o email");
            campoNombre.setForeground(Color.GRAY);
        }
    }

    // Evento que permite restablecer los datos del email cuando se cambia de campo
    public static void confirmacionRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        // Borra el texto por defecto de confirmación
        if(!String.valueOf(campoConfirmacion.getPassword()).isEmpty())
        {
            campoConfirmacion.setText("");
            campoConfirmacion.setForeground(Color.BLACK);
        }

        // Restaura el placeholder de contraseña
        if(String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("********");
            campoContrasena.setForeground(Color.GRAY);
        }

        // Restaura el placeholder del nombre
        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre o email");
            campoNombre.setForeground(Color.GRAY);
        }
    }

    // Evento para volver a la pantalla de login y resetear los campos
    public static void volverInicio(JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        campoNombre.setText("Introduzca nombre o email");
        campoContrasena.setText("********");
        campoConfirmacion.setText("********");
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
    }

    // Evento para ejecutar el cambio de contraseña
    public static void ejecutarCambioContrasena(String mensaje, JTextField campoNombre, JPasswordField campoContrasena, JPasswordField campoConfirmacion)
    {
        // Si no hay error
        if(mensaje == null)
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), "Cambio de contraseña exitoso", "Cambio Exitoso", JOptionPane.INFORMATION_MESSAGE);
            campoNombre.setText("Introduzca nombre o email");
            campoContrasena.setText("********");
            campoConfirmacion.setText("********");
            PanelGenerador.getMain().add(PanelApp.crearPanelInicio(), "Inicio");
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
        }
        // Si hay error
        else
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), mensaje, "Cambio no efectuado", JOptionPane.ERROR_MESSAGE);
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "CambioContrasena");
        }
    }
}

