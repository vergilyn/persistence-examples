DROP TABLE IF EXISTS `mybatis_plus`;
CREATE TABLE `mybatis_plus`
(
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `is_deleted`  TINYINT(1)          NOT NULL DEFAULT 0,
    `name`        VARCHAR(255)        NOT NULL DEFAULT '',
    `enum_field`  VARCHAR(50)         NOT NULL DEFAULT 'NONE',
    `version`     int                 NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
