<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>E-nurse - Προφίλ</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<?PHP
	ensureLogin(true);
?>
<span id="top"></span>
<div>
<h1>Λογαριασμός</h1>
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
	if(isset($_GET["update"])){
		$uname = $_POST["update_name"];
		$usurname = $_POST["update_surname"];
		$uage = $_POST["update_age"];
		$umale = $_POST["update_male"];
		$uweight = $_POST["update_weight"];
		$uheight = $_POST["update_height"];
		$uhistory = $_POST["update_history"];
		
		$sqlupdate= "UPDATE patientcard SET age='$uage', male='$umale', history=N'$uhistory', weight='$uweight', height='$uheight' WHERE clientID=".$_SESSION["userID"];
		$resultupdate= $conn->query($sqlupdate);
		$sqlupdateUser= "UPDATE users SET name=N'$uname', surname=N'$usurname' WHERE id=".$_SESSION["userID"];
		$resultupdateUser= $conn->query($sqlupdateUser);
		if($resultupdate){
			echo "<span class='info'>Επιτυχής ενημέρωση λογαριασμού.</span>";
		} else {
			echo "<span class='error'>Δεν ήταν επιτυχής η ενημέρωση του λογαριασμού.</span>";
		}
	}
	$sqlclient = "SELECT * FROM users WHERE id=".$_SESSION["userID"];
	$resultclient = $conn->query($sqlclient);
	$sqlclientCard = "SELECT * FROM patientcard WHERE clientID=".$_SESSION["userID"];
	$resultclientCard = $conn->query($sqlclientCard);
	if($resultclient->num_rows >0 & $resultclientCard->num_rows >0){
		$rowClient = $resultclient->fetch_assoc();
		$rowClientCard = $resultclientCard->fetch_assoc();
		$name = $rowClient["name"];
		$surname = $rowClient["surname"];
		$age = $rowClientCard["age"];
		$male = intval($rowClientCard["male"]);
		$weight = intval($rowClientCard["weight"]);
		$height = intval($rowClientCard["height"]);
		$history = $rowClientCard["history"];
    	echo "<form action='?update=1' method='post'>";
		echo "<span class='patientCard'>";
		echo "<span class='patientCardName'>Όνομα: <input  name= 'update_name' type='text' value='$name' style='text-align: center'></span>";
		echo "<span class='patientCardName'>Επώνυμο: <input  name= 'update_surname' type='text' value='$surname' style='text-align: center'></span>";
		echo "<br>";
		echo "<span class='patientCardItem'>Φύλλο: ";
		echo "<select name='update_male' size='2'>";
		if($male == 1){
			echo "<option value='1' selected='selected'>Άνδρας</option>";
			echo "<option value='0'>Γυναίκα</option>";
		} else {
			echo "<option value='1'>Άνδρας</option>";
			echo "<option value='0' selected='selected'>Γυναίκα</option>";
		}
		echo "</span></select><br><br>";
		echo "<span class='patientCardItem'>Ηλικία: <input  name= 'update_age' type='text' value='$age' style='text-align: center'></span>";
		echo "<span class='patientCardItem'>Βάρος: <input  name= 'update_weight' type='text' value='$weight' style='text-align: center'></span>";
		echo "<span class='patientCardItem'>Ύψος: <input  name= 'update_height' type='text' value='$height' style='text-align: center'></span>";
		echo "<span class='patientCardName'>Ιστορικό</span>";
		echo "<span class='patientCardHistory'><textarea name='update_history' cols='40' rows='10'/>$history</textarea></span>";
		echo "<span class='patientCardHistory'>*Παρακαλούμε χωρίστε το ιστορικό με - (παύλα)</span>";
		echo "<br>";
		echo "<input type='submit' value='Ενημέρωση'/>";
		echo "</form>";
	} else {
		echo "Δεν βρέθηκαν τα στοιχεία του λογαριασμού σας. Παρακαλούμε επικοινωνήστε με τη τεχνική υποστήριξη.";
		die();
	}
	$conn->close();
?>
</div>
</body>
</html>