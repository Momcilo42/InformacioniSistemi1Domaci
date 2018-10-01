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
-- Table structure for table `na_lageru`
--

DROP TABLE IF EXISTS `na_lageru`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `na_lageru` (
  `Prodavnica` int(10) unsigned NOT NULL,
  `Proizvod` int(10) unsigned NOT NULL,
  `Kolicina` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Prodavnica`,`Proizvod`),
  KEY `lager_u_prodavnici` (`Proizvod`) USING BTREE,
  CONSTRAINT `lager_u_prodavnici` FOREIGN KEY (`Prodavnica`) REFERENCES `prodavnice` (`IdPrd`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `proizvod_na_lageru` FOREIGN KEY (`Proizvod`) REFERENCES `proizvodi` (`IdPrz`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `na_lageru`
--

LOCK TABLES `na_lageru` WRITE;
/*!40000 ALTER TABLE `na_lageru` DISABLE KEYS */;
INSERT INTO `na_lageru` VALUES (1,1,36),(1,2,17),(2,1,44),(2,2,2),(2,4,1),(3,1,13),(3,3,33),(3,4,1);
/*!40000 ALTER TABLE `na_lageru` ENABLE KEYS */;
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
