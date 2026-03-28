package main.app.Controlador;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;

import main.app.Modelo.*;
import main.app.Vista.PanelGenerador;

public class ControladorRegistro 
{
    // Método que permite validar los datos del usuario
    public static String registrarUsuario(String nombre, String email, String contrasena)
    {
        // Extensiones válidas para los correos
        String[] extensiones = {"@gmail.com", "@yahoo.es", "@yahoo.com", "@outlook.com", "@hotmail.com", "@live.com"};

        // Valoración si se incluye en los campos correspondientes los datos correctos
        String nom = nombre == null ? "" : nombre.trim();
        String mail = email == null ? "" : email.trim();
        String contr = contrasena == null ? "" : contrasena.trim();

        // Comprueba que ningún campo esté vacío
        if(nom.isEmpty() || mail.isEmpty() || contr.isEmpty())
        {
            return "Debes rellenar todos los campos para el registro";
        }
            

        // Comprueba longitud mínima del nombre
        if (nom.length() < 5)
        {
            return "No se puede crear un usuario con un nombre de menos de 5 letras";
        }
            

        // Comprueba que el campo de contraseña no se deje como el ejemplo
        if (contr.equals("********"))
        {
            return "Debes introducir una contraseña real (no el texto de ejemplo)";
        }
            
        // Comprueba longitud mínima de la contraseña
        if (contr.length() < 6)
        {
            return "La longitud de la contraseña debe ser de al menos 6 caracteres";
        }
            

        // Comprueba que la contraseña tenga una mayúscula, una minúscula, un número y un símbolo
        if(!contr.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$"))
        {
            return "La contraseña debe incluir al menos: una mayúscula, una minúscula, un número y un símbolo";
        }

        // Comprueba que no exista un usuario con el mismo nombre o email
        for(Usuario u : Comunidad.getUsuarios())
        {
            if(u.getnombreUsuario().equals(nom))
            {
                return "Ya existe un usuario con ese nombre";
            }

            if(u.getEmail().equalsIgnoreCase(mail))
            {
                return "Ya se ha registrado un usuario con ese correo";
            }    
                
        }

        // Comprueba que el email tenga una extensión válida
        boolean extensionValida = false;

        for(String ext : extensiones)
        {
            if(mail.toLowerCase().endsWith(ext))
            {
                extensionValida = true;
                break;
            }
        }

        if(!extensionValida)
        {
            return "No se ha incluido una extensión de correo válida";
        }
            

        // Crea el nuevo usuario y lo agrega a la comunidad
        Usuario nuevoUsuario = new Usuario(contr, mail, nom);
        Comunidad.setUsuarios(nuevoUsuario);

        return null; // Registro exitoso
    }

    // Evento que permite ver si se ha pulsado el textfield de nombre
    public static void nombreRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JTextField campoEmail, JPasswordField campoContrasena)
    {
        // Borra el texto por defecto del nombre
        if(campoNombre.getText().equals("Introduzca nombre"))
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

        // Coloca el texto por defecto en email si está vacío
        if(campoEmail.getText().isEmpty())
        {
            campoEmail.setText("Introduzca email");
            campoEmail.setForeground(Color.GRAY);
        }
    }

    // Evento que permite ver si se ha pulsado el textfield de email
    public static void emailRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre,  JTextField campoEmail, JPasswordField campoContrasena)
    {
        // Borra el texto por defecto del email
        if(campoEmail.getText().equals("Introduzca email"))
        {
            campoEmail.setText("");
            campoEmail.setForeground(Color.BLACK);
        }

        // Coloca el texto por defecto en contraseña si está vacío
        if(String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("********");
            campoContrasena.setForeground(Color.GRAY);
        }

        // Restaura el texto de ejemplo del nombre si está vacío
        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre");
            campoNombre.setForeground(Color.GRAY);
        }
    }

    // Evento que permite ver si se ha pulsado el textfield de contraseña
    public static void contrasenaRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JTextField campoEmail, JPasswordField campoContrasena)
    {
        // Borra el texto por defecto de contraseña
        if(!String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("");
            campoContrasena.setForeground(Color.BLACK);
        }

        // Restaura el texto de ejemplo del nombre si está vacío
        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre");
            campoNombre.setForeground(Color.GRAY);
        }

        // Restaura el texto de ejemplo del email si está vacío
        if(campoEmail.getText().isEmpty())
        {
            campoEmail.setText("Introduzca email");
            campoEmail.setForeground(Color.GRAY);
        }
    }

    // Vuelve a poner por defecto los texto de los campos cuando regresa al login
    public static void botonInicioPresionado(JTextField campoNombre, JTextField campoEmail, JPasswordField campoContrasena)
    {
        campoNombre.setText("Introduzca nombre");
        campoEmail.setText("Introduzca email");
        campoContrasena.setText("********");
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
    }

    // Evento cuando se pulsa para comprobar el registro (Exitoso o fallido)
    public static void botonRegistroPresionado(String mensaje, JTextField campoNombre, JTextField campoEmail, JPasswordField campoContrasena)
    {
        // Registro exitoso (Mensaje correcto, Vuelta de los textos de ejemplo y Vuelta al login)
        if(mensaje == null)
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), "Usuario registrado correctamente", "Usuario registrado", JOptionPane.INFORMATION_MESSAGE);
            campoNombre.setText("Introduzca nombre");
            campoEmail.setText("Introduzca email");
            campoContrasena.setText("********");
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
        }
        // Si hay error (Mensaje de error y se queda el menú de registro)
        else
        {
            JOptionPane.showMessageDialog(PanelGenerador.getMain(), mensaje, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Registro");
        }
    }
}
