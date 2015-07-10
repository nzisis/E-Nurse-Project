<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>E-nurse - Άσκηση</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<?PHP
	ensureLogin(true);
?>
<span id="top"></span>
<div>
<h1>Άσκηση</h1>
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
			echo "Είσαστε υπό επίβλεψη του/της Δρ.".$row["surname"]."<br>";
		} else {
			echo "Δεν βρέθηκαν τα στοιχεία του γιατρού που σας έχει αναλάβει. Παρακαλώ επικοινωνήστε με τη τεχνική υποστήριξη.<br>";
		}
	} else {
		echo "Δεν σας έχει αναλάβει προσωπικά κάποιος γιατρός.<br>";
	}
	echo '</span><br>';
	echo "Προσθέστε νέα άσκηση."; 
	?>
    <form action="submit.php?exercise=1" method="post">
    Είδος άσκησης: <select name="form_exerciseType">
    <?php
	$enum = "'Τρέξιμο','Γυμναστήριο','Άθλημα','Σεξ'";
	$enum = str_getcsv($enum, ',', "'");
	foreach ($enum as $vOption) {
		echo '<option value='.$vOption.'>'.$vOption.'</option>';
	}
	?>
    </select><br>
    Διάρκεια (σε λεπτά): <input name= "form_exerciseDuration" type="number" width="50px"/><br>	
    <input type="submit" value="Υποβολή"/>
    </form>
    <?php
	echo "<h3>Ιστορικό Ασκήσεων</h3>";
	$sql = 'SELECT * FROM exercise WHERE clientID = '.$_SESSION['userID'].' ORDER BY date DESC';
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$id = $row["id"];
			$date = $row["date"];
			$type = $row["type"];
			$duration = $row["duration"];
			
			echo "<div class='announcement' id='exc$id'>";
			echo "<h3>Άσκηση $date</h3>";
			echo "<strong>Τύπος:</strong> $type<br>";
			echo "<strong>Διάρκεια:</strong> $duration λεπτά.<br>";
			echo "</div>";
		}
	} else {
		echo "Δεν υπάρχουν ασκήσεις.";
	}
	$conn->close();
?>
<span class="returnTop"><a name="top" href="#top">Top</a></span>
</div>
</body>
</html>