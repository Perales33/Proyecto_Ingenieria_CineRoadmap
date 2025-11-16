import java.util.ArrayList;

public class Controlador 
{
    static int idUsuario;

    // Menú de Inicio (Página Login)
    protected static void mostrarMenuSesion()
    {
        System.out.println("\n+----------------------------+");
        System.out.println("| ¡Bienvenido a CineRoadmap! |");
        System.out.println("+----------------------------+");
        System.out.println("| 1. Iniciar Sesión          |");
        System.out.println("| 2. Registrarse             |");
        System.out.println("| 3. Cambio de contraseña    |");
        System.out.println("| 4. Salir                   |");
        System.out.println("+----------------------------+");
        System.out.print("Opción: ");

        while(!Vista.sc.hasNextInt())
        {
            System.out.println("\n+-----------------------------------+");
            System.out.println("| Error: Introduzca un valor entero |");
            System.out.println("+-----------------------------------+\n");
            Controlador.mostrarMenuSesion();
            Vista.sc.nextLine();
        }

        Vista.opcionInicio = Vista.sc.nextInt();
        Vista.sc.nextLine();
    }

    // Formulario de registro (Página de Registro)
    protected static void mostrarRegistro()
    {
        // Variables para poder valorar más adelante los datos a guardar del usuario
        String email;
        String contrasena;
        String nombre;

        // Busqueda del usuario para verificar que no exista un usuario con un nombre o email antes registrado
        boolean nombreEncontrado = false;

        System.out.println("\n+----------------------------------+");
        System.out.println("|      Formulario de Registro      |");
        System.out.println("+----------------------------------+");
        System.out.print("Nombre Usuario: ");
        nombre = Vista.sc.nextLine();

        System.out.print("Email: ");
        email = Vista.sc.nextLine();

        System.out.print("Contraseña: ");
        contrasena = Vista.sc.nextLine();
        
        System.out.println("+----------------------------------+");
        
        // Buscar un usuario que coincida con el correo o nombre para poder bloquear que se cree otro con los mismo datos
        for(int i = 0; i < Vista.listaUsuarios.size(); i++)
        {
            if(Vista.listaUsuarios.get(i).getnombreUsuario().equals(nombre) || Vista.listaUsuarios.get(i).getEmail().equals(email))
            {
                nombreEncontrado = true;
                break;
            }
        }

        // Valoración de que hacer tras encontrar o no al usuario
        if(nombreEncontrado)
        {
            System.out.println("\n+------------------------------------------+");
            System.out.println("| Ya se está usando este nombre de usuario |");
            System.out.println("+------------------------------------------+");
        }
        else
        {
            Usuario usuario = new Usuario(contrasena, email, nombre);
            Vista.listaUsuarios.add(usuario);
            System.out.println("\n+----------------------------------+");
            System.out.println("| Usuario Registrado Correctamente |");
            System.out.println("+----------------------------------+");
        }
    }

    // Formulario de Inicio de Sesión (Página de Login)
    protected static void inicioSesion()
    {
        // Variables para poder iniciar sesion con el usuario
        String nombreOEmail;
        String contraseña;
        
        //Variable para buscar al usuario en el ArrayList de usuarios 
        boolean usuarioEncontrado = false;

        System.out.println("\n+------------------+");
        System.out.println("| Inicio de Sesión |");
        System.out.println("+------------------+");
        System.out.print("Nombre o Email: ");
        nombreOEmail = Vista.sc.nextLine();

        System.out.print("Contraseña: ");
        contraseña = Vista.sc.nextLine();
        System.out.println("+------------------+");

        // Si se encuentra un usuario se guarda su id (Posición dentro del ArrayList) y se indica que se a encontrado (Boolean a True)
        for(int i = 0; i < Vista.listaUsuarios.size(); i++)
        {
            if(Vista.listaUsuarios.get(i).getnombreUsuario().equals(nombreOEmail) || Vista.listaUsuarios.get(i).getEmail().equals(nombreOEmail) && Vista.listaUsuarios.get(i).getContrasena().equals(contraseña))
            {
                usuarioEncontrado = true;
                idUsuario = i;
                break;
            }
        }

        // Valoración en caso de encontrar al usuario
        if(usuarioEncontrado)
        {
            Sesion.sesionActiva();
        }
        else
        {
            System.out.println("\n+--------------------------------------------------------------------+");
            System.out.println("| Error: Nombre de Usuario o Contraseña incorrectos. Pruebe otra vez |");
            System.out.println("+--------------------------------------------------------------------+");
        }
    }

