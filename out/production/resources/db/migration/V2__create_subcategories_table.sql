CREATE TABLE `subcategories` (
  `id`                  INT               NOT NULL    AUTO_INCREMENT,
  `version`             INT               NOT NULL    DEFAULT 0,
  `name`                VARCHAR(45)       NOT NULL,
  `categories_id`       INT               NULL,
  `created`             TIMESTAMP         NOT NULL    DEFAULT NOW(),
  `modified`            TIMESTAMP         NOT NULL    DEFAULT NOW(),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_categories_id`
  FOREIGN KEY (`categories_id`)
  REFERENCES `categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
