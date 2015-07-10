<?php 
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
function isCurrent($page){
	$current = $_SERVER['PHP_SELF'];
	if(strpos($current, $page) !== false){
		return ' id = "menuButtonSelf"';
	} else {
		return "";
	}
}
echo '<div class="menu">
<ul>
<li class="menuButton"><a href="index.php"'.isCurrent("/index.php").'>Αρχική Σελίδα</a></li>
<li class="menuButton"><a href="announcement.php"'.isCurrent("/announcement.php").'>Ανακοινώσεις</a></li>';
if(isset($_SESSION["LoggedRole"])){
	if($_SESSION["LoggedRole"] == "client"){
		echo '<li class="menuButton"><a href="profile.php"'.isCurrent("/profile.php").'>Προφίλ</a></li>
		<li class="menuButton"><a href="exercise.php"'.isCurrent("/exercise.php").'>Άσκηση</a></li>
		<li class="menuButton"><a href="nutrition.php"'.isCurrent("/nutrition.php").'>Διατροφή</a></li>';
	} else if($_SESSION["LoggedRole"] == "doctor"){
		echo '<li class="menuButton"><a href="doctor.php"'.isCurrent("/doctor.php").'>Ασθενείς</a></li>';
	}
}
echo '
</ul>
</div>';
?>