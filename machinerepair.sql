-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Апр 07 2015 г., 20:14
-- Версия сервера: 5.6.17
-- Версия PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `machinerepair`
--

-- --------------------------------------------------------

--
-- Структура таблицы `clients`
--

CREATE TABLE IF NOT EXISTS `clients` (
  `clients_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `user_id` bigint(11) NOT NULL,
  PRIMARY KEY (`clients_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `user_id_2` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Дамп данных таблицы `clients`
--

INSERT INTO `clients` (`clients_id`, `name`, `user_id`) VALUES
(1, 'OOO Proletar', 1),
(2, 'PP Chornoguz Ltd', 2),
(3, 'NPO PischePromAvtomatika', 3),
(4, 'SP SD OOO TransKombayn', 4),
(5, 'Zorro', 5),
(6, 'Gromozeka', 6),
(10, 'tester', 13);

-- --------------------------------------------------------

--
-- Структура таблицы `machines`
--

CREATE TABLE IF NOT EXISTS `machines` (
  `machines_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `machine_serviceable_id` bigint(11) NOT NULL,
  `serial_number` varchar(32) NOT NULL,
  `year` int(4) NOT NULL,
  `times_repaired` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`machines_id`),
  UNIQUE KEY `serial_number` (`serial_number`),
  KEY `machine_serviceable_id` (`machine_serviceable_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Дамп данных таблицы `machines`
--

INSERT INTO `machines` (`machines_id`, `machine_serviceable_id`, `serial_number`, `year`, `times_repaired`) VALUES
(1, 1, 'zp46-78vp-890u', 2000, 2),
(2, 2, 'yu46-67vp-819f', 1991, 4),
(3, 3, 'ppt6-8721-000k', 2004, 1),
(4, 4, 'oo62-PL67-g47M', 2013, 2),
(5, 4, 'km67-zptr-fffQ-1768', 2013, 2),
(6, 5, '567-67yy-optr-Z', 2010, 1),
(7, 5, 'llmm-7p90-12FF', 2010, 5),
(8, 5, 'ppp', 1978, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `machines_serviceable`
--

CREATE TABLE IF NOT EXISTS `machines_serviceable` (
  `machines_serviceable_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `trademark` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  PRIMARY KEY (`machines_serviceable_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Дамп данных таблицы `machines_serviceable`
--

INSERT INTO `machines_serviceable` (`machines_serviceable_id`, `name`, `trademark`, `country`) VALUES
(1, 'S1784RD', 'Siemens', 'Germany'),
(2, 'MKP43-U', 'Uralmash', 'Russia'),
(3, 'Ruta', 'MotorSich', 'Ukraine'),
(4, 'LPD-15', 'Red Dragon', 'China'),
(5, 'Rumbler-9867', 'Caterpillar', 'USA');

-- --------------------------------------------------------

--
-- Структура таблицы `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `orders_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(11) NOT NULL,
  `repair_type_id` bigint(11) NOT NULL,
  `machine_id` bigint(11) NOT NULL,
  `start` date NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`orders_id`),
  KEY `user_id` (`client_id`),
  KEY `repair_type_id` (`repair_type_id`,`machine_id`),
  KEY `machine_id` (`machine_id`),
  KEY `client_id` (`client_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Дамп данных таблицы `orders`
--

INSERT INTO `orders` (`orders_id`, `client_id`, `repair_type_id`, `machine_id`, `start`, `status`) VALUES
(1, 1, 1, 1, '2014-07-04', 'finished'),
(2, 2, 3, 2, '2014-06-15', 'ready'),
(4, 4, 1, 4, '2013-10-11', 'ready'),
(5, 2, 2, 2, '2012-10-25', 'finished'),
(9, 3, 3, 3, '2010-11-12', 'ready'),
(10, 6, 1, 6, '2014-09-09', 'ready'),
(11, 1, 3, 1, '2014-09-21', 'finished'),
(12, 1, 1, 7, '2014-09-21', 'finished'),
(15, 1, 4, 7, '2014-10-04', 'started'),
(16, 1, 4, 1, '2014-10-08', 'pending');

-- --------------------------------------------------------

--
-- Структура таблицы `repair_types`
--

CREATE TABLE IF NOT EXISTS `repair_types` (
  `repair_types_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `duration` int(2) NOT NULL,
  PRIMARY KEY (`repair_types_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Дамп данных таблицы `repair_types`
--

INSERT INTO `repair_types` (`repair_types_id`, `name`, `price`, `duration`) VALUES
(1, 'Full', '1000.00', 12),
(2, 'Partial', '500.00', 3),
(3, 'Guarantee', '0.00', 1),
(4, 'Insurance', '250.00', 2),
(5, 'Insurance Partial', '300.00', 1);

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `users_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(128) NOT NULL,
  `password_text` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`users_id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`users_id`, `login`, `password`, `password_text`, `enabled`) VALUES
(1, 'proletar', '$2a$10$yv7G5STusSk5QeRcKOsh8uHMWDK9JOzbjT4Esr9mDUrlb0e9l/6YC', 'qwerty', 1),
(2, 'chornoguz', '$2a$10$ShT98Yr9PyEfAIrHLx6tWeqR.dbgi8doHvBF98z.CDoQ3lpPXfdwy', '12345', 1),
(3, 'ppa', '$2a$10$GUK7oBTZp2NgNoIuXoXI3ustGovh2wnHjCPnHzrJvoYdPf9A6Iqxi', 'pass', 1),
(4, 'trkomb', '$2a$10$z4oXKO8VILy2XfnpmGIHauUblTvyuuluudH93LCTQBz.qnspZ.BKC', '0000', 1),
(5, 'zorro', '$2a$10$Umdd.bBV7H0oFxjh7oaH9eV2.J6K1iJ3f.UKJbyIUhOX5Z9cuofim', 'zzz', 1),
(6, 'gromozeka', '$2a$10$pVemCFg2F6mMWFbuh0kAmuWdtwk.WNIxYNaP8g0smclu.T3vf/Fnq', 'valeryanka', 1),
(8, 'admin', '$2a$10$XX6z9tVswh39IKizA9ewCuLndBANXtFnF8kCOl2GxI38FzuN6KP76', 'kokoko', 1),
(9, 'manager1', '$2a$10$fUWyGXE5eRqv3AmcUPBnp.1LF8oPzY7Kbbg8V0qyNrCBvue3omque', 'koko', 1),
(13, 'tester', '$2a$10$aPQbx.Oiae6ZyMXLc5ZPwO7nUGPKLCMZqX/B3Run4y0b/e0U0K.RO', '11', 1),
(14, 'bobobo', '$2a$10$0ku2RHliH4pW0784L.Psy.YG1RyjyLvD8Q7SMRXBde8/oqtGhxAge', '123', 1);

-- --------------------------------------------------------

--
-- Структура таблицы `user_authorization`
--

CREATE TABLE IF NOT EXISTS `user_authorization` (
  `user_authorization_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_authorization_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

--
-- Дамп данных таблицы `user_authorization`
--

INSERT INTO `user_authorization` (`user_authorization_id`, `user_id`, `role`) VALUES
(1, 8, 'ROLE_ADMIN'),
(2, 8, 'ROLE_CLIENT'),
(3, 9, 'ROLE_CLIENT'),
(4, 9, 'ROLE_MANAGER'),
(5, 1, 'ROLE_CLIENT'),
(6, 2, 'ROLE_CLIENT'),
(7, 6, 'ROLE_CLIENT'),
(8, 3, 'ROLE_CLIENT'),
(10, 4, 'ROLE_CLIENT'),
(11, 5, 'ROLE_CLIENT'),
(15, 13, 'ROLE_CLIENT');

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `clients`
--
ALTER TABLE `clients`
  ADD CONSTRAINT `clients_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`users_id`);

--
-- Ограничения внешнего ключа таблицы `machines`
--
ALTER TABLE `machines`
  ADD CONSTRAINT `machines_ibfk_1` FOREIGN KEY (`machine_serviceable_id`) REFERENCES `machines_serviceable` (`machines_serviceable_id`);

--
-- Ограничения внешнего ключа таблицы `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`clients_id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`repair_type_id`) REFERENCES `repair_types` (`repair_types_id`),
  ADD CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`machine_id`) REFERENCES `machines` (`machines_id`);

--
-- Ограничения внешнего ключа таблицы `user_authorization`
--
ALTER TABLE `user_authorization`
  ADD CONSTRAINT `user_authorization_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`users_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
