<?php
    $titulo ="Competencias y Retos - CineRoadmap";
    $sloganPDPA="Toda gran historia comienza con un desafío. ¿Tienes el coraje para dirigir la tuya?";
?>
<?php require_once ("./includes/headerPDPA.php");?>
<?php require_once("./actions/auth_check.php"); ?>

<div class="cuerpoPDPA">
    <section class="gridDirectores">
        <?php require ("./components/gridRetos.php") ?>
    </section>
</div>

<?php require_once ("./includes/footer.php");?>