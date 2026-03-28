<?php

require("../initdb.php");

$usuario = $_POST['usuario'];
$contraseña = $_POST['contraseña'];

$consulta = mysqli_prepare($conn, "SELECT idUsuario, contraseña FROM usuarios WHERE usuario = ?");
mysqli_stmt_bind_param($consulta, "s", $usuario);
mysqli_stmt_execute($consulta);
$resultado = mysqli_stmt_get_result($consulta);

if($fila = mysqli_fetch_assoc($resultado)) {
    if(password_verify($contraseña, $fila["contraseña"])) {
        $consultaNombreUsuario = mysqli_prepare($conn, "SELECT usuario FROM usuarios WHERE idUsuario = ?");
        mysqli_stmt_bind_param($consultaNombreUsuario, "i", $fila["idUsuario"]);
        mysqli_stmt_execute($consultaNombreUsuario);
        $resultadoNombreUsuario = mysqli_stmt_get_result($consultaNombreUsuario);
        $nombreUsuario = mysqli_fetch_assoc($resultadoNombreUsuario)["usuario"];

        session_start();
        $_SESSION["logged_user"] = $nombreUsuario;
        $_SESSION["idUsuario"] = $fila["idUsuario"];
        header('Location: ../index.php');
        exit();
    } else {
        echo "Contraseña incorrecta";
    }
} else {
    echo "Usuario no encontrado";
}

mysqli_stmt_close($consulta);
mysqli_close($conn);

?>
