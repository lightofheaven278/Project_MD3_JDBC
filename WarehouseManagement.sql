create database warehouse_management;
use warehouse_management;

create table Product(
Product_Id char(5) primary key,
Product_Name varchar(150) not null,
Manufacturer varchar(200) not null,
Created date default(current_date()),
Batch smallint not null,
Quantity int not null default(0),
Product_Status bit default(1)
);

create table Employee(
Emp_Id char(5) primary key,
Emp_Name varchar(100) not null unique,
Birth_Of_Date date,
Email varchar(100) not null,
Phone varchar(100) not null,
Address text not null,
emp_status int not null
);

create table Account(
acc_id int primary key auto_increment,
user_name varchar(30) not null unique,
password varchar(30) not null,
permission bit default(1),
emp_id char(5) not null unique,
foreign key(emp_id) references Employee(emp_id),
acc_status bit default(1)
);

create table Bill(
bill_id bigint primary key auto_increment,
bill_code varchar(10) not null,
bill_type bit not null,
emp_id_created char(5) not null,
foreign key(emp_id_created) references Employee(emp_id),
created date default(current_date()),
emp_id_auth char(5) not null,
foreign key(emp_id_auth) references Employee(emp_id),
auth_date date default(current_date()),
bill_status smallint not null default(0)
);

create table Bill_Detail(
bill_detail_id bigint primary key auto_increment,
bill_id bigint not null,
foreign key(bill_id) references Bill(bill_id),
product_id char(5) not null,
foreign key(product_id) references Product(product_id),
quantity int not null check(quantity > 0),
price float not null check(price > 0)
);

/*=========================Product Procedures=========================*/
-- ok
DELIMITER //
create procedure get_original_products()
begin
select Product_Id, Product_Name, Manufacturer, Created, Batch, Quantity, Product_Status 
from product;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_all_products(
dataPerPage int,
startData int
)
begin
select Product_Id, Product_Name, Manufacturer, Created, Batch, Quantity, Product_Status 
from product
limit dataPerPage offset startData;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure add_product(
Product_Id_in char(5), 
Product_Name_in varchar(150),
Manufacturer_in varchar(200),
Created_in date,
Batch_in smallint,
Quantity_in int,
Product_Status_in bit 
)
begin
insert into Product
value(Product_Id_in, Product_Name_in, Manufacturer_in, Created_in, Batch_in, Quantity_in, Product_Status_in);
end //
DELIMITER ;
call add_product('P0001', 'sport shoes', 'nike', '2023-05-02', 1, 100, 1);
call add_product('P0002', 'military shoes', 'american god', '2023-05-02', 1, 100, 1);
call add_product('P0003', 'business coat', 'english fashion', '2023-05-02', 1, 100, 1);
call add_product('P0004', 'business suit', 'english fashion', '2023-05-02', 1, 100, 1);
call add_product('P0005', 'business shoes', 'english fashion', '2023-05-02', 1, 100, 1);
call add_product('P0006', 'T-shirt', 'english fashion', '2023-05-02', 1, 100, 1);
call add_product('P0007', 'British cap', 'english fashion', '2023-05-02', 1, 100, 1);
call add_product('P0008', 'Leather coat', 'english fashion', '2023-05-02', 1, 100, 1);
call add_product('P0009', 'rain coat', 'english fashion', '2023-05-02', 1, 100, 1);
call add_product('P0010', 'Business bag', 'english fashion', '2023-05-02', 1, 100, 1);
call add_product('P0011', 'hiking bag', 'nike', '2023-05-02', 1, 100, 1);
call add_product('P0012', 'men underwear', 'uniquilo', '2023-05-02', 1, 100, 1);

-- ok
DELIMITER //
create procedure delete_product(
Product_Id_in char(5)
)
begin
delete from Product
where Product_Id = Product_Id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_product_by_id(
Product_Id_in char(5)
)
begin
select Product_Id, Product_Name, Manufacturer, Created, Batch, Quantity, Product_Status 
from product
where Product_Id = Product_Id_in;
end //
DELIMITER ;

DELIMITER //
create procedure update_product(
Product_Id_in char(5), 
Product_Name_in varchar(150),
Manufacturer_in varchar(200),
Created_in date,
Batch_in smallint,
Quantity_in int,
Product_Status_in bit 
)
begin
update Product
set Product_Name = Product_Name_in,
 Manufacturer = Manufacturer_in,
 Created = Created_in,
 Batch = Batch_in,
 Quantity = Quantity_in,
 Product_Status =  Product_Status_in
 where Product_Id = Product_Id_in;
