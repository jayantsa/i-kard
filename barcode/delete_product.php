<?php
 
/*
 * Following code will delete a product from table
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$con=mysqli_connect("localhost","gautam_bathla","gautam007","employee");
if(mysqli_connect_errno())
{
echo "FAILED TO CONNECT TO MySQL: " .mysqli_connect_errno();
}
else
{   
$response = array();
 
// check for required fields
if (isset($_POST['pid'])) {
    $pid = $_POST['pid'];
 
    
    // mysql update row with matched pid
    $result = mysql_query("DELETE FROM products WHERE pid = $pid");
 
    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully deleted";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
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