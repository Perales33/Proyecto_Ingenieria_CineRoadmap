<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> <?= $titulo ?> </title>
    <link rel="stylesheet" href="../css/styles.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>

<body>
    <main class="estructuraPrincipal">
        <!-- HEADER DE LA PAGINA, aqui mostraremos el nombre de la empresa y el logo -->
        <header class="header">
            <a href="/">
                <section class="fnc">
                    <h1 class="f">Cine</h1>
                    <span class="n">R</span>
                    <h1 class="c">oadMap</h1>
                </section>
            </a>
            <h2> <?= $slogan ?> </h2>
        </header>
        <!-- FIN HEADER DE LA PAGINA -->

        <!--BARRA DE NAVEGACIÓN~, aqui mostraremos la barra de navegación, esta tendra luego funcionalidad de menu Hamburguesa -->
        <?php require("./includes/nav.php") ?>
        <?php require("./actions/actualizarLogros.php") ?> <!-- Este include se encarga de actualizar los logros del usuario cada vez que se carga una página -->
        <!--FIN DE LA BARRA DE NAVEGACIÓN -->