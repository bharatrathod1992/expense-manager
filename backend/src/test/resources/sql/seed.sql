use expense_manager_test;

set FOREIGN_KEY_CHECKS =0;
TRUNCATE TABLE transactions;
TRUNCATE TABLE subcategories;
TRUNCATE TABLE categories;
TRUNCATE TABLE accounts;
set FOREIGN_KEY_CHECKS =1;

INSERT INTO categories (name) VALUES ("Food"),
                                     ("Living");
INSERT INTO subcategories (name,categories_id) VALUES ("Chapati",1),
                                                      ("Rice",1),
                                                      ("Pizza",1),
                                                      ("Oid",2),
                                                      ("Onion",2);

INSERT INTO accounts (accno,name,account_type,amount) VALUES (1267,"SBI","BANK",555.50),
                                                             (5555,"CASH","CASH",200.00);

INSERT INTO transactions (transaction_type,amount,to_accounts_accno) VALUES ("INCOME",5000,1267);
INSERT INTO transactions (transaction_type,amount,from_accounts_accno,categories_id,subcategories_id) VALUES ("EXPENSE",5000,5555,2,1);
INSERT INTO transactions (transaction_type,amount,from_accounts_accno,to_accounts_accno) VALUES ("TRANSFER",5000,1267,5555);