DROP TABLE IF EXISTS `mybatis_counters`;
CREATE TABLE `mybatis_counters`
(
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `counter`     int                 NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

INSERT INTO `mybatis_counters`(`id`, `counter`) VALUES (1, 0);