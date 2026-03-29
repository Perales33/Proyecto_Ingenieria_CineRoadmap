<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
require_once("./initdb.php");

$idUsuario = isset($_SESSION['idUsuario']) ? intval($_SESSION['idUsuario']) : 1; 

if ($conn && $idUsuario > 0) {

    // 1. CAPTURAR EL FILTRO
    $filtroTipo = isset($_GET['tipo']) ? mysqli_real_escape_string($conn, $_GET['tipo']) : '';
    $whereClause = "";
    if ($filtroTipo != '') {
        $whereClause = " WHERE l.tipo_requisito = '$filtroTipo' ";
    }

    // --- BLOQUE DE PAGINACIÓN ---
    $numLogrosPorPagina = 8; 
    $pagina = isset($_GET['pag']) ? (int)$_GET['pag'] : 1;
    if ($pagina < 1) $pagina = 1;
    $offset = ($pagina - 1) * $numLogrosPorPagina;

    // Contamos el total (con filtro aplicado)
    $sqlTotal = "SELECT COUNT(*) as total FROM logros l $whereClause";
    $resTotal = mysqli_query($conn, $sqlTotal);
    $totalLogros = mysqli_fetch_assoc($resTotal)['total'];
    $totalPaginas = ceil($totalLogros / $numLogrosPorPagina);

    // --- RENDER DEL MENÚ DE FILTROS ---
    ?>
    <div class="filtrosLogros">
        <a href="?tipo=" class="btn-filtro <?= $filtroTipo == '' ? 'activo' : '' ?>">Todos</a>
        <a href="?tipo=genero" class="btn-filtro <?= $filtroTipo == 'genero' ? 'activo' : '' ?>">Géneros</a>
        <a href="?tipo=director" class="btn-filtro <?= $filtroTipo == 'director' ? 'activo' : '' ?>">Directores</a>
        <a href="?tipo=actor" class="btn-filtro <?= $filtroTipo == 'actor' ? 'activo' : '' ?>">Actores</a>
    </div>
    <?php

    // --- BLOQUE DE VISUALIZACIÓN ---
    // Añadimos el WHERE clause a la consulta principal
    $query = "SELECT l.idLogro, l.nombreReto, l.descripcion, l.objetivo, i.srcImagen, IFNULL(lu.progreso, 0) AS progreso
              FROM logros l
              INNER JOIN insignias i ON l.idInsignia = i.idInsignia
              LEFT JOIN logros_usuario lu ON lu.idLogro = l.idLogro AND lu.idUsuario = $idUsuario
              $whereClause
              LIMIT $offset, $numLogrosPorPagina";

    $result = mysqli_query($conn, $query);

    if ($result) {
        echo '<div class="wrapperLogrosInterno">';
        if (mysqli_num_rows($result) > 0) {
            while ($row = mysqli_fetch_assoc($result)) {
                ?>
                <div class="contenedorDirectores">
                    <div class="posicionFrontal">
                        <img class="imagenFrontal" src="<?php echo htmlspecialchars($row['srcImagen']); ?>" alt="Insignia">
                    </div>
                    <div class="posicionCarta">
                        <section class="informacionDirector">
                            <h1><?php echo htmlspecialchars($row['nombreReto']); ?></h1>
                            <div class="col2">
                                <h2>Descripción</h2>
                                <p><?php echo htmlspecialchars($row['descripcion']); ?></p>
                            </div>
                            <div class="col3">
                                <h2>Progreso</h2>
                                <p><?php echo $row['progreso']; ?> / <?php echo $row['objetivo']; ?></p>
                            </div>
                        </section>
                    </div>
                </div>
                <?php
            }
        } else {
            echo "<p style='color:white; grid-column: 1/-1;'>No hay logros en esta categoría.</p>";
        }
        echo '</div>';

        // Renderizado de la paginación (Manteniendo el filtro en los enlaces)
        if ($totalPaginas > 1) {
            echo '<div class="paginacionLogros">';
            $urlFiltro = $filtroTipo != '' ? "&tipo=$filtroTipo" : "";

            if ($pagina > 1) echo "<a href='?pag=".($pagina-1).$urlFiltro."' class='btn-pag'>« Anterior</a>";
            
            for ($i = 1; $i <= $totalPaginas; $i++) {
                $claseActiva = ($i == $pagina) ? 'activa' : '';
                echo "<a href='?pag=$i".$urlFiltro."' class='btn-pag $claseActiva'>$i</a>";
            }
            
            if ($pagina < $totalPaginas) echo "<a href='?pag=".($pagina+1).$urlFiltro."' class='btn-pag'>Siguiente »</a>";
            echo '</div>';
        }
    }
}
?>