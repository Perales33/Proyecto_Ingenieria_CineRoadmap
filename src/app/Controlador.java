package app;

import javax.swing.JPasswordField;

public class Controlador
{
    private static Usuario usuarioActivo;

    protected static Usuario getUsuarioActivo() { return usuarioActivo; }
    private static void setUsuarioActivo(Usuario u) {usuarioActivo = u; }
    protected static void setUsuarioActivo() {usuarioActivo = null; }

    protected static void verContrasena(JPasswordField boton)
    {
        if (boton.getEchoChar() == 0) 
        {
            boton.setEchoChar('*'); // ocultar
        } 
        else 
        {
            boton.setEchoChar((char) 0); // mostrar
        }
    }

    protected static String inicioSesion(String nombreEmail, String contrasena)
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
                        setUsuarioActivo(u);
                        return null;
                    }        
                } 
            } 
            return "El usuario no existe";
        }
    }

    protected static String registrarUsuario(String nombre, String email, String contrasena)
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

    protected static String cambiarContrasena(String nombre,String contrasena, String nuevaContrasena)
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

    protected static String guardarCalificacion(Pelicula pelicula, int nota)
    {
        if(Usuario.getUsuarios() == null || Usuario.getUsuarios().isEmpty())
        {
            return "No hay usuarios registrados";
        }
        
        if(nota == 0)
        {
            return "Valoré la película de 1 a 5";
        }

        if(pelicula == null)
        {
            return "Seleccione una película para valorar";
        }
        
        if (nota < 1 || nota > 5)
        {
            return "La valoración de la película debe estár entre 1 y 5";
        }

        try 
        {
            Usuario usuario = Controlador.getUsuarioActivo();
            boolean calificacionExistente = false;

            for(Calificaciones c : Calificaciones.getListaCalificaciones())
            {
                if(c.getUsuario().equals(usuario) && c.getPelicula().equals(pelicula))
                {
                    c.setCalificacion(nota);
                    calificacionExistente = true;
                    break;
                }
            }
            
            if(!calificacionExistente)
            {
                Calificaciones cNueva = new Calificaciones(nota, pelicula, usuario);
                Calificaciones.getListaCalificaciones().add(cNueva);
            }

            if(!usuario.getPeliculas().contains(pelicula))
            {
                usuario.setPeliculas(pelicula);
            }
            return null;
        } 
        catch (Exception e) 
        {
            return e.getMessage();
        }
    }

    protected static boolean actualizarEmail(String nuevoEmail)
    {
        String extensiones[] = 
        {
            "@gmail.com", "@yahoo.es", "@yahoo.com",
            "@outlook.com", "@hotmail.com", "@live.com"
        };

        String emaillower = nuevoEmail.toLowerCase();
        for(String ext : extensiones)
        {
            if(emaillower.endsWith(ext))
            {
                return true;
            }
        }
        return false;
    }

    protected static boolean actualizarNombre(String nuevoNombre)
    {
        if(nuevoNombre.length() >= 5)
        {
            return true;
        }
        return false;
    }
}