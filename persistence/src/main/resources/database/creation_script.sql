-- -----------------------------------------------------
-- Table `gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificate`
(
    `id`               INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`             VARCHAR(25)   NULL DEFAULT NULL,
    `description`      VARCHAR(200)  NULL DEFAULT NULL,
    `price`            DECIMAL(6, 2) NULL DEFAULT NULL,
    `duration`         INT           NULL DEFAULT NULL,
    `create_date`      DATETIME(3)   NULL DEFAULT NULL,
    `last_update_date` DATETIME(3)   NULL DEFAULT NULL
);

-- -----------------------------------------------------
-- Table `tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tag`
(
    `id`   INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NULL DEFAULT NULL
);

-- -----------------------------------------------------
-- Table `certificate_purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificate_purchase`
(
    `id_certificate` INT NOT NULL,
    `id_tag`         INT NOT NULL,
    CONSTRAINT `certificate_id`
        FOREIGN KEY (`id_certificate`)
            REFERENCES `gift_certificate` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `tag_id`
        FOREIGN KEY (`id_tag`)
            REFERENCES `tag` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
