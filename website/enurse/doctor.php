<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>E-nurse - Ασθενείς</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<?PHP
	ensureLogin(true);
?>
<span id="top"></span>
<div>
<h1>Λίστα Ασθενών</h1>
</div>
<?php
	include('menu.php');
?>
<div class="mainContent">
<?php
	if($_SESSION['LoggedRole'] == "client"){
		echo "Αυτή η σελίδα είναι διαθέσιμη μόνο για γιατρούς.";
		die();
	}
	$conn = dbConn();
	$sql = "SET CHARSET 'utf8'";
	$result = $conn->query($sql);
	$sql = "SELECT clientID FROM clients WHERE doctorID=".$_SESSION['userID'];
	$result = $conn->query($sql);
	echo '<span class="info">';
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$sqlDoc = "SELECT * FROM users WHERE id=".$row["clientID"];
			$resultDoc = $conn->query($sqlDoc);
			if($resultDoc->num_rows > 0){
				$rowDoc = $resultDoc->fetch_assoc();
				$name = $rowDoc["name"];
				$surname = $rowDoc["surname"];
				echo "<div>$name $surname [<a href='clientinfo.php?client=".$row["clientID"]."&exercise=1'>Ασκήσεις</a>] [<a href='clientinfo.php?client=".$row["clientID"]."&nutrition=1'>Διατροφή</a>]</div>";
			} else {
				echo "Δεν βρέθηκαν τα στοιχεία του ασθενή ".$row['clientID'].". Παρακαλώ επικοινωνήστε με τη τεχνική υποστήριξη.<br>";
			}
		}
	} else {
		echo "Δεν έχετε υπό την επίβλεψή σας κανένα ασθενή.<br>";
	}
	echo '</span>';
	$conn -> close();
?>
</div>
</body>
</html>