-- --------------------------------------------------------
-- Host:                         localhost
-- Server-Version:               10.6.16-MariaDB-0ubuntu0.22.04.1 - Ubuntu 22.04
-- Server-Betriebssystem:        debian-linux-gnu
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Exportiere Datenbank-Struktur für EasyAndHealthyDB
CREATE DATABASE IF NOT EXISTS `EasyAndHealthyDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `EasyAndHealthyDB`;

-- Exportiere Struktur von Tabelle EasyAndHealthyDB.benutzer
CREATE TABLE IF NOT EXISTS `benutzer` (
  `benutzer_id` int(50) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `benutzer_name` varchar(50) NOT NULL,
  `passwort` varchar(60) NOT NULL,
  PRIMARY KEY (`benutzer_id`),
  UNIQUE KEY `benutzer_name` (`benutzer_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6751 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle EasyAndHealthyDB.benutzer: ~7 rows (ungefähr)
INSERT INTO `benutzer` (`benutzer_id`, `benutzer_name`, `passwort`) VALUES
	(15, 'Ciprian', '$2a$10$XV0KqN/S32MfztehwAQLCObAnrYM/Sqg5vCvLGGfptymZDIK63W/6'),
	(6714, 'Alex', '$2a$10$r8eWnadZlXA1266ELL4qgOyipBAFrtck5eyHiTWfTX15XKGnrwjh2'),
	(6715, 'Matei', '$2a$10$qRsqQZXfiu4m88qWpUvAWO09P/hUNcmyRk4xDkDJ.lRYw1yN/.LqW'),
	(6731, 'Mihai', '$2a$10$eXYctdwoRBAViH59ZtpY8uvIONTdW1RTLs.s0NZ34w9z0hepVNf8G'),
	(6733, 'Andreea', '$2a$10$fT2XV2jk/LUQTujl2y75vOtaFGkiPyhC8S8BKm378F9rIxV/KDJHe'),
	(6737, 'Marta', '$2a$10$e1grGY8a.Ps5U26W2WOQQOmB9pZjBujPyOZO9qJps7akalTbdxVMm'),
	(6746, 'Müller', '$2a$10$JeV5ten5vBJKkYXxHnXyUOTXwXXpCMIJmDqNZ1wx/nlA.eLrMx6Ue');

