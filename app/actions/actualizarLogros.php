<?php 
if (session_status() === PHP_SESSION_NONE) {
    session_start(); 
}

// --- LÓGICA DE ACTUALIZACIÓN AUTOMÁTICA DE LOGROS ---
require_once("./initdb.php");
$idUsuarioLogros = isset($_SESSION['idUsuario']) ? intval($_SESSION['idUsuario']) : 0;

if ($conn && $idUsuarioLogros > 0) {
    $sqlLogrosInfo = "SELECT idLogro, objetivo, tipo_requisito, valor_requisito FROM logros";
    $resLogrosInfo = mysqli_query($conn, $sqlLogrosInfo);

    if ($resLogrosInfo) {
        while ($logro = mysqli_fetch_assoc($resLogrosInfo)) {
            $idL = $logro['idLogro'];
            $obj = $logro['objetivo'];
            $tipo = $logro['tipo_requisito'];
            $valor = mysqli_real_escape_string($conn, $logro['valor_requisito'] ?? '');

            $sqlCount = "";
            switch ($tipo) {
                case 'genero':
                    $sqlCount = "SELECT COUNT(DISTINCT v.pelicula_id) as total FROM valoraciones v
                                    JOIN pelicula_generos pg ON v.pelicula_id = pg.pelicula_id
                                    JOIN generos g ON pg.genero_id = g.id
                                    WHERE v.usuario_id = $idUsuarioLogros AND g.nombre = '$valor'";
                    break;
                case 'director':
                    $sqlCount = "SELECT COUNT(DISTINCT v.pelicula_id) as total FROM valoraciones v
                                    JOIN pelicula_directores pd ON v.pelicula_id = pd.pelicula_id
                                    JOIN directores d ON pd.director_id = d.id
                                    WHERE v.usuario_id = $idUsuarioLogros AND d.nombre LIKE '%$valor%'";
                    break;
                case 'actor':
                    $sqlCount = "SELECT COUNT(DISTINCT v.pelicula_id) as total FROM valoraciones v
                                    JOIN pelicula_actores pa ON v.pelicula_id = pa.pelicula_id
                                    JOIN actores a ON pa.actor_id = a.id
                                    WHERE v.usuario_id = $idUsuarioLogros AND a.nombre LIKE '%$valor%'";
                    break;
                case 'anio':
                    $sqlCount = "SELECT COUNT(*) as total FROM valoraciones v 
                                    JOIN peliculas p ON v.pelicula_id = p.id 
                                    WHERE v.usuario_id = $idUsuarioLogros AND p.anio = '$valor'";
                    break;
                default:
                    $sqlCount = "SELECT COUNT(*) as total FROM valoraciones WHERE usuario_id = $idUsuarioLogros";
                    break;
            }

            $resCount = mysqli_query($conn, $sqlCount);
            $totalCumplido = ($resCount) ? mysqli_fetch_assoc($resCount)['total'] : 0;
            $progresoActual = ($totalCumplido >= $obj) ? $obj : $totalCumplido;
            $completado = ($totalCumplido >= $obj) ? 1 : 0;

            $updateQuery = "INSERT INTO logros_usuario (idUsuario, idLogro, progreso, completado) 
                            VALUES ($idUsuarioLogros, $idL, $progresoActual, $completado)
                            ON DUPLICATE KEY UPDATE progreso = $progresoActual, completado = $completado";
            mysqli_query($conn, $updateQuery);
        }
    }
}
// --- FIN LÓGICA LOGROS ---
?>