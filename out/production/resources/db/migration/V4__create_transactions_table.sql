CREATE TABLE `transactions` (
  `id`                        INT                                       NOT NULL      AUTO_INCREMENT,
  `version`                   INT                                       NULL          DEFAULT 0,
  `transaction_type`                      ENUM('INCOME', 'EXPENSE', 'TRANSFER')     NOT NULL,
  `amount`                    DOUBLE                                    NOT NULL      DEFAULT 0.0,
  `from_accounts_accno`       INT                                       NULL,
  `to_accounts_accno`         INT                                       NULL,
  `categories_id`             INT                                       NULL,
  `subcategories_id`          INT                                       NULL,
  `created`                   TIMESTAMP                                 NULL          DEFAULT now(),
  `modified`                  TIMESTAMP                                 NULL          DEFAULT now(),
  PRIMARY KEY (`id`),
  INDEX `fk_from_idx` (`from_accounts_accno` ASC),
  INDEX `fk_to_idx` (`to_accounts_accno` ASC),
  INDEX `fk_categories_idx` (`categories_id` ASC),
  INDEX `fk_subcategories_idx` (`subcategories_id` ASC),
  CONSTRAINT `fk_from`
  FOREIGN KEY (`from_accounts_accno`)
  REFERENCES `accounts` (`accno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_to`
  FOREIGN KEY (`to_accounts_accno`)
  REFERENCES `accounts` (`accno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_categories`
  FOREIGN KEY (`categories_id`)
  REFERENCES `categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subcategories`
  FOREIGN KEY (`subcategories_id`)
  REFERENCES `subcategories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);