end //
DELIMITER ;

DELIMITER //
create procedure search_product(
product_name_in varchar(100)
)
begin
select Product_Id, Product_Name, Manufacturer, Created, Batch, Quantity, Product_Status 
from product
where product_name = product_name_in;
end //
DELIMITER ;

DELIMITER //
create procedure update_product_status(
Product_Id_in char(5), 
Product_Status_in bit 
)
begin
update Product
set Product_Status = Product_Status_in
where Product_Id = Product_Id_in;
end //
DELIMITER ;

/*==============================Employee Procedure===========================*/

-- ok
DELIMITER //
create procedure get_all_employees(
)
begin
select emp_id, emp_name, birth_of_date, email, phone, address, emp_status
from Employee;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_limit_employees(
dataPerPage int,
startData int
)
begin
select emp_id, emp_name, birth_of_date, email, phone, address, emp_status
from Employee
limit dataPerPage offset startData;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_employee_by_id(
emp_id_in char(5)
)
begin
select emp_id, emp_name, birth_of_date, email, phone, address, emp_status
from Employee
where emp_id = emp_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure add_employee(
emp_id_in char(5), 
emp_name_in varchar(100), 
birth_of_date_in date,
email_in varchar(100),
phone_in varchar(100),
address_in text,
emp_status_in int
)
begin
insert into Employee
value(emp_id_in, emp_name_in, birth_of_date_in, email_in, phone_in, address_in, emp_status_in);
end //
DELIMITER ;

call add_employee('EP001', 'Cao Minh Chau', '1994-05-06', 'chau@gmail.com', '0985654256', 'thai binh', '0');
call add_employee('EP002', 'Cao Thanh Tung', '1994-05-06', 'tung@gmail.com', '0985654256', 'thai binh', '0');
call add_employee('EP003', 'cao van hinh', '1986-05-06', 'hinh@gmail.com', '0985654256', 'thai binh', '0');
call add_employee('EP004', 'cao viet hung', '1997-05-06', 'hung@gmail.com', '0985654256', 'thai binh', '0');

DELIMITER //
create procedure update_employee(
emp_id_in char(5), 
emp_name_in varchar(100), 
birth_of_date_in date,
email_in varchar(100),
phone_in varchar(100),
address_in text,
emp_status_in int
)
begin
update Employee
set emp_name = emp_name_in, 
birth_of_date = birth_of_date_in,
email = email_in,
phone = phone_in,
address = address_in,
emp_status = emp_status_in
where emp_id = emp_id_in;
end //
DELIMITER ;

DELIMITER //
create procedure update_employee_status(
emp_id_in char(5), 
emp_status_in int
)
begin
update Employee
set emp_status = emp_status_in
where emp_id = emp_id_in;
end //
DELIMITER ;

drop procedure search_employee;
DELIMITER //
create procedure search_employee(
emp_name_in varchar(100)
)
begin
select emp_id, emp_name, birth_of_date, email, phone, address, emp_status
from Employee
where emp_name like concat('%', emp_name_in, '%');
end //
DELIMITER ;

/*===========================Account Procedure============================*/

-- ok
DELIMITER //
create procedure get_all_accounts()
begin
select acc_id, user_name, password, permission, emp_id, acc_status
from Account;
end //
DELIMITER ;

DELIMITER //
create procedure get_emp_non_acc()
begin
select emp_id, emp_name, birth_of_date, email, phone, address, emp_status
from Employee
where emp_id not in (select emp.emp_id from Employee as emp inner join Account as acc on emp.emp_id = acc.emp_id);
end //
DELIMITER ;

DELIMITER //
create procedure get_account_by_id(
acc_id_in int
)
begin
select acc_id, user_name, password, permission, emp_id, acc_status
from Account
where acc_id = acc_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_account_by_user_name(
user_name_in varchar(30)
)
begin
select acc_id, user_name, password, permission, emp_id, acc_status
from Account
where user_name = user_name_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure add_account(
acc_id_in int,
user_name_in varchar(30),
password_in varchar(30),
permission_in bit,
emp_id_in char(5),
acc_status_in bit
)
begin
insert into Account
value(acc_id_in, user_name_in, password_in, permission_in, emp_id_in, acc_status_in);
end //
DELIMITER ;
call add_account(1, 'lightofheaven', '1234', 0, 'EP003',1);
call add_account(2, 'hungvipdb', '1235', 1, 'EP004',1);
call add_account(3, 'caominhchau', '12345', 0, 'EP001',1);
call add_account(4, 'caothanhtung', '12345', 0, 'EP002',1);

