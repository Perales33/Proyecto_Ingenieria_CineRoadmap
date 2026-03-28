<?php
if (session_status() === PHP_SESSION_NONE) { 
    session_start(); 
}

require("./initdb.php");
require("./actions/auth_check.php");

// 1. OBTENCIÓN DE VARIABLES BÁSICAS
$id = isset($_GET['id']) ? intval($_GET['id']) : 0;
$idUser = isset($_SESSION['idUsuario']) ? intval($_SESSION['idUsuario']) : 0;
$tmdb_path = "https://image.tmdb.org/t/p/w500"; 

// 2. LÓGICA DE PROCESAMIENTO (POST)
if ($_SERVER['REQUEST_METHOD'] === 'POST' && $idUser > 0) {
    
    // --- ACCIÓN: VALORAR PELÍCULA ---
    if (isset($_POST['puntos'])) {
        $puntos = intval($_POST['puntos']);
        
        if ($puntos >= 1 && $puntos <= 5) {
            // Insertar o actualizar la valoración
            $sql_voto = "INSERT INTO valoraciones (pelicula_id, usuario_id, puntuacion) 
                         VALUES (?, ?, ?) 
                         ON DUPLICATE KEY UPDATE puntuacion = VALUES(puntuacion)";
            $stmt = mysqli_prepare($conn, $sql_voto);
            mysqli_stmt_bind_param($stmt, "iii", $id, $idUser, $puntos);
            mysqli_stmt_execute($stmt);

            // LOGICA UNIVERSITARIA: Si la valora, se quita automáticamente de pendientes
            $sql_clean = "DELETE FROM lista_pendientes WHERE pelicula_id = ? AND usuario_id = ?";
            $stmt_c = mysqli_prepare($conn, $sql_clean);
            mysqli_stmt_bind_param($stmt_c, "ii", $id, $idUser);
            mysqli_stmt_execute($stmt_c);

            header("Location: ./peliculaInformacion.php?id=$id&status=voto_ok");
            exit;
        }
    }

    // --- ACCIÓN: GESTIONAR LISTA DE PENDIENTES ---
    if (isset($_POST['accion_lista'])) {
        if ($_POST['accion_lista'] == 'agregar') {
            $sql_l = "INSERT IGNORE INTO lista_pendientes (pelicula_id, usuario_id) VALUES (?, ?)";
        } else {
            $sql_l = "DELETE FROM lista_pendientes WHERE pelicula_id = ? AND usuario_id = ?";
        }
        $stmt_l = mysqli_prepare($conn, $sql_l);
        mysqli_stmt_bind_param($stmt_l, "ii", $id, $idUser);
        mysqli_stmt_execute($stmt_l);
        
        header("Location: ./peliculaInformacion.php?id=$id");
        exit;
    }
}

// 3. CONSULTA DE ESTADO Y DATOS
$en_lista = false;
if ($idUser > 0) {
    $res_l = mysqli_query($conn, "SELECT 1 FROM lista_pendientes WHERE pelicula_id = $id AND usuario_id = $idUser");
    if (mysqli_num_rows($res_l) > 0) $en_lista = true;
}

$sql = "SELECT p.*, 
        (SELECT AVG(puntuacion) FROM valoraciones WHERE pelicula_id = p.id) AS promedio_votos,
        GROUP_CONCAT(DISTINCT g.nombre SEPARATOR ', ') AS lista_generos,
        GROUP_CONCAT(DISTINCT a.nombre SEPARATOR ', ') AS lista_actores,
        GROUP_CONCAT(DISTINCT d.nombre SEPARATOR ', ') AS lista_directores
        FROM peliculas p
        LEFT JOIN pelicula_generos pg ON p.id = pg.pelicula_id
        LEFT JOIN generos g ON pg.genero_id = g.id
        LEFT JOIN pelicula_actores pa ON p.id = pa.pelicula_id
        LEFT JOIN actores a ON pa.actor_id = a.id
        LEFT JOIN pelicula_directores pd ON p.id = pd.pelicula_id
        LEFT JOIN directores d ON pd.director_id = d.id
        WHERE p.id = ? GROUP BY p.id";

