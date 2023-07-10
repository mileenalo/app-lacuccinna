--Criação da Tabela de Menu
CREATE TABLE menu (
  id serial PRIMARY KEY,
  description VARCHAR(255),
  title VARCHAR(255),
  url VARCHAR(255),
  price DECIMAL(10,2)
);

--Popula a tabela de menu
Insert into menu (description, title, price, url) values ('Molho feito a base de tomates frescos e temperos','Macarrão ao Molho Sugo', 28, 'R.drawable.molho_sugo') 
Insert into menu (description, title, price, url) values ('Parmesão, provolone, mozzarella e gorgonzola','Nhoque 4 Queijos', 34, 'R.drawable.nhoque_4_queijos');
Insert into menu (description, title, price, url) values ('Cogumelos Fughi, creme de leite e temperos frescos','Nhoque ao Molho Fughi', 34, 'R.drawable.nhoque_fughi')
Insert into menu (description, title, price, url) values ('Ovos, bacon e queijo parmesão ralado','Macarrão Carbonara', 32, 'R.drawable.carbonara')
Insert into menu (description, title, price, url) values ('Carne bovina moída, tomates, cebolas e demais temperos para compor um delicioso molho','Macarrão à Bolonhesa', 32, 'R.drawable.bolonhesa')
Insert into menu (description, title, price, url) values ('Creme de leite fresco, queijo parmesão ralado e manteiga','Fetuccine Alfredo', 32, 'R.drawable.fetuccine')

--Cria a tabela de pedidos
create table pedido (
	id serial primary key,
	mesa varchar(5),
	dtpedido varchar(8),
	hrpedido varchar(5),
	status varchar(30),
	qtditens int,
	valor float
);

--Cria a tabela de itens do pedido
create table itens_pedido(
	id serial primary key,
	idpedido int,
	idmenu int, 
	qtditem int, 
	valor float,
	obsitem varchar(255)
);