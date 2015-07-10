<?php
	// Author: Miltos Nedelkos, nedelkosm at gmail dot com
	// Project: E-nurse, find more at ...
	session_start();
	include('functions.php');
?>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
<title>E-nurse - Ανακοινώσεις</title>
<link rel="stylesheet" type="text/css" href="mainstyle.css">
</head>

<body>
<?PHP
	ensureLogin(false);
?>
<span id="top"></span>
<div>
<h1>Ανακοινώσεις</h1>
</div>
<?php
	include('menu.php');
?>
<div class="mainContent">
<?php
	$conn = dbConn();
	$sql = "SET CHARSET 'utf8'";
	$result = $conn->query($sql);
	
	$sql = "SELECT * FROM announcements ORDER BY date DESC";
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$id = $row["id"];
			$date = $row["date"];
			$subject = $row["title"];
			$text = $row["text"];
			echo "<div class='announcement' id='anc$id'>";
			echo "<h2>Ανακοίνωση $date </h2>";
			echo "<strong>$subject</strong><br>";
			echo "$text";
			echo "</div>";
		}
	}
	$conn->close();
?>
<span class="returnTop"><a name="top" href="#top">Top</a></span>
</div>
</body>
</html>
