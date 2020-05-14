USE evbx_resource;

CREATE TABLE IF NOT EXISTS `industry_report` (
	`id` BIGINT AUTO_INCREMENT,
    `industry_name` VARCHAR(255),
	`text` LONGTEXT,
	`description` VARCHAR(255),
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`deleted_at` TIMESTAMP NULL DEFAULT NULL,
	`created_by` VARCHAR(255),
	`updated_by` VARCHAR(255),
	`deleted_by` VARCHAR(255),
	PRIMARY KEY (`id`),
	CONSTRAINT uc_name UNIQUE (industry_name)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `e_books` (
	`id` BIGINT AUTO_INCREMENT,
    `book_name` VARCHAR(255),
    `description` VARCHAR(255),
	`text` LONGTEXT,
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`deleted_at` TIMESTAMP NULL DEFAULT NULL,
	`created_by` VARCHAR(255),
	`updated_by` VARCHAR(255),
	`deleted_by` VARCHAR(255),
	PRIMARY KEY (`id`),
	CONSTRAINT uc_name UNIQUE (book_name)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `specifications` (
	`id` BIGINT AUTO_INCREMENT,
    `specification_name` VARCHAR(255),
    `description` VARCHAR(255),
	`text` LONGTEXT,
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`deleted_at` TIMESTAMP NULL DEFAULT NULL,
	`created_by` VARCHAR(255),
	`updated_by` VARCHAR(255),
	`deleted_by` VARCHAR(255),
	PRIMARY KEY (`id`),
	CONSTRAINT uc_name UNIQUE (specification_name)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;