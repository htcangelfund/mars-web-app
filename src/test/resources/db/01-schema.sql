CREATE TABLE `room_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `country` varchar(64) NOT NULL,
  `city` varchar(64) NOT NULL,
  `location` varchar(64) NOT NULL,
  `floor` varchar(64) NOT NULL,
  `name` varchar(128) NOT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `update_timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `room_status_history` (
  `id` INTEGER (16) NOT NULL AUTO_INCREMENT,
  `room_id` int NOT NULL,
  `status` varchar(32) NOT NULL,
  `update_timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT room_id_fk_status_history FOREIGN KEY (`room_id`) REFERENCES `room_info`(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
