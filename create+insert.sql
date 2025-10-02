-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2024 at 02:09 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dollarbookshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `UserID` char(5) DEFAULT NULL,
  `ProductID` char(5) DEFAULT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`UserID`, `ProductID`, `Quantity`) VALUES
('US002', 'PD001', 1),
('US002', 'PD004', 1),
('US003', 'PD001', 2),
('US003', 'PD005', 1),
('US004', 'PD002', 1),
('US004', 'PD004', 1),
('US004', 'PD001', 2),
('US003', 'PD002', 1),
('US003', 'PD003', 2),
('US002', 'PD005', 1);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` char(5) NOT NULL CHECK (`ProductID` regexp 'PD[0-9][0-9][0-9]'),
  `Name` varchar(50) NOT NULL,
  `Genre` varchar(50) NOT NULL,
  `Stock` int(11) NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `Name`, `Genre`, `Stock`, `Price`) VALUES
('PD001', 'Duno', 'Science Fiction', 10, 500000),
('PD002', 'Pride and Prejuice', 'Romance', 50, 800000),
('PD003', 'The Great Gutsby', 'Classic Literature', 70, 450000),
('PD004', 'Done Girl', 'Thriller', 100, 250000),
('PD005', 'Boy in Mansion', 'Mystery', 210, 100000);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_details`
--

CREATE TABLE `transaction_details` (
  `TransactionID` char(5) DEFAULT NULL,
  `ProductID` char(5) DEFAULT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction_details`
--

INSERT INTO `transaction_details` (`TransactionID`, `ProductID`, `Quantity`) VALUES
('TR001', 'PD001', 1),
('TR002', 'PD005', 2),
('TR002', 'PD002', 1),
('TR003', 'PD004', 2),
('TR003', 'PD005', 1),
('TR002', 'PD001', 1),
('TR003', 'PD001', 2),
('TR004', 'PD003', 1),
('TR005', 'PD002', 1),
('TR005', 'PD003', 1);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_headers`
--

CREATE TABLE `transaction_headers` (
  `TransactionID` char(5) NOT NULL CHECK (`TransactionID` regexp 'TR[0-9][0-9][0-9]'),
  `UserID` char(5) DEFAULT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction_headers`
--

INSERT INTO `transaction_headers` (`TransactionID`, `UserID`, `TransactionDate`) VALUES
('TR001', 'US003', '2024-05-27'),
('TR002', 'US002', '2024-05-30'),
('TR003', 'US004', '2024-06-04'),
('TR004', 'US002', '2024-06-05'),
('TR005', 'US002', '2024-05-30'),
('TR006', 'US004', '2024-06-04'),
('TR007', 'US003', '2024-06-05'),
('TR008', 'US005', '2024-05-30'),
('TR009', 'US004', '2024-06-04'),
('TR010', 'US005', '2024-06-08');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` char(5) NOT NULL CHECK (`UserID` regexp 'US[0-9][0-9][0-9]'),
  `Email` varchar(50) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `DOB` date NOT NULL,
  `Role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `Email`, `Username`, `Password`, `DOB`, `Role`) VALUES
('US001', 'admin@gmail.com', 'admin', 'admin1234', '1990-01-01', 'admin'),
('US002', 'david@gmail.com', 'david', 'david1234', '1991-02-02', 'user'),
('US003', 'benny@gmail.com', 'benny', 'benny1234', '1992-03-03', 'user'),
('US004', 'charles@gmail.com', 'charles', 'charles1234', '1993-04-04', 'user'),
('US005', 'budi@gmail.com', 'budi', 'budi1234', '1994-05-05', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ProductID` (`ProductID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`);

--
-- Indexes for table `transaction_details`
--
ALTER TABLE `transaction_details`
  ADD KEY `TransactionID` (`TransactionID`);

--
-- Indexes for table `transaction_headers`
--
ALTER TABLE `transaction_headers`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`),
  ADD CONSTRAINT `carts_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`);

--
-- Constraints for table `transaction_details`
--
ALTER TABLE `transaction_details`
  ADD CONSTRAINT `transaction_details_ibfk_1` FOREIGN KEY (`TransactionID`) REFERENCES `transaction_headers` (`TransactionID`);

--
-- Constraints for table `transaction_headers`
--
ALTER TABLE `transaction_headers`
  ADD CONSTRAINT `transaction_headers_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
