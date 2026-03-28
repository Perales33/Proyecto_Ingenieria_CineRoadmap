<?php
// 1. Sesión al principio para evitar errores de cabeceras
session_start();
require("./initdb.php");

require("./actions/auth_check.php");
// Verificamos si hay usuario en sesión, si no, redirigimos o paramos
if (!isset($_SESSION['logged_user'])) {
    die("Error: No has iniciado sesión.");
}

// 2. Lógica de actualización
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nick = $_POST['nick'] ?? '';
    $telefono = $_POST['telefono'] ?? '';
    $correo = $_POST['correo'] ?? '';

    if(!empty($nick) && !empty($telefono) && !empty($correo)){
        $actualizar = "UPDATE usuarios SET nick=?, telefono=?, email=? WHERE usuario=?";
        $stmt = $conn->prepare($actualizar);
        
        // CORRECCIÓN AQUÍ: 
        // 'telefono' es int(11) en tu BD, así que usamos "i" para ese campo.
        // El orden es: nick (s), telefono (i), email (s), usuario (s)
        $stmt->bind_param("siss", $nick, $telefono, $correo, $_SESSION['logged_user']);
        
        if($stmt->execute()){
            $stmt->close();
            header("Location: ./perfil.php");
            exit();
        } else {
            echo "Error al actualizar: " . $stmt->error;
        }
    }
}

// 3. Cargar el diseño
$titulo = "Editar Perfil - Cineroadmap";
require("./includes/headerPerfil.php"); 

// 4. Consulta de datos
$datosconsulta = "SELECT * FROM usuarios WHERE usuario = ?";
$datos = $conn->prepare($datosconsulta);
$datos->bind_param("s", $_SESSION['logged_user']);
$datos->execute();
$result = $datos->get_result();

if ($row = $result->fetch_assoc()) {
    ?>
    <div class='informacion' id='informacion'>
    <form method='post'>
        <h4 style="color: #e2b616;"><?php echo htmlspecialchars($row['usuario']); ?></h4>
        <h5> CONFIGURACIÓN DE PERFIL </h5>
        
        <label for='nick'>Nick Name</label>
        <input name='nick' type='text' value="<?php echo htmlspecialchars($row['nick']); ?>" required>
        
        <label for='telefono'>Teléfono de Contacto</label>
        <input name='telefono' type='text' value="<?php echo htmlspecialchars($row['telefono']); ?>" required>
        
        <label for='correo'>Correo Electrónico</label>
        <input name='correo' type='email' value="<?php echo htmlspecialchars($row['email']); ?>" required>
        
        <input type='submit' value='Guardar Cambios'>
        <a href='./perfil.php' style="text-decoration:none;"><input type='button' value='Cancelar'></a>
    </form>
</div>
    <?php
}

$datos->close();
$conn->close(); 
require("./includes/footer.php"); 
?>