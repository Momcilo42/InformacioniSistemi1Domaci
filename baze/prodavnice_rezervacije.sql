-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: prodavnice
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rezervacije`
--

DROP TABLE IF EXISTS `rezervacije`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezervacije` (
  `IdRez` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Proizvod` int(10) unsigned NOT NULL,
  `Prodavnica` int(10) unsigned NOT NULL,
  `Korisnik` int(11) DEFAULT NULL,
  `Kolicina` int(10) DEFAULT NULL,
  `Vreme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IdRez`),
  KEY `proizvod` (`Proizvod`) USING BTREE,
  KEY `prodavnica` (`Prodavnica`) USING BTREE,
  KEY `korisnik` (`Korisnik`) USING BTREE,
  CONSTRAINT `korisnik_rezervise` FOREIGN KEY (`Korisnik`) REFERENCES `korisnik` (`IdKor`),
  CONSTRAINT `rezervacija_proizvoda` FOREIGN KEY (`Proizvod`) REFERENCES `proizvodi` (`IdPrz`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rezervacija_u_prodavnici` FOREIGN KEY (`Prodavnica`) REFERENCES `prodavnice` (`IdPrd`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezervacije`
--

LOCK TABLES `rezervacije` WRITE;
/*!40000 ALTER TABLE `rezervacije` DISABLE KEYS */;
INSERT INTO `rezervacije` VALUES (1,2,1,1,3,'2018-01-01 23:51:29'),(3,1,1,1,10,'2018-01-09 14:53:50'),(9,1,1,1,5,'2018-01-26 16:55:36'),(10,2,1,1,3,'2018-01-26 19:55:23'),(12,1,1,1,3,'2018-01-26 20:29:14');
/*!40000 ALTER TABLE `rezervacije` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-27  3:41:15
