<?php 
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
function dbConn(){
	$servername = "localhost";
	$username = "username";
	$password = "password";
	$db = "database name";
	
	$filecontents = file_get_contents('dbconn.txt') or die("Unable to open server configuration file!");
	$words = preg_split('/[\s]+/', $filecontents, -1, PREG_SPLIT_NO_EMPTY);
	
	$servername = $words[0];
	$username = $words[1];
	$password = $words[2];
	$db = $words[3];
	
	// Create connection
	$conn = new mysqli($servername,$username,$password,$db);
	
	// Check connection
	if ($conn->connect_error) {
		echo "Could not connect to database";
		echo "Server: ".$servername;
		echo "Username: ".$username;
		echo "Password: ".$password;
		echo "Database: ".$db;
		return NULL;
	} else {
		return $conn;
	}
}
function ensureLogin($forceDie){
	if(isset($_GET["logout"])){
		if($_GET["logout"]){
			$_SESSION["LoggedUsername"] = NULL;
			$_SESSION["LoggedRole"] = NULL;
			$_SESSION["logged"] = NULL;
			$_SESSION["userID"] = NULL;
			$_GLOBALS['user'] = NULL;
			$_GLOBALS['usertype'] = NULL;
			echo "Δεν είσαι συνδεδεμένος. Πήγαίνε στη <a href='login.php'>σελίδα εισόδου.</a>";
			if($forceDie){
				die();
			}
		}
	} else
	if(!isset($_SESSION["logged"])){
		echo "Δεν είσαι συνδεδεμένος. Πήγαίνε στη <a href='login.php'>σελίδα εισόδου.</a>";
		if($forceDie){
			die();
		}
	} else {
		$GLOBALS['user'] = $_SESSION["LoggedUsername"];
		$GLOBALS['usertype'] = $_SESSION["LoggedRole"];
		echo "Καλωσήρθες ".$GLOBALS['user']." [".$GLOBALS['usertype']."]";
		echo " <a href='index.php?logout=true'>Έξοδος</a>";
	}
}
?>