package main.app.Controlador;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

import main.app.Vista.*;
import main.app.Modelo.*;

public class ControladorLogin
{
    // Comprueba si los datos introducidos son correctos para iniciar sesión
    public static String inicioSesion(String nombreEmail, String contrasena)
    {
        // Comprueba si hay usuarios registrados
        if(Comunidad.getUsuarios().isEmpty())
        {
            return "No hay usuarios registrados";
        }
        // Comprueba que no haya campos vacíos
        else if(nombreEmail.trim().isEmpty() || contrasena.trim().isEmpty())
        {
            return "Debes rellenar todos los campos para el registro";
        }
        else
        {
            // Recorre la lista de usuarios de la comunidad
            for(Usuario u : Comunidad.getUsuarios())
            {
                // Comprueba si el nombre o el email coinciden
                if(u.getnombreUsuario().equalsIgnoreCase(nombreEmail) || u.getEmail().equals(nombreEmail))
                {
                    // Comprueba si la contraseña es correcta
                    if(!u.getContrasena().equals(contrasena))
                    {
                        return "Contraseña incorrecta";
                    }
                    else
                    {
                        // Guarda el usuario como activo
                        ControladorUsuario.setUsuarioActivo(u);
                        return null; // Login correcto
                    }
                }
            }
            // Error: el usuario no existe
            return "El usuario no existe";
        }
    }

    // Evento que permite restablecer los datos del campo nombre cuando se cambia de campo
    public static void emailUsuarioRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena)
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
    }

    // Evento que permite restablecer los datos de la contraseña cuando se cambia de campo
    public static void contrasenaRatonPresionado(java.awt.event.MouseEvent e, JTextField campoNombre, JPasswordField campoContrasena)
    {
        // Borra el texto por defecto de contraseña
        if(!String.valueOf(campoContrasena.getPassword()).isEmpty())
        {
            campoContrasena.setText("");
            campoContrasena.setForeground(Color.BLACK);
        }

        // Restaura el placeholder del nombre
        if(campoNombre.getText().isEmpty())
        {
            campoNombre.setText("Introduzca nombre o email");
            campoNombre.setForeground(Color.GRAY);
        }
    }

    // Evita duplicar tarjetas en el CardLayout
    private static boolean existeTarjeta(String nombreTarjeta)
    {
        // Recorre los paneles existentes
        for (Component c : PanelGenerador.getMain().getComponents())
        {
            // Comprueba si ya existe la tarjeta
            if (nombreTarjeta.equals(c.getName())) return true;
        }
        return false;
    }

    // Añade una tarjeta solo si no existe
    private static void addTarjetaSiNoExiste(String nombreTarjeta, JPanel panel)
    {
        // Comprueba que la tarjeta no exista
        if (!existeTarjeta(nombreTarjeta))
        {
            panel.setName(nombreTarjeta);
            PanelGenerador.getMain().add(panel, nombreTarjeta);
        }
    }

    // Evento al pulsar el botón de iniciar sesión
    public static void pulsarBotonInicio(String mensaje, JTextField campoNombre, JPasswordField campoContrasena)
    {
        // Si no hay errores
        if(mensaje == null)
        {
            JOptionPane.showMessageDialog(
                    PanelGenerador.getMain(),
                    "Bienvenido " + ControladorUsuario.getUsuarioActivo().getnombreUsuario(),
                    "Inicio de sesión",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Resetea los campos
            campoNombre.setText("Introduzca nombre o email");
            campoContrasena.setText("********");

            // Registra las pantallas principales
            addTarjetaSiNoExiste("Inicio", PanelApp.crearPanelInicio());
            addTarjetaSiNoExiste("Peliculas", PanelPeliculas.crearPanelPeliculas());
            addTarjetaSiNoExiste("Comunidad", PanelComunidad.crearPanelComunidad());
            addTarjetaSiNoExiste("Perfil", PanelPerfil.crearPanelPerfil());
            addTarjetaSiNoExiste("Retos", PanelRetosRecomendaciones.crearPanel());
            addTarjetaSiNoExiste("MisRetos", PanelMisRetos.crearPanel());

            // Muestra la pantalla de inicio
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Inicio");
        }
        else
        {
            // Muestra el mensaje de error
            JOptionPane.showMessageDialog(
                    PanelGenerador.getMain(),
                    mensaje,
                    "Usuario no registrado",
                    JOptionPane.ERROR_MESSAGE
            );

            // Limpia los campos
            campoNombre.setText("Introduzca nombre o email");
            campoContrasena.setText("********");

            // Vuelve a la pantalla de login
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Login");
        }
    }

    // Evento para ir a la pantalla de registro
    public static void pulsarBotonRegistro(JTextField campoNombre, JPasswordField campoContrasena)
    {
        campoNombre.setText("Introduzca nombre o email");
        campoContrasena.setText("********");
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Registro");
    }

    // Evento para ir a la pantalla de cambio de contraseña
    public static void pulsarBotonContrasena(JTextField campoNombre, JPasswordField campoContrasena)
    {
        campoNombre.setText("Introduzca nombre o email");
        campoContrasena.setText("********");
        PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "CambioContrasena");
    }
}
