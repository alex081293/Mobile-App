<?php
function setUpConnection() {	
	$mysql_host = "mysql1.000webhost.com";
	$mysql_database = "a2503721_android";
	$mysql_user = "a2503721_root";
	$mysql_password = "usfbull!2";

	$con=mysqli_connect($mysql_host, $mysql_user, $mysql_password, $mysql_database);

	if($db->connect_errno > 0){
	    die('Unable to connect to database [' . $db->connect_error . ']');
	}  	

	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	 }

	return $con;

}

function dbFetch($query) {

	$con = setUpConnection();

	$safeQuery = mysqli_escape_string($con, $query);
	$safeQuery = str_replace("\\", "", $safeQuery);
	$queryReturn = mysqli_query($con, $safeQuery);

	if ($queryReturn) { 
		while($result = mysqli_fetch_assoc($queryReturn)) {
			$return[] = $result;
		}
	} else {
		$return = array('success' => false);
	}
	
	return $return;
}

function dbQuery($query) {

	$con = setUpConnection();

	$safeQuery = mysqli_escape_string($con, $query);
	$safeQuery = str_replace("\\", "", $safeQuery);	
	$queryReturn = mysqli_query($con, $safeQuery);
	$return['success'] = ($queryReturn) ? true : false;

	return $return;
}

?>
