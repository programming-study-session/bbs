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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_list`
--

LOCK TABLES `comment_list` WRITE;
/*!40000 ALTER TABLE `comment_list` DISABLE KEYS */;
INSERT INTO `comment_list` VALUES (15,13,1,'細江','テスト①完了','2020-02-29 19:44:04'),(16,14,1,'細江','テスト②','2020-02-29 19:44:27'),(17,15,1,'鈴木','テスト③完了','2020-02-29 19:45:03'),(18,16,1,'日野','テスト④完了','2020-02-29 19:45:34'),(19,17,1,'細江','テスト⑤完了','2020-02-29 19:49:34'),(20,17,2,'鈴木','こんにちは','2020-02-29 19:49:52'),(21,19,1,'細江','テスト⑥完了','2020-02-29 19:51:54'),(22,20,1,'細江','テスト⑦完了','2020-02-29 19:52:28'),(23,21,1,'細江','テスト⑧完了','2020-02-29 19:53:20'),(24,22,1,'細江','テスト⑨完了','2020-02-29 19:53:49'),(25,23,1,'細江','テスト⑩完了','2020-02-29 19:54:12'),(26,24,1,'細江','おはよう','2020-03-03 17:18:28'),(27,25,1,'細江','おはよう','2020-03-17 12:28:00'),(28,15,2,'佐藤','おはよう','2020-03-17 12:38:26'),(29,27,1,'00002','','2020-03-17 12:56:18'),(30,23,2,'細江洸作','こんにちは','2020-03-18 16:08:15'),(31,31,1,'細江洸作','おはよう','2020-03-20 15:48:18');
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_list`
--

LOCK TABLES `thread_list` WRITE;
/*!40000 ALTER TABLE `thread_list` DISABLE KEYS */;
INSERT INTO `thread_list` VALUES (13,'①テスト',NULL,NULL),(14,'②テスト',NULL,NULL),(15,'③テスト',NULL,NULL),(16,'④テスト',NULL,NULL),(17,'⑤テスト',NULL,NULL),(19,'⑥テスト',NULL,NULL),(20,'⑦テスト',NULL,NULL),(21,'⑧テスト',NULL,NULL),(22,'⑨テスト',NULL,NULL),(23,'⑩テスト',NULL,NULL),(24,'テスト⑪',NULL,NULL),(25,'⑫テスト',NULL,NULL),(26,'',NULL,NULL),(27,'',NULL,NULL),(28,'',NULL,NULL),(29,'⑬テスト',NULL,NULL),(30,'⑭テスト','細江洸作','2020-03-20 15:16:00'),(31,'⑮テスト','細江洸作','2020-03-20 15:48:08');
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
  `last_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_list`
--

LOCK TABLES `users_list` WRITE;
/*!40000 ALTER TABLE `users_list` DISABLE KEYS */;
INSERT INTO `users_list` VALUES (00001,'細江由美','女','1966-03-19',53,NULL,'2020-03-10 20:17:56'),(00002,'細江洸作','男','1990-10-25',29,'00001','2020-03-10 20:19:23');
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

-- Dump completed on 2020-03-22 14:51:38
