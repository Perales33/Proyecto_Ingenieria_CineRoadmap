<?php
    $titulo ="Logros - CineRoadmap";
    $sloganPDPA="Completa logros, desbloquea insignias y demuestra tu pasión por el cine.";
    require_once("./actions/auth_check.php");
    require_once("./includes/headerPDPA.php");
?>

<div class="cuerpoPDPA">
    <!-- Mantenemos la section pero le damos una clase de layout específica -->
    <section class="gridDirectores layoutLogros">
        <?php require("./components/gridLogros.php"); ?>
    </section>
</div>

<?php require_once("./includes/footer.php"); ?>