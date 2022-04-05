-- -----------------------------------------------------
-- Schema certificatesystem
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `certificatesystem` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `certificatesystem`;

-- -----------------------------------------------------
-- Table `certificatesystem`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificatesystem`.`gift_certificate`
(
    `id`               INT           NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(25)   NULL DEFAULT NULL,
    `description`      VARCHAR(200)  NULL DEFAULT NULL,
    `price`            DECIMAL(6, 2) NULL DEFAULT NULL,
    `duration`         INT           NULL DEFAULT NULL,
    `create_date`      DATETIME(3)   NULL DEFAULT NULL,
    `last_update_date` DATETIME(3)   NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);

-- -----------------------------------------------------
-- Table `certificatesystem`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificatesystem`.`tag`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);

-- -----------------------------------------------------
-- Table `certificatesystem`.`certificate_purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificatesystem`.`certificate_purchase`
(
    `id_certificate` INT NOT NULL,
    `id_tag`         INT NOT NULL,
    INDEX `id_certificate_idx` (`id_certificate` ASC) VISIBLE,
    INDEX `id_tag_idx` (`id_tag` ASC) INVISIBLE,
    CONSTRAINT `certificate_id`
        FOREIGN KEY (`id_certificate`)
            REFERENCES `certificatesystem`.`gift_certificate` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `tag_id`
        FOREIGN KEY (`id_tag`)
            REFERENCES `certificatesystem`.`tag` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
