<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Δημιουργία Λογαριασμού</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<div>
<h1>Δημιουργία Λογαριασμού</h1>
</div>
<div class="mainContent">
<center>
<form action="login.php" method="POST">
<table width="50%" border="0">
  <tr>
    <td style="text-align: right">Όνομα Λογαριασμού</td>
    <td><input type="text" name="create_username" size="20"><br>
*Μόνο λατινικούς χαρακτήρες</td>
  </tr>
  <tr>
    <td style="text-align: right">Κωδικός</td>
    <td><input type="password" name="create_password" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Όνομα Ασθενούς</td>
    <td><input type="text" name="create_name" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Επίθετο Ασθενούς</td>
    <td><input type="text" name="create_surname" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Φύλο</td>
    <td><input type="radio" name="create_sex_male" size="20">Άνδρας<br>
		<input type="radio" name="create_sex_female" size="20">Γυναίκα</td>
  </tr>
  <tr>
    <td style="text-align: right">Βάρος</td>
    <td><input type="text" name="create_weight" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Ύψος</td>
    <td><input type="text" name="create_height" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Ηλικία</td>
    <td><input type="text" name="create_age" size="20"></td>
  </tr>
  <tr>
    <td style="text-align: right">Επιβλέπων Ιατρός</td>
    <td>
    <?php
		$conn = dbConn();
		$sql = "SET CHARSET 'utf8'";
		$result = $conn->query($sql);
		$sql = "SELECT * FROM users WHERE type='doctor'";
		$result = $conn->query($sql);
		echo "<select name='create_doctor'>";
		while($row = $result->fetch_assoc()) {
			echo "<option value='create_doctor_".$row['id']."'>".$row['name']." ".$row['surname']."</option>";
			$_SESSION["LoggedUsername"] = $row["name"];
		}
	?>
    </td>
  </tr>
  <tr>
    <td style="text-align: right">Ιστορικό</td>
    <td><textarea name='create_history' cols='40' rows='10'/></textarea><br>*Παρακαλούμε χωρίστε το ιστορικό με - (παύλα)</td>
  </tr>
</table>
<br>
<input type="submit" value="Δημιουργία Λογαριασμού">
</form><br>

<form action="index.php" method="get">
<input type="submit" value="Ακύρωση">
</form>
</center>
</div>
</body>
</html>
