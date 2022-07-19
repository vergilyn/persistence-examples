CREATE TABLE `sharding_sphere_month`
(
    `id`          VARCHAR(255)   NOT NULL,
    `create_time` datetime NOT NULL,
    `name`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;