<?php
// Si este archivo no es llamado desde otro que ya tiene session_start, 
// es obligatorio ponerlo aquí arriba:
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

require_once("./initdb.php");

// 1. Usamos la variable correcta: idUsuario
$usuario_id = isset($_SESSION['idUsuario']) ? $_SESSION['idUsuario'] : null;
?>