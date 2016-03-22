<?php
$response=array();
if(isset($_POST["ReferanceNo"])&&isset($_POST["Cost"])&&isset($_POST["ReceiverAccount"])&&isset($_POST["ProductList"]))
{
	$ReferanceNo=$_POST["ReferanceNo"];
	$Cost=$_POST["Cost"];
	$ReceiverAccount=$_POST["ReceiverAccount"];
	$ProductList=$_POST["ProductList"];
	require_once __DIR__.'/DbConnect.php';
	$bd=new DB_CONNECT;
	$db=$bd->connect();
	$sql="INSERT INTO bill (ReferanceNo,Cost,ReceiverAccount,ProductList) VALUES('$ReferanceNo','$Cost','$ReceiverAccount','$ProductList')";
	if(mysqli_query($db,$sql))
	{
		$response["success"]=1;
		$response["message"]="Bill Info Added Safely";
		echo json_encode($response);
	}
	else
	{
		$response["success"]=0;
		$response["message"]="error error error !!!!";
		echo json_encode($response);
	}
	$db->close();

}
else
{
		$response["success"]=0;
		$response["message"]="Required Fields Are Missing";
		echo json_encode($response);
		
}

?>