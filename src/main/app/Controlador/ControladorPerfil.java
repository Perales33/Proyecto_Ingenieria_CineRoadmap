package main.app.Controlador;

public class ControladorPerfil
{
    public static boolean actualizarEmail(String nuevoEmail)
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

    public static boolean actualizarNombre(String nuevoNombre)
    {
        if(nuevoNombre.length() >= 5)
        {
            return true;
        }
        return false;
    }
}