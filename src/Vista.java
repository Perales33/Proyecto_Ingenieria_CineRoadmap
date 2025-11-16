import java.util.Scanner;
import java.util.ArrayList;

public class Vista
{
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    static ArrayList<Pelicula> catalogoPeliculas = new ArrayList<>();
    static int opcionInicio;
    public static void main(String[] args) 
    {
        Pelicula.cargarPeliculas(catalogoPeliculas);
        while(true)
        {
            try 
            {
                Controlador.mostrarMenuSesion();

                switch (opcionInicio) 
                {
                    case 1:
                        if(listaUsuarios.isEmpty())
                        {
                            System.out.println("\n+-------------------------------------------+");
                            System.out.println("| Error: No se ha registrado ningún usuario |");
                            System.out.println("+-------------------------------------------+");
                            break;
                        }

                        Controlador.inicioSesion();
                        break;
                    case 2:
                        Controlador.mostrarRegistro();
                        break;
                    case 3:
                        Controlador.cambioContrasena();
                        break;
                    case 4:
                        System.out.println("\nCerrando programa...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("+-------------------------+");
                        System.out.println("| Error: Opción no válida |");
                        System.out.println("+-------------------------+\n");
                        break;
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }    
    }
}