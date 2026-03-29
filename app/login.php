<?php

error_reporting(E_ALL);
ini_set('display_errors', 1);

    $titulo ="Login - CineRoadmap";
    $fuenteCss ="../css/loginD.css"
?>
<? require_once("./includes/headerActions.php"); ?>
  <div class="cuadro">
    <form action="./actions/loginPost.php" method="POST">
      <h1>Login</h1>
      <div class="caja">
        <input name="usuario" id="usuario" type="text" placeholder="Usuario" required>
        <i class='bx bxs-user'></i>
      </div>

      <div class="caja">
        <input name="contraseña" id="contraseña" type="password" placeholder="Contraseña" required>
        <i class='bx bxs-lock-alt' ></i>
      </div>

      <div class="olvidado">
        <label><input type="checkbox">Recuérdamela</label>
        <a href="#">¿Has olvidado la contraseña?</a>
      </div>

      <a id="irIndex" class="boton" href=""><button name="botonLogin" id="botonLogin" type="submit" class="boton">Login</button></a>

       <div class="linkRegistro">
        <p>¿No tienes cuenta? <a href="./registro.php">Regístrate</a></p>
        
      </div>

    </form>
  </div>
<? require_once("./includes/footerActions.php"); ?> 

