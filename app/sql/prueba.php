<?php
// 1. Configuración de entorno
set_time_limit(0); 
ini_set('memory_limit', '512M');
require("./initdb.php"); 

$apiKey = "8301a21598f8b45668d5711a814f01f6";
$paginasTotales = 500; 

echo "<h2>🚀 Iniciando Importación (ID de TMDB como ID principal)</h2>";

// --- PASO A: GÉNEROS ---
$urlGen = "https://api.themoviedb.org/3/genre/movie/list?api_key=$apiKey&language=es-ES";
$resGen = json_decode(@file_get_contents($urlGen), true);
if (isset($resGen['genres'])) {
    foreach ($resGen['genres'] as $g) {
        $idG = intval($g['id']);
        $nomG = mysqli_real_escape_string($conn, $g['name']);
        mysqli_query($conn, "INSERT IGNORE INTO generos (id, nombre) VALUES ($idG, '$nomG')");
    }
}

// --- PASO B: BUCLE DE PÁGINAS ---
for ($página = 1; $página <= $paginasTotales; $página++) {
    
    $url = "https://api.themoviedb.org/3/movie/popular?api_key=$apiKey&language=es-ES&page=$página";
    $res = json_decode(@file_get_contents($url), true);

    if (!isset($res['results'])) continue;

    foreach ($res['results'] as $movie) {
        if (empty($movie['poster_path']) || empty($movie['overview'])) continue;

        // ESTE ES TU ID PRINCIPAL (EL DE TMDB)
        $peliId = intval($movie['id']);
        
        // Llamada para obtener duración (runtime)
        $urlDetalle = "https://api.themoviedb.org/3/movie/$peliId?api_key=$apiKey&language=es-ES";
        $detalles = json_decode(@file_get_contents($urlDetalle), true);
        
        $duracion = isset($detalles['runtime']) ? intval($detalles['runtime']) : 0;
        $lenguaje = mysqli_real_escape_string($conn, $movie['original_language']);
        $titulo   = mysqli_real_escape_string($conn, $movie['title']);
        $anio     = !empty($movie['release_date']) ? substr($movie['release_date'], 0, 4) : "NULL";
        $sinopsis = mysqli_real_escape_string($conn, $movie['overview']);
        $img      = $movie['poster_path']; 

        // 1. Insertar Película usando el ID de la API como 'id' en tu tabla
        $valAnio = ($anio === "NULL") ? "NULL" : "'$anio'";
        
        // IMPORTANTE: Aquí insertamos $peliId en la columna 'id'
        $sqlPeli = "INSERT IGNORE INTO peliculas (id, nombre, anio, duracion, lenguaje_orig, sinapsis, srcImagen) 
                    VALUES ($peliId, '$titulo', $valAnio, $duracion, '$lenguaje', '$sinopsis', '$img')";
        
        if (mysqli_query($conn, $sqlPeli)) {
            // 2. Relacionar Géneros
            if (isset($movie['genre_ids'])) {
                foreach ($movie['genre_ids'] as $generoID) {
                    mysqli_query($conn, "INSERT IGNORE INTO pelicula_generos (pelicula_id, genero_id) VALUES ($peliId, $generoID)");
                }
            }

            // 3. Créditos
            $urlCredits = "https://api.themoviedb.org/3/movie/$peliId/credits?api_key=$apiKey";
            $credits = json_decode(@file_get_contents($urlCredits), true);

            if ($credits) {
                // Directores
                if (isset($credits['crew'])) {
                    foreach ($credits['crew'] as $person) {
                        if ($person['job'] == 'Director') {
                            $nomDir = mysqli_real_escape_string($conn, $person['name']);
                            mysqli_query($conn, "INSERT IGNORE INTO directores (nombre) VALUES ('$nomDir')");
                            $resD = mysqli_query($conn, "SELECT id FROM directores WHERE nombre = '$nomDir'");
                            $idDir = mysqli_fetch_assoc($resD)['id'];
                            mysqli_query($conn, "INSERT IGNORE INTO pelicula_directores (pelicula_id, director_id) VALUES ($peliId, $idDir)");
                        }
                    }
                }

                // Actores (Top 5)
                if (isset($credits['cast'])) {
                    for ($i = 0; $i < 5; $i++) {
                        if (!isset($credits['cast'][$i])) break;
                        $nomAct = mysqli_real_escape_string($conn, $credits['cast'][$i]['name']);
                        mysqli_query($conn, "INSERT IGNORE INTO actores (nombre) VALUES ('$nomAct')");
                        $resA = mysqli_query($conn, "SELECT id FROM actores WHERE nombre = '$nomAct'");
                        $idAct = mysqli_fetch_assoc($resA)['id'];
                        mysqli_query($conn, "INSERT IGNORE INTO pelicula_actores (pelicula_id, actor_id) VALUES ($peliId, $idAct)");
                    }
                }
            }
        }
    }
    if ($página % 5 == 0) { echo "✅ Página $página lista.<br>"; flush(); }
    usleep(200000); 
}
echo "<h3>🏁 ¡Hecho! Datos guardados con ID de TMDB.</h3>";
?>