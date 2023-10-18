INSERT INTO `customer` (`name`,`email`,`mobile_number`,`created_dt`, `created_by`)
 VALUES ('Eazy Bytes','tutor@eazybytes.com','9876548337',CURDATE(), 'sonali');
 
INSERT INTO `account` (`customer_id`, `account_number`, `account_type`, `branch_address`, `created_dt`, `created_by`)
 VALUES (1, 186576453, 'Savings', '123 Main Street, New York', CURDATE(), 'sonali');