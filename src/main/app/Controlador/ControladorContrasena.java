package main.app.Controlador;

import main.app.Modelo.Usuario;

public class ControladorContrasena
{
    public static String cambiarContrasena(String nombre,String contrasena, String nuevaContrasena)
    {
        if(Usuario.getUsuarios().isEmpty() || Usuario.getUsuarios() == null)
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
            for(Usuario u : Usuario.getUsuarios())
            {
                if(u.getnombreUsuario() != null && u.getnombreUsuario().equals(nombre))
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
}