-- ok
DELIMITER //
create procedure update_account(
acc_id_in int,
user_name_in varchar(30),
password_in varchar(30),
permission_in bit,
emp_id_in char(5),
acc_status_in bit
)
begin
update Account
set user_name = user_name_in,
password = password_in,
permission = permission_in,
emp_id = emp_id_in,
acc_status = acc_status_in
where acc_id = acc_id_in;
end //
DELIMITER ;
call update_account_status('lightofheaven',1);

DELIMITER //
create procedure update_account_status_to_block(
emp_id_in char(5)
)
begin
update Account
set acc_status = 0
where emp_id = emp_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure update_account_status(
user_name_in varchar(30),
acc_status_in bit
)
begin
update Account
set acc_status = acc_status_in
where user_name = user_name_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure search_account(
keyword varchar(100)
)
begin
select acc.acc_id, acc.user_name, acc.password, acc.permission, acc.emp_id, acc.acc_status
from account as acc
inner join Employee as emp on acc.emp_id = emp.emp_id
where acc.user_name = keyword or emp.emp_name = keyword ;
end //
DELIMITER ;

/*====================Bill Common==================*/

DELIMITER //
create procedure get_all_receipt_and_bill()
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill;
end //
DELIMITER ;

DELIMITER //
create procedure get_all_receipt_and_bill_detail() 
begin 
select bd.bill_detail_id, bd.bill_id, bd.product_id, bd.quantity, bd.price
from Bill_Detail as bd;
end //
DELIMITER ;

/*====================Receipt Procedure=====================*/

-- ok
DELIMITER //
create procedure get_all_receipt()
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill
where bill_type = 1;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_receipt_by_id(
bill_id_in bigint
)
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill
where bill_type = 1 and bill_id = bill_id_in;
end //
DELIMITER ;


-- ok
DELIMITER //
create procedure create_receipt(
bill_id_in bigint,
bill_code_in varchar(10),
emp_id_created_in char(5),
created_in date,
emp_id_auth_in char(5),
auth_date_in date,
bill_status_in smallint
) 
begin
insert into bill(bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status)
value(bill_id_in, bill_code_in, 1, emp_id_created_in, created_in, emp_id_auth_in, auth_date_in, bill_status_in);
end //
DELIMITER ;
call create_receipt(100, 'RE001', 'EP001', '2023-02-15', 'EP001', '2023-02-16', 0);
call create_receipt(101, 'RE002', 'EP001', '2023-02-15', 'EP001', '2023-02-16', 0);
call create_receipt(102, 'RE003', 'EP002', '2023-02-18', 'EP002', '2023-02-20', 0);
call create_receipt(103, 'RE004', 'EP002', '2023-02-18', 'EP002', '2023-02-20', 0);

-- ok
DELIMITER //
create procedure update_receipt(
bill_id_in bigint,
bill_code_in varchar(10),
emp_id_created_in char(5),
created_in date,
emp_id_auth_in char(5),
auth_date_in date,
bill_status_in smallint
) 
begin
update bill
set bill_code = bill_code_in,
 emp_id_created = emp_id_created_in,
 created = created_in,
 emp_id_auth = emp_id_auth_in,
 auth_date = auth_date_in,
 bill_status = bill_status_in
where bill_id = bill_id_in and (bill_status = 0 or bill_status = 1);
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_all_receipt_detail() 
begin 
select bd.bill_detail_id, bd.bill_id, bd.product_id, bd.quantity, bd.price
from Bill_Detail as bd
inner join bill as b on bd.bill_id = b.bill_id
where b.bill_type = 1;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure create_receipt_detail(
bill_detail_id_in bigint,
bill_id_in varchar(10),
product_id_in char(5),
quantity_in int,
price_in float
) 
begin
insert into Bill_Detail(bill_detail_id, bill_id, product_id, quantity, price)
value(bill_detail_id_in, bill_id_in, product_id_in, quantity_in, price_in);
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_receipt_detail_by_id(
bill_id_in varchar(10)
) 
begin 
select bill_detail_id, bill_id, product_id, quantity, price
from Bill_Detail
where bill_id = bill_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure update_receipt_detail(
bill_detail_in bigint,
bill_id_in bigint,
product_id_in char(5),
quantity_in int,
price_in float
) 
begin
update bill_detail
set product_id = product_id_in,
quantity = quantity_in,
price = price_in
where bill_id = bill_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure delete_receipt(
bill_id_in varchar(10)
) 
begin
delete from Bill
where bill_id = bill_id_in;
end //
DELIMITER ;
call delete_receipt(105);

