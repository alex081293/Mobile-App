<?php
// http://usfandroidapp.net63.net/mysqlConnect.php?q=

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

$query  = $_GET['q'];
$type = $_GET['t'];

$safeQuery = mysqli_escape_string($con, $query);
$safeQuery = str_replace("\\", "", $safeQuery);
$queryReturn = mysqli_query($con, $safeQuery);
$jsonReturn = array();

switch($type) {
	case 'u':
	case 'i':
	case 'd':
		$jsonReturn['success'] = ($queryReturn) ? true : false;
		break;
	case 'f':
	default:
		while($result = mysqli_fetch_assoc($queryReturn)) {
			$jsonReturn[] = $result;
		}
		$response = json_encode($jsonReturn);
		break;
}
$response = json_encode($jsonReturn);
var_dump($response);
?>
