<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<?=$fuenteCss?>">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <title><?=$titulo?></title>
</head>
<body>
  <nav class="navHamburger" role="navigation">
    <div id="menuToggle">
        <input type="checkbox" />
        <span></span>
        <span></span>
        <span></span>

    <? require("./includes/navMovil.php") ?>
    </div>
  </nav>