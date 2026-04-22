import java.sql.*;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class pruebaConexion 
{
    public static void main(String[] args) 
    {
        String url = "jdbc:mysql://localhost:3307/fnc?serverTimezone=UTC";
        String userDB = "cineroadmap";
        String passDB = "1234";

        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, userDB, passDB);

            System.out.println("--- LOGIN CINEROADMAP ---");
            System.out.print("Usuario: ");
            String inputUser = sc.nextLine();
            System.out.print("Contraseña: ");
            String inputPass = sc.nextLine();

            String sql = "SELECT * FROM usuarios WHERE usuario = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, inputUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                int idUsuarioActual = rs.getInt("idUsuario");
                String hashBD = rs.getString("contraseña").replaceFirst("^\\$2y\\$", "\\$2a\\$");

                if (BCrypt.checkpw(inputPass, hashBD)) {
                    System.out.println("\nBienvenido, " + rs.getString("usuario") + ".");
                    
                    boolean salir = false;
                    while (!salir) 
                    {
                        System.out.println("\n========= MENÚ PRINCIPAL =========");
                        System.out.println("1. Ver mi Perfil");
                        System.out.println("2. Ver Catálogo de Películas");
                        System.out.println("3. Ver mis Retos");
                        System.out.println("4. Ver mis Logros");
                        System.out.println("5. Salir");
                        System.out.print("Seleccione una opción: ");
                        
                        String lectura = sc.nextLine();
                        int opcion = Integer.parseInt(lectura);

                        switch (opcion) 
                        {
                            case 1:
                                System.out.println("\n--- MI PERFIL ---");
                                System.out.println("Nick: " + rs.getString("nick"));
                                System.out.println("Email: " + rs.getString("email"));
                                System.out.println("Teléfono: " + rs.getString("telefono"));
                                break;

                            case 2:
                                System.out.println("\n--- CATÁLOGO DE PELÍCULAS ---");
                                String sqlPelis = "SELECT p.nombre, p.anio, p.duracion, p.sinapsis, " +
                                                    "GROUP_CONCAT(DISTINCT d.nombre) AS directores, " +
                                                    "GROUP_CONCAT(DISTINCT g.nombre) AS generos, " + // <-- Coma añadida
                                                    "GROUP_CONCAT(DISTINCT a.nombre) AS actores " + // <-- Nueva línea
                                                    "FROM peliculas p " +
                                                    "LEFT JOIN pelicula_directores pd ON p.id = pd.pelicula_id " +
                                                    "LEFT JOIN directores d ON pd.director_id = d.id " +
                                                    "LEFT JOIN pelicula_generos pg ON p.id = pg.pelicula_id " +
                                                    "LEFT JOIN generos g ON pg.genero_id = g.id " +
                                                    "LEFT JOIN pelicula_actores pa ON p.id = pa.pelicula_id " + // <-- Unión con tu tabla intermedia
                                                    "LEFT JOIN actores a ON pa.actor_id = a.id " +            // <-- Unión con tabla actores
                                                    "GROUP BY p.id LIMIT 10";
                                Statement st = conn.createStatement();
                                ResultSet rsPelis = st.executeQuery(sqlPelis);
                                while (rsPelis.next()) 
                                {
                                    System.out.println("\n" + rsPelis.getString("nombre") + " (" + rsPelis.getInt("anio") + ")");
                                    System.out.println("Director/es: " + rsPelis.getString("directores"));
                                    System.out.println("Actor/es o Actrices: " + rsPelis.getString("actores"));
                                    System.out.println("Géneros: " + rsPelis.getString("generos"));
                                    System.out.println("Sinopsis: " + rsPelis.getString("sinapsis"));

                                }
                                break;

                            case 3:
                                System.out.println("\n--- MIS RETOS ACTUALES ---");
                                String sqlRetos = "SELECT c.nombre, c.descripcion, ur.estado, ur.progreso_actual, c.progreso_objetivo, ur.fecha_fin " +
                                                 "FROM usuario_retos ur " +
                                                 "JOIN catalogo_retos c ON ur.reto_id = c.id " +
                                                 "WHERE ur.usuario_id = ?";
                                PreparedStatement psRetos = conn.prepareStatement(sqlRetos);
                                psRetos.setInt(1, idUsuarioActual);
                                ResultSet rsRetos = psRetos.executeQuery();
                                while (rsRetos.next()) 
                                {
                                    System.out.println("\n[" + rsRetos.getString("estado") + "] " + rsRetos.getString("nombre"));
                                    System.out.println("   Progreso: " + rsRetos.getInt("progreso_actual") + "/" + rsRetos.getInt("progreso_objetivo"));
                                    System.out.println("   Finaliza el: " + rsRetos.getDate("fecha_fin"));
                                    System.out.println("   " + rsRetos.getString("descripcion"));
                                }
                                break;

                            case 4:
                                System.out.println("\n--- MIS LOGROS ---");
                                String sqlLogros = "SELECT l.nombreReto, l.descripcion, lu.progreso, l.objetivo, lu.completado " +
                                                  "FROM logros_usuario lu " +
                                                  "JOIN logros l ON lu.idLogro = l.idLogro " +
                                                  "WHERE lu.idUsuario = ?";
                                PreparedStatement psLogros = conn.prepareStatement(sqlLogros);
                                psLogros.setInt(1, idUsuarioActual);
                                ResultSet rsLogros = psLogros.executeQuery();
                                while (rsLogros.next()) 
                                {
                                    String status = rsLogros.getBoolean("completado") ? "COMPLETADO" : "EN PROGRESO";
                                    System.out.println("\n" + rsLogros.getString("nombreReto") + ": " + status);
                                    System.out.println("  Descripción: " + rsLogros.getString("descripcion"));
                                    System.out.println("  Progreso: " + rsLogros.getInt("progreso") + "/" + rsLogros.getInt("objetivo"));
                                }
                                break;

                            case 5:
                                salir = true;
                                System.out.println("Cerrando sesión...");
                                break;
                        }
                    }
                } 
                else 
                {
                    System.out.println("❌ Contraseña incorrecta.");
                }
            } 
            else 
            {
                System.out.println("❌ Usuario no encontrado.");
            }
            conn.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}