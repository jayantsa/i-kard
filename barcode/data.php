<?php
$con=mysqli_connect("localhost","cg_admin","gautam@007","college_guide");
if(mysqli_connect_errno())
{
echo "FAILED TO CONNECT TO MySQL: " .mysqli_connect_errno();
}
else{
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['barcode_id']) && isset($_POST['product_name'])) {
 
    $id = $_POST['barcode_id'];
    $name = $_POST['product_name'];
    $price= $_POST['price'];
   
 
    // mysql inserting a new row
    $result = mysqli_query($con,"INSERT INTO iKart(barcode_id, product_name,price) VALUES('$id', '$name','$price')");
 
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