$consulta = mysqli_prepare($conn, $sql);
mysqli_stmt_bind_param($consulta, "i", $id);
mysqli_stmt_execute($consulta);
$peli = mysqli_fetch_assoc(mysqli_stmt_get_result($consulta));
$valoracion_global = ($peli && $peli['promedio_votos']) ? $peli['promedio_votos'] : 0; 

require_once("./includes/headerPDPA.php");
?>

<div class="cuerpoPDPA">
    <?php if ($peli): 
        $imgFinal = (strpos($peli['srcImagen'], '/') === 0) ? $tmdb_path . $peli['srcImagen'] : "/img/peliculas/" . $peli['srcImagen'];
    ?>
        <div class="detalle-layout">
            
            <div class="columna-poster">
                <img src="<?= $imgFinal ?>" alt="<?= htmlspecialchars($peli['nombre']) ?>">
            </div>

            <div class="columna-info">
                <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                    <div>
                        <h1><?= htmlspecialchars($peli['nombre']) ?></h1>
                        <div class="anio-texto"><?= $peli['anio'] ?></div>
                    </div>

                    <?php if ($idUser > 0): ?>
                        <form method="POST">
                            <button type="submit" name="accion_lista" value="<?= $en_lista ? 'quitar' : 'agregar' ?>" class="btn-watchlist <?= $en_lista ? 'remove' : 'add' ?>">
                                <?= $en_lista ? '✓ En mi lista' : '+ Mi Lista' ?>
                            </button>
                        </form>
                    <?php endif; ?>
                </div>

                <h2 class="metadata-label" style="color: #e50914;">Sinopsis</h2>
                <p class="sinopsis-texto"><?= nl2br(htmlspecialchars($peli['sinapsis'])) ?></p>
                
                <div class="info-grid">
                    <div>
                        <div class="metadata-label">Director</div>
                        <div class="metadata-value"><?= $peli['lista_directores'] ?: 'No disponible' ?></div>
                        <div class="metadata-label">Género</div>
                        <div class="metadata-value"><?= $peli['lista_generos'] ?: 'Varios' ?></div>
                    </div>
                    <div>
                        <div class="metadata-label">Reparto</div>
                        <div class="metadata-value"><?= $peli['lista_actores'] ?: 'No disponible' ?></div>
                    </div>
                </div>

                <div class="valoracion-container">
                    <div class="metadata-label">Nota de la Comunidad</div>
                    <div class="promedio-valor">
                        ★ <?= number_format($valoracion_global, 1) ?> <span>/ 5.0</span>
                    </div>

                    <?php if ($idUser > 0): ?>
                        <div class="metadata-label" style="font-size: 0.75rem;">Tu valoración (marca como vista):</div>
                        <form method="POST" class="estrellas-form">
                            <button type="submit" name="puntos" value="5" class="btn-estrella">★</button>
                            <button type="submit" name="puntos" value="4" class="btn-estrella">★</button>
                            <button type="submit" name="puntos" value="3" class="btn-estrella">★</button>
                            <button type="submit" name="puntos" value="2" class="btn-estrella">★</button>
                            <button type="submit" name="puntos" value="1" class="btn-estrella">★</button>
                        </form>
                        <?php if (isset($_GET['status']) && $_GET['status'] == 'voto_ok'): ?>
                            <p class="status-ok">¡Voto registrado!</p>
                        <?php endif; ?>
                    <?php else: ?>
                        <div class="login-alerta">
                            <a href="./login.php">Inicia sesión para votar y añadir a tu lista</a>
                        </div>
                    <?php endif; ?>
                </div>
                <?php if ($idUser > 0): ?>
                    <a href="./peliculas.php" class="btn-volver">← VOLVER AL CATÁLOGO</a>
                <?php endif; ?>
            </div>
        </div>
    <?php else: ?>
        <div style="text-align: center; padding: 100px;">
            <h2>Película no encontrada</h2>
            <a href="./peliculas.php" style="color: #e50914;">Volver al catálogo</a>
        </div>
    <?php endif; ?>
</div>

<?php require_once("./includes/footer.php"); ?>