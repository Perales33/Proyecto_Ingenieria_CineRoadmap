<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
require_once("./initdb.php");

$idUsuario = isset($_SESSION['idUsuario']) ? intval($_SESSION['idUsuario']) : 0;

if ($conn) {
    // Consulta corregida con los nombres exactos de tus capturas
    $query = "SELECT 
                l.idLogro,
                l.nombreReto,
                l.descripcion,
                l.objetivo,
                i.srcImagen,
                IFNULL(lu.progreso, 0) AS progreso
            FROM logros l
            JOIN insignias i ON l.idInsignia = i.idInsignia
            LEFT JOIN logros_usuario lu 
                ON lu.idLogro = l.idLogro AND lu.idUsuario = $idUsuario";

    $result = mysqli_query($conn, $query);

    if ($result) {
        while ($row = mysqli_fetch_assoc($result)) {
            echo '<div class="contenedorDirectores">';
            echo '  <div class="posicionFrontal">';
            echo '      <img class="imagenFrontal" src="' . htmlspecialchars($row['srcImagen']) . '" alt="' . htmlspecialchars($row['nombreReto']) . '">';
            echo '  </div>';
            echo '  <div class="posicionCarta">';
            echo '      <section class="informacionDirector">';
            echo '          <h1>' . htmlspecialchars($row['nombreReto']) . '</h1>';
            echo '          <div class="col2">';
            echo '              <h2>Descripción</h2>';
            echo '              <p>' . htmlspecialchars($row['descripcion']) . '</p>';
            echo '          </div>';
            echo '          <div class="col3">';
            echo '              <h2>Progreso</h2>';
            echo '              <p>' . htmlspecialchars($row['progreso']) . ' / ' . htmlspecialchars($row['objetivo']) . '</p>';
            echo '          </div>';
            echo '      </section>';
            echo '  </div>';
            echo '</div>';
        }
    } else {
        echo "Error en la base de datos: " . mysqli_error($conn);
    }
}
?>