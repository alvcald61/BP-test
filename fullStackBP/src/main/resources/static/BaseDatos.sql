-- MySQL dump 10.13  Distrib 8.0.28, for Linux (x86_64)
--
-- Host: localhost    Database: pruebaPichincha
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_account` (
  `id` bigint NOT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `account_type` int DEFAULT NULL,
  `current_balance` double NOT NULL,
  `initial_balance` double NOT NULL,
  `is_active` bit(1) NOT NULL,
  `client_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjuqwfph00x8hxnfo002xx0y25` (`client_id`),
  CONSTRAINT `FKjuqwfph00x8hxnfo002xx0y25` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_account`
--

LOCK TABLES `bank_account` WRITE;
/*!40000 ALTER TABLE `bank_account` DISABLE KEYS */;
INSERT INTO `bank_account` VALUES (5,'478758',1,2000,2000,_binary '',1),(6,'225487',0,700,100,_binary '',2),(7,'495878',1,0,0,_binary '',3),(8,'496825',1,0,540,_binary '',2),(9,'585545',0,1000,1000,_binary '',1);
/*!40000 ALTER TABLE `bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `is_active` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `client_id` bigint NOT NULL,
  PRIMARY KEY (`client_id`),
  CONSTRAINT `FKcxli0sgm0c24a09lfqwoi9wmt` FOREIGN KEY (`client_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (_binary '','1234',1),(_binary '','5678',2),(_binary '','12345',3),(_binary '',NULL,14),(_binary '',NULL,15),(_binary '',NULL,16),(_binary '',NULL,17),(_binary '',NULL,18),(_binary '',NULL,19),(_binary '',NULL,20),(_binary '',NULL,21),(_binary '',NULL,22),(_binary '',NULL,23),(_binary '',NULL,24);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (28);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movement`
--

DROP TABLE IF EXISTS `movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movement` (
  `id` bigint NOT NULL,
  `amount` double NOT NULL,
  `available_balance` double NOT NULL,
  `initial_balance` double NOT NULL,
  `movement_type` int DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `bank_account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbyurlgp66ujuxmj5qvcykbfrs` (`bank_account_id`),
  CONSTRAINT `FKbyurlgp66ujuxmj5qvcykbfrs` FOREIGN KEY (`bank_account_id`) REFERENCES `bank_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movement`
--

LOCK TABLES `movement` WRITE;
/*!40000 ALTER TABLE `movement` DISABLE KEYS */;
INSERT INTO `movement` VALUES (10,600,700,100,1,'2022-02-10 00:00:00.000000',6),(11,540,0,540,0,'2022-02-08 00:00:00.000000',8);
/*!40000 ALTER TABLE `movement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Otavalo sn y principal',22,'M','12345678','Jose Lema','098254785'),(2,'Amazonas y NNUU',22,'M','12345678','Marianela Montalvo','097548965'),(3,'13 de junio y Equinoccial',22,'M','12345678','Juan Osorio','098874587'),(14,'kljfdjkld',30,'M',NULL,'Alvarosssss',NULL),(15,'kljfdjkld',30,'M',NULL,'Alvarosssss',NULL),(16,'kljfdjkld',30,'M',NULL,'Alvarosssss',NULL),(17,'kljfdjkld',30,'M',NULL,'Alvarosssss',NULL),(18,'kljfdjkld',30,'M',NULL,'Alvarosssss',NULL),(19,'kljfdjkld',30,'M',NULL,'Alvarosssss',NULL),(20,'kljfdjkld',30,'M',NULL,'Alvaro',NULL),(21,'kljfdjkld',30,'M',NULL,'Alvaro',NULL),(22,'kljfdjkld',30,'M',NULL,'Alvaro',NULL),(23,'Principal',77,'M','843837','Alvarosssss','0324454'),(24,NULL,0,'M',NULL,NULL,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-03 16:51:41
