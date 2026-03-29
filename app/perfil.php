<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
require("./initdb.php");
require("./actions/auth_check.php");

$titulo = "Mi Perfil";
$idUser = $_SESSION['idUsuario']; 

// 1. DATOS DE USUARIO
$consulta_usuario = mysqli_prepare($conn, "SELECT usuario, nick, email, telefono FROM usuarios WHERE idUsuario = ?");
mysqli_stmt_bind_param($consulta_usuario, "i", $idUser); 
mysqli_stmt_execute($consulta_usuario);
$usuario = mysqli_fetch_array(mysqli_stmt_get_result($consulta_usuario));

// 2. ESTADÍSTICAS
$sqlStats = "SELECT COUNT(v.id) as total_vistas, SUM(p.duracion) as tiempo_total 
             FROM valoraciones v 
             INNER JOIN peliculas p ON v.pelicula_id = p.id 
             WHERE v.usuario_id = ?";
$stmtStats = mysqli_prepare($conn, $sqlStats);
mysqli_stmt_bind_param($stmtStats, "i", $idUser);
mysqli_stmt_execute($stmtStats);
$stats = mysqli_fetch_assoc(mysqli_stmt_get_result($stmtStats));

$totalVistas = $stats['total_vistas'] ?? 0;
$mediaTiempo = $totalVistas > 0 ? round($stats['tiempo_total'] / $totalVistas) : 0;

// 3. DATOS GRÁFICA
$sqlGrafica = "SELECT MONTHNAME(fecha_registro) as mes, COUNT(*) as cantidad 
               FROM valoraciones 
               WHERE usuario_id = ? 
               GROUP BY MONTH(fecha_registro), MONTHNAME(fecha_registro) 
               ORDER BY MONTH(fecha_registro) ASC";
$stmtGraf = mysqli_prepare($conn, $sqlGrafica);
mysqli_stmt_bind_param($stmtGraf, "i", $idUser);
mysqli_stmt_execute($stmtGraf);
$resGraf = mysqli_stmt_get_result($stmtGraf);

$meses = []; $cantidades = [];
while($row = mysqli_fetch_assoc($resGraf)) {
    $meses[] = $row['mes'];
    $cantidades[] = $row['cantidad'];
}

// 4. DATOS LOGROS (Para JS)
$logrosArr = [];
$q = "SELECT l.nombreReto, l.descripcion, i.srcImagen FROM logros l 
      JOIN insignias i ON l.idInsignia = i.idInsignia 
      JOIN logros_usuario lu ON lu.idLogro = l.idLogro 
      WHERE lu.idUsuario = $idUser AND lu.completado = 1";
$resLogros = mysqli_query($conn, $q);
while($r = mysqli_fetch_assoc($resLogros)) {
    $logrosArr[] = [
        'nombre' => $r['nombreReto'],
        'desc' => $r['descripcion'],
        'img' => $r['srcImagen']
    ];
}
?>

<?php require_once("./includes/headerPerfil.php"); ?>

<!-- Enlaces a archivos externos -->
<link rel="stylesheet" href="./css/perfil.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- Inyección de datos PHP a JS -->
<script>
    window.datosGrafica = {
        meses: <?= json_encode($meses) ?>,
        cantidades: <?= json_encode($cantidades) ?>
    };
    window.todosLosLogros = <?= json_encode($logrosArr) ?>;
</script>

<div class="perfil-wrapper">
    <div class="seccion-superior">
        <aside class="card-negra col-info">
            <h1><?= htmlspecialchars($usuario["usuario"]) ?></h1>
            <div class="dato-fila"><small>NICKNAME</small><span><?= htmlspecialchars($usuario["nick"]) ?></span></div>
            <div class="dato-fila"><small>EMAIL</small><span><?= htmlspecialchars($usuario["email"]) ?></span></div>
            <div class="dato-fila"><small>TELÉFONO</small><span><?= htmlspecialchars($usuario["telefono"]) ?></span></div>
            <a href="./editarPerfil.php" class="btn-editar-rojo">Editar Perfil</a>
        </aside>

        <section class="card-negra">
            <div class="stats-header-grid">
                <div class="stat-box-img"><span>PELIS VISTAS</span><strong><?= $totalVistas ?></strong></div>
                <div class="stat-box-img"><span>DURACIÓN MEDIA</span><strong><?= $mediaTiempo ?>'</strong></div>
            </div>
            <h3 style="color: #e2b616; font-size: 0.9rem; margin-bottom: 10px;">ACTIVIDAD RECIENTE</h3>
            <div style="height: 180px;"><canvas id="graficaVistas"></canvas></div>
        </section>
    </div>

    <div class="card-negra">
        <h2 style="color: #e2b616; text-align: center; margin-bottom: 30px;">MIS INSIGNIAS</h2>
        <div class="controles-logros">
            <input type="text" id="busquedaLogro" placeholder="Filtrar por nombre o descripción..." oninput="iniciarFiltrado()">
        </div>
        <div id="contenedorLogros" class="logros-grid"></div>
        <div id="paginador" class="paginacion-container"></div>
    </div>
</div>
<?php require_once("./includes/footer.php"); ?>