-- sample.organizatons definition

CREATE OR REPLACE SEQUENCE `organizations_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 1 nocache nocycle ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `organizations` (
    `id` bigint(20) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `instituition_name` varchar(255) DEFAULT NULL,
    `country` varchar(255) DEFAULT NULL,
    `cep` varchar(255) DEFAULT NULL,
    `state` varchar(255) DEFAULT NULL,
    `city` varchar(255) DEFAULT NULL,
    `number` varchar(255) DEFAULT NULL,
    `street` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;