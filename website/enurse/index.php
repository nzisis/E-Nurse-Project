<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>E-nurse - Αρχική</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>

<?php ensureLogin(false); ?>
<div>
<h1>Enurse</h1>
<?php
	include('menu.php');
?>
</div>
<div class="mainContent">
<p>
Καλωσήρθατε στο Enurse.</p>
<p>[βλαβλα]</p>
</div>
</body>
</html>
