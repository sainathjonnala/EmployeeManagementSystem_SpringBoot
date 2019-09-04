-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: EMS_Spring
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

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
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `departments` (
  `department_id` varchar(15) NOT NULL,
  `department_name` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` VALUES ('CMID-01','HR'),('CMID-02','PGB'),('CMID-03','FINANCE'),('CMID-04','FACILITIES'),('CMID-05','ICT'),('CMID-06','MANAGEMENT');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `employee_id` varchar(15) NOT NULL,
  `address` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `first_name` varchar(15) DEFAULT NULL,
  `last_name` varchar(15) DEFAULT NULL,
  `manager_id` varchar(20) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `department_department_id` varchar(15) DEFAULT NULL,
  `leave_balance_id` int(11) DEFAULT NULL,
  `login_username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `FKcuynmkmmiwb8snx53ufp9mjab` (`department_department_id`),
  KEY `FK3bxgdsvctgvs89fa5ac66hkkx` (`leave_balance_id`),
  KEY `FKsy3n339wf72d21bxo7t8yc7um` (`login_username`),
  CONSTRAINT `FK3bxgdsvctgvs89fa5ac66hkkx` FOREIGN KEY (`leave_balance_id`) REFERENCES `leave_balance` (`id`),
  CONSTRAINT `FKcuynmkmmiwb8snx53ufp9mjab` FOREIGN KEY (`department_department_id`) REFERENCES `departments` (`department_id`),
  CONSTRAINT `FKsy3n339wf72d21bxo7t8yc7um` FOREIGN KEY (`login_username`) REFERENCES `login` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES ('CMI-100','hyderabad','shruthi.p@comakeit.com','podduturi','shruthi','CMI-100',70000,'CMID-06',7,'UN41891'),('CMI-1043','Begumpet','shravya.d@comakeit.com','shravya','dharmapuri','CMI-100',70000,'CMID-01',10,'UN56649'),('CMI-152','Dilshuknagar','kamala.t@comakieit.com','kamala','taduri','CMI-100',40000,'CMID-02',12,'UN20566'),('CMI-550','Kukatpally','ganesh.a@gmail.com','akondi','ganesh','CMI-100',40000,'CMID-06',11,'UN-51164'),('CMI-T984','warangal','sainath.jonnala@gmail.com','jonnala','sainath','CMI-152',18000,'CMID-03',5,'UN93120'),('CMI-T986','kothagudem','manajari.k@gmail.com','manjari','kundam','CMI-152',20000,'CMID-04',13,'UN83982'),('CMI-T991','alwal','abhishek@gmail.com','abhishek','jaksani','CMI-152',20000,'CMID-03',8,'UN-9656');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (14);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_application`
--

DROP TABLE IF EXISTS `leave_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leave_application` (
  `leave_id` varchar(15) NOT NULL,
  `apply_to` varchar(20) DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `leave_type` varchar(15) DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `employee_employee_id` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`leave_id`),
  KEY `FKnl4tyhje5w4kqs8a457w15xqj` (`employee_employee_id`),
  CONSTRAINT `FKnl4tyhje5w4kqs8a457w15xqj` FOREIGN KEY (`employee_employee_id`) REFERENCES `employees` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_application`
--

LOCK TABLES `leave_application` WRITE;
/*!40000 ALTER TABLE `leave_application` DISABLE KEYS */;
INSERT INTO `leave_application` VALUES ('LV-51239','CMI-152','2019-09-18','loss_of_pay','personal','cancelled','2019-09-21','CMI-T984'),('LV-56671','CMI-152','2019-08-27','loss_of_pay','birthday','approved','2019-08-28','CMI-T984'),('LV-69739','CMI-100','2019-09-03','casual','festival celebrations','cancelled','2019-09-04','CMI-1043'),('LV10627','CMI-152','2019-09-05','casual','outing','approved','2019-09-06','CMI-T986'),('LV31571','CMI-152','2019-09-05','casual','outing','approved','2019-09-06','CMI-T991');
/*!40000 ALTER TABLE `leave_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_balance`
--

DROP TABLE IF EXISTS `leave_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leave_balance` (
  `id` int(11) NOT NULL,
  `casual_leaves` int(11) NOT NULL,
  `loss_of_pay` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_balance`
--

LOCK TABLES `leave_balance` WRITE;
/*!40000 ALTER TABLE `leave_balance` DISABLE KEYS */;
INSERT INTO `leave_balance` VALUES (1,10,10),(5,9,9),(7,10,10),(8,9,10),(9,10,10),(10,10,10),(11,10,10),(12,10,10),(13,9,10);
/*!40000 ALTER TABLE `leave_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `username` varchar(255) NOT NULL,
  `password` varchar(15) DEFAULT NULL,
  `role_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FKd060mt7sdmpf60fhiunsvu4w0` (`role_role_id`),
  CONSTRAINT `FKd060mt7sdmpf60fhiunsvu4w0` FOREIGN KEY (`role_role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('admin','admin',1),('UN-51164','PW41173',3),('UN-9656','PW9491',2),('UN20566','PW-27567',3),('UN21552','PW8813',2),('UN41891','PW69992',3),('UN56649','PW77047',2),('UN83982','PW-65031',2),('UN93120','PW-7362',2);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'employee'),(3,'manager');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

