<?php
// includes/auth-check.php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

if (!isset($_SESSION["logged_user"])) {
    // 1. Enviamos el código HTTP 403 (Forbidden)
    http_response_code(403);
    
    // 2. Redirigimos a la página visual que está en la carpeta 'error'
    // Como este archivo se incluye en páginas de la RAÍZ, la ruta es ./error/403.php
    header("Location: ./error/403.php"); 
    exit();
}
?>