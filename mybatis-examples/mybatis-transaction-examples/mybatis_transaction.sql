DROP TABLE IF EXISTS `mybatis_transaction`;
CREATE TABLE `mybatis_transaction`
(
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `is_deleted`  TINYINT(1)          NOT NULL DEFAULT 0,
    `name`        VARCHAR(255)        NOT NULL DEFAULT '',
    `enum_field`  VARCHAR(50)         NOT NULL DEFAULT 'NONE',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `mybatis_transaction`(`id`, `create_time`, `is_deleted`, `name`, `enum_field`)
    VALUES (1, '2021-02-20 12:00:00', 0, 'mybatis-transaction', 'NONE');