    // Comprobación de la existencia del Usuarios (Página Login)
    protected static Usuario getUsuarioActivo()
    {
        if(idUsuario >= 0 && idUsuario < Vista.listaUsuarios.size())
        {
            return Vista.listaUsuarios.get(idUsuario);
        }
        return null;
    }

    // Menú de cambio de Contraseña (Página de Cambio de Contraseña)
    protected static void cambioContrasena()
    {
        // Usuario que vamos a modificarle la contraseña
        Usuario usuarioEncontrar = null;

        System.out.println("\n+----------------------+");
        System.out.println("| Cambio de Contraseña |");
        System.out.println("+----------------------+");

        System.out.print("Ingrese su nombre de usuario o email: ");
        String uc = Vista.sc.nextLine();
        
        // Busqueda del usuario en el ArrayList (Mirar Vista.java#listaUsuarios)
        usuarioEncontrar = encontrarUsuarioCredencial(uc);

        // Valoración si se ha encontrado o no al usuario
        if(usuarioEncontrar == null)
        {
            System.out.println("\n+---------------------------------------------+");
            System.out.println("| Error: El usuario no se ha podido encontrar |");
            System.out.println("+---------------------------------------------+");
        }
        else
        {
            System.out.print("Ingrese su contraseña actual: ");
            String ca = Vista.sc.nextLine();

            System.out.print("Ingrese su contraseña nueva: ");
            String cn = Vista.sc.nextLine();

            // Valoración en caso de que la contraseña sea la misma
            if(!usuarioEncontrar.getContrasena().equals(ca))
            {
                System.out.println("\n+------------------------------------------------------------------------------------+");
                System.out.println("| Error: La contraseña especificada no coincide con la contraseña actual del usuario |");
                System.out.println("+------------------------------------------------------------------------------------+");
            }
            else
            {
                if(ca.equals(cn))
                {
                    System.out.println("\n+-----------------------------------------------------------+");
                    System.out.println("| Error: La nueva contraseña no puede ser igual a la actual |");
                    System.out.println("+-----------------------------------------------------------+");
                }
                else
                {
                    usuarioEncontrar.setContrasena(cn);
                    System.out.println("\n+-------------------------------+");
                    System.out.println("| Cambio de contraseña existoso |");
                    System.out.println("+-------------------------------+");
                }
                
            }
        }
    }

    // Verificación de que el usuario se encuentra en la lista de usuarios (Página de cambio de Contraseña)
    private static Usuario encontrarUsuarioCredencial(String usuario)
    {
        for(Usuario uc : Vista.listaUsuarios)
        {
            if(uc.getnombreUsuario().equals(usuario) || uc.getEmail().equals(usuario))
            {
                return uc;
            }
        }
        return null;
    }
}

// Clase Sesión: Una vez loggeado el usuario le aparecerá el menú principal
class Sesion
{
    private static ArrayList<Calificaciones> listaValoraciones = new ArrayList<>();

