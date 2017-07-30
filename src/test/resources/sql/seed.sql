use expense_manager_test;

set FOREIGN_KEY_CHECKS =0;
TRUNCATE TABLE Subcategories;
TRUNCATE TABLE Categories;
TRUNCATE TABLE Accounts;
set FOREIGN_KEY_CHECKS =1;

INSERT INTO categories (name) VALUES ("Food"),
                                     ("Living");
INSERT INTO subcategories (name,categories_id) VALUES ("Chapati",1),
  ("Rice",1),
  ("Pizza",1),
  ("Oid",2),
  ("Onion",2);

INSERT INTO accounts (accno,name,account_type,amount) VALUES (1267,"SBI","BANK",555.50);