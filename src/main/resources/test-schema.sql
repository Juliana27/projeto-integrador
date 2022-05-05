insert into WAREHOUSE (id, name, region) values (1, 'Tools', 'Doujia');

INSERT INTO "user" (role, id, cpf, last_name, name, password, username) VALUES('ADMIN', 1, NULL, 'ADMIN', 'ADMIN', '$2a$10$81VuVXFi5JmfOdCblgjj0ODqsn11TUXfUEYnm.jInkbUIcd9xx31u', 'ADMIN');

INSERT INTO "user" (role, id, cpf, last_name, name, password, username) VALUES('Supervisor', 2, NULL, 'Supervisor', 'Supervisor', '$2a$10$81VuVXFi5JmfOdCblgjj0ODqsn11TUXfUEYnm.jInkbUIcd9xx31u', 'Supervisor');

INSERT INTO "user" (role, id, cpf, last_name, name, password, username) VALUES('Seller', 3, NULL, 'Seller', 'Seller', '$2a$10$81VuVXFi5JmfOdCblgjj0ODqsn11TUXfUEYnm.jInkbUIcd9xx31u', 'Seller');

INSERT INTO "user" (role, id, cpf, last_name, name, password, username) VALUES('Buyer', 4, NULL, 'Buyer', 'Buyer', '$2a$10$81VuVXFi5JmfOdCblgjj0ODqsn11TUXfUEYnm.jInkbUIcd9xx31u', 'Buyer');

INSERT INTO sector (id, capacity, category, "supervisor_id", warehouse_id) VALUES(1, 1000, 'CONGELADOS', 2, 1);

INSERT INTO inbound_order (id, order_date, sector_id) VALUES(99, '2022-05-04', 1);

INSERT INTO product (id,category,name,price,volume,"seller_id") VALUES
    (1,'CONGELADOS','frango',20.00,1,3);

INSERT INTO batch_stock (id, category, current_quantity, current_temperature, due_date, initial_quantity, initial_temperature, manufacturing_date, manufacturing_time, inbound_order_id, product_id) VALUES(99, 'CONGELADOS', 1, 2, '2025-10-10', 1, 2, '2022-10-10', '20:20:20', 99, 1);

insert into batch_stock (id, category, initial_quantity, current_quantity, initial_temperature, current_temperature, manufacturing_date,
                         manufacturing_time, due_date, product_id, inbound_order_id) values (1, 'CONGELADOS', 10, 10, 9, 9, '2022-04-20', '20:00:00', '2023-10-10', 1, 99);

insert into purchased_order (id, date, status, "buyer_id") values (1,'2022-04-20', 'aberto', 1);

insert into purchased_order (id, date, status, "buyer_id") values (2,'2022-04-20', 'finalizado', 4);

insert into cart (id, quantity, product_id, purchased_order_id) values (1, 10, 1, 1);