    // Lógica una vez iniciado sesión (Página Principal)
    protected static void sesionActiva()
    {
        boolean sesionActiva = true;
        System.out.println("\n Bienvenido usuario " + Controlador.getUsuarioActivo().getnombreUsuario());
        
        while (sesionActiva) 
        {
            // Muestra el menú y guarda la opción del usuario
            mostrarMenuInicio();
            while(!Vista.sc.hasNextInt())
            {
                System.out.println("\n+-----------------------------------+");
                System.out.println("| Error: Introduzca un valor entero |");
                System.out.println("+-----------------------------------+\n");
                mostrarMenuInicio();
                Vista.sc.nextLine();
            }
            Vista.opcionInicio = Vista.sc.nextInt();

            Vista.sc.nextLine();

            // Valora la opción del usuario para mostrar una sección u otra
            switch (Vista.opcionInicio) 
            {
                case 1:
                    seccionPerfil(); // Ver función secciónPerfil
                    break;
                case 2:
                    seccionPeliculas(); // Ver función secciónPeliculas
                    break;
                case 3:
                    seccionLogrosInsignias(); // Sin implementar
                    break;
                case 4:
                    seccionRetos(); // Sin implementar
                    break;
                case 5:
                    // Se cierra sesión y se termina el bucle para volver al menú de inicio o registro
                    System.out.println("\nCerrando Sesión...");
                    sesionActiva = false;
                    break;
                case 6:
                    System.out.println("\nCerrando programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n+-------------------------+");
                    System.out.println("| Error: Opción no válida |");
                    System.out.println("+-------------------------+");
            }    
        }
    }

    // Menú una vez iniciado sesión (Página Principal)
    protected static void mostrarMenuInicio()
    {
        System.out.println("\n+----------------------------+");
        System.out.println("|       Menú Principal       |");
        System.out.println("+----------------------------+");
        System.out.println("| 1. Perfil Usuario          |");
        System.out.println("| 2. Películas               |");
        System.out.println("| 3. Logros e Insignias      |");
        System.out.println("| 4. Retos                   |");
        System.out.println("| 5. Cerrar Sesión           |");
        System.out.println("| 6. Salir                   |");
        System.out.println("+----------------------------+");
        System.out.print("Opción: ");
    }

    // Página para poder ver los datos del usuario (Página del Usuario)
    private static void seccionPerfil()
    {
        // Recogemos los datos del usuario que ha iniciado sesión
        System.out.println("\n+----------------+");
        System.out.println("| Perfil Usuario |");
        System.out.println("+----------------+");
        System.out.println("Foto usuario: " + Controlador.getUsuarioActivo().getFoto()); // Foto de perfil
        System.out.println("Nombre usuario: " + Controlador.getUsuarioActivo().getnombreUsuario()); // Nombre de Usuario
        System.out.println("Email: " + Controlador.getUsuarioActivo().getEmail()); // Correo con el que se registro
        System.out.println("+----------------+");
        
        // Mostras las películas vistas por el usuario (Se actualiza si el usuario pone una valoración a una película)
        System.out.println("\n+------------------+");
        System.out.println("| Películas Vistas |");
        System.out.println("+------------------+");

        // Valoración si el ArrayList del usuario tiene datos o no
        if(Controlador.getUsuarioActivo().getPeliculas().isEmpty())
        {
            System.out.println("No hay películas vistas");
        }
        else
        {
            // Busca las películas del Usuario y las muestra
            for(int i = 0; i < Controlador.getUsuarioActivo().getPeliculas().size(); i++)
            {
                Pelicula pel = Controlador.getUsuarioActivo().getPeliculas().get(i);
                System.out.println("Película " + i + ": " + pel.getnombrePelicula());
            }
        }
        System.out.println("+------------------+\n");

        // Petición al usuario para actualizar sus datos
        System.out.print("¿Desea modificar datos (S/N)? ");
        String respuesta = Vista.sc.nextLine();

        if(respuesta.equals("N") || respuesta.equals("S"))
        {
            // En caso de que indique una "S" se le pasará al formulario de modificación de datos (Solo la imagen y su nombre de perfil)
            if(respuesta.equals("S"))
            {
                System.out.println("\n+----------------------------+");
                System.out.println("| Formulario de modificación |");
                System.out.println("+----------------------------+");
                System.out.print("Modificar nombre: ");
                Controlador.getUsuarioActivo().setnombreUsuario(Vista.sc.nextLine());
                System.out.print("Modificar foto perfil: ");
                Controlador.getUsuarioActivo().setFoto(Vista.sc.nextLine() + ".jpg");
                System.out.println("+----------------------------+");
            }
        }
        else
        {
            System.out.println("\n+-----------------------------------------------------------+");
            System.out.println("| Error: No se ha podido verificar el deseo de modificación |");
            System.out.println("+-----------------------------------------------------------+");
        }
    }

