

        <?php
        error_reporting(E_ALL);
        ini_set('display_errors', 1);
        require_once("../initdb.php");
        if ($conn) {
            $query = "SELECT nombre, genero, sinapsis, fechaEstreno, duracion, presupuesto, recaudacion, srcImagen FROM peliculas";
            $result = mysqli_query($conn, $query);

            if ($result) {
                while ($row = mysqli_fetch_assoc($result)) {
                    echo '<div class="contenedorPeliculas">';
                        echo '<div class="posicionFrontal">';
                            echo '<img class="imagenFrontal" src="' . $row['srcImagen'] . '" alt="' . $row['nombre'] . '">';
                        echo '</div>';
                        echo '<div class="posicionCarta">';
                            echo '<section class="informacionPelicula">';
                                echo '<h1>' . $row['nombre'] . '</h1>';
                                echo '<div class="col2">';
                                    echo '<h2>Género</h2>';
                                    echo '<p>' . $row['genero'] . '</p>';
                                echo '</div>';
                                echo '<div class="col3">';
                                    echo '<h2>Sinopsis</h2>';
                                    echo '<p>' . $row['sinapsis'] . '</p>';
                                echo '</div>';
                                echo '<div class="col4">';
                                    echo '<h2>Fecha de Estreno</h2>';
                                    echo '<p>' . $row['fechaEstreno'] . '</p>';
                                echo '</div>';
                                echo '<div class="col5">';
                                    echo '<h2>Duración</h2>';
                                    echo '<p>' . $row['duracion'] . '</p>';
                                echo '</div>';
                                echo '<div class="col6">';
                                    echo '<h2>Presupuesto</h2>';
                                    echo '<p>' . $row['presupuesto'] . '</p>';
                                echo '</div>';
                                echo '<div class="col7">';
                                    echo '<h2>Recaudación</h2>';
                                    echo '<p>' . $row['recaudacion'] . '</p>';
                                echo '</div>';
                            echo '</section>';
                        echo '</div>';
                    echo '</div>';
                }
            } else {
                echo "Error al ejecutar la consulta: " . mysqli_error($conn);
            }

            mysqli_close($conn);
        } 
    

           
    
    ?>
</div>


<div class="contenedorPeliculas animacionDefault">
    <img class="imgPeliculaActive imgPelicula" src="<?=$imgPelicula?>" alt="Descripción de la imagen">
    <img class="imgPeliculaDetras imgPelicula" src="<?=$imgDetras?>" alt="">
    <div class="overlay">
        <div class="overlayDefault">
            <h1 class="nombreTitulo"><?=$tituloPelicula?>></h1>
            <br>
            <span class="rating">
                <h2>RATING</h2>
                <br>
            </span>
            <div class="contenedorStars"><?=$valoracionPelicula?></div>
            <h4>Click para más información</h4>
        </div>

        <div class="overlayFlip">
            <h1><?=$tituloPelicula?>/h1>
            <br>
            <h2>Sinapsis</h2>
            <p> <?=$sinapsis ?></p>
            <br>
            <h2>Equipo de rodaje</h2>
            <h3>Producido por: <?=$productores?> </h3>
            <h3>Dirección por: <?=$directores?> </h3>
            <h3>Actores principales por: <?=$actores?> </h3>
            <h2>Género</h2>
            <h3><?=$genero?></h3>
            <h4>Click para volver a la calificación</h4>
        </div>   
    </div>
 </div>