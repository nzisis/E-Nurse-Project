<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>E-nurse - Διατροφή</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<?PHP
	ensureLogin(true);
?>
<span id="top"></span>
<div>
<h1>Διατροφή</h1>
</div>
<?php
	include('menu.php');
?>
<div class="mainContent">
<?php
	if($_SESSION['LoggedRole'] == "doctor"){
		echo "Αυτή η σελίδα είναι διαθέσιμη μόνο για ασθενείς.";
		die();
	}
	$conn = dbConn();
	$sql = "SET CHARSET 'utf8'";
	$result = $conn->query($sql);
	$sql = "SELECT doctorID FROM clients WHERE clientID=".$_SESSION['userID'];
	$result = $conn->query($sql);
	echo '<span class="info">';
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$sqlDoc = "SELECT surname FROM users WHERE id=".$row["doctorID"];
		$resultDoc = $conn->query($sqlDoc);
		if($resultDoc->num_rows > 0){
			$row = $resultDoc->fetch_assoc();
			echo "<center>Είσαστε υπό επίβλεψη του/της Δρ.".$row["surname"]."</center><br>";
		} else {
			echo "Δεν βρέθηκαν τα στοιχεία του γιατρού που σας έχει αναλάβει. Παρακαλώ επικοινωνήστε με τη τεχνική υποστήριξη.<br>";
		}
	} else {
		echo "Δεν σας έχει αναλάβει προσωπικά κάποιος γιατρός.<br>";
	}
	echo '</span><br>';
	echo "Προσθέστε νέο γεύμα."; 
	?>
    <form action="submit.php?food=1" method="post">
    Γεύμα: <input name= "form_foodFood" type="text"/><br>	
    Ώρα γεύματος: <input name= "form_foodTime" type="time"/><br>	
    <input type="submit" value="Υποβολή"/>
    </form>
    <?php
	echo "<h3>Ιστορικό Γευμάτων</h3>";
	$sql = 'SELECT * FROM nutrition WHERE clientID = '.$_SESSION['userID'].' ORDER BY date DESC';
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$id = $row["id"];
			$date = $row["date"];
			$meal = $row["meal"];
			$mealTime = $row["mealTime"];
			
			echo "<div class='announcement' id='food$id'>";
			echo "<h2>Γεύμα <span class='info'>$date</span></h2>";
			echo "<strong>Γεύμα:</strong> $meal<br>";
			echo "<strong>Ώρα γεύματος:</strong> $mealTime.<br>";
			echo "</div>";
		}
	} else {
		echo "Δεν υπάρχουν γεύματα.";
	}
	$conn->close();
?>
<span class="returnTop"><a name="top" href="#top">Top</a></span>
</div>
</body>
</html>