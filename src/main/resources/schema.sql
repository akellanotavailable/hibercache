DROP DATABASE IF EXISTS test1;
CREATE DATABASE test1;

USE test1;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` varchar(255) NOT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        `role` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `request`;
CREATE TABLE `request` (
                           `id` varchar(255) NOT NULL,
                           `cost` decimal(19,2) DEFAULT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `user_id` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKgl490g7646oyowsncniikpbmf` (`user_id`),
                           CONSTRAINT `FKgl490g7646oyowsncniikpbmf` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
