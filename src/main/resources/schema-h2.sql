DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) DEFAULT NULL,
  `password` VARCHAR(100) DEFAULT NULL,
  `role` VARCHAR(50) DEFAULT NULL
);

DROP TABLE IF EXISTS `COURSE`;

CREATE TABLE `COURSE` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) DEFAULT NULL
);