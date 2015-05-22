/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

CREATE TABLE IF NOT EXISTS `clients` (
  `clients_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` bigint(11) NOT NULL,
  PRIMARY KEY (`clients_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `user_id_2` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=101 ;

CREATE TABLE IF NOT EXISTS `machines` (
  `machines_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `machine_serviceable_id` bigint(11) NOT NULL,
  `serial_number` varchar(32) NOT NULL,
  `year` int(4) NOT NULL,
  `times_repaired` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`machines_id`),
  UNIQUE KEY `serial_number` (`serial_number`),
  KEY `machine_serviceable_id` (`machine_serviceable_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=22 ;

CREATE TABLE IF NOT EXISTS `machines_serviceable` (
  `machines_serviceable_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trademark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `country` varchar(50) NOT NULL,
  `country_ru` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`machines_serviceable_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

CREATE TABLE IF NOT EXISTS `orders` (
  `orders_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(11) NOT NULL,
  `repair_type_id` bigint(11) NOT NULL,
  `machine_id` bigint(11) NOT NULL,
  `status_id` bigint(11) NOT NULL,
  `start` date NOT NULL,
  `manager` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-',
  PRIMARY KEY (`orders_id`),
  KEY `user_id` (`client_id`),
  KEY `repair_type_id` (`repair_type_id`,`machine_id`),
  KEY `machine_id` (`machine_id`),
  KEY `client_id` (`client_id`),
  KEY `status_id` (`status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=38 ;

CREATE TABLE IF NOT EXISTS `order_statuses` (
  `order_statuses_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `order_status_number` int(3) NOT NULL,
  `order_status_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `order_status_name_ru` varchar(50) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`order_statuses_id`),
  UNIQUE KEY `order_status_name` (`order_status_name`),
  UNIQUE KEY `order_status_name_ru` (`order_status_name_ru`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

CREATE TABLE IF NOT EXISTS `repair_types` (
  `repair_types_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `name_ru` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `duration` int(2) NOT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`repair_types_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_ru` (`name_ru`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

CREATE TABLE IF NOT EXISTS `users` (
  `users_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) NOT NULL, 
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`users_id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=111 ;

CREATE TABLE IF NOT EXISTS `user_authorization` (
  `user_authorization_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `role` varchar(50) DEFAULT NULL,
  `desc_en` varchar(50) NOT NULL DEFAULT '-',
  `desc_ru` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-',
  PRIMARY KEY (`user_authorization_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=106 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;