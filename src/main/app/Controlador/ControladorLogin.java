package main.app.Controlador;

import java.awt.Color;
import java.awt.Component;
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

    // --- Evita duplicar tarjetas en el CardLayout ---
    private static boolean existeTarjeta(String nombreTarjeta)
    {
        for (Component c : PanelGenerador.getMain().getComponents())
        {
            // Caso normal: name seteado
            if (nombreTarjeta.equals(c.getName())) return true;
        }
        return false;
    }

    private static void addTarjetaSiNoExiste(String nombreTarjeta, JPanel panel)
    {
        if (!existeTarjeta(nombreTarjeta))
        {
            panel.setName(nombreTarjeta);
            PanelGenerador.getMain().add(panel, nombreTarjeta);
        }
    }

    public static void pulsarBotonInicio(String mensaje, JTextField campoNombre, JPasswordField campoContrasena)
    {
        if(mensaje == null)
        {
            JOptionPane.showMessageDialog(
                    PanelGenerador.getMain(),
                    "Bienvenido " + ControladorUsuario.getUsuarioActivo().getnombreUsuario(),
                    "Inicio de sesión",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Reset campos
            campoNombre.setText("Introduzca nombre o email");
            campoContrasena.setText("********");

            // ✅ Registrar TODAS las pantallas tras login (una sola vez)
            addTarjetaSiNoExiste("Inicio", PanelApp.crearPanelInicio());
            addTarjetaSiNoExiste("Peliculas", PanelPeliculas.crearPanelPeliculas());
            addTarjetaSiNoExiste("Comunidad", PanelComunidad.crearPanelComunidad());
            addTarjetaSiNoExiste("Perfil", PanelPerfil.crearPanelPerfil());
            addTarjetaSiNoExiste("Retos", PanelRetosRecomendaciones.crearPanel());

            // ✅ NUEVO: Mis Retos
            addTarjetaSiNoExiste("MisRetos", PanelMisRetos.crearPanel());

            // Mostrar Inicio
            PanelGenerador.getColocacion().show(PanelGenerador.getMain(), "Inicio");
        }
        else
        {
            JOptionPane.showMessageDialog(
                    PanelGenerador.getMain(),
                    mensaje,
                    "Usuario no registrado",
                    JOptionPane.ERROR_MESSAGE
            );

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
