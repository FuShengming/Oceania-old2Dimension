create database if not exists oceania;
use oceania;
drop table if exists vertex_label;
drop table if exists edge_label;
drop table if exists domain_label;
drop table if exists work_space;
drop table if exists code;
drop table if exists user_authority;
drop table if exists authority;
drop table if exists `user`;





create table if not exists `user`(
	id int auto_increment primary key,
	name varchar(32) NOT NULL,
	pwd varchar(64) NOT NULL
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;

BEGIN;
INSERT INTO `user` (name, pwd) VALUES ('oceania_admin', '$2a$10$kYTlmG/7.TOgQwE/AimJJu7qyuflc31joGCaz70tESA6xYOYu8rw2');
COMMIT;

create table if not exists authority(
	id int auto_increment primary key,
	name varchar(32) NOT NULL
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;

BEGIN;
INSERT INTO `authority` VALUES ('1', 'ROLE_ADMIN'), ('2', 'ROLE_USER');
COMMIT;

create table if not exists user_authority(
	user_id int NOT NULL,
	authority_id int NOT NULL,
	KEY `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id`),
    KEY `FKpqlsjpkybgos9w2svcri7j8xy` (`user_id`),
    CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
    CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;

BEGIN;
INSERT INTO `user_authority` VALUES (1, 1);
COMMIT;

create table if not exists code(
    id int auto_increment primary key,
	user_id int NOT NULL,
	name varchar(8192),
	num_of_vertices int,
	num_of_edges int,
	num_of_domains int,
	is_default int,
	CONSTRAINT code_fk_user_id FOREIGN KEY(user_id) REFERENCES user(id)  ON DELETE Cascade
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table if not exists code
(
    id              int auto_increment primary key,
    user_id         int NOT NULL,
    name            varchar(8192),
    num_of_vertices int,
    num_of_edges    int,
    num_of_domains  int,
    is_default      int,
    CONSTRAINT code_fk_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table if not exists vertex_label
(
    id        int auto_increment primary key,
    user_id   int NOT NULL,
    code_id   int NOT NULL,
    vertex_id int NOT NULL,
    content   varchar(8192),
    title     varchar(255),
    KEY vertex_identity (vertex_id),
    CONSTRAINT vertex_fk_user_identity FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE Cascade,
    CONSTRAINT vertex_fk_code_id FOREIGN KEY (code_id) REFERENCES code (id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;



create table if not exists edge_label
(
    id      int auto_increment primary key,
    user_id int NOT NULL,
    code_id int NOT NULL,
    edge_id int NOT NULL,
    content varchar(8192),
    title   varchar(255),
    KEY edge_identity (edge_id),
    CONSTRAINT edge_fk_user_identity FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE Cascade,
    CONSTRAINT edge_fk_code_id FOREIGN KEY (code_id) REFERENCES code (id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


create table if not exists domain_label
(
    id              int auto_increment primary key,
    user_id         int NOT NULL,
    code_id         int NOT NULL,
    first_edge_id   int NOT NULL,
    num_of_vertices int NOT NULL,
    content         varchar(8192),
    title           varchar(255),
    KEY domain_identity (first_edge_id, num_of_vertices),
    CONSTRAINT domain_fk_user_identity FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE Cascade,
    CONSTRAINT domain_fk_code_id FOREIGN KEY (code_id) REFERENCES code (id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table if not exists work_space
(
    id              int auto_increment primary key,
    user_id         int                 NOT NULL,
    code_id         int                 NOT NULL,
    work_space_date timestamp default CURRENT_TIMESTAMP,
    closeness       double    default 0 NOT NULL,
    cy_info         mediumtext          NULL,
    CONSTRAINT work_fk_user_identity FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE Cascade,
    CONSTRAINT work_fk_code_id FOREIGN KEY (code_id) REFERENCES code (id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