-- ok
DELIMITER //
create procedure approve_receipt(
bill_id_in bigint
) 
begin
update bill_detail as bd
inner join bill as b on bd.bill_id = b.bill_id
inner join Product as p on p.product_id = bd.product_id
set p.quantity = p.quantity + bd.quantity,
	b.bill_status = 2
where bd.bill_id = bill_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure search_receipt(
bill_id_in bigint,
bill_code_in varchar(10)
) 
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill
where bill_id = bill_id_in or bill_code = bill_code_in;
end //
DELIMITER ;

/*==============================Bill Procedure=============================*/


-- ok
DELIMITER //
create procedure get_all_bills()
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill
where bill_type = 0;
end //
DELIMITER ;

-- ok

DELIMITER //
create procedure get_all_bill_detail() 
begin 
select bd.bill_detail_id, b.bill_id, bd.product_id, bd.quantity, bd.price
from Bill_Detail as bd
inner join bill as b on bd.bill_id = b.bill_id
where b.bill_type = 0;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_bill_by_id(
bill_id_in bigint
)
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill
where bill_type = 0 and bill_id = bill_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure create_bill(
bill_id_in bigint,
bill_code_in varchar(10),
emp_id_created_in char(5),
created_in date,
emp_id_auth_in char(5),
auth_date_in date,
bill_status_in smallint
) 
begin
insert into bill
value(bill_id_in, bill_code_in, 0, emp_id_created_in, created_in, emp_id_auth_in, auth_date_in, bill_status_in);
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure create_bill_detail(
bill_detail_id_in bigint,
bill_id_in varchar(10),
product_id_in char(5),
quantity_in int,
price_in float
) 
begin
insert into Bill_Detail(bill_detail_id, bill_id, product_id, quantity, price)
value(bill_detail_id_in, bill_id_in, product_id_in, quantity_in, price_in);
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure update_bill(
bill_id_in bigint,
bill_code_in varchar(10),
emp_id_created_in char(5),
created_in date,
emp_id_auth_in char(5),
auth_date_in date,
bill_status_in smallint
) 
begin
update bill
set bill_code = bill_code_in,
 emp_id_created = emp_id_created_in,
 created = created_in,
 emp_id_auth = emp_id_auth_in,
 auth_date = auth_date_in,
 bill_status = bill_status_in
where bill_id = bill_id_in and bill_status <> 2;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure get_bill_detail_by_id(
bill_id_in varchar(10)
) 
begin 
select bill_detail_id, bill_id, product_id, quantity, price
from Bill_Detail
where bill_id = bill_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure update_bill_detail(
bill_id_in varchar(10),
product_id char(5),
quantity_in int,
price_in float
) 
begin
update bill_detail
set product_id = product_id_in,
quantity = quantity_in,
price = price_in
where bill_id = bill_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure approve_bill(
bill_id_in bigint
) 
begin
update bill_detail as bd
inner join bill as b on bd.bill_id = b.bill_id
inner join Product as p on p.product_id = bd.product_id
set p.quantity = if(p.quantity >= bd.quantity, p.quantity - bd.quantity, 
concat('Bill approval failed due to insufficient quantity.', ' Available quantity of ', p.product_name , ' is ',  p.quantity)),
	b.bill_status = 2
where bd.bill_id = bill_id_in;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure search_bill(
bill_id_in bigint,
bill_code_in varchar(10)
) 
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill
where bill_id = bill_id_in or bill_code = bill_code_in;
end //
DELIMITER ;

/*====================================Report Procedure===============================*/

-- ok
-- ????? bill status
DELIMITER //
create procedure statistics_expenses_by_date_time() 
begin
select b.auth_date, sum(bd.price*bd.quantity) as Expenses
from Bill_Detail as bd
inner join bill as b on bd.bill_id = b.bill_id
where b.bill_type = 1 and bill_status = 2
group by b.auth_date;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure statistics_expenses_in_time_interval(
date_in date,
date_out date
) 
begin
select b.auth_date, sum(bd.price*bd.quantity) as Expenses
from Bill_Detail as bd
inner join bill as b on bd.bill_id = b.bill_id
where  b.auth_date between date_in and date_out and b.bill_type = 1
group by b.auth_date;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure statistics_Revenue_by_date_time() 
begin
select b.auth_date, sum(bd.price*bd.quantity) as revenue
from Bill_Detail as bd
inner join bill as b on bd.bill_id = b.bill_id
where b.bill_type = 0
group by b.auth_date;
end //
DELIMITER ;

