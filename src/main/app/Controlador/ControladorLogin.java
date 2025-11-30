package main.app.Controlador;

import main.app.Modelo.Usuario;

public class ControladorLogin 
{
    public static String inicioSesion(String nombreEmail, String contrasena)
    {
        if(Usuario.getUsuarios().isEmpty())
        {
            return "No hay usuarios registrados";
        }
        else if(nombreEmail.trim().isEmpty() || contrasena.trim().isEmpty())
        {
            return "Debes rellenar todos los campos para el registro";
        }
        else
        {
            for(Usuario u : Usuario.getUsuarios())
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
}
