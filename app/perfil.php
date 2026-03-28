<?php

error_reporting(E_ALL);
ini_set('display_errors', 1);

session_start();
$titulo = "Perfil de: " .  $_SESSION["logged_user"];
require("./initdb.php");
require("./actions/auth_check.php");

$consulta_usuario = mysqli_prepare($conn, "SELECT usuario, nick, email, telefono FROM usuarios WHERE usuario = ?");
mysqli_stmt_bind_param($consulta_usuario, "s", $_SESSION["logged_user"]); 
mysqli_stmt_execute($consulta_usuario);
$resultado_usuario = mysqli_stmt_get_result($consulta_usuario);

if ($resultado_usuario) {
    $usuario = mysqli_fetch_array($resultado_usuario);

/*    if ($usuario === NULL) {
        header("Location: ./_404.php");
        exit();
    }
*/
}
?>

<?php require_once("./includes/headerPerfil.php"); ?>
<div class="cuerpoUsuario">
    <section class="tarjeta-perfil">
        <div class="perfil-header">
            <h1 style="color: #e2b616;"><?= htmlspecialchars($usuario["usuario"]) ?></h1>
            <p>MIEMBRO DE CINEROADMAP</p>
        </div>
        
        <div class="perfil-detalles">
            <div class="detalle-item">
                <span class="etiqueta">NICKNAME</span>
                <span class="valor"><?= htmlspecialchars($usuario["nick"]) ?></span>
            </div>
            <div class="detalle-item">
                <span class="etiqueta">EMAIL</span>
                <span class="valor"><?= htmlspecialchars($usuario["email"]) ?></span>
            </div>
            <div class="detalle-item">
                <span class="etiqueta">TELÉFONO</span>
                <span class="valor"><?= htmlspecialchars($usuario["telefono"]) ?></span>
            </div>
        </div>

        <div class="perfil-acciones">
            <a href="./editarPerfil.php" class="boton-primario">Editar perfil</a>
        </div>
    </section>
</div>

<?php require_once("./includes/footer.php"); ?>