    // Ver lista de Películas, Valorarlas y Marcar que has visto la película cuando la valoras
    protected static void seccionPeliculas()
    {
        // Valoración si se ha cargado correctamente los datos en el ArrayList de Películas (Mirar Modelo#Pelicula y Vista#listaPeliculas<>)
        if(Vista.catalogoPeliculas.isEmpty())
        {
            System.out.println("\n+--------------------------------------------+");
            System.out.println("| Error: El catálogo de películas está vacío |");
            System.out.println("+--------------------------------------------+");
        }
        else // En caso de que se hayan cargado correctamente se muestra de cada película los datos
        {
            System.out.println("\n+------------------------+");
            System.out.println("| Catálogo de Películas: |");
            System.out.println("+------------------------+");

            for(int i = 0; i < Vista.catalogoPeliculas.size(); i++)
            {
                System.out.println("Película " + (i + 1)+ ":");
                System.out.println("");
                System.out.println("Nombre: " + Vista.catalogoPeliculas.get(i).getnombrePelicula());
                System.out.println("Foto: " + Vista.catalogoPeliculas.get(i).getFoto());
                System.out.println("Año de estreno: " + Vista.catalogoPeliculas.get(i).getAnio());
                System.out.println("Generos: " + Vista.catalogoPeliculas.get(i).getGeneros());
                System.out.println("Directores: " + Vista.catalogoPeliculas.get(i).getDirectores());
                System.out.println("Actores: " + Vista.catalogoPeliculas.get(i).getActores());
                System.out.println("Descripción: " + Vista.catalogoPeliculas.get(i).getDescripcion());
                System.out.println("+-----------------------------------------------------+");
            }

            // Se le pide al usuario valorar una película
            System.out.print("\n¿Desea valorar una película (S/N)? ");
            String respuesta = Vista.sc.nextLine();

            if(respuesta.equals("N") || respuesta.equals("S"))
            {
                // Solo si el usuario ha decido hacer una valoración se le muestran sus valoraciones anteriores y se le pone las películas que hay en el catálogo
                if(respuesta.equals("S"))
                {
                    System.out.println("\n+-------------------+");
                    System.out.println("| Tus valoraciones: |");
                    System.out.println("+-------------------+");

                    if(listaValoraciones.isEmpty())
                    {
                        System.out.println("No has hecho ninguna valoración");
                        System.out.println("+-------------------+");
                    }
                    else
                    {
                        boolean tusCalificaciones = false;

                        // Se recuperarn todas las valoraciones que tenga el usuario hechas en el ArrayList de Valoraciones (Mirar Sesion#listaValoraciones)
                        for(int i = 0; i < listaValoraciones.size(); i++)
                        {
                            if(Controlador.getUsuarioActivo().getnombreUsuario().equals(listaValoraciones.get(i).getUsuario().getnombreUsuario()))
                            {
                                System.out.println("Película: " + listaValoraciones.get(i).getPelicula().getnombrePelicula() + ", Valoración: " + listaValoraciones.get(i).getCalificacion());
                                tusCalificaciones = true;
                            }
                        }

                        // En caso de no haber valoraciones de este usuario no se muestra nada
                        if(!tusCalificaciones)
                        {
                            System.out.println("No has hecho ninguna valoración");
                        }
                        System.out.println("+-------------------+");
                    }
                    
                    try 
                    {
                        // Se le muestra al usuario las películas a valorar
                        System.out.println("\n+-------------------------------+");
                        System.out.println("| ¿Qué película deseas valorar? |");
                        System.out.println("+-------------------------------+");
                        for(int i = 0; i < Vista.catalogoPeliculas.size(); i++)
                        {
                            System.out.println("Película " + i + ": " + Vista.catalogoPeliculas.get(i).getnombrePelicula());
                        }
                        System.out.println("+-------------------------------+");
                        System.out.print("Opción: ");

                        while(!Vista.sc.hasNextInt())
                        {
                            System.out.println("\n+-------------------------------+");
                            System.out.println("| ¿Qué película deseas valorar? |");
                            System.out.println("+-------------------------------+");
                            for(int i = 0; i < Vista.catalogoPeliculas.size(); i++)
                            {
                                System.out.println("Película " + i + ": " + Vista.catalogoPeliculas.get(i).getnombrePelicula());
                            }
                            System.out.println("+-------------------------------+");
                            System.out.print("Opción: ");
                            Vista.sc.nextLine();   
                        }

                        int opcionPelicula = Vista.sc.nextInt();

                        // Confirmado que la opción del usuario se valora si se puede valorar la película elegida por el usuario o no
                        if(opcionPelicula > Vista.catalogoPeliculas.size() || opcionPelicula < 0)
                        {   
                            System.out.println("\n+----------------------------------------+");
                            System.out.println("| Error: La película no se ha encontrado |");
                            System.out.println("+----------------------------------------+");
                        }
                        else
                        {
                            // En caso de que si esté la película especificada, se le pide al usuario una nota

                            Pelicula peliculaSeleccionada = Vista.catalogoPeliculas.get(opcionPelicula);
                            System.out.println("\nValorando: " + peliculaSeleccionada.getnombrePelicula());

                            Calificaciones calificacionExistente = null;

                            for( int i = 0; i < listaValoraciones.size(); i++) 
                            {
                                Calificaciones cal = listaValoraciones.get(i);

                                if (cal.getPelicula().equals(peliculaSeleccionada) && cal.getUsuario().equals(Controlador.getUsuarioActivo())) 
                                {

                                    calificacionExistente = cal;
                                    break; 
                                }
                            }

                            Vista.sc.nextLine();
                            System.out.print("¿Qué nota le quieres dar entre 1 y 5? ");

                            while(!Vista.sc.hasNextInt())
                            {
                                System.out.print("¿Qué nota le quieres dar entre 1 y 5? ");
                                Vista.sc.nextLine();   
                            }

                            int nota = Vista.sc.nextInt();
                            
                            // Solo se permite valora entre 1 y 5
                            if(nota < 1 || nota > 5)
                            {
                                System.out.println("\n+--------------------------------------------+");
                                System.out.println("| Error: La película no se ha podido valorar |");
                                System.out.println("+--------------------------------------------+");
                            }
                            else
                            {
                                // Se añade la película elegida por el usuario al ArrayList de películas vistas por el usuario
                                Controlador.getUsuarioActivo().setPelicula(peliculaSeleccionada);

                                // Se valora si existía una calificación anterior de esa película por el usuario
                                if(calificacionExistente != null) // En caso de que no exista se establece la nueva nota en la valoración de la película elegida
                                {
                                    calificacionExistente.setCalificacion(nota);
                                }
                                else // En caso contrario se crea una nueva calificación propia del usuario
                                {
                                    Calificaciones nuevaCalificacion = new Calificaciones();
                                    nuevaCalificacion.setCalificacion(nota);
                                    nuevaCalificacion.setPelicula(Vista.catalogoPeliculas.get(opcionPelicula));
                                    nuevaCalificacion.setUsuario(Controlador.getUsuarioActivo());

                                    listaValoraciones.add(nuevaCalificacion);
                                }   
                                System.out.println("\n+----------------------------------------------+");
                                System.out.println("| Su calificación se ha guardado correctamente |");
                                System.out.println("+----------------------------------------------+");
                            }
                        }
                    } catch (Exception e) 
                    {
                        // TODO: handle exception
                    }
                    
                }
            }
            else
            {
                System.out.println("\n+----------------------------------------------------------+");
                System.out.println("| Error: No se ha podido verificar el deseo de valoraciónn |");
                System.out.println("+----------------------------------------------------------+");
            }
        }
    }

    // Sección de Logros e Insignias (No implementado)
    protected static void seccionLogrosInsignias() 
    {
        System.out.println("\n+---------------------------------+");
        System.out.println("| Función todavía no implementada |");
        System.out.println("+---------------------------------+");
    }

    // Sección de retos (No implementado)
    protected static void seccionRetos()
    {
        System.out.println("\n+---------------------------------+");
        System.out.println("| Función todavía no implementada |");
        System.out.println("+---------------------------------+");
    }
}