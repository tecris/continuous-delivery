CREATE TABLE `Planet` (
  `id` bigint(20) NOT NULL,
  `Galaxy` varchar(255) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
