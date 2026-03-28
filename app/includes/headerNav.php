<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?=$titulo?></title>
    <link rel="stylesheet" href="../css/styles.css">
    <? session_start() ?>
</head>
<body>
    <main class="estructuraPremios">
        <nav class="navHamburger" role="navigation">
                <div id="menuToggle">
                    <input type="checkbox" />
                    <span></span>
                    <span></span>
                    <span></span>

                <? require("./includes/navMovil.php") ?>
                </div>
        </nav>