-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: java_study
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment_list`
--

DROP TABLE IF EXISTS `comment_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comment_list` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `thread_id` int(10) unsigned NOT NULL,
  `comment_no` int(10) unsigned NOT NULL,
  `name` text,
  `comment` text,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_list`
--

LOCK TABLES `comment_list` WRITE;
/*!40000 ALTER TABLE `comment_list` DISABLE KEYS */;
INSERT INTO `comment_list` VALUES (51,69,1,'細江洸作','7月後半に予定します','2020-06-28 20:37:38'),(52,70,1,'細江洸作','select,insert,update,deleteができればOKです','2020-06-28 20:38:14'),(53,71,1,'細江洸作','来月の開催予定はどうなっていますか？','2020-06-28 20:38:34');
/*!40000 ALTER TABLE `comment_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_list`
--

DROP TABLE IF EXISTS `thread_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `thread_list` (
  `thread_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `thread_title` text,
  `name` varchar(30) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`thread_id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_list`
--

LOCK TABLES `thread_list` WRITE;
/*!40000 ALTER TABLE `thread_list` DISABLE KEYS */;
INSERT INTO `thread_list` VALUES (69,'プログラミング合宿について','細江洸作','2020-06-28 20:36:34'),(70,'MySQL基本知識','細江洸作','2020-06-28 20:36:59'),(71,'勉強会今後の予定','細江洸作','2020-06-28 20:37:18');
/*!40000 ALTER TABLE `thread_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_list`
--

DROP TABLE IF EXISTS `users_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_list` (
  `id` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `mother_id` char(5) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mail_adress` text,
  `last_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_list`
--

LOCK TABLES `users_list` WRITE;
/*!40000 ALTER TABLE `users_list` DISABLE KEYS */;
INSERT INTO `users_list` VALUES (00001,'細江由美','女','1966-03-19',53,NULL,'c6b8171b041aa8d558a69ee776067ce39aed5ebcb1b2c57ee0fae7f45ff8eee4','hosoe1234@yahoo.co.jp','2020-04-18 08:17:28'),(00002,'細江洸作','男','1990-10-25',29,'00001','d3dec3f35387156495cbc21471313f87155f878f3435b693f50077c2be479033','hosoe.adgjmptz@gmail.com','2020-06-28 20:32:05');
/*!40000 ALTER TABLE `users_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-29 22:56:46
