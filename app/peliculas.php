<?php
if (session_status() === PHP_SESSION_NONE) { 
    session_start(); 
}
require("./initdb.php");
require("./actions/auth_check.php");

$titulo = "Películas - CineRoadmap";
$sloganPDPA="El catálogo de tus sueños, a un clic de distancia. Tu butaca está lista, elige la función.";
$tmdb_path = "https://image.tmdb.org/t/p/w500";
$idUser = isset($_SESSION['idUsuario']) ? intval($_SESSION['idUsuario']) : 0;

// --- CONFIGURACIÓN DE PAGINACIÓN ---
$peliculasPorPagina = 40; 
$paginaActual = isset($_GET['p']) ? intval($_GET['p']) : 1;
if ($paginaActual < 1) $paginaActual = 1;
$offset = ($paginaActual - 1) * $peliculasPorPagina;

// --- RECOGIDA DE FILTROS ---
$busqueda = isset($_GET['busqueda']) ? $_GET['busqueda'] : '';
$genero_id = isset($_GET['genero']) ? intval($_GET['genero']) : 0;
$anio_filtro = isset($_GET['anio']) ? intval($_GET['anio']) : 0;

// NUEVOS FILTROS ESPECIALES
$ver_lista = isset($_GET['ver']) && $_GET['ver'] == 'lista' && $idUser > 0;
$ver_valoradas = isset($_GET['ver']) && $_GET['ver'] == 'valoradas' && $idUser > 0;

// 1. Obtener lista de géneros para el select
$resGeneros = mysqli_query($conn, "SELECT * FROM generos ORDER BY nombre ASC");
$todosLosGeneros = mysqli_fetch_all($resGeneros, MYSQLI_ASSOC);

// 2. Construir la consulta SQL con filtros dinámicos
$sqlBase = "FROM peliculas p";

// Si filtramos por género O por listas especiales, necesitamos JOINs
if ($genero_id > 0) {
    $sqlBase .= " INNER JOIN pelicula_generos pg ON p.id = pg.pelicula_id";
}
if ($ver_lista) {
    $sqlBase .= " INNER JOIN lista_pendientes lp ON p.id = lp.pelicula_id";
}
if ($ver_valoradas) {
    $sqlBase .= " INNER JOIN valoraciones v ON p.id = v.pelicula_id";
}

$condiciones = [];
$params = [];
$types = "";

if (!empty($busqueda)) {
    $condiciones[] = "p.nombre LIKE ?";
    $params[] = "%$busqueda%";
    $types .= "s";
}
if ($genero_id > 0) {
    $condiciones[] = "pg.genero_id = ?";
    $params[] = $genero_id;
    $types .= "i";
}
if ($anio_filtro > 0) {
    $condiciones[] = "p.anio = ?";
    $params[] = $anio_filtro;
    $types .= "i";
}
if ($ver_lista) {
    $condiciones[] = "lp.usuario_id = ?";
    $params[] = $idUser;
    $types .= "i";
}
if ($ver_valoradas) {
    $condiciones[] = "v.usuario_id = ?";
    $params[] = $idUser;
    $types .= "i";
}

$whereSQL = "";
if (count($condiciones) > 0) {
    $whereSQL = " WHERE " . implode(" AND ", $condiciones);
}

// 3. Contar el TOTAL para la paginación
$sqlConteo = "SELECT COUNT(DISTINCT p.id) as total " . $sqlBase . $whereSQL;
$stmtCount = mysqli_prepare($conn, $sqlConteo);
if (!empty($types)) {
    mysqli_stmt_bind_param($stmtCount, $types, ...$params);
}
mysqli_stmt_execute($stmtCount);
$resCount = mysqli_stmt_get_result($stmtCount);
$totalPeliculas = mysqli_fetch_assoc($resCount)['total'];
$totalPaginas = ceil($totalPeliculas / $peliculasPorPagina);

// 4. Obtener las películas finales
$sqlFinal = "SELECT p.id, p.nombre, p.srcImagen " . $sqlBase . $whereSQL . " GROUP BY p.id ORDER BY p.anio DESC LIMIT $offset, $peliculasPorPagina";
$stmtFinal = mysqli_prepare($conn, $sqlFinal);
if (!empty($types)) {
    mysqli_stmt_bind_param($stmtFinal, $types, ...$params);
}
mysqli_stmt_execute($stmtFinal);
$resultado = mysqli_stmt_get_result($stmtFinal);
$peliculas = mysqli_fetch_all($resultado, MYSQLI_ASSOC);
?>

