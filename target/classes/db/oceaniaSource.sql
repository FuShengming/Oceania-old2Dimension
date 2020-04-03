create database if not exists oceania;
use oceania;
drop table if exists vertex_label;
drop table if exists edge_label;
drop table if exists domain_label;
drop table if exists work_space;
drop table if exists code;
drop table if exists user;





create table if not exists user(
	id int auto_increment primary key,
	name varchar(32) NOT NULL,
	pwd varchar(16) NOT NULL
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists code(
    id int auto_increment primary key,
	user_id int NOT NULL,
	code_id int NOT NULL,
	name varchar(8192),
	numOfVertices int,
	numOfEdges int,
	numOfDomains int,
	CONSTRAINT code_fk_user_id FOREIGN KEY(user_id) REFERENCES user(id)  ON DELETE Cascade,
	KEY code_identity(code_id)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists vertex_label(
id int auto_increment primary key,
	user_id int NOT NULL,
	code_id int NOT NULL,
	vertex_id int NOT NULL,
	content varchar(8192),
	KEY vertex_identity(vertex_id),
 	CONSTRAINT vertex_fk_user_identity FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE Cascade,
 	CONSTRAINT vertex_fk_code_id FOREIGN KEY(code_id) REFERENCES code(code_id)  ON DELETE Cascade
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table if not exists edge_label(
id int auto_increment primary key,
	user_id int NOT NULL,
	code_id int NOT NULL,
	edge_id int NOT NULL,
	content varchar(8192),
	KEY edge_identity(edge_id),
 	CONSTRAINT edge_fk_user_identity FOREIGN KEY(user_id) REFERENCES user(id)  ON DELETE Cascade,
 	CONSTRAINT edge_fk_code_id FOREIGN KEY(code_id) REFERENCES code(code_id)  ON DELETE Cascade
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table if not exists domain_label(
    id int auto_increment primary key,
	user_id int NOT NULL,
	code_id int NOT NULL,
	first_edge_id int NOT NULL,
	num_of_vertices int NOT NULL,
	content varchar(8192),
	KEY domain_identity(first_edge_id,num_of_vertices),
 	CONSTRAINT domain_fk_user_identity FOREIGN KEY(user_id) REFERENCES user(id)  ON DELETE Cascade,
 	CONSTRAINT domain_fk_code_id FOREIGN KEY(code_id) REFERENCES code(code_id)  ON DELETE Cascade
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists work_space(
    id int auto_increment primary key,
	user_id int NOT NULL,
	code_id int NOT NULL,
	work_space_date timestamp default CURRENT_TIMESTAMP,
	closeness double default 0 NOT NULL,
	CONSTRAINT work_fk_user_identity FOREIGN KEY(user_id) REFERENCES user(id)  ON DELETE Cascade,
 	CONSTRAINT work_fk_code_id FOREIGN KEY(code_id) REFERENCES code(code_id)  ON DELETE Cascade
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;

