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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

