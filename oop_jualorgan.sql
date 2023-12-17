-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2023 at 12:57 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oop_jualorgan`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_datasellv1`
--

CREATE TABLE `tb_datasellv1` (
  `Pembeli` varchar(255) NOT NULL,
  `Penjual` varchar(255) NOT NULL,
  `Organ` varchar(255) NOT NULL,
  `Harga` int(11) NOT NULL,
  `Bayar` int(11) NOT NULL,
  `Kembalian` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_datasellv1`
--

INSERT INTO `tb_datasellv1` (`Pembeli`, `Penjual`, `Organ`, `Harga`, `Bayar`, `Kembalian`) VALUES
('Anon', 'Andrew', 'Jantung', 5000000, 70000000, 65000000),
('Kanocin', 'Karl', 'Jantung dan Ginjal', 7000000, 90000000, 83000000),
('Kunto', 'Karl', 'Ginjal', 2000000, 50000000, 48000000);

-- --------------------------------------------------------

--
-- Table structure for table `tb_datasellv2`
--

CREATE TABLE `tb_datasellv2` (
  `Pembeli` varchar(255) NOT NULL,
  `Penjual` varchar(10) NOT NULL,
  `Organ` varchar(20) NOT NULL,
  `jmlOrgan` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `bayar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_datasellv2`
--

INSERT INTO `tb_datasellv2` (`Pembeli`, `Penjual`, `Organ`, `jmlOrgan`, `harga`, `bayar`) VALUES
('bambang', 'Andrew', 'Ginjal', 3, 3000000, 50000000),
('nicon', 'Karl', 'Ginjal', 1, 1000000, 12233);

-- --------------------------------------------------------

--
-- Table structure for table `tb_datasellv3`
--

CREATE TABLE `tb_datasellv3` (
  `Pembeli` varchar(255) NOT NULL,
  `Penjual` varchar(10) NOT NULL,
  `Organ` varchar(20) NOT NULL,
  `Harga` int(11) NOT NULL,
  `Bayar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_datasellv3`
--

INSERT INTO `tb_datasellv3` (`Pembeli`, `Penjual`, `Organ`, `Harga`, `Bayar`) VALUES
('Anon', 'Andrew', 'Jantung', 5000000, 6000000),
('Bew', 'Karl', 'Ginjal', 1000000, 2000000),
('Naydes', 'Andrew', 'Ginjal', 1000000, 3500000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_datasellv1`
--
ALTER TABLE `tb_datasellv1`
  ADD PRIMARY KEY (`Pembeli`);

--
-- Indexes for table `tb_datasellv2`
--
ALTER TABLE `tb_datasellv2`
  ADD PRIMARY KEY (`Pembeli`);

--
-- Indexes for table `tb_datasellv3`
--
ALTER TABLE `tb_datasellv3`
  ADD PRIMARY KEY (`Pembeli`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
