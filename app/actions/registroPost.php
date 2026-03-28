<?php
require("../initdb.php");

// 1. Validación inicial
if (!isset($_POST['usuario']) || !isset($_POST['nick']) || !isset($_POST['email']) || !isset($_POST['contraseña'])){
    header("location: ../registro.php");
    exit();
}

// 2. Preparación de datos
$usuario = $_POST['usuario'];
$nick = $_POST['nick'];
$email = $_POST['email'];
$telefono = $_POST['telefono'];
$contraseña = password_hash($_POST['contraseña'], PASSWORD_DEFAULT);

// 3. Insertar nuevo usuario
$consulta = mysqli_prepare($conn, "INSERT INTO usuarios(usuario, nick, contraseña, email, telefono) VALUES (?,?,?,?,?)");
mysqli_stmt_bind_param($consulta, "sssss", $usuario, $nick, $contraseña, $email, $telefono);

if (mysqli_stmt_execute($consulta)) {
    $idUsuario = mysqli_insert_id($conn);

    // 4. Asignar Logros iniciales
    $queryLogros = "INSERT INTO logros_usuario (idUsuario, idLogro, progreso, completado)
                    SELECT $idUsuario, idLogro, 0, 0 FROM logros";
    mysqli_query($conn, $queryLogros);

    // 5. Asignar Retos aleatorios con duración dinámica
    // Obtenemos 3 retos al azar junto con su tipo
    $queryRetos = "SELECT id, tipo FROM catalogo_retos ORDER BY RAND() LIMIT 3";
    $resultRetos = mysqli_query($conn, $queryRetos);
    
    while ($row = mysqli_fetch_assoc($resultRetos)) {
        $reto_id = $row['id'];
        $tipo = $row['tipo'];
        
        // Asignamos duración según el tipo de reto
        switch ($tipo) {
            case 'DIARIO':   $dias = 1;  break;
            case 'SEMANAL':  $dias = 7;  break;
            case 'TEMATICO': $dias = 30; break;
            default:         $dias = 7;
        }
        
        $fecha_fin = date('Y-m-d', strtotime("+$dias days"));
        
        // Insertamos el reto para el usuario
        $stmtReto = $conn->prepare("INSERT INTO usuario_retos (usuario_id, reto_id, fecha_fin) VALUES (?, ?, ?)");
        $stmtReto->bind_param("iis", $idUsuario, $reto_id, $fecha_fin);
        $stmtReto->execute();
    }

    // Éxito: redirigir al login
    header("location: /login.php");
    exit();

} else {
    // Error en la inserción
    echo "Error en el registro: " . mysqli_stmt_error($consulta);
    header("location: /registro.php");
}

mysqli_stmt_close($consulta);
?>