<?php

         $con = mysqli_connect("localhost","gautam_bathla","gautam007","employee");
         if (!$con)
           {
             die('Could not connect: ' . mysqli_connect_errno());
           }

          $v1=$_REQUEST['id'];
          if($v1==NULL)
            {


                $r["re"]="Enter the number!!!";
                 print(json_encode($r));
                die('Could not connect: ' . mysqli_connect_errno());
          }

          else

            {


                $i=mysqli_query($con,"select * from employe_data where id=$v1");
               $check='';
               while($row = mysqli_fetch_array($i))
                {
  
                  $r[]=$row;
                  $check=$row['id'];
                 }
                  if($check==NULL)
                   {            
                      $r["re"]="Record is not available";
                      print(json_encode($r));
                 
                     }
                   else
                     {
                         $r["re"]="success";
                            print(json_encode($r));
                              
                       } 



}

 mysqli_close($con);
               
    ?> 