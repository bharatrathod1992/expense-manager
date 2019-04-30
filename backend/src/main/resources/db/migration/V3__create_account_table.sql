CREATE TABLE `accounts` (
  `accno`         INT(4)                            NOT NULL,
  `version`       INT                               NOT NULL    DEFAULT 0,
  `name`          VARCHAR(45)                       NOT NULL,
  `account_type`  ENUM('BANK', 'CASH', 'WALLET')    NOT NULL,
  `amount`        DECIMAL(10,2)                     NOT NULL,
  `created`       TIMESTAMP                         NOT NULL    DEFAULT NOW(),
  `modified`      TIMESTAMP                         NOT NULL    DEFAULT NOW(),
  PRIMARY KEY (`accno`));
