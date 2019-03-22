/* MYSQL 测试库`sakila.customer`
 * 去掉物理外键，及索引
 */

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
  -- ,KEY `idx_fk_store_id` (`store_id`),
  -- KEY `idx_fk_address_id` (`address_id`),
  -- KEY `idx_last_name` (`last_name`),
  -- CONSTRAINT `fk_customer_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON UPDATE CASCADE,
  -- CONSTRAINT `fk_customer_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '1', 'MARY', 'SMITH', 'MARY.SMITH@sakilacustomer.org', '5', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('2', '1', 'PATRICIA', 'JOHNSON', 'PATRICIA.JOHNSON@sakilacustomer.org', '6', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('3', '1', 'LINDA', 'WILLIAMS', 'LINDA.WILLIAMS@sakilacustomer.org', '7', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('4', '2', 'BARBARA', 'JONES', 'BARBARA.JONES@sakilacustomer.org', '8', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('5', '1', 'ELIZABETH', 'BROWN', 'ELIZABETH.BROWN@sakilacustomer.org', '9', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('6', '2', 'JENNIFER', 'DAVIS', 'JENNIFER.DAVIS@sakilacustomer.org', '10', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('7', '1', 'MARIA', 'MILLER', 'MARIA.MILLER@sakilacustomer.org', '11', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('8', '2', 'SUSAN', 'WILSON', 'SUSAN.WILSON@sakilacustomer.org', '12', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('9', '2', 'MARGARET', 'MOORE', 'MARGARET.MOORE@sakilacustomer.org', '13', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('10', '1', 'DOROTHY', 'TAYLOR', 'DOROTHY.TAYLOR@sakilacustomer.org', '14', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('11', '2', 'LISA', 'ANDERSON', 'LISA.ANDERSON@sakilacustomer.org', '15', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('12', '1', 'NANCY', 'THOMAS', 'NANCY.THOMAS@sakilacustomer.org', '16', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('13', '2', 'KAREN', 'JACKSON', 'KAREN.JACKSON@sakilacustomer.org', '17', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('14', '2', 'BETTY', 'WHITE', 'BETTY.WHITE@sakilacustomer.org', '18', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('15', '1', 'HELEN', 'HARRIS', 'HELEN.HARRIS@sakilacustomer.org', '19', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('16', '2', 'SANDRA', 'MARTIN', 'SANDRA.MARTIN@sakilacustomer.org', '20', '0', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('17', '1', 'DONNA', 'THOMPSON', 'DONNA.THOMPSON@sakilacustomer.org', '21', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('18', '2', 'CAROL', 'GARCIA', 'CAROL.GARCIA@sakilacustomer.org', '22', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('19', '1', 'RUTH', 'MARTINEZ', 'RUTH.MARTINEZ@sakilacustomer.org', '23', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('20', '2', 'SHARON', 'ROBINSON', 'SHARON.ROBINSON@sakilacustomer.org', '24', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('21', '1', 'MICHELLE', 'CLARK', 'MICHELLE.CLARK@sakilacustomer.org', '25', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('22', '1', 'LAURA', 'RODRIGUEZ', 'LAURA.RODRIGUEZ@sakilacustomer.org', '26', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('23', '2', 'SARAH', 'LEWIS', 'SARAH.LEWIS@sakilacustomer.org', '27', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('24', '2', 'KIMBERLY', 'LEE', 'KIMBERLY.LEE@sakilacustomer.org', '28', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('25', '1', 'DEBORAH', 'WALKER', 'DEBORAH.WALKER@sakilacustomer.org', '29', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('26', '2', 'JESSICA', 'HALL', 'JESSICA.HALL@sakilacustomer.org', '30', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('27', '2', 'SHIRLEY', 'ALLEN', 'SHIRLEY.ALLEN@sakilacustomer.org', '31', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('28', '1', 'CYNTHIA', 'YOUNG', 'CYNTHIA.YOUNG@sakilacustomer.org', '32', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('29', '2', 'ANGELA', 'HERNANDEZ', 'ANGELA.HERNANDEZ@sakilacustomer.org', '33', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('30', '1', 'MELISSA', 'KING', 'MELISSA.KING@sakilacustomer.org', '34', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('31', '2', 'BRENDA', 'WRIGHT', 'BRENDA.WRIGHT@sakilacustomer.org', '35', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('32', '1', 'AMY', 'LOPEZ', 'AMY.LOPEZ@sakilacustomer.org', '36', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('33', '2', 'ANNA', 'HILL', 'ANNA.HILL@sakilacustomer.org', '37', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('34', '2', 'REBECCA', 'SCOTT', 'REBECCA.SCOTT@sakilacustomer.org', '38', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('35', '2', 'VIRGINIA', 'GREEN', 'VIRGINIA.GREEN@sakilacustomer.org', '39', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('36', '2', 'KATHLEEN', 'ADAMS', 'KATHLEEN.ADAMS@sakilacustomer.org', '40', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('37', '1', 'PAMELA', 'BAKER', 'PAMELA.BAKER@sakilacustomer.org', '41', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('38', '1', 'MARTHA', 'GONZALEZ', 'MARTHA.GONZALEZ@sakilacustomer.org', '42', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('39', '1', 'DEBRA', 'NELSON', 'DEBRA.NELSON@sakilacustomer.org', '43', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
INSERT INTO `customer` VALUES ('40', '2', 'AMANDA', 'CARTER', 'AMANDA.CARTER@sakilacustomer.org', '44', '1', '2006-02-14 22:04:36', '2006-02-15 04:57:20');
DROP TRIGGER IF EXISTS `customer_create_date`;
DELIMITER ;;
CREATE TRIGGER `customer_create_date` BEFORE INSERT ON `customer` FOR EACH ROW SET NEW.create_date = NOW()
;;
DELIMITER ;