-- Exportiere Struktur von Tabelle EasyAndHealthyDB.rezept
CREATE TABLE IF NOT EXISTS `rezept` (
  `rezept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `rezept_name` varchar(50) NOT NULL,
  `portionen` int(2) NOT NULL,
  `foto` mediumblob DEFAULT NULL,
  `benutzer_id` int(50) DEFAULT NULL,
  PRIMARY KEY (`rezept_id`),
  UNIQUE KEY `unique_rezept_name` (`rezept_name`),
  KEY `benutzer_id` (`benutzer_id`),
  CONSTRAINT `benutzer_id` FOREIGN KEY (`benutzer_id`) REFERENCES `benutzer` (`benutzer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle EasyAndHealthyDB.rezept: ~14 rows (ungefähr)
INSERT INTO `rezept` (`rezept_id`, `rezept_name`, `portionen`, `foto`, `benutzer_id`) VALUES
	(1, 'Caprese Salat', 2, NULL, NULL),
	(2, 'Guacamole', 2, NULL, 15),
	(3, 'Omelett', 2, NULL, NULL),
	(4, 'Bruschetta', 2, NULL, NULL),
	(5, 'Pesto Nudeln', 2, NULL, NULL),
	(6, 'Gebackene Süßkartoffeln', 2, NULL, NULL),
	(7, 'Erdnussbutter Bananen Sandwich', 2, NULL, NULL),
	(8, 'Griechischer Joghurt mit Honig und Nüssen', 2, NULL, NULL),
	(9, 'Gebratener Reis', 2, NULL, NULL),
	(10, 'Ofenkartoffeln', 2, NULL, NULL),
	(13, 'Cashewmilch', 4, NULL, NULL),
	(30, 'Pizza Capricciosa', 4, NULL, 15),
	(31, 'Pasta bolognese', 2, NULL, 15),
	(78, 'Gemüsesuppe', 4, NULL, NULL);

-- Exportiere Struktur von Tabelle EasyAndHealthyDB.rezept_benutzer
CREATE TABLE IF NOT EXISTS `rezept_benutzer` (
  `benutzer_id` int(11) NOT NULL COMMENT 'Foreign Key',
  `rezept_id` int(11) NOT NULL COMMENT 'Foreign Key',
  KEY `benutzer_id` (`benutzer_id`),
  KEY `rezept_id` (`rezept_id`),
  CONSTRAINT `rezept_benutzer_ibfk_1` FOREIGN KEY (`benutzer_id`) REFERENCES `benutzer` (`benutzer_id`),
  CONSTRAINT `rezept_benutzer_ibfk_2` FOREIGN KEY (`rezept_id`) REFERENCES `rezept` (`rezept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle EasyAndHealthyDB.rezept_benutzer: ~7 rows (ungefähr)
INSERT INTO `rezept_benutzer` (`benutzer_id`, `rezept_id`) VALUES
	(15, 4),
	(15, 8),
	(15, 1),
	(6714, 1),
	(6731, 2),
	(6714, 10),
	(15, 10);

-- Exportiere Struktur von Tabelle EasyAndHealthyDB.rezept_zutat
CREATE TABLE IF NOT EXISTS `rezept_zutat` (
  `rezept_id` int(11) NOT NULL COMMENT 'Foreign Key',
  `zutat_id` int(11) NOT NULL COMMENT 'Foreign Key',
  `menge` int(2) NOT NULL,
  KEY `rezept_id` (`rezept_id`),
  KEY `zutat_id` (`zutat_id`),
  CONSTRAINT `rezept_zutat_ibfk_1` FOREIGN KEY (`rezept_id`) REFERENCES `rezept` (`rezept_id`),
  CONSTRAINT `rezept_zutat_ibfk_2` FOREIGN KEY (`zutat_id`) REFERENCES `zutaten` (`zutat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle EasyAndHealthyDB.rezept_zutat: ~26 rows (ungefähr)
INSERT INTO `rezept_zutat` (`rezept_id`, `zutat_id`, `menge`) VALUES
	(1, 2, 3),
	(1, 24, 1),
	(1, 25, 1),
	(1, 11, 2),
	(1, 26, 1),
	(1, 5, 1),
	(1, 15, 1),
	(13, 51, 100),
	(13, 10, 1000),
	(10, 1, 500),
	(10, 5, 1),
	(10, 9457, 1),
	(13, 5, 1),
	(10, 32, 100),
	(8, 42, 500),
	(8, 44, 50),
	(8, 51, 50),
	(8, 14, 1),
	(8, 43, 50),
	(2, 27, 3),
	(2, 40, 1),
	(31, 35, 200),
	(78, 1, 400),
	(78, 19, 200),
	(78, 4, 2),
	(78, 16, 1);

-- Exportiere Struktur von Tabelle EasyAndHealthyDB.vorrat
CREATE TABLE IF NOT EXISTS `vorrat` (
  `benutzer_id` int(11) NOT NULL COMMENT 'Foreign Key',
  `zutat_id` int(11) NOT NULL COMMENT 'Foreign Key',
  `menge` int(11) DEFAULT NULL,
  KEY `benutzer_id` (`benutzer_id`),
  KEY `zutat_id` (`zutat_id`),
  CONSTRAINT `vorrat_ibfk_1` FOREIGN KEY (`benutzer_id`) REFERENCES `benutzer` (`benutzer_id`),
  CONSTRAINT `vorrat_ibfk_2` FOREIGN KEY (`zutat_id`) REFERENCES `zutaten` (`zutat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle EasyAndHealthyDB.vorrat: ~5 rows (ungefähr)
INSERT INTO `vorrat` (`benutzer_id`, `zutat_id`, `menge`) VALUES
	(15, 51, 100),
	(15, 9457, 1),
	(15, 1, 500),
	(15, 32, 100),
	(15, 16, 4);

-- Exportiere Struktur von Tabelle EasyAndHealthyDB.zutaten
CREATE TABLE IF NOT EXISTS `zutaten` (
  `zutat_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `zutat_name` varchar(20) NOT NULL,
  `einheit` enum('g','ml','stück','prise','EL','Handvoll','zehe','Zweige','Scheibe','Tasse') NOT NULL,
  PRIMARY KEY (`zutat_id`),
  UNIQUE KEY `zutat_name` (`zutat_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9541 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle EasyAndHealthyDB.zutaten: ~47 rows (ungefähr)
INSERT INTO `zutaten` (`zutat_id`, `zutat_name`, `einheit`) VALUES
	(1, 'Kartoffeln', 'g'),
	(2, 'Tomaten', 'stück'),
	(3, 'Zwiebeln', 'stück'),
	(4, 'Karroten', 'stück'),
	(5, 'Salz', 'prise'),
	(6, 'Zucker', 'g'),
	(7, 'Gemüsebrühe', 'ml'),
	(8, 'Reis', 'g'),
	(9, 'Knoblauch', 'zehe'),
	(10, 'Wasser', 'ml'),
	(11, 'Olivenöl', 'EL'),
	(13, 'Haferflocken', 'g'),
	(14, 'Honig', 'EL'),
	(15, 'Pfeffer', 'prise'),
	(16, 'Paprika', 'stück'),
	(17, 'Dinkel Vollkornmehl', 'g'),
	(18, 'Dinkelmehl Typ 1050', 'g'),
	(19, 'Erbsen', 'g'),
	(23, 'Kokosöl', 'EL'),
	(24, 'Mozarella', 'stück'),
	(25, 'Basilikum', 'Handvoll'),
	(26, 'Balsamico-Essig', 'EL'),
	(27, 'Avocado', 'stück'),
	(29, 'Milch', 'ml'),
	(31, 'Feta-Käse', 'g'),
	(32, 'Käse', 'g'),
	(33, 'Ciabatta-Brot', 'stück'),
	(34, 'Baguette', 'stück'),
	(35, 'Nudeln', 'g'),
	(36, 'Pesto', 'EL'),
	(37, 'Parmesan', 'Handvoll'),
	(38, 'Thymian', 'Zweige'),
	(40, 'Brot', 'Scheibe'),
	(41, 'Erdnussbutter', 'EL'),
	(42, 'Griechischer Joghurt', 'ml'),
	(43, 'Walnüsse', 'Handvoll'),
	(44, 'Mandeln', 'Handvoll'),
	(45, 'Gemüse', 'Tasse'),
	(47, 'Sojasauce', 'EL'),
	(49, 'Trauben', 'g'),
	(50, 'Pfaumen', 'g'),
	(51, 'Cashewkerne', 'g'),
	(9455, 'Papaya', 'stück'),
	(9457, 'Butter', 'EL'),
	(9459, 'Ananas', 'stück'),
	(9460, 'Oliven', 'g'),
	(9490, 'Erdnüsse', 'g');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
