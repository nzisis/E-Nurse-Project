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
<h1>Κάρτα Ασθενούς</h1></div>
<?php
	include('menu.php');
?>
<div class="mainContent">
<?php
	if($_SESSION['LoggedRole'] == "client"){
		echo "Αυτή η σελίδα είναι διαθέσιμη μόνο για γιατρούς.";
		die();
	}
	if(isset($_GET["client"])){
		$conn = dbConn();
		$sql = "SET CHARSET 'utf8'";
		$result = $conn->query($sql);
		$sql = "SELECT id FROM clients WHERE doctorID=".$_SESSION['userID']." AND clientID=".$_GET["client"];
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			$sqlclient = "SELECT * FROM users WHERE id=".$_GET["client"];
			$resultclient = $conn->query($sqlclient);
			$sqlclientCard = "SELECT * FROM patientcard WHERE clientID=".$_GET["client"];
			$resultclientCard = $conn->query($sqlclientCard);
			if($resultclient->num_rows >0 & $resultclientCard->num_rows >0){
				$rowClient = $resultclient->fetch_assoc();
				$rowClientCard = $resultclientCard->fetch_assoc();
				$name = $rowClient["name"];
				$surname = $rowClient["surname"];
				$age = $rowClientCard["age"];
				$male = intval($rowClientCard["male"]);
				$weight = intval($rowClientCard["weight"]);
				$history = $rowClientCard["history"];
				if($history != NULL) $historyItems = explode("-",$history);
				else $historyItems = NULL;
				echo "<span class='patientCard'>";
				echo "<span class='patientCardName'>$name $surname </span>";
				echo "<span class='patientCardItem'>Φύλλο: ";
				if($male == 1){
					echo "Άνδρας";
				} else {
					echo "Γυναίκα";
				}
				echo "</span>";
				echo "<span class='patientCardItem'>Ηλικία: $age</span>";
				echo "<span class='patientCardItem'>Βάρος: $weight</span><br>";
				echo "<span class='patientCardName'>Ιστορικό</span>";
				if($historyItems != NULL & sizeof($historyItems) > 0){
					foreach($historyItems as $hitem){
						echo "<span class='patientCardItem'>$hitem</span>";
					}
				} else {
					echo "<span class='patientCardItem'>Ο πελάτης δεν έχει ιστορικό</span>";
				}
				echo "<br>[<a href='clientinfo.php?client=".$_GET["client"]."&exercise=1'>Ασκήσεις</a>] [<a href='clientinfo.php?client=".$_GET["client"]."&nutrition=1'>Διατροφή</a>]";
				echo "</span>";
			} else {
				echo "Δεν βρέθηκαν τα στοιχεία του ασθενούς. Παρακαλούμε επικοινωνήστε με τη τεχνική υποστήριξη.";
				die();
			}
			if(isset($_GET["exercise"])){
				$sqlinfo = "SELECT * FROM exercise WHERE clientID=".$_GET["client"];
				$resultinfo = $conn->query($sqlinfo);
				if($resultinfo->num_rows > 0){
					echo "<span class='info'>Ασκήσεις</span>";
					while($rowinfo = $resultinfo->fetch_assoc()) {
						$id = $rowinfo["id"];
						$date = $rowinfo["date"];
						$type = $rowinfo["type"];
						$duration = $rowinfo["duration"];
						
						echo "<div class='announcement' id='exc$id'>";
						echo "<h3>Άσκηση $date</h3>";
						echo "<strong>Τύπος:</strong> $type<br>";
						echo "<strong>Διάρκεια:</strong> $duration λεπτά.<br>";
						echo "</div>";
					}
				} else {
					echo "Ο ασθενής δεν έχει κάνει καμία άσκηση.";
				}
			} else if(isset($_GET["nutrition"])){
				$sqlinfo = "SELECT * FROM nutrition WHERE clientID=".$_GET["client"];
				$resultinfo = $conn->query($sqlinfo);
				if($resultinfo->num_rows > 0){
					echo "<span class='info'>Διατροφή</span>";
					while($rowinfo = $resultinfo->fetch_assoc()) {
						$id = $rowinfo["id"];
						$date = $rowinfo["date"];
						$meal = $rowinfo["meal"];
						$mealTime = $rowinfo["mealTime"];
						
						echo "<div class='announcement' id='food$id'>";
						echo "<h3>Γεύμα $date</h3>";
						echo "<strong>Γεύμα:</strong> $meal<br>";
						echo "<strong>Ώρα γεύματος:</strong> $mealTime.<br>";
						echo "</div>";
					}
				} else {
					echo "Ο ασθενής δεν έχει εισάγει ακόμη γεύματα στο λογαριασμό του.";
				}
			} else {
				echo "Δεν έχει επιλεχθεί το είδος πληροφορίας που ζητήθηκε.";
			}
		} else {
			echo "<span class='info'>Ο επιλεγμένος ασθενής δεν είναι υπό τη δική σας επίβλεψη.</span><br>";
		}
		$conn -> close();
	} else {
		echo "Δεν έχει επιλεχθεί ασθενής.";
	}
?>
</div>
</body>
</html>