-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sty 11, 2025 at 05:13 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `restauracja`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `kategoria`
--

CREATE TABLE `kategoria` (
  `id` int(11) NOT NULL,
  `product_id` varchar(100) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategoria`
--

INSERT INTO `kategoria` (`id`, `product_id`, `product_name`, `type`, `price`, `status`) VALUES
(31, 'P001', 'Pizza Margherita', 'Jedzenie', 25, 'Dostępne'),
(32, 'P002', 'Burger Classic', 'Jedzenie', 18.5, 'Dostępne'),
(33, 'P003', 'Sałatka Cezar', 'Jedzenie', 15, 'Niedostępne'),
(34, 'P004', 'Coca-Cola', 'Napoje', 5.5, 'Dostępne'),
(35, 'P005', 'Woda Mineralna', 'Napoje', 3, 'Dostępne'),
(36, 'P006', 'Kawa Latte', 'Napoje', 12, 'Niedostępne'),
(37, 'P007', 'Spaghetti Bolognese', 'Jedzenie', 22, 'Dostępne'),
(38, 'P008', 'Zupa Pomidorowa', 'Jedzenie', 12, 'Dostępne'),
(39, 'P009', 'Tacos', 'Jedzenie', 17.5, 'Niedostępne'),
(40, 'P010', 'Sushi Roll', 'Jedzenie', 35, 'Dostępne'),
(41, 'P011', 'Frytki', 'Jedzenie', 8.5, 'Dostępne'),
(42, 'P012', 'Nuggetsy Kurczaka', 'Jedzenie', 14, 'Niedostępne'),
(43, 'P013', 'Kanapka BLT', 'Jedzenie', 16, 'Dostępne'),
(44, 'P014', 'Omlet Warzywny', 'Jedzenie', 13, 'Dostępne'),
(45, 'P015', 'Stek Wołowy', 'Jedzenie', 55, 'Dostępne'),
(46, 'P016', 'Lasagna', 'Jedzenie', 28, 'Niedostępne'),
(47, 'P017', 'Herbata Zielona', 'Napoje', 8, 'Dostępne'),
(48, 'P018', 'Sok Pomarańczowy', 'Napoje', 7.5, 'Dostępne'),
(49, 'P019', 'Smoothie Truskawkowe', 'Napoje', 15, 'Niedostępne'),
(50, 'P020', 'Piwo Lager', 'Napoje', 9, 'Dostępne'),
(51, 'P021', 'Wino Czerwone', 'Napoje', 40, 'Dostępne'),
(52, 'P022', 'Koktajl Mojito', 'Napoje', 18, 'Niedostępne'),
(53, 'P023', 'Lemoniada Cytrynowa', 'Napoje', 10, 'Dostępne'),
(54, 'P024', 'Czekolada na Gorąco', 'Napoje', 12.5, 'Dostępne'),
(55, 'P025', 'Espresso', 'Napoje', 7, 'Dostępne');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `customer_id` int(100) NOT NULL,
  `product_id` varchar(100) DEFAULT NULL,
  `product_name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(100) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `customer_id`, `product_id`, `product_name`, `type`, `price`, `quantity`, `date`) VALUES
(1, 1, 'JD-987', 'Cola', 'Napoje', 20, 2, '2025-01-10'),
(2, 1, 'JD-101', 'Hamburger', 'Jedzenie', 125, 5, '2025-01-10'),
(3, 1, 'JD-987', 'Cola', 'Napoje', 20, 2, '2025-01-10'),
(4, 2, 'JD-101', 'Hamburger', 'Jedzenie', 75, 3, '2025-01-10'),
(6, 3, 'P002', 'Burger Classic', 'Jedzenie', 55.5, 3, '2025-01-11');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `product_info`
--

CREATE TABLE `product_info` (
  `id` int(11) NOT NULL,
  `customer_id` int(100) NOT NULL,
  `product_id` varchar(100) DEFAULT NULL,
  `total` double NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product_info`
--

INSERT INTO `product_info` (`id`, `customer_id`, `product_id`, `total`, `date`) VALUES
(1, 1, NULL, 165, '2025-01-10'),
(2, 2, NULL, 75, '2025-01-10'),
(3, 3, NULL, 55.5, '2025-01-11');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `kategoria`
--
ALTER TABLE `kategoria`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `product_info`
--
ALTER TABLE `product_info`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategoria`
--
ALTER TABLE `kategoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `product_info`
--
ALTER TABLE `product_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
