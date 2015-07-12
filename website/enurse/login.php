<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<div>
<h1>Είσοδος</h1>
</div>
<div class="mainContent">
<center>
<?php
	if(isset($_GET["falselogin"])){
		echo "<span class='error'>Πρέπει πρώτα να συνδεθείτε με το λογαριασμό σας.</span><br>";
	}
?>
<?php
	if(isset($_POST['create_username'])){
		$conn = dbConn();
		$sql = "SET CHARSET 'utf8'";
		$result = $conn->query($sql);
		
		$cUsername = $_POST['create_username'];
		
		$sql = "SELECT id FROM users WHERE username='".$cUsername."'";
		$result = $conn->query($sql);
		if($result->num_rows > 0){
			echo "<span class='error'>Το όνομα λογαριασμού είναι ήδη σε χρήση.</span>";
			die();
		}
		$cPassword = $_POST['create_password'];
		$cName = $_POST['create_name'];
		$cSurname = $_POST['create_surname'];
		if(isset($_POST['create_sex_male'])){
			$cSex = "1";
		} else {
			$cSex = "0";
		}
		$cWeight = $_POST['create_weight'];
		$cHeight = $_POST['create_height'];
		$cAge = $_POST['create_age'];
		
		$sql = "SELECT id FROM users WHERE type='doctor'";
		$result = $conn->query($sql);
		$cDoctor = -1;
		while($row = $result->fetch_assoc()) {
			if($_POST['create_doctor'] == "create_doctor_".$row['id']){
				$cDoctor = $row['id'];
				break;
			}
		}
		$cHistory = $_POST['create_history'];
		
		$sql = "INSERT INTO users (name,surname,username,password,type) VALUES 
		(N'".$cName."',N'".$cSurname."','".$cUsername."','".$cPassword."','client')";
		$result = $conn->query($sql);
		if($result){
			$Usql = "SELECT id FROM users WHERE username='".$cUsername."'";
			$Uresult = $conn->query($Usql);
			$Urow = $Uresult->fetch_assoc();
			$cID = $Urow['id'];
			
			$Psql = "INSERT INTO patientcard (clientID,age,male,history,weight,height) VALUES
			('".$cID."','".$cAge."','".$cSex."',N'".$cHistory."','".$cWeight."','".$cHeight."')";
			$Presult = $conn->query($Psql);
			if($Presult){
				$Csql = "INSERT INTO clients (clientID,doctorID) VALUES ('".$cID."','".$cDoctor."')";
				$Cresult = $conn->query($Csql);
				if($Cresult){
					echo "Η δημιουργία του λογαριασμού ήταν επιτυχής.";
				} else {
					echo "<span class='error'>Δεν ήταν δυνατή η δημιουργία του λογαριασμού.</span>";
				}
			} else {
				echo "<span class='error'>Δεν ήταν δυνατή η δημιουργία του λογαριασμού.</span>";
			}
 		} else {
			echo "<span class='error'>Δεν ήταν δυνατή η δημιουργία του λογαριασμού.</span>";
		}
		// TODO: Check if ok
	}
?>
<?php
	if(isset($_POST["login_name"])){
		$conn = dbConn();
		$sql = "SET CHARSET 'utf8'";
		$result = $conn->query($sql);
		$sql = "SELECT * FROM users WHERE username='".$_POST["login_name"]."' AND password='".$_POST["login_password"]."'";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			$row = $result->fetch_assoc();
			$_SESSION["LoggedUsername"] = $row["name"];
			$_SESSION["LoggedRole"] = $row["type"];
			$_SESSION["logged"] = true;
			$_SESSION["userID"] = $row["id"];
			$conn->close();
			echo "Επιτυχής είσοδος! Πήγαινε στην <a href='index.php'>κύρια σελίδα.</a>";
			die();
		} else {
			echo "<span class='error'>Λάθος στοιχεία. Δοκίμασε ξανά.</span><br>";
			$_SESSION["LoggedUsername"] = NULL;
			$_SESSION["LoggedRole"] = NULL;
			$_SESSION["logged"] = NULL;
			$_SESSION["userID"] = NULL;
		}
	}
?>
<form action="login.php" method="POST">
<table width="50%" border="0">
  <tr>
    <td style="text-align: right">Όνομα λογαριασμού:</td>
    <td><input type="text" name="login_name" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Κωδικός</td>
    <td><input type="password" name="login_password" size="20"></td>
  </tr>
</table>
<br>
<input type="submit" value="Είσοδος"> 
</form><br>
<form action="create.php" method="POST">
<input type="submit" value="Δημιουργία Λογαριασμού">
</form>
</center>
</div>
</body>
</html>
