<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>E-nurse</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<?PHP
	ensureLogin(true);
?>
<span id="top"></span>
<div>
<h1>Διαχείρηση περιεχομένου</h1>
</div>
<?php
	include('menu.php');
?>
<div class="mainContent">
<?php
	if(isset($_GET["exercise"])){
		if($_SESSION['LoggedRole'] == "doctor"){
			echo "Αυτή η ενέργεια είναι διαθέσιμη μόνο για ασθενείς.";
			die();
		} else {
			if(isset($_POST["form_exerciseType"]) && isset($_POST["form_exerciseDuration"]) && isset($_SESSION["userID"])){
				$userid = $_SESSION["userID"];
				$duration = $_POST["form_exerciseDuration"];
				$type = $_POST["form_exerciseType"];
				$sql = "INSERT INTO exercise (type,duration,clientID) VALUES (N'$type','$duration','$userid')";
				$conn = dbConn();
				$result = $conn -> query($sql);
				if($result){
					echo "Η άσκηση προστέθηκε.";
				} else {
					echo "Δεν ήταν επιτυχής η προσθήκη της άσκησης";
				}
				$conn -> close();
			} else {
				echo "Δεν στάλθηκαν στοιχεία.";
				die();
			}
		}
	} else if(isset($_GET["food"])){
		if($_SESSION['LoggedRole'] == "doctor"){
			echo "Αυτή η ενέργεια είναι διαθέσιμη μόνο για ασθενείς.";
			die();
		} else {
			if(isset($_POST["form_foodFood"]) && isset($_POST["form_foodTime"]) && isset($_SESSION["userID"])){
				$userid = $_SESSION["userID"];
				$food = $_POST["form_foodFood"];
				$time = $_POST["form_foodTime"];
				$sql = "INSERT INTO nutrition (meal,mealTime,clientID) VALUES (N'$food','$time','$userid')";
				$conn = dbConn();
				$result = $conn -> query($sql);
				if($result){
					echo "To γεύμα προστέθηκε.";
				} else {
					echo "Δεν ήταν επιτυχής η προσθήκη του γεύματος";
				}
				$conn -> close();
			} else {
				echo "Δεν στάλθηκαν στοιχεία.";
				die();
			}
		}
	} else if(isset($_GET["drug"])){
		if($_SESSION['LoggedRole'] == "doctor"){
			echo "Αυτή η ενέργεια είναι διαθέσιμη μόνο για ασθενείς.";
			die();
		} else {
			if(isset($_POST["form_drugName"]) && isset($_POST["form_drugTime"]) && isset($_SESSION["userID"])){
				$userid = $_SESSION["userID"];
				$name = $_POST["form_drugName"];
				$time = $_POST["form_drugTime"];
				$date = $_POST["form_drugDate"];
				$quantity = $_POST["form_drugQuantity"];
				$reason = $_POST["form_drugReason"];
				$sql = "INSERT INTO drugs (clientID,name,time,date,reason,quantity) VALUES ($userid,N'$name','$time','$date',N'$reason',N'$quantity')";
				$conn = dbConn();
				$result = $conn -> query($sql);
				if($result){
					echo "Η λήψη φαρμάκου προστέθηκε.";
				} else {
					echo "Δεν ήταν επιτυχής η προσθήκη της λήψης φαρμάκου.";
				}
				$conn -> close();
			} else {
				echo "Δεν στάλθηκαν στοιχεία.";
				die();
			}
		}
	}
?>
<span class="returnTop"><a name="top" href="#top">Top</a></span>
</div>
</body>
</html>