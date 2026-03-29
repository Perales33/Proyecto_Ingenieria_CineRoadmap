<?php
    $titulo = "Comunidad - CineRoadmap";
    $sloganPDPA = "Actuaciones que inspiran, historias que perduran. Descubre el arte de la interpretación.";
?>
<?php require_once("./actions/auth_check.php"); ?>
<?php require_once("./includes/headerPDPA.php"); ?>
    <div class="cuerpoPDPA">
        <section class="gridActor">
            <?php require_once("./components/gridComunidad.php"); ?>
        </section>
    </div>
<?php require_once("./includes/footer.php"); ?>