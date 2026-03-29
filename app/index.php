<?php
session_start();
require("./initdb.php");

$titulo = "Inicio - CineRoadmap";
$slogan = "Tu guía cinéfila: Opiniones auténticas, decisiones acertadas.";
$usuario_nombre = isset($_SESSION["logged_user"]) ? $_SESSION["logged_user"] : "Invitado";
$idUsuario = isset($_SESSION['idUsuario']) ? intval($_SESSION['idUsuario']) : 0;

$tmdb_path = "https://image.tmdb.org/t/p/w500";

// Semillas
$semillaSemanal = date("W") . date("Y");
$semillaDia = date("Ymd");
$mesActual = date("m");
$anioActual = date("Y");

// --- 1. PELÍCULA RECOMENDADA DEL DÍA ---
$consultaDia = "SELECT id, nombre, srcImagen FROM peliculas ORDER BY RAND($semillaDia) LIMIT 1";
$resDia = mysqli_query($conn, $consultaDia);
$peliculaDia = mysqli_fetch_assoc($resDia);

// --- 2. LÓGICA TOP 6 MENSUAL ---
$consultaTop = "SELECT p.id, p.nombre, p.srcImagen, AVG(v.puntuacion) as media 
                FROM peliculas p
                INNER JOIN valoraciones v ON p.id = v.pelicula_id
                WHERE MONTH(v.fecha_registro) = $mesActual 
                AND YEAR(v.fecha_registro) = $anioActual
                GROUP BY p.id
                ORDER BY media DESC, p.id DESC
                LIMIT 6";

$resultadoTop = mysqli_query($conn, $consultaTop);
$peliculasTop = mysqli_fetch_all($resultadoTop, MYSQLI_ASSOC);

// Relleno si faltan hasta 6
if (count($peliculasTop) < 6) {
    $idsExcluir = array_column($peliculasTop, 'id');
    $whereNotIn = !empty($idsExcluir) ? "WHERE id NOT IN (" . implode(',', $idsExcluir) . ")" : "";
    $faltan = 6 - count($peliculasTop);
    $consultaRelleno = "SELECT id, nombre, srcImagen FROM peliculas $whereNotIn ORDER BY RAND() LIMIT $faltan";
    $resultadoRelleno = mysqli_query($conn, $consultaRelleno);
    $peliculasRelleno = mysqli_fetch_all($resultadoRelleno, MYSQLI_ASSOC);
    $peliculasTop = array_merge($peliculasTop, $peliculasRelleno);
}

// --- 3. LÓGICA RECOMENDADAS SEMANALES ---
if ($idUsuario > 0) {
    $sqlRecom = "SELECT DISTINCT p.id, p.nombre, p.srcImagen 
                 FROM peliculas p
                 LEFT JOIN pelicula_generos pg ON p.id = pg.pelicula_id
                 LEFT JOIN pelicula_actores pa ON p.id = pa.pelicula_id
                 LEFT JOIN pelicula_directores pd ON p.id = pd.pelicula_id
                 WHERE (
                    pg.genero_id IN (SELECT pg2.genero_id FROM valoraciones v2 JOIN pelicula_generos pg2 ON v2.pelicula_id = pg2.pelicula_id WHERE v2.usuario_id = $idUsuario AND v2.puntuacion >= 7)
                    OR pa.actor_id IN (SELECT pa2.actor_id FROM valoraciones v2 JOIN pelicula_actores pa2 ON v2.pelicula_id = pa2.pelicula_id WHERE v2.usuario_id = $idUsuario AND v2.puntuacion >= 7)
                    OR pd.director_id IN (SELECT pd2.director_id FROM valoraciones v2 JOIN pelicula_directores pd2 ON v2.pelicula_id = pd2.pelicula_id WHERE v2.usuario_id = $idUsuario AND v2.puntuacion >= 7)
                 )
                 AND p.id NOT IN (SELECT pelicula_id FROM valoraciones WHERE usuario_id = $idUsuario)
                 ORDER BY RAND($semillaSemanal) LIMIT 3";
    $resultadoRecom = mysqli_query($conn, $sqlRecom);
    if (mysqli_num_rows($resultadoRecom) < 3) {
        $sqlRecom = "SELECT id, nombre, srcImagen FROM peliculas ORDER BY RAND($semillaSemanal) LIMIT 3";
        $resultadoRecom = mysqli_query($conn, $sqlRecom);
    }
} else {
    $sqlRecom = "SELECT id, nombre, srcImagen FROM peliculas ORDER BY RAND($semillaSemanal) LIMIT 3";
    $resultadoRecom = mysqli_query($conn, $sqlRecom);
}
$peliculasRecomendadas = mysqli_fetch_all($resultadoRecom, MYSQLI_ASSOC);
?>

