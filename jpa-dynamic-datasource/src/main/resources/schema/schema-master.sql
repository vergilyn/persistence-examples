
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
                          `customer_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                          `store_id` tinyint(3) unsigned NOT NULL,
                          `first_name` varchar(45) NOT NULL,
                          `last_name` varchar(45) NOT NULL,
                          `email` varchar(50) DEFAULT NULL,
                          `address_id` smallint(5) unsigned NOT NULL,
                          `active` tinyint(1) NOT NULL DEFAULT '1',
                          `create_date` datetime NOT NULL,
                          `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '1', 'MARY', 'SMITH', 'master', '5', '1', '2006-02-14 22:04:36', '2019-03-25 15:45:44');
INSERT INTO `customer` VALUES ('2', '1', 'PATRICIA', 'JOHNSON', 'jdbc@1553225813487', '6', '1', '2006-02-14 22:04:36', '2019-03-22 11:36:53');
INSERT INTO `customer` VALUES ('3', '1', 'LINDA', 'WILLIAMS', 'named@1553225813512', '7', '1', '2006-02-14 22:04:36', '2019-03-22 11:36:53');
INSERT INTO `customer` VALUES ('4', '2', 'BARBARA', 'JONES', 'BARBARA.JONES@sakilacustomer.org', '8', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('5', '1', 'ELIZABETH', 'BROWN', 'ELIZABETH.BROWN@sakilacustomer.org', '9', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('6', '2', 'JENNIFER', 'DAVIS', 'JENNIFER.DAVIS@sakilacustomer.org', '10', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('7', '1', 'MARIA', 'MILLER', 'MARIA.MILLER@sakilacustomer.org', '11', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('8', '2', 'SUSAN', 'WILSON', 'SUSAN.WILSON@sakilacustomer.org', '12', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('9', '2', 'MARGARET', 'MOORE', 'MARGARET.MOORE@sakilacustomer.org', '13', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('10', '1', 'DOROTHY', 'TAYLOR', 'DOROTHY.TAYLOR@sakilacustomer.org', '14', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
DROP TRIGGER IF EXISTS `customer_create_date`;
DELIMITER ;;
CREATE TRIGGER `customer_create_date` BEFORE INSERT ON `customer` FOR EACH ROW SET NEW.create_date = NOW()
;;
DELIMITER ;
