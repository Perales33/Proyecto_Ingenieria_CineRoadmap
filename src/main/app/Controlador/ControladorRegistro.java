package main.app.Controlador;

import main.app.Modelo.Usuario;

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
            for(Usuario u : Usuario.getUsuarios())
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
            Usuario.setUsuarios(nuevoUsuario); 
            return null;
        }
    }
}
