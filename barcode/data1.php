<?php
$con=mysqli_connect("localhost","gautam_bathla","gautam007","employee");
if(mysqli_connect_errno())
{
echo "FAILED TO CONNECT TO MySQL: " .mysqli_connect_errno();
}
else
{
	$response = array();
 
if (isset($_POST['name']) && isset($_POST['price']) && isset($_POST['description'])) {
 
    $name = $_POST['name'];
    $price = $_POST['price'];
    $description = $_POST['description'];
 
   
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO products(name, price, description) VALUES('$name', '$price', '$description')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
}
?>