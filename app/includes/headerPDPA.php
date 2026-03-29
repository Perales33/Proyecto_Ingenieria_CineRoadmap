<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?=$titulo?></title>
    <link rel="stylesheet" href="../css/styles.css">
    <? session_start()?>
</head>
<body >
    <main class="estructuraPDPA">
        <!-- HEADER DE LA PAGINA, aqui mostraremos el nombre de la empresa y el logo -->
        <header class="headerPDPA">
            <a href="/">
                <section class="fncPDPA">
                    <h1 class="fPDPA">C</h1><br>
                    <h1 class="nPDPA">R</h1>
                    <h1 class="cPDPA"></h1>
                </section>
            </a>
            <h2><?=$sloganPDPA?></h2>
        </header>
        <!-- FIN HEADER DE LA PAGINA -->

        <!--BARRA DE NAVEGACIÓN~, aqui mostraremos la barra de navegación, esta tendra luego funcionalidad de menu Hamburguesa -->
        <?php require("./includes/nav.php") ?> 
        <?php require("./actions/actualizarLogros.php") ?> <!-- Este include se encarga de actualizar los logros del usuario cada vez que se carga una página -->
        <!--FIN DE LA BARRA DE NAVEGACIÓN -->