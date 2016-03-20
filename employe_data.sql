-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2016 at 11:58 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `employee`
--

-- --------------------------------------------------------

--
-- Table structure for table `employe_data`
--

CREATE TABLE IF NOT EXISTS `employe_data` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employe_data`
--

INSERT INTO `employe_data` (`id`, `name`, `price`) VALUES
('9788131721018', 'Compler Design by Ullman and Sethi', 400),
('9788120329683', 'Theory of Computer Science by K.L.P Mishra and N.Chandrasekaran', 275),
('9780070087705', 'Artificial Intelligence by Rich and Knight', 350),
('9788178087948', 'Computer Graphics by Hearn and Baker', 400),
('8902519001993', 'Classmate Register ', 35);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