<?php require_once("./includes/header.php"); ?>

<div class="cuerpo">
    <section class="espacioFondo">
        <h1>Bienvenido <?= htmlspecialchars($usuario_nombre) ?></h1>
        <h4>CineRoadmap: Traza tu ruta, descubre la crítica y vive el cine. Tu próxima historia favorita empieza aquí.</h4>
    </section>

    <div class="separador"></div>

    <section class="espacioCine">
        <!-- LADO IZQUIERDO: SLIDER SEMANAL -->
        <div class="slider">
            <section class="espacioFondo">
                <h1>Selección de la semana</h1>
            </section>
            <ul>
                <?php foreach($peliculasRecomendadas as $pelicula): 
                    $imgUrl = (strpos($pelicula['srcImagen'], '/') === 0) ? $tmdb_path . $pelicula['srcImagen'] : "/img/peliculas/" . $pelicula['srcImagen'];
                ?>
                    <li>
                        <a href="./peliculaInformacion.php?id=<?=$pelicula['id']?>">
                            <img src="<?= $imgUrl ?>" alt="<?= htmlspecialchars($pelicula["nombre"]) ?>">
                        </a>
                    </li>
                <?php endforeach; ?> 
            </ul>
        </div>
        
        <!-- LADO DERECHO: CONDICIONAL -->
        <!-- LADO DERECHO: CONDICIONAL -->
        <div class="graficaSeccion">
            <?php if($idUsuario == 0): ?>
                <!-- BANNER "UNETE" -->
                <div class="banner-unete">
                    <h2>Únete a la mayor comunidad cinéfila</h2>
                    <p>"Un nuevo desafío te espera en cada fotograma"</p>
                    
                    <div class="uneteIndex">
                        <a href="./registro.php" class="btn-desafio">ACEPTAR DESAFÍO</a>
                    </div>
                </div>

            <?php else: 
                $imgDia = (strpos($peliculaDia['srcImagen'], '/') === 0) ? $tmdb_path . $peliculaDia['srcImagen'] : "/img/peliculas/" . $peliculaDia['srcImagen'];
            ?>
                <!-- RECOMENDACIÓN DIARIA -->
                <section class="espacioFondo">
                    <h1 class="recomendacion-diaria-titulo">¿No sabes qué ver hoy?</h1>
                </section>
                
                <div class="recomendacion-diaria-contenedor">
                    <a href="./peliculaInformacion.php?id=<?=$peliculaDia['id']?>">
                        <img class="peliculas" src="<?= $imgDia ?>" alt="Hoy: <?= htmlspecialchars($peliculaDia['nombre']) ?>">
                    </a>
                    <section class="espacioFondo recomendacion-diaria-nombre">
                        <h4><?= htmlspecialchars($peliculaDia['nombre']) ?></h4>
                    </section>
                </div>
            <?php endif; ?>
        </div>
    </section>

    <div class="separador"></div>

    <section class="espacioFondo">
        <h1 class="titulo_cuerpo">Top 6 películas del mes</h1><br>
    </section>

    <section class="espacioTopPeliculas">
        <?php
        $i = 1; 
        foreach($peliculasTop as $pelicula):
            $imgUrl = (strpos($pelicula['srcImagen'], '/') === 0) ? $tmdb_path . $pelicula['srcImagen'] : "/img/peliculas/" . $pelicula['srcImagen'];
        ?>
            <div>
                <a href="./peliculaInformacion.php?id=<?=$pelicula['id']?>">
                    <img src="<?= $imgUrl ?>" alt="Top <?= $i ?> - <?= htmlspecialchars($pelicula['nombre']) ?>">
                </a>
            </div>
        <?php $i++; endforeach; ?>
    </section>
</div>

<?php require_once("./includes/footer.php"); ?>