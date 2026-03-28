<?php
    $titulo ="Logros";
    $sloganPDPA="Completa logros,desbloquea insignias y demuestra tu pasión por el cine.";
?>
<?php require_once("./actions/auth_check.php"); ?>
<? require_once ("./includes/headerPDPA.php");?>

<div class="cuerpoPDPA">
    <section class="gridDirectores">
        <? require ("./components/gridLogros.php") ?>
    </section>

</div>

<? require_once ("./includes/footer.php");?>