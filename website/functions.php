<?php
	
function newLocation($location) {
	$host = $_SERVER['HTTP_HOST'];
	header("Location: http://" . $host . $location); die;
}


function validateUserData($data) {
	$errors = array();

	if (is_null($data['firstName'])) $errors[] = 'First name is required';
	if (is_null($data['lastName'])) $errors[] = 'Last name is required';
	if (is_null($data['phoneNumber'])) $errors[] = 'Phone number is required';
	if (is_null($data['email'])) $errors[] = 'Email is required';
	if (is_null($data['injuryType'])) $errors[] = 'Injury is required';

	return (empty($errors)) ? array('success' => true) : $errors;

}

function generateToken($x) {
	if (($x % 5) == 0) return $x * 9533 * 743;
	else if (($x % 3) == 0) return $x * 8081 * 1879;
	else if (($x % 2) == 0) return $x * 9539 * 2371;
	else if (($x % 7) == 0) return $x * 6763 * 2729;
	else if (($x % 11) == 0) return $x * 7717 * 7307;
	else return $x * 9949 * 5393;
}

function getDrNotes($drId,$patientId) {

	$query = "SELECT * FROM messages WHERE userId='$drId' and userType='0' and patient='$patientId' and private='1' ORDER BY time DESC";
	$personal = dbFetch($query);

	return $personal;
}

function getClientMessages($drId, $patientId) {

	$query = "SELECT * FROM messages WHERE userId='$drId' and userType='0' and patient='$patientId' and private='0' ORDER BY time DESC";
	$drMsgs = dbFetch($query);
	
	$query = "SELECT * FROM messages WHERE userId='$patientId' and userType='1' and private='0' ORDER BY time DESC";
	$patientMsgs = dbFetch($query);

	if (!(is_array($drMsgs)) && !(is_array($patientMsgs))) $messages = NULL;
	else if (is_array($drMsgs) && !(is_array($patientMsgs))) $messages = $drMsgs;
	else if (is_array($patientMsgs) && !(is_array($drMsgs))) $messages = $patientMsgs;
	else if (is_array($drMsgs) && is_array($patientMsgs)) {
		$messages = array_merge($drMsgs, $patientMsgs);
		
		function sortArray($a1, $a2){
	    	if ($a1['time'] == $a2['time']) return 0;
	    	return ($a1['time'] > $a2['time']) ? -1 : 1;
		}

		usort($messages, "sortArray");
	}
	return $messages;
}

function getUnseenDrMsgs($drId, $patientId) {
	$query = "SELECT COUNT(*) as count FROM messages WHERE userId='$patientId' and userType='1' and private='0' and viewed='0'";
	$count = dbFetch($query);
	return $count[0]['count'];
}

function updateUnseenDrMsgs($drId, $patientId) {
	$query = "UPDATE messages SET viewed='1' WHERE userId='$patientId' and userType='1' and private='0' and viewed='0'";
	dbQuery($query);
}

function insertMessage($drId, $patientId, $private, $message) {
	$query = "INSERT INTO messages (userId, userType, patient, message, private, time) VALUES('$drId', '0', '$patientId', '$message', '$private', NOW())";
	dbQuery($query);
}

function deletePatient($patientId) {
	$query = "DELETE FROM patients WHERE id='$patientId'";
	dbQuery($query);
	$query = "DELETE FROM messages WHERE userId='$patientId' and userType='1'";
	dbQuery($query);
	$query = "DELETE FROM messages WHERE patient='$patientId'";
	dbQuery($query);
	$query = "DELETE FROM sessions where patient='$patientId'";
}

function generateTable($patient) {
	$patient['age'] = ($patient['age'] == '0000-00-00' || is_null($patient['age'])) ? 'N/A' : floor((time() - strtotime($patient['age'])) / (60*60*24*365));
	echo '
	<style type="text/css">
	.tg  {border-collapse:collapse;border-spacing:0;}
	.tg td{font-family:Arial, sans-serif;font-size:16px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
	.tg th{font-family:Arial, sans-serif;font-size:20px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
	</style>
	<table class="tg">
	  <tr>
	    <th class="tg-031e">Name</th>
	    <th class="tg-031e">Injury</th>
	    <th class="tg-031e">Injury Details</th>
	    <th class="tg-031e">Phone Number</th>
	    <th class="tg-031e">Email Address</th>
	    <th class="tg-031e">Age</th>
	    <th class="tg-031e">Login Token</th>
	  </tr>
	  <tr>
	    <td class="tg-031e">'. $patient['firstName'] .' '. $patient['lastName'] . '</td>
	    <td class="tg-031e">'. $patient['injuryType'] .'</td>
	    <td class="tg-031e">'. $patient['injuryDetails'] .'</td>
	    <td class="tg-031e">'. $patient['phoneNumber'] .'</td>
	    <td class="tg-031e">'. $patient['email'] .'</td>
	    <td class="tg-031e">'. $patient['age'] .'</td>
	    <td class="tg-031e">'. $patient['loginToken'] .'</td>
	  </tr>
	</table>';
}

function generateCompletedTable($completedSessions) {
	echo '<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
	</style>
	<table class="tg" style="width:100%;">
	  <tr>
	    <th class="tg-031e">Session Name</th>
	    <th class="tg-031e">Notes</th>
	    <th class="tg-031e">Time Completed</th>
	    <th class="tg-031e">Heart Rate</th>
	    <th class="tg-031e">Breathing Rate</th>
	    <th class="tg-031e">Activity Level</th>
	    <th class="tg-031e">Posture</th>
	    <th class="tg-031e">Temperature</th>
	  </tr> ';
	  foreach ($completedSessions as $sesh) {
	 	echo '
	 	<tr>
		    <td class="tg-031e">' . $sesh['sessionName'] . '</td>
		    <td class="tg-031e">' . $sesh['notes'] . '</td>
		    <td class="tg-031e">' . date('F j, Y, g:i a', strtotime($sesh['time'])). '</td>
		    <td class="tg-031e">' . $sesh['heartRate'] . '</td>
		    <td class="tg-031e">' . $sesh['breathingRate'] . '</td>
		    <td class="tg-031e">' . $sesh['activityLevel'] . '</td>
		    <td class="tg-031e">' . $sesh['posture'] . '</td>
		    <td class="tg-031e">' . $sesh['temp'] . '</td>
	    </tr>';
	  }
	echo '</table>';
}

function generateUpcomingTable($completedSessions) {
	echo '<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
	</style>
	<table class="tg" style="width:100%;">
	  <tr>
	    <th class="tg-031e">Session Name</th>
	    <th class="tg-031e">Notes</th>
	    <th class="tg-031e">Date to Completed</th>
	    <th class="tg-031e">Video</th>
	  </tr> ';
	  foreach ($completedSessions as $sesh) {
	 	echo '
	 	<tr>
		    <td class="tg-031e">' . $sesh['sessionName'] . '</td>
		    <td class="tg-031e">' . $sesh['notes'] . '</td>
		    <td class="tg-031e">' . date('F j, Y', strtotime($sesh['time'])) . '</td>
		    <td class="tg-031e">'; 
		    	if ($sesh['url']) echo $sesh['url']; 
			echo '</td>
	    </tr>';
	  }
	echo '</table>';
}


function authenticate($pid, $dId) {
	$query = "SELECT doctorId from patients where id='$pid'";
	$returned = dbFetch($query);
	if ($dId != $returned[0]['doctorId']) newLocation('/404');
}

function isNotWeekend($date) {
    return (date('N', strtotime($date)) < 6);
}



?>