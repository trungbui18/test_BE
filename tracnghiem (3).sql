-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 26, 2025 at 03:47 AM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tracnghiem`
--

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
CREATE TABLE IF NOT EXISTS `answer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `correct` bit(1) NOT NULL,
  `id_question` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKco1pkchpedfpv4r4v3nkyxsx3` (`id_question`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` (`id`, `content`, `correct`, `id_question`) VALUES
(109, 'thầy hùng', b'1', 28),
(110, 'cô thư', b'0', 28),
(111, 'thầy an', b'0', 28),
(112, 'cô ý', b'0', 28),
(113, 'rat de ', b'0', 29),
(114, 'hoi kho', b'0', 29),
(115, 'binh thuong', b'1', 29),
(116, 'cuc kho', b'0', 29),
(117, 'Java', b'0', 30),
(118, 'Dash', b'1', 30),
(119, 'Javascript', b'0', 30),
(120, 'Python', b'0', 30),
(121, 'Khó', b'0', 31),
(122, 'Hơi Khó', b'1', 31),
(123, 'Dễ', b'0', 31),
(124, 'Cực dễ', b'0', 31);

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
CREATE TABLE IF NOT EXISTS `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `img` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `id_quiz` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKvnp1u9psthblq5s0v4g49b73` (`id_quiz`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`id`, `img`, `question`, `id_quiz`) VALUES
(28, NULL, 'Ai dạy web', 21),
(29, NULL, 'web de ko', 21),
(30, NULL, 'Flutter viết bằng ngôn ngữ gì', 22),
(31, NULL, 'Thi Windown 2024-2025 khó ko ', 23);

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
CREATE TABLE IF NOT EXISTS `quiz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(6) DEFAULT NULL,
  `created` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `time` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `id_topic` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKlv8x2qhieleojps78dj6voumo` (`code`),
  KEY `FKeq0uruigatgdbv0mltyke7gyi` (`id_topic`),
  KEY `FK3ir9kgbscpi8pdmf74wvw73vh` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`id`, `code`, `created`, `description`, `image`, `time`, `title`, `id_topic`, `id_user`) VALUES
(21, '006594', '2025-04-26 10:38:51.841000', 'web thầy hùng', NULL, 120, 'Xây dựng phần mềm web', 1, 3),
(22, '009875', '2025-04-26 10:41:01.361000', 'android thay an', NULL, 120, 'Xây dựng phần mềm android', 12, 3),
(23, '005777', '2025-04-26 10:42:41.085000', 'mon cua thay tung', NULL, 120, 'Xây dựng phần mềm windown', 14, 3);

-- --------------------------------------------------------

--
-- Table structure for table `quiz_result`
--

DROP TABLE IF EXISTS `quiz_result`;
CREATE TABLE IF NOT EXISTS `quiz_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `score` int NOT NULL,
  `submitted_at` datetime(6) DEFAULT NULL,
  `total_question` int DEFAULT NULL,
  `id_quiz` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6v65kpcvcbxsltnikv3o61eyo` (`id_quiz`),
  KEY `FKbjyh438swxbpg66vp05igj861` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `quiz_result`
--

INSERT INTO `quiz_result` (`id`, `score`, `submitted_at`, `total_question`, `id_quiz`, `id_user`) VALUES
(124, 2, '2025-04-26 10:45:17.701000', 2, 21, 3),
(125, 1, '2025-04-26 10:47:26.957000', 1, 22, 2);

-- --------------------------------------------------------

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
CREATE TABLE IF NOT EXISTS `topic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `topic`
--

INSERT INTO `topic` (`id`, `name`) VALUES
(1, 'WEB'),
(12, 'ANDROID'),
(14, 'WINDOWN');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `role`, `username`) VALUES
(1, 'trungcute@gmail.com', '123456789', 'USER', 'trung'),
(2, 'trungcute123@gmail.com', 'e807f1fcf82d132f9bb018ca6738a19f', 'USER', 'trungb'),
(3, 'admin@gmail.com', '25f9e794323b453885f5181f1b624d0b', 'ADMIN', 'trung123'),
(4, 't@gmail.com', '25d55ad283aa400af464c76d713c07ad', 'USER', 'aaaa');

-- --------------------------------------------------------

--
-- Table structure for table `user_result`
--

DROP TABLE IF EXISTS `user_result`;
CREATE TABLE IF NOT EXISTS `user_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_correct` bit(1) NOT NULL,
  `id_question` int DEFAULT NULL,
  `id_quiz` int DEFAULT NULL,
  `quiz_result_id` int DEFAULT NULL,
  `selected_answer_id` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6e79iiwp5t7yeisnl17kvoack` (`id_question`),
  KEY `FKnw7prh81pvhykwce17g3vvxhb` (`id_quiz`),
  KEY `FKkc5tw61xlte73npxw7haisfal` (`quiz_result_id`),
  KEY `FKdxyn3gkalyx4lwqps7gn7v0vk` (`selected_answer_id`),
  KEY `FKdwggixvtcdbyqdfil36ukjts3` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=300 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_result`
--

INSERT INTO `user_result` (`id`, `is_correct`, `id_question`, `id_quiz`, `quiz_result_id`, `selected_answer_id`, `id_user`) VALUES
(297, b'1', 28, 21, 124, 109, 3),
(298, b'1', 29, 21, 124, 115, 3),
(299, b'1', 30, 22, 125, 118, 2);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `FKco1pkchpedfpv4r4v3nkyxsx3` FOREIGN KEY (`id_question`) REFERENCES `question` (`id`);

--
-- Constraints for table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FKvnp1u9psthblq5s0v4g49b73` FOREIGN KEY (`id_quiz`) REFERENCES `quiz` (`id`);

--
-- Constraints for table `quiz`
--
ALTER TABLE `quiz`
  ADD CONSTRAINT `FK3ir9kgbscpi8pdmf74wvw73vh` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKeq0uruigatgdbv0mltyke7gyi` FOREIGN KEY (`id_topic`) REFERENCES `topic` (`id`);

--
-- Constraints for table `quiz_result`
--
ALTER TABLE `quiz_result`
  ADD CONSTRAINT `FK6v65kpcvcbxsltnikv3o61eyo` FOREIGN KEY (`id_quiz`) REFERENCES `quiz` (`id`),
  ADD CONSTRAINT `FKbjyh438swxbpg66vp05igj861` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Constraints for table `user_result`
--
ALTER TABLE `user_result`
  ADD CONSTRAINT `FK6e79iiwp5t7yeisnl17kvoack` FOREIGN KEY (`id_question`) REFERENCES `question` (`id`),
  ADD CONSTRAINT `FKdwggixvtcdbyqdfil36ukjts3` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKdxyn3gkalyx4lwqps7gn7v0vk` FOREIGN KEY (`selected_answer_id`) REFERENCES `answer` (`id`),
  ADD CONSTRAINT `FKkc5tw61xlte73npxw7haisfal` FOREIGN KEY (`quiz_result_id`) REFERENCES `quiz_result` (`id`),
  ADD CONSTRAINT `FKnw7prh81pvhykwce17g3vvxhb` FOREIGN KEY (`id_quiz`) REFERENCES `quiz` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
