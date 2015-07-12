<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>E-nurse - Φάρμακα</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<?PHP
	ensureLogin(true);
?>
<span id="top"></span>
<div>
<h1>Φάρμακα</h1>
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
	?>
    <form action="submit.php?drug=1" method="post">
    <center><h3>Προσθήκη νέας λήψης φαρμάκου</h3></center>
    <table width="100%" border="0">
  <tr>
    <td style="text-align: right">Φάρμακο</td>
    <td><input type="text" name="form_drugName" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Ώρα</td>
    <td><select name="form_drugTime">
    <option value="Morning">Πρωί</option>
    <option value="Midday">Μεσημέρι</option>
    <option value="Noon">Απόγευμα</option>
    </select><br>	</td>
  </tr>
  <tr>
    <td style="text-align: right">Ημέρα Λήψης</td>
    <td><input type="date" name="form_drugDate" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Ποσότητα</td>
    <td><input type="text" name="form_drugQuantity" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Αιτιολογία Λήψης</td>
	<td><textarea name='form_drugReason' cols='40' rows='10'/></textarea></td>
  </tr>
</table>
    <br><center><input type="submit" value="Υποβολή"/></center>
    </form>
    <?php
	echo "<h3>Ιστορικό Φαρμάκων</h3>";
	$sql = 'SELECT * FROM drugs WHERE clientID = '.$_SESSION['userID'].' ORDER BY date DESC';
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$id = $row["id"];
			$name = $row["name"];
			$time = $row["time"];
			$date = $row["date"];
			$reason = $row["reason"];
			$quantity = $row["quantity"];
			
			echo "<div class='announcement' id='drug$id'>";
			echo "<h2>Φάρμακο <span class='info'>$date</span></h2>";
			echo "<strong>Φάρμακο:</strong> $name<br>";
			if($time == "Morning"){
				$timeGr = "Πρωί";
			} else
			if($time == "Midday"){
				$timeGr = "Μεσημέρι";
			} else
			if($time == "Noon"){
				$timeGr = "Απόγευμα";
			}
			echo "<strong>Ώρα λήψης:</strong> $timeGr.<br>";
			echo "<strong>Ποσότητα:</strong> $quantity.<br>";
			echo "<strong>Αιτιολογία:</strong> $reason.<br>";
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