-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hostiteľ: 127.0.0.1:3306
-- Čas generovania: St 17.Apr 2019, 13:26
-- Verzia serveru: 5.7.23
-- Verzia PHP: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáza: `glbank`
--

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccNum` varchar(10) NOT NULL,
  `amount` float NOT NULL,
  `IDClient` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDClient` (`IDClient`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Sťahujem dáta pre tabuľku `account`
--

INSERT INTO `account` (`ID`, `AccNum`, `amount`, `IDClient`) VALUES
(1, '1234567891', 788.45, 1),
(2, '8795461238', 789.98, 1),
(3, '1285077246', 0, 9),
(4, '1775286084', 0, 1),
(5, '4486823488', 0, 10),
(6, '5539236439', 0, 10);

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `card`
--

DROP TABLE IF EXISTS `card`;
CREATE TABLE IF NOT EXISTS `card` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PIN` varchar(4) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `expireY` int(11) NOT NULL,
  `expireM` int(11) NOT NULL,
  `IDAccount` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDAccount` (`IDAccount`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `cardtrans`
--

DROP TABLE IF EXISTS `cardtrans`;
CREATE TABLE IF NOT EXISTS `cardtrans` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `transDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `transAmount` int(11) NOT NULL,
  `IDCard` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDCard` (`IDCard`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `fname` varchar(30) NOT NULL,
  `lname` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Sťahujem dáta pre tabuľku `client`
--

INSERT INTO `client` (`fname`, `lname`, `email`, `ID`) VALUES
('Ada', 'Matthews', 'Ada.Matthews@gmail.com', 1),
('Andrea', 'Matthews ', 'Andrea.Matthews@gmail.com', 2),
('Aditya ', 'Matthews ', 'Aditya .Matthews@gmail.com', 3),
('Asa', 'Matthews ', 'Aditya .Matthews@gmail.com', 4),
('Anika ', 'Matthews ', 'Anika.Matthews@gmail.com', 5),
('Adam', 'Matthews ', 'Adam.Matthews@gmail.com', 6),
('Alexander', 'Matthews ', 'Alexander.Matthews@gmail.com', 7),
('Alfred', 'Matthews ', 'Alfred.Matthews@gmail.com', 8),
('Adam', 'Ivan', 'adam.ivan@akademiasovy.sk', 9),
('Jezis', 'Krist', 'big.K@heaven.hv', 10),
('Marian', 'Kotleta', 'majo.kotleta@nsdap.de', 12);

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `lname` varchar(30) NOT NULL,
  `fname` varchar(30) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `position` (`position`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Sťahujem dáta pre tabuľku `employee`
--

INSERT INTO `employee` (`ID`, `lname`, `fname`, `position`) VALUES
(4, 'Kotleta', 'Marian', 2),
(2, 'Bartos', 'Martin', 1),
(3, 'Mino', 'Mazurek', 2),
(1, 'Ivan', 'Adam', 1);

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `failcardlog`
--

DROP TABLE IF EXISTS `failcardlog`;
CREATE TABLE IF NOT EXISTS `failcardlog` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FailDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IDCard` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDCard` (`IDCard`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `loginclient`
--

DROP TABLE IF EXISTS `loginclient`;
CREATE TABLE IF NOT EXISTS `loginclient` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `IDClient` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDClient` (`IDClient`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `loginemployee`
--

DROP TABLE IF EXISTS `loginemployee`;
CREATE TABLE IF NOT EXISTS `loginemployee` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `IDEmployee` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDEmployee` (`IDEmployee`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Sťahujem dáta pre tabuľku `loginemployee`
--

INSERT INTO `loginemployee` (`ID`, `login`, `password`, `IDEmployee`) VALUES
(1, 'adam', 'heslo', 1);

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `loginhistory`
--

DROP TABLE IF EXISTS `loginhistory`;
CREATE TABLE IF NOT EXISTS `loginhistory` (
  `loginTime` timestamp NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `success` tinyint(1) NOT NULL,
  `IDLoginClient` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDLoginClient` (`IDLoginClient`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `positions`
--

DROP TABLE IF EXISTS `positions`;
CREATE TABLE IF NOT EXISTS `positions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `transaction`
--

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE IF NOT EXISTS `transaction` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `transAmount` int(11) NOT NULL,
  `transDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `RecAccount` int(11) NOT NULL,
  `IDAccount` int(11) NOT NULL,
  `IDEmployee` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDAccount` (`IDAccount`),
  KEY `IDEmployee` (`IDEmployee`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
