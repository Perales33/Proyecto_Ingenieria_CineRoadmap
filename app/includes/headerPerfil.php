<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> <?= htmlspecialchars($titulo) ?> </title>
    <link rel="stylesheet" href="../css/styles.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>

<body>
    <main class="estructuraPerfil">
        <header class="header">
            <a href="/" style="text-decoration: none;">
                <section class="fnc">
                    <h1 class="f">Cine</h1>
                    <span class="n">R</span>
                    <h1 class="c">oadMap</h1>
                </section>
            </a>
        </header>
        
        <?php require("./includes/nav.php"); ?>
        <?php require("./actions/actualizarLogros.php"); ?> <!-- Este include se encarga de actualizar los logros del usuario cada vez que se carga una página -->