<?php require_once("./includes/headerPDPA.php"); ?>

<div class="cuerpoPDPA">
    <section class="filtros-container">
        <form action="./peliculas.php" method="GET" class="filtros-form">
            
            <div class="campo">
                <label>Búsqueda:</label>
                <input type="text" name="busqueda" class="input-busqueda" placeholder="Título..." value="<?= htmlspecialchars($busqueda) ?>">
            </div>
            
            <div class="campo">
                <label>Género:</label>
                <select name="genero" class="select-genero">
                    <option value="0">Todos</option>
                    <?php foreach($todosLosGeneros as $g): ?>
                        <option value="<?= $g['id'] ?>" <?= $genero_id == $g['id'] ? 'selected' : '' ?>>
                            <?= htmlspecialchars($g['nombre']) ?>
                        </option>
                    <?php endforeach; ?>
                </select>
            </div>

            <div class="campo">
                <label>Año:</label>
                <input type="number" name="anio" class="input-anio" value="<?= $anio_filtro > 0 ? $anio_filtro : '' ?>" placeholder="Ej: 2024">
            </div>

            <button type="submit" class="btn-aplicar">APLICAR</button>
            
            <div class="filtros-rapidos">
                <a href="./peliculas.php" class="btn-filtro-lista <?= (!$ver_lista && !$ver_valoradas) ? 'btn-activo' : 'btn-inactivo' ?>">Todas</a>
                
                <?php if ($idUser > 0): ?>
                    <a href="./peliculas.php?ver=lista" class="btn-filtro-lista <?= $ver_lista ? 'btn-activo' : 'btn-inactivo' ?>">
                        <span>★</span> Mi Lista
                    </a>
                    <a href="./peliculas.php?ver=valoradas" class="btn-filtro-lista <?= $ver_valoradas ? 'btn-activo' : 'btn-inactivo' ?>">
                        <span>✔</span> Valoradas
                    </a>
                <?php endif; ?>
            </div>

            <a href="./peliculas.php" class="btn-limpiar">Limpiar</a>
        </form>
    </section>

    <section class="gridPeliculas">
        <?php if (count($peliculas) > 0): ?>
            <?php foreach($peliculas as $pelicula): 
                $imgFinal = (strpos($pelicula['srcImagen'], '/') === 0) 
                            ? $tmdb_path . $pelicula['srcImagen'] 
                            : "/img/peliculas/" . $pelicula['srcImagen'];
            ?>
                <div class="contenedorPeliculas">
                    <a href="./peliculaInformacion.php?id=<?=$pelicula['id']?>">
                        <img class="imgPelicula" src="<?= $imgFinal ?>" alt="<?= htmlspecialchars($pelicula["nombre"]) ?>" loading="lazy">
                    </a>
                </div>
            <?php endforeach; ?>
        <?php else: ?>
            <div style="grid-column: 1 / -1; text-align: center; padding: 50px;">
                <h3 style="color:white;">No se encontraron películas en esta sección.</h3>
                <?php if($ver_lista): ?>
                    <p style="color:#888;">Añade películas a "Mi Lista" desde su ficha de información.</p>
                <?php elseif($ver_valoradas): ?>
                    <p style="color:#888;">Aún no has valorado ninguna película.</p>
                <?php endif; ?>
            </div>
        <?php endif; ?>
    </section>

    <div class="paginacion">
        <?php 
        $especialParam = "";
        if ($ver_lista) $especialParam = "&ver=lista";
        if ($ver_valoradas) $especialParam = "&ver=valoradas";

        $urlParams = "busqueda=$busqueda&genero=$genero_id&anio=$anio_filtro" . $especialParam;
        $urlBase = "./peliculas.php?$urlParams&p=";
        
        if($paginaActual > 1): ?>
            <a href="<?= $urlBase . ($paginaActual - 1) ?>" class="btn-pag">&laquo; Anterior</a>
        <?php endif; ?>

        <?php 
        $rango = 2;
        for($i = max(1, $paginaActual - $rango); $i <= min($totalPaginas, $paginaActual + $rango); $i++): 
        ?>
            <a href="<?= $urlBase . $i ?>" class="btn-pag <?= ($i == $paginaActual) ? 'activo' : '' ?>"><?= $i ?></a>
        <?php endfor; ?>

        <?php if($paginaActual < $totalPaginas): ?>
            <a href="<?= $urlBase . ($paginaActual + 1) ?>" class="btn-pag">Siguiente &raquo;</a>
        <?php endif; ?>
    </div>
</div>

<?php require_once("./includes/footer.php"); ?>