drop database if exists oceania;
create database if not exists oceania;
use oceania;
drop table if exists vertex_label;
drop table if exists edge_label;
drop table if exists domain_label;
drop table if exists work_space;
drop table if exists team_members;
drop table if exists team_code;
drop table if exists team;
drop table if exists code;
drop table if exists user_authority;
drop table if exists authority;
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

  create table if not exists team
(
    id              int auto_increment primary key,
    group_name       varchar(255) NOT NULL,
    description      varchar(8192) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table if not exists code
(
    id              int auto_increment primary key,
    user_id         int not null,
    name            varchar(8192),
    num_of_vertices int,
    num_of_edges    int,
    num_of_domains  int,
    is_default      int,

    CONSTRAINT code_fk_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE Cascade

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


  create table if not exists team_code
(
    id              int auto_increment primary key,
    group_id         int not null,
    code_id          int not null,

    CONSTRAINT group_code_fk_user_id FOREIGN KEY (group_id) REFERENCES team (id) ON DELETE Cascade,
    CONSTRAINT group_code_fk_code_id FOREIGN KEY (code_id) REFERENCES code (id) ON DELETE Cascade


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




create table if not exists team_member
(
    id              int auto_increment primary key,
    group_id        int  NOT NULL,
    user_id         int  NOT NULL,
    is_leader       int  NOT NULL,
     CONSTRAINT group_members_fk_user_identity FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE Cascade,
    CONSTRAINT group_members_fk_group_id FOREIGN KEY (group_id) REFERENCES team (id) ON DELETE Cascade

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;




create table if not exists team_task
(
    id              int auto_increment primary key,
    group_id        int  NOT NULL,
    name            varchar(255) NOT NULL,
    description     varchar(8192) NOT NULL,
    label           varchar(255) NOT NULL,
    state           int NOT NULL,
    start_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT group_task_fk_group_id FOREIGN KEY (group_id) REFERENCES team (id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

  create table if not exists team_task_assignment
(
    id              int auto_increment primary key,
    group_id        int  NOT NULL,
    task_id         int  NOT NULL,
    user_id         int  NOT NULL,
    CONSTRAINT group_task_assignment_fk_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE Cascade,
    CONSTRAINT group_task_assignment_fk_task_id FOREIGN KEY (task_id) REFERENCES team_task (id) ON DELETE Cascade,
    CONSTRAINT group_task_assignment_fk_group_id FOREIGN KEY (group_id) REFERENCES team (id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


create table if not exists team_announcement
(
    id              int auto_increment primary key,
    group_id        int  NOT NULL,
    title           varchar(255) NOT NULL,
    content     varchar(8192) NOT NULL,
    release_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT team_announcement_fk_group_id FOREIGN KEY (group_id) REFERENCES team (id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table if not exists team_announcement_read
(
    id          int auto_increment primary key,
    announcement_id  int NOT NULL,
    user_id     int NOT NULL,
    has_read    int NOT NULL,
     CONSTRAINT team_announcement_read_fk_user_id FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE Cascade,
     CONSTRAINT team_announcement_read_fk_annoucement_id FOREIGN KEY (announcement_id) REFERENCES team_announcement(id) ON DELETE Cascade
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table if not exists invitation_message
(
    id          int auto_increment primary key,
    group_id  int NOT NULL,
    user_id     int NOT NULL,
    inviter_id     int NOT NULL,
    has_read    int NOT NULL,
    state       int NOT NULL,
    CONSTRAINT invitation_message_fk_user_id FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE Cascade,
    CONSTRAINT invitation_message_fk_inviter_id FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE Cascade,
    CONSTRAINT invitation_message_fk_group_id FOREIGN KEY (group_id) REFERENCES team(id) ON DELETE Cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table if not exists chat_message(
    id   int auto_increment primary key,
    sender_id INT NOT NULL,
    recipient_id INT NOT NULL,
    send_date timestamp NOT NULL,
    has_read INT NOT NULL,
    CONSTRAINT chat_message_fk_sender_id FOREIGN KEY (sender_id) REFERENCES user(id) ON DELETE Cascade,
    CONSTRAINT chat_message_fk_recipient_id FOREIGN KEY (recipient_id) REFERENCES user(id) ON DELETE Cascade
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table if not exists chat_workspace(
    id  int auto_increment primary key,
    user_id int not null,
    chatting_list varchar(12800) not null,
    date timestamp not null,
    CONSTRAINT chat_workspace_fk_sender_id FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE Cascade
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