-- ok
DELIMITER //
create procedure statistics_revenue_in_time_interval(
date_in date,
date_out date
) 
begin
select b.auth_date, sum(bd.price*bd.quantity) as revenue
from Bill_Detail as bd
inner join bill as b on bd.bill_id = b.bill_id
where b.auth_date between date_in and date_out and b.bill_type = 0
group by b.auth_date;
end //
DELIMITER ;

--ok
DELIMITER //
create procedure statistics_number_of_emp_by_status() 
begin
select emp_status, count(emp_status) as NumberOfEmp
from Employee 
group by emp_status;
end //
DELIMITER ;


DELIMITER //
create procedure statistics_most_import_product(
date_in date,
date_out date
) 
begin
select p.product_name, sum(bd.quantity) as total_quantity
from bill_detail as bd
inner join product as p on p.product_id = bd.product_id
inner join bill as b on b.bill_id = bd.bill_id
where b.bill_type = 1 and b.bill_status = 2 and b.auth_date between date_in and date_out
group by p.product_name
order by total_quantity desc
limit 1;
end //
DELIMITER ;


DELIMITER //
create procedure statistics_least_import_product(
date_in date,
date_out date
) 
begin
select p.product_name, sum(bd.quantity) as total_quantity
from bill_detail as bd
inner join product as p on p.product_id = bd.product_id
inner join bill as b on b.bill_id = bd.bill_id
where b.bill_type = 1 and b.bill_status = 2 and b.auth_date between date_in and date_out
group by p.product_name
order by total_quantity
limit 1;
end //
DELIMITER ;


DELIMITER //
create procedure statistics_most_export_product(
date_in date,
date_out date
) 
begin
select p.product_name, sum(bd.quantity) as total_quantity
from bill_detail as bd
inner join product as p on p.product_id = bd.product_id
inner join bill as b on b.bill_id = bd.bill_id
where b.bill_type = 0 and b.bill_status = 2 and b.auth_date between date_in and date_out
group by p.product_name
order by total_quantity desc
limit 1;
end //
DELIMITER ;

drop procedure statistics_least_export_product;
DELIMITER //
create procedure statistics_least_export_product(
date_in date,
date_out date
) 
begin
select p.product_name, sum(bd.quantity) as total_quantity
from bill_detail as bd
inner join product as p on p.product_id = bd.product_id
inner join bill as b on b.bill_id = bd.bill_id
where b.bill_type = 0 and b.bill_status = 2 and b.auth_date between date_in and date_out
group by p.product_name
order by total_quantity
limit 1;
end //
DELIMITER ;

/*============================User Procedure==========================*/

DELIMITER //
create procedure get_all_receipt_by_status(
user_name_in varchar(30)
)
begin
select b.bill_id, b.bill_code, b.bill_type, b.emp_id_created, b.created, b.emp_id_auth, b.auth_date, b.bill_status
from bill as b
inner join account as a on b.emp_id_created = a.emp_id
where b.bill_type = 1 and a.user_name = user_name_in
order by bill_status;
end //
DELIMITER ;

DELIMITER //
create procedure get_emp_id_by_user_name(
user_name_in varchar(30)
)
begin
select e.emp_id
from Account as a
inner join Employee as e on a.emp_id = e.emp_id
where a.user_name = user_name_in;
end //
DELIMITER ;

DELIMITER //
create procedure get_all_bills_by_status(
user_name_in varchar(30)
)
begin
select b.bill_id, b.bill_code, b.bill_type, b.emp_id_created, b.created, b.emp_id_auth, b.auth_date, b.bill_status
from bill as b
inner join account as a on b.emp_id_created = a.emp_id
where b.bill_type = 0 and a.user_name = user_name_in
order by bill_status;
end //
DELIMITER ;


DELIMITER //
create procedure search_receipt_by_user(
bill_id_in bigint
) 
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill
where bill_id = bill_id_in and bill_type = 1;
end //
DELIMITER ;

DELIMITER //
create procedure search_bill_by_user(
bill_id_in bigint
) 
begin
select bill_id, bill_code, bill_type, emp_id_created, created, emp_id_auth, auth_date, bill_status
from bill
where bill_id = bill_id_in and bill_type = 0;
end //
DELIMITER ;
