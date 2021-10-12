-- Database: rollsroyce

-- DROP DATABASE rollsroyce;

CREATE DATABASE rollsroyce
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
	create table customers(
		member_id SERIAL PRIMARY KEY,
		first_name VARCHAR(30),
		last_name VARCHAR(30),
		phone_number VARCHAR(13),
		email VARCHAR(50),
		address VARCHAR(50),
		city VARCHAR(30),
		state CHAR(2),
		zip_code char(5)
	);
	create table products(
		product_id SERIAL PRIMARY KEY,
		model_name VARCHAR(30),
		price NUMERIC(8,2),
		stock INT
	);
	create table transaction(
		transaction_id SERIAL PRIMARY KEY, 
  		member_id int references customers(member_id),
 		product_id int references products(product_id),
		dateOfPurchase DATE,
		quantity INT,
		amount NUMERIC(9,2),
 		creditCard int,
 		cardExpire date
	);
	create table orderHistory (
 		member_id int references customers(member_id),
		transaction_id int references transaction (transaction_id),
 		primary key (member_id, transaction_id)
	);
	
	select * from customers order by member_id;
	select * from products order by product_id;
	select * from transaction order by transaction_id;
	select * from orderHistory;
	delete from customers
	
	insert into products(model_name, price, stock)values
	('PHANTOM',889.99,2),
	('CULLINAN',789.99,20),
	('GHOST',689.99,20),
	('WRAITH',998.99,20),
	('DAWN',1069.79,20),
	('BLACK BADGE',969.88,20),
	('X2',36600,20),
	('X3',43000,20),
	('X4',51600,20);
	
	
	