<div class="bNavegacion">
    <nav class="links">
        <ul>
            <?php if (!isset($_SESSION["logged_user"])): ?>
                <!-- 2. Ítems para Invitados -->
                <li class="despegable"><a href="/">Inicio</a></li>
                <li class="despegable"><a href="/login.php">Login</a></li>
                <li class="despegable"><a href="/registro.php">Registro</a></li>
            <?php else: ?>
                <!-- 3. Ítems para Usuarios Logueados -->
                <li class="despegable"><a href="/">Inicio</a></li>
                <li class="despegable"><a href="/peliculas.php">Películas</a></li>
                <li class="despegable"><a href="/logros.php">Logros e Insignias</a></li>
                <li class="despegable"><a href="/retos.php">Retos</a></li>
                <li class="despegable"><a href="/comunidad.php">Comunidad</a></li> 
                <li class="despegable"><a href="/perfil.php">Perfil</a></li>
                <li class="despegable"><a href="/actions/logout.php">Cerrar sesión</a></li>
            <?php endif; ?>

        </ul>
    </nav>
</div>