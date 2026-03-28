<?php
session_start();

// 1. Borramos TODAS las variables de sesión por seguridad
$_SESSION = array();

// 2. Destruimos la sesión en el servidor
if (session_id() != "" || isset($_COOKIE[session_name()])) {
    setcookie(session_name(), '', time() - 42000, '/');
}
session_destroy();

// 3. Decidimos a dónde redirigir
$redireccion = './index.php'; // Por defecto al index

if (isset($_SERVER['HTTP_REFERER'])) {
    // Si venía de una página, intentamos volver ahí (ahora saldrá "Inicie sesión")
    $redireccion = $_SERVER['HTTP_REFERER'];
}

// 4. Redirigir (Usamos comillas DOBLES para que la variable funcione)
header("Location: $redireccion");
exit();
?>