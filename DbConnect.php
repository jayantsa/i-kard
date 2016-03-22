<?php
class DB_CONNECT{
	var $con;

	function connect()
	{
		require_once __DIR__.'/db_config.php';
		//$con=mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD) or die(mysqli_connect_error());
		$this->con=mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD);
		if(mysqli_connect_error())
		{
			die("connection failed".mysqli_connect_error());
		}
		$db=mysqli_select_db($this->con,DB_DATABASE) or die(mysqli_error($this->con));
		//echo 'connected';
		return $this->con;
	}
	function close()
	{
		if($this->con)
		{
			mysqli_close($this->con);
			//echo 'close';
		}

	}
}